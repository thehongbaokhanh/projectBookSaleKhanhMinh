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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeAdminController {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn idColumn;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn imageColumn;

    @FXML
    private TableColumn authorColumn;

    @FXML
    private TableColumn publishedYearColumn;

    @FXML
    private TableColumn discriptionColumn;

    @FXML
    private TableColumn categoryColumn;

    @FXML
    private TableColumn priceColumn;

    @FXML
    private TableColumn actionColumn;

    @FXML
    private TableColumn addColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private ImageView search;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

//    public void initialize() {
//        if (searchTextField.getText().isEmpty()) {
//            loadData();
//        } else {
//            loadData();
//        }
//    }

    private void loadData() {
        Connection connection = ConnectionJDBC.getConnection();
        String query = "SELECT * FROM products where role = 'Customer'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String image = resultSet.getString("image");
                String author = resultSet.getString("author");
                int publishedYear = resultSet.getInt("publishedYear");
                String discription = resultSet.getString("discription");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int stokeQuantity = resultSet.getInt("stokeQuantity");
                productList.add(new Product(id, name, image, author, publishedYear, discription, category, price, stokeQuantity));
            }
            productTable.setItems(productList);
            connection.close();
            setColumn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        imageColumn.setCellFactory(column -> new TableCell<Product, String>() {

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
        discriptionColumn.setCellValueFactory(new PropertyValueFactory<>("discription"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
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
