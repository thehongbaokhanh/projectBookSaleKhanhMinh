
package com.example.projectbooksalekhanhminh.Controller;

import com.example.projectbooksalekhanhminh.connection.ConnectionJDBC;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    private TextField passwordTextField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = showPasswordCheckBox.isSelected() ? passwordTextField.getText() : passwordField.getText();
        String confirmPassword = showPasswordCheckBox.isSelected() ? confirmPasswordTextField.getText() : confirmPasswordField.getText();

        if (password.equals(confirmPassword)) {
            if (checkLogin(username, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + username + "!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Passwords do not match.");
        }
    }

    @FXML
    private void toggleShowPassword() {
        if (showPasswordCheckBox.isSelected()) {
            // Show password in TextField and hide PasswordField
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordTextField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);

            confirmPasswordTextField.setText(confirmPasswordField.getText());
            confirmPasswordTextField.setVisible(true);
            confirmPasswordTextField.setManaged(true);
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
        } else {
            // Hide password and show PasswordField
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordTextField.setVisible(false);
            passwordTextField.setManaged(false);

            confirmPasswordField.setText(confirmPasswordTextField.getText());
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
            confirmPasswordTextField.setVisible(false);
            confirmPasswordTextField.setManaged(false);
        }
    }

    public Boolean checkLogin(String username, String password) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleRegisterButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectbooksalekhanhminh/Register.fxml"));
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
}
