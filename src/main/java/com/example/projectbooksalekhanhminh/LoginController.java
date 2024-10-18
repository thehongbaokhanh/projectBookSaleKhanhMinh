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
    private PasswordField confirmPasswordField; // Thêm trường xác nhận mật khẩu

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + username + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private boolean authenticate(String username, String password) {
        return false; // Implement authentication logic here
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
            passwordField.setPromptText(passwordField.getText());
            passwordField.setText("");
            confirmPasswordField.setPromptText(confirmPasswordField.getText());
            confirmPasswordField.setText("");
        } else {
            passwordField.setText(passwordField.getPromptText());
            confirmPasswordField.setText(confirmPasswordField.getPromptText());
            passwordField.setPromptText("");
            confirmPasswordField.setPromptText("");
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
