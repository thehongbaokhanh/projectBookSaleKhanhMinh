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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController{

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

        if (authenticate(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Đã đăng nhập thành công.", "Xin chào " + username + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Đăng nhập thất bại.", " Tên đăng nhập hoặc mật khẩu không tồn tại.");
        }
    }

    private boolean authenticate(String username, String password) {
        return false;
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

    public User[] getUsersInfor(){
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "SELECT * FROM users";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("userID");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");
                User user = new User(id, userName, password, phoneNumber, email, address, role);
                users[numberOfUsers] = user;
                numberOfUsers++;
            }
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }
}
