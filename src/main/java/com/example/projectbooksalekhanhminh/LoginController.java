package com.example.projectbooksalekhanhminh;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    private int numberOfUsers = 0;

    private User[] users = new User[numberOfUsers];

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField; // Thêm trường xác nhận mật khẩu

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.equals(confirmPassword)) {
            if (checkLogin(username, password)) {

                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + username + "!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
            }
        }else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Passwords do not match.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    public Boolean checkLogin(String username, String password) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @FXML
    private void handleRegisterButton() {
        try {
            // Tải lại trang đăng ky
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
