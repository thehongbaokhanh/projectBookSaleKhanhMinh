package com.example.projectbooksalekhanhminh.Controller;

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
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private void handleRegisterButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        // Kiểm tra thông tin đăng ký
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectbooksalekhanhminh/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String generateUsersID(int numberOfUsers) {
        int totalDigitals = 5;
        String formattedID = String.format("KH%0" + totalDigitals + "d", numberOfUsers + 1);
        return formattedID;
    }


}
