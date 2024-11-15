package com.example.projectbooksalekhanhminh.Admin;

import com.example.projectbooksalekhanhminh.Class.Product;
import com.example.projectbooksalekhanhminh.connection.ConnectionJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class HomeAdminController {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> imageColumn;

    @FXML
    private TableColumn<Product, String> authorColumn;

    @FXML
    private TableColumn<Product, Integer> publishedYearColumn;

    @FXML
    private TableColumn<Product, String> descriptionColumn;

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> stockQuantityColumn;

    @FXML
    private TableColumn<Product, Boolean> statusColumn;

    @FXML
    private TableColumn<Product, Void> actionColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private ImageView search;

    @FXML
    private ImageView refreshButton;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    public void initialize() {
        if (searchTextField.getText().isEmpty()) {
            loadData();
            searchProductWithName();
        } else {
            loadData();
        }
    }

    private void loadData() {
        Connection connection = ConnectionJDBC.getConnection();
        String query = "SELECT * FROM products";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("productID");
                String name = resultSet.getString("productName");
                String image = resultSet.getString("picture");
                String author = resultSet.getString("author");
                int publishedYear = resultSet.getInt("publicationYear");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int stockQuantity = resultSet.getInt("stockQuantity");
                boolean status = resultSet.getBoolean("status");
                Product product = new Product(id, name, image, author, publishedYear, description, category, price, stockQuantity, status);
                productTable.getItems().add(product);
            }
            connection.close();
            setColumn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchProductWithName() {
        String query = "SELECT productID, productName, picture, author, publicationYear, description, category, price, stockQuantity FROM products WHERE productName LIKE ?";
        search.setOnMouseClicked(event -> {
            String searchText = searchTextField.getText();
            if (!searchText.isEmpty()) {
                try {
                    Connection connection = ConnectionJDBC.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "%" + searchText + "%");

                    productTable.getItems().clear();

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        int id = resultSet.getInt("productID");
                        String name = resultSet.getString("productName");
                        String image = resultSet.getString("picture");
                        String author = resultSet.getString("author");
                        int publishedYear = resultSet.getInt("publicationYear");
                        String description = resultSet.getString("description");
                        String category = resultSet.getString("category");
                        double price = resultSet.getDouble("price");
                        int quantity = resultSet.getInt("stockQuantity");
                        Product product = new Product(id, name, image, author, publishedYear, description, category, price, quantity);
                        productTable.getItems().add(product);
                    }
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                productTable.getItems().clear();
                loadData();
            }
        });
    }

    private void setColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(imagePath));
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    setGraphic(imageView);
                }
            }
        });
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publishedYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        stockQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    public Product findProductByID(int id){
        String query = "SELECT productName, picture, author, publicationYear, description, category, price, stockQuantity, status FROM products WHERE productID LIKE ?";
        Product product = null;
        try {
            Connection connection = ConnectionJDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("productName");
                String image = resultSet.getString("picture");
                String author = resultSet.getString("author");
                int publishedYear = resultSet.getInt("publicationYear");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("stockQuantity");
                boolean status = resultSet.getBoolean("status");
                product = new Product(id, name, image, author, publishedYear, description, category, price, quantity, status);

            }
            connection.close();
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void showEditDialog(int id) {
        Dialog<Product> editDialog = new Dialog<>();
        Product product = findProductByID(id);
        editDialog.setTitle("Update information of Product");

        Label idLabel = new Label("ID:");
        Label nameLabel = new Label("Name:");
        Label authorLabel = new Label("Author:");
        Label imageLabel = new Label("Image:");
        Label publishedYearLabel = new Label("Published Year:");
        Label descriptionLabel = new Label("Description:");
        Label categoryLabel = new Label("Category:");
        Label priceLabel = new Label("Price:");
        Label quantityLabel = new Label("Quantity:");
        Label statusLabel = new Label("Status:");

        TextField idField = new TextField(String.valueOf(product.getId()));
        TextField nameField = new TextField(product.getName());
        TextField authorField = new TextField(product.getAuthor());
        TextField imageField = new TextField(product.getImage());
        TextField publishedYearField = new TextField(String.valueOf(product.getPublishedYear()));
        TextField descriptionField = new TextField(product.getDescription());
        TextField categoryField = new TextField(product.getCategory());
        TextField priceField = new TextField(String.valueOf(product.getPrice()));
        TextField quantityField = new TextField(String.valueOf(product.getQuantity()));
        CheckBox statusField = new CheckBox();
        statusField.setSelected(product.getStatus());

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(authorLabel, 0, 2);
        gridPane.add(authorField, 1, 2);
        gridPane.add(imageLabel, 0, 3);
        gridPane.add(imageField, 1, 3);
        gridPane.add(publishedYearLabel, 0, 4);
        gridPane.add(publishedYearField, 1, 4);
        gridPane.add(descriptionLabel, 0, 5);
        gridPane.add(descriptionField, 1, 5);
        gridPane.add(categoryLabel, 0, 6);
        gridPane.add(categoryField, 1, 6);
        gridPane.add(priceLabel, 0, 7);
        gridPane.add(priceField, 1, 7);
        gridPane.add(quantityLabel, 0, 8);
        gridPane.add(quantityField, 1, 8);
        gridPane.add(statusLabel, 0, 9);
        gridPane.add(statusField, 1, 9);

        editDialog.getDialogPane().setContent(gridPane);
        editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Product newProduct = new Product();
        editDialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                newProduct.setId(Integer.parseInt(idField.getText()));
                newProduct.setName(nameField.getText());
                newProduct.setAuthor(authorField.getText());
                newProduct.setImage(imageField.getText());
                newProduct.setPublishedYear(Integer.parseInt(publishedYearField.getText()));
                newProduct.setDescription(descriptionField.getText());
                newProduct.setCategory(categoryField.getText());
                newProduct.setPrice(Double.parseDouble(priceField.getText()));
                newProduct.setQuantity(Integer.parseInt(quantityField.getText()));
                newProduct.setStatus(statusField.isSelected());
            }
            return newProduct;
        });

        Optional<Product> result = editDialog.showAndWait();
        if (result.isPresent()) {
            editProductInDB(newProduct, newProduct.getName(), newProduct.getAuthor(), newProduct.getImage(), newProduct.getPublishedYear(), newProduct.getDescription(), newProduct.getCategory(), newProduct.getPrice(), newProduct.getQuantity(), newProduct.getStatus());
            loadData();
        }
    }



    private void showChangeStatusDialog(Product product) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Status Change");
        confirmationAlert.setHeaderText("Are you sure you want to change the status of this user?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            changeStatus(product.getId());
        }
    }

    public void changeStatus(int id) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "UPDATE products SET status = ? WHERE productID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            connection.close();
            System.out.println("Change status successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editProductInDB(Product product, String name, String author, String image, int publishedYear, String description, String category, double price, int quantity, boolean status) {
        String query = "UPDATE products SET productName = ?, author = ?, picture = ?, publicationYear = ?, description = ?, category = ?, price = ?, stockQuantity = ? , status = ? WHERE productID = ?";

        try {
            Connection connection = ConnectionJDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, image);
            preparedStatement.setInt(4, publishedYear);
            preparedStatement.setString(5, description);
            preparedStatement.setString(6, category);
            preparedStatement.setDouble(7, price);
            preparedStatement.setInt(8, quantity);
            preparedStatement.setBoolean(9, status);
            preparedStatement.setInt(10, product.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkStatusProduct(int id) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String query = "SELECT status FROM products WHERE productID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
    private void handleEditProduct() {
        chooseTheID();
    }

    public void chooseTheID() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Choose the ID of the product you want to edit");
        confirmationAlert.setHeaderText("Enter the ID here:");

        TextField idField = new TextField();
        idField.setPromptText("Product ID");

        GridPane contentPane = new GridPane();
        contentPane.setHgap(10);
        contentPane.setVgap(10);
        contentPane.add(new Label("Product ID:"), 0, 0);
        contentPane.add(idField, 1, 0);

        confirmationAlert.getDialogPane().setContent(contentPane);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                showEditDialog(id);
            } catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid numeric ID.");
                errorAlert.showAndWait();
            }
        }

    }

    private void changeSceneHomeAdmin(Button button) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectbooksalekhanhminh/FXML/HomeAdmin.fxml"));
            Parent loginRoot = fxmlLoader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProduct() {
        showAddDialog();
    }

    public void showAddDialog() {
        Dialog<Product> editDialog = new Dialog<>();
        editDialog.setTitle("Add information to data base");

        Label nameLabel = new Label("Name:");
        Label authorLabel = new Label("Author:");
        Label imageLabel = new Label("Image:");
        Label publishedYearLabel = new Label("Published Year:");
        Label descriptionLabel = new Label("Description:");
        Label categoryLabel = new Label("Category:");
        Label priceLabel = new Label("Price:");
        Label quantityLabel = new Label("Quantity:");
        Label statusLabel = new Label("Status:");

        TextField nameField = new TextField();
        TextField authorField = new TextField();
        TextField imageField = new TextField();
        TextField publishedYearField = new TextField();
        TextField descriptionField = new TextField();
        TextField categoryField = new TextField();
        TextField priceField = new TextField();
        TextField quantityField = new TextField();
        CheckBox statusField = new CheckBox();


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(authorLabel, 0, 1);
        gridPane.add(authorField, 1, 1);
        gridPane.add(imageLabel, 0, 2);
        gridPane.add(imageField, 1, 2);
        gridPane.add(publishedYearLabel, 0, 3);
        gridPane.add(publishedYearField, 1, 3);
        gridPane.add(descriptionLabel, 0, 4);
        gridPane.add(descriptionField, 1, 4);
        gridPane.add(categoryLabel, 0, 5);
        gridPane.add(categoryField, 1, 5);
        gridPane.add(priceLabel, 0, 6);
        gridPane.add(priceField, 1, 6);
        gridPane.add(quantityLabel, 0, 7);
        gridPane.add(quantityField, 1, 7);
        gridPane.add(statusLabel, 0, 8);
        gridPane.add(statusField, 1, 8);

        editDialog.getDialogPane().setContent(gridPane);
        editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Product product = new Product();
        editDialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                product.setName(nameField.getText());
                product.setAuthor(authorField.getText());
                product.setImage(imageField.getText());
                product.setPublishedYear(Integer.parseInt(publishedYearField.getText()));
                product.setDescription(descriptionField.getText());
                product.setCategory(categoryField.getText());
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setQuantity(Integer.parseInt(quantityField.getText()));
                product.setStatus(statusField.isSelected());
            }
            return product;
        });

        Optional<Product> result = editDialog.showAndWait();
        if (result.isPresent()) {
            addProductToDB( product.getName(), product.getAuthor(), product.getImage(), product.getPublishedYear(), product.getDescription(), product.getCategory(), product.getPrice(), product.getQuantity(), product.getStatus());
            loadData();
        }
    }
    public void addProductToDB( String productName, String authorName, String image, int publishedYear, String description, String category, double price, int quantity, boolean status) {
        String query = "INSERT INTO products (productName, picture, author, publicationYear, description, category, price, stockQuantity, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = ConnectionJDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, image);
            preparedStatement.setString(3, authorName);
            preparedStatement.setInt(4, publishedYear);
            preparedStatement.setString(5, description);
            preparedStatement.setString(6, category);
            preparedStatement.setDouble(7, price);
            preparedStatement.setInt(8, quantity);
            preparedStatement.setBoolean(9, status);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        productTable.getItems().clear();
        loadData();
    }
}
