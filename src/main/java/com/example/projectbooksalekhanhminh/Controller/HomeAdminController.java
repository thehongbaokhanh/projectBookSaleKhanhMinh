package com.example.projectbooksalekhanhminh.Controller;

import com.example.projectbooksalekhanhminh.User;
import com.example.projectbooksalekhanhminh.connection.ConnectionJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
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

    @FXML
    private TableColumn actionColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private ImageView search;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    public void initialize() {
        if (searchTextField.getText().isEmpty()) {
            loadData();
            searchUserWithName();
        } else {
            loadData();
        }
    }

    private void setColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        actionColumn.setCellFactory(col -> new TableCell<User, Void>() {
            private final Button changeStatusButton = new Button("Change Status User");
            {
                changeStatusButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    showChangeStatusDialog(user);
                });
            }

            private final Button editButton = new Button("Edit User");
            {
                editButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    showEditDialog(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(30, editButton, changeStatusButton));
                }
            }
        });
    }

    private void searchUserWithName() {
        String query = "SELECT userID, userName, phoneNumber, email, address, role, status FROM user WHERE role = 'Customer' AND userName LIKE ?";
        search.setOnMouseClicked(event -> {
            String searchText = searchTextField.getText();
            if (!searchText.isEmpty()) {
                try {
                    Connection connection = ConnectionJDBC.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "%" + searchText + "%");

                    userTable.getItems().clear();

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        String id = resultSet.getString("userID");
                        String username = resultSet.getString("userName");
                        String phoneNumber = resultSet.getString("phoneNumber");
                        String email = resultSet.getString("email");
                        String address = resultSet.getString("address");
                        String role = resultSet.getString("role");
                        boolean status = resultSet.getBoolean("status");
                        User user = new User(id, username, phoneNumber, email, address, role, status);
                        userTable.getItems().add(user);
                    }
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                userTable.getItems().clear();
                loadData();
            }
        });
    }


    private void showEditDialog(User user) {
        Dialog<User> editDialog = new Dialog<>();
        editDialog.setTitle("Update information of User");

        TextField usernameField = new TextField(user.getUsername());
        TextField phoneField = new TextField(user.getPhoneNumber());
        TextField emailField = new TextField(user.getEmail());
        TextField addressField = new TextField(user.getAddress());
        TextField roleField = new TextField(user.getRole());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Phone Number:"), 0, 1);
        grid.add(phoneField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Address:"), 0, 3);
        grid.add(addressField, 1, 3);
        grid.add(new Label("Role:"), 0, 4);
        grid.add(roleField, 1, 4);

        editDialog.getDialogPane().setContent(grid);
        editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        editDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                user.setUsername(usernameField.getText());
                user.setEmail(emailField.getText());
                user.setPhoneNumber(phoneField.getText());
                user.setEmail(emailField.getText());
                user.setAddress(addressField.getText());
                user.setRole("Customer");
                return user;
            }
            return null;
        });
        Optional<User> result = editDialog.showAndWait();
        if (result.isPresent()) {
            userTable.getItems().set(userTable.getItems().indexOf(user), result.get());
        }
        setInforUserInDB(user.getId(), user.getUsername(), user.getPassword(), user.getPhoneNumber(), user.getEmail(), user.getAddress());
    }

    private void showChangeStatusDialog(User user) {
        Dialog<User> infoDialog = new Dialog<>();
        infoDialog.setTitle("User Information");

        Label usernameLabel = new Label(user.getUsername());
        Label phoneLabel = new Label(user.getPhoneNumber());
        Label emailLabel = new Label(user.getEmail());
        Label addressLabel = new Label(user.getAddress());
        Label roleLabel = new Label(user.getRole());

        CheckBox statusCheckbox = new CheckBox("Active");
        statusCheckbox.setSelected(user.getStatus());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameLabel, 1, 0);
        grid.add(new Label("Phone Number:"), 0, 1);
        grid.add(phoneLabel, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailLabel, 1, 2);
        grid.add(new Label("Address:"), 0, 3);
        grid.add(addressLabel, 1, 3);
        grid.add(new Label("Role:"), 0, 4);
        grid.add(roleLabel, 1, 4);
        grid.add(new Label("Status:"), 0, 5);
        grid.add(statusCheckbox, 1, 5);

        infoDialog.getDialogPane().setContent(grid);
        infoDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        infoDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                user.setStatus(statusCheckbox.isSelected());
                return user;
            }
            return null;
        });

        Optional<User> result = infoDialog.showAndWait();
        result.ifPresent(updatedUser -> {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Status Change");
            confirmationAlert.setHeaderText("Are you sure you want to change the status of this user?");
            confirmationAlert.setContentText("Current status: " + (user.getStatus() ? "Active" : "Inactive"));

            Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
            if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                userTable.getItems().set(userTable.getItems().indexOf(user), updatedUser);
                changeStatus(user.getId(), user.getStatus());
            }
        });
    }

    private void loadData() {
        Connection connection = ConnectionJDBC.getConnection();
        String query = "SELECT * FROM user where role = 'Customer'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("userid");
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
            setColumn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeStatus(String id, boolean status) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "UPDATE user SET status = ? WHERE userid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, status);
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
        String query = "INSERT INTO user (userid, username, password, phoneNumber, email, address, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
        alert.setTitle("Log out");
        alert.setHeaderText("Do you want to log out?");
        alert.setContentText("Are you sure?");

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

    @FXML
    private Button ShowEditUserButton;

    @FXML
    public void handleEditUserButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectbooksalekhanhminh/UserInformation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ShowEditUserButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setInforUserInDB(String id, String username, String password, String phone, String email, String address) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "UPDATE user SET username = ?, password = ?, phoneNumber = ?, email = ?, address = ? WHERE userid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, id);
            preparedStatement.executeUpdate();
            connection.close();
            System.out.println("Update information successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}