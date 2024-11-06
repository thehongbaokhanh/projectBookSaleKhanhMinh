package com.example.projectbooksalekhanhminh.Controller;

import com.example.projectbooksalekhanhminh.Product;
import com.example.projectbooksalekhanhminh.User;
import com.example.projectbooksalekhanhminh.connection.ConnectionJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.*;

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
    private TableColumn actionColumn;

    @FXML
    private TableColumn addColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private ImageView search;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    public void initialize() {
        if (searchTextField.getText().isEmpty()) {
            loadData();
            searchUserWithName();
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
                Product product = new Product(id, name, image, author, publishedYear, description, category, price, stockQuantity);
                productTable.getItems().add(product);
                }
            connection.close();
            setColumn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchUserWithName() {
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
                    imageView.setFitHeight(50); // Chiều cao của ảnh
                    imageView.setFitWidth(50);  // Chiều rộng của ảnh
                    setGraphic(imageView);
                }
            }
        });
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publishedYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        actionColumn.setCellFactory(col -> new TableCell<User, Void>() {
//            private final Button changeStatusButton = new Button("Change Status User");
//
//            {
//                changeStatusButton.setOnAction(event -> {
//                    User user = getTableView().getItems().get(getIndex());
//                    showChangeStatusDialog(user);
//                });
//            }
//
//            private final Button editButton = new Button("Edit User");
//
//            {
//                editButton.setOnAction(event -> {
//                    User user = getTableView().getItems().get(getIndex());
//                    showEditDialog(user);
//                });
//            }
//        });
    }
}
