package com.example.projectbooksalekhanhminh.Admin;

import com.example.projectbooksalekhanhminh.Class.Product;
import com.example.projectbooksalekhanhminh.Class.User;
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

public class HomeUserManagementController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn idColumn;

    @FXML
    private TableColumn usernameColumn;

    @FXML
    private TableColumn passwordColumn;

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
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
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

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(30, changeStatusButton));
                }
            }
        });
    }

    private void searchUserWithName() {
        String query = "SELECT userID, userName, password, phoneNumber, email, address, role, status FROM user WHERE role = 'Customer' AND userName LIKE ?";
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
                        String password = resultSet.getString("password");
                        String phoneNumber = resultSet.getString("phoneNumber");
                        String email = resultSet.getString("email");
                        String address = resultSet.getString("address");
                        String role = resultSet.getString("role");
                        boolean status = resultSet.getBoolean("status");
                        User user = new User(id, username, password, phoneNumber, email, address, role, status);
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

    public User findUserByID(String id){
        String query = "SELECT userName, password, phoneNumber, email, address, role, status FROM user WHERE userID LIKE ?";
        User user = null;
        try {
            Connection connection = ConnectionJDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("userName");
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");
                boolean status = resultSet.getBoolean("status");
                user = new User(id, username, password, phoneNumber, email, address, role, status);
            }
            connection.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void showEditDialog(String id) {
        Dialog<User> editDialog = new Dialog<>();
        User user = findUserByID(id);
        editDialog.setTitle("Update information of Product");

        Label idLabel = new Label("ID:");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label phoneNumberLabel = new Label("Phone Number:");
        Label emailLabel = new Label("Email:");
        Label addressLabel = new Label("Address:");
        Label roleLabel = new Label("Role:");
        Label statusLabel = new Label("Status:");

        Label idField = new Label(String.valueOf(user.getId()));
        TextField userNameField = new TextField(user.getUsername());
        TextField passwordField = new TextField(user.getPassword());
        TextField phoneNumberField = new TextField(user.getPhoneNumber());
        TextField emailField = new TextField(user.getEmail());
        TextField addressField = new TextField(user.getAddress());
        TextField roleField = new TextField(user.getRole());

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(userNameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(phoneNumberLabel, 0, 3);
        gridPane.add(phoneNumberField, 1, 3);
        gridPane.add(emailLabel, 0, 4);
        gridPane.add(emailField, 1, 4);
        gridPane.add(addressLabel, 0, 5);
        gridPane.add(addressField, 1, 5);
        gridPane.add(roleLabel, 0, 6);
        gridPane.add(roleField, 1, 6);

        editDialog.getDialogPane().setContent(gridPane);
        editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        User newUser = new User();
        editDialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                newUser.setId(idField.getText());
                newUser.setUsername(userNameField.getText());
                newUser.setPassword(passwordField.getText());
                newUser.setPhoneNumber(phoneNumberField.getText());
                newUser.setEmail(emailField.getText());
                newUser.setAddress(addressField.getText());
                newUser.setRole(roleField.getText());
            }
            return newUser;
        });

        Optional<User> result = editDialog.showAndWait();
        if (result.isPresent()) {
            setInforUserInDB(id, newUser.getUsername(), newUser.getPassword(), newUser.getPhoneNumber(), newUser.getEmail(), newUser.getAddress());
        }
    }

    public void chooseTheID() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Choose the ID of the user you want to Update");
        confirmationAlert.setHeaderText("Enter the ID here:");

        TextField idField = new TextField();
        idField.setPromptText("User ID");

        GridPane contentPane = new GridPane();
        contentPane.setHgap(10);
        contentPane.setVgap(10);
        contentPane.add(new Label("User ID:"), 0, 0);
        contentPane.add(idField, 1, 0);

        confirmationAlert.getDialogPane().setContent(contentPane);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                showEditDialog(generateIDByNumber(id));
            } catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid numeric ID.");
                errorAlert.showAndWait();
            }
        }

    }

    @FXML
    private void handleEditUser() {
        chooseTheID();
    }

    private void showChangeStatusDialog(User user) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Status Change");
        confirmationAlert.setHeaderText("Are you sure you want to change the status of this user?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            changeStatus(user.getId());
        }
    }

    private void loadData() {
        Connection connection = ConnectionJDBC.getConnection();
        String query = "SELECT * FROM user where role = 'Customer'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("userID");
                String username = resultSet.getString("userName");
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");
                boolean status = resultSet.getBoolean("status");
                userList.add(new User(id, username,password , phoneNumber, email, address, role, status));
            }
            userTable.setItems(userList);
            connection.close();
            setColumn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkStatusUser(String id) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "SELECT status FROM user WHERE userID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void changeStatus(String id) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "UPDATE user SET status = ? WHERE userid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, !checkStatusUser(id));
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            connection.close();
            System.out.println("Change status successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMoreUser( String username, String password, String phone, String email, String role, String
            address, boolean status) {
        String id = generateID();
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
            preparedStatement.setString(7, role);
            preparedStatement.setBoolean(8, status);
            preparedStatement.executeUpdate();
            connection.close();
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectbooksalekhanhminh/FXML/Login.fxml"));
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
    public void handleAddUser() throws IOException {
        showAddDialog();
    }

    public void showAddDialog() {
        Dialog<User> editDialog = new Dialog<>();
        editDialog.setTitle("Add information to data base");

        Label nameLabel = new Label("Name:");
        Label passwordLabel = new Label("Password:");
        Label phoneNumberLabel = new Label("Phone Number:");
        Label emailLabel = new Label("Email:");
        Label addressLabel = new Label("Address:");
        Label roleLabel = new Label("Role:");
        Label statusLabel = new Label("Status:");

        TextField nameField = new TextField();
        TextField passwordField = new TextField();
        TextField phoneNumberField = new TextField();
        TextField emailField = new TextField();
        TextField addressField = new TextField();
        TextField roleField = new TextField();
        CheckBox statusField = new CheckBox();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(phoneNumberLabel, 0, 2);
        gridPane.add(phoneNumberField, 1, 2);
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailField, 1, 3);
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);
        gridPane.add(roleLabel, 0, 5);
        gridPane.add(roleField, 1, 5);
        gridPane.add(statusLabel, 0, 6);
        gridPane.add(statusField, 1, 6);

        editDialog.getDialogPane().setContent(gridPane);
        editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        User user = new User();
        editDialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                user.setUsername(nameField.getText());
                user.setPassword(passwordField.getText());
                user.setPhoneNumber(phoneNumberField.getText());
                user.setEmail(emailField.getText());
                user.setAddress(addressField.getText());
                user.setRole(roleField.getText());
                user.setStatus(statusField.isSelected());
            }
            return user;
        });

        Optional<User> result = editDialog.showAndWait();
        if (result.isPresent()) {
            addMoreUser( user.getUsername(), user.getPassword(), user.getPhoneNumber(), user.getEmail(), user.getRole(), user.getAddress(), user.getStatus());
        }
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

    @FXML
    private void handleRefresh() {
        userTable.getItems().clear();
        loadData();
    }

    private final String PREFIX = "CM";
    private final int ID_LENGTH = 5;

    public String generateID() {
        String numberPart = String.format("%0" + ID_LENGTH + "d", checkTheNumberOfUser() + 1);
        return PREFIX + numberPart;
    }

    public String generateIDByNumber(int number) {
        String numberPart = String.format("%0" + ID_LENGTH + "d", number);
        return PREFIX + numberPart;
    }

    public int checkTheNumberOfUser() {
        int numberOfUsers = 0;
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "SELECT COUNT(*) FROM user";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfUsers = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return numberOfUsers;
    }
}