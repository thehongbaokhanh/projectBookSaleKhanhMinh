package com.example.projectbooksalekhanhminh.Controller;

import com.example.projectbooksalekhanhminh.User;
import com.example.projectbooksalekhanhminh.connection.ConnectionJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;

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

    public void findUser(String username) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "SELECT * FROM user where userName = ?";
        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
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
}

