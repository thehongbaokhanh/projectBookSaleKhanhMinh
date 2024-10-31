package com.example.projectbooksalekhanhminh.Controller;

import com.example.projectbooksalekhanhminh.User;
import com.example.projectbooksalekhanhminh.connection.ConnectionJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class HomeAdminController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn idColumn;

    @FXML
    private TableColumn usernameColumn;

    @FXML
    private TableColumn phoneNumberColumn;

    @FXML
    private TableColumn emailColumn;

    @FXML
    private TableColumn addressColumn;

    @FXML
    private TableColumn roleColumn;

    @FXML
    private TableColumn statusColumn;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    private void loadData() {
        Connection connection = ConnectionJDBC.getConnection();
        String query = "SELECT * FROM user";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");
                boolean status = resultSet.getBoolean("status");
                userList.add(new User(id, username, phoneNumber, email, address, role, status));
            }
            userTable.setItems(userList);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeStatus(String id) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "UPDATE user SET status = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            connection.close();
            System.out.println("Change status successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMoreAdminUser(String id, String username, String password, String phone, String email, String address) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "INSERT INTO user (id, username, password, phoneNumber, email, address, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, address);
            preparedStatement.setString(7, "admin");
            preparedStatement.setBoolean(8, true);
            preparedStatement.executeUpdate();
            connection.close();
            System.out.println("Add more admin successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất không?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectbooksalekhanhminh/Login.fxml"));
                Parent loginRoot = fxmlLoader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(loginRoot));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}