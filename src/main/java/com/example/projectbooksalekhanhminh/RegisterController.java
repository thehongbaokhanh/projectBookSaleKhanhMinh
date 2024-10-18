package com.example.projectbooksalekhanhminh;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField phoneField;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private void handleRegisterButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String phone = phoneField.getText();

        // Kiểm tra số điện thoại
        if (!isValidPhoneNumber(phone)) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Phone number must be 10 digits and start with 0.");
            return;
        }

        // Logic đăng ký ở đây
        if (password.equals(confirmPassword)) {
            // Thực hiện đăng ký
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Welcome " + username + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match.");
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^0\\d{9}$"); // Kiểm tra 10 chữ số bắt đầu bằng 0
    }

    @FXML
    private void handleLoginLink() {
        try {
            // Tải lại trang đăng nhập
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
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
