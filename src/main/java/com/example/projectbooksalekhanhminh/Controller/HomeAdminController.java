package com.example.projectbooksalekhanhminh.Controller;

import com.example.projectbooksalekhanhminh.User;
import com.example.projectbooksalekhanhminh.connection.ConnectionJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomeAdminController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> idColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> phoneNumberColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> addressColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, Boolean> statusColumn;

    @FXML
    private TableColumn<User, Void> actionColumn;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    public void initialize() {
        loadData();
    }

    private void setColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        actionColumn.setCellFactory(col -> new TableCell<>() {
                }
        );
    }


    private void loadData() {
        Connection connection = ConnectionJDBC.getConnection();

        String query = "SELECT userID, userName, phoneNumber, email, address, role, status FROM user where role = 'Customer'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("userID");
                String username = resultSet.getString("username");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");
                boolean status = resultSet.getBoolean("status");
                User user = new User(id, username, phoneNumber, email, address, role, status);
                userTable.getItems().add(user);
            }
            System.out.println("Load data successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setColumn();
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

    public void addMoreAdminUser(String id, String username, String password, String phone, String email, String
            address) {
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
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("userID");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");
                boolean status = resultSet.getBoolean("status");
                User user = new User(id, username, phoneNumber, email, address, role, status);
                userTable.getItems().add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setColumn();
    }
}

