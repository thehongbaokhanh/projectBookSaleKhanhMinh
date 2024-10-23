package com.example.projectbooksalekhanhminh.Controller;

import com.example.projectbooksalekhanhminh.connection.ConnectionJDBC;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

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

        if (username.isEmpty() && password.isEmpty() && confirmPassword.isEmpty() && phone.isEmpty() && email.isEmpty() && address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Register Failed", "Please enter all fields");
        } else if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Register Failed", "Passwords do not match");
        }else if (!isValidPhoneNumber(phone)) {
            showAlert(Alert.AlertType.ERROR, "Register Failed", "Invalid phone number");
        } else if (isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Register Failed", "Password must be at least 8 characters long.");
        }else {
            int numberOfUsers = numberOfUsers();
            String id = generateUsersID(numberOfUsers + 1);
            addUser(id, username, password, phone, email, address);
            showAlert(Alert.AlertType.INFORMATION, "Register Successful", "User registered successfully");
            handleLoginLink();
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^0\\d{9}$"); // Kiểm tra 10 chữ số bắt đầu bằng 0
    }

    private boolean isValidPassword(String password) {
        return password.matches("\"^.{8,}$\"\n");
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

    private int numberOfUsers(){
        int numberOfUsers = 0;
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "SELECT * FROM user";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                numberOfUsers++;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
     return numberOfUsers;
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
        String formattedID = String.format("CM%0" + totalDigitals + "d", numberOfUsers + 1);
        return formattedID;
    }

    public void addUser(String id, String username, String password, String phone, String email, String address) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, phone);
            statement.setString(5, email);
            statement.setString(6, address);
            statement.setString(7, "customer");
            statement.setBoolean(8, true);
            statement.executeUpdate();
            connection.close();
            System.out.println("Add user successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
