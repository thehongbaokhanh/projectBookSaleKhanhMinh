package com.example.projectbooksalekhanhminh;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField showPasswordField; // TextField cho hiển thị mật khẩu

    @FXML
    private TextField showConfirmPasswordField; // TextField cho hiển thị xác nhận mật khẩu

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = showPasswordCheckBox.isSelected() ? showPasswordField.getText() : passwordField.getText();

        if (authenticate(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + username + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private boolean authenticate(String username, String password) {
        return false; // Thực hiện logic xác thực tại đây
    }

    @FXML
    private void handleRegisterLink() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Scene signUpScene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(signUpScene);
            stage.setTitle("Sign Up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowPassword() {
        if (showPasswordCheckBox.isSelected()) {
            // Hiển thị TextField, ẩn PasswordField
            showPasswordField.setText(passwordField.getText());
            showPasswordField.setVisible(true);
            showPasswordField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);

            showConfirmPasswordField.setText(confirmPasswordField.getText());
            showConfirmPasswordField.setVisible(true);
            showConfirmPasswordField.setManaged(true);
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
        } else {
            // Hiển thị lại PasswordField, ẩn TextField
            passwordField.setText(showPasswordField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            showPasswordField.setVisible(false);
            showPasswordField.setManaged(false);

            confirmPasswordField.setText(showConfirmPasswordField.getText());
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
            showConfirmPasswordField.setVisible(false);
            showConfirmPasswordField.setManaged(false);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
