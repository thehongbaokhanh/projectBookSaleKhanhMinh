package com.example.projectbooksalekhanhminh.Controller;

import com.example.projectbooksalekhanhminh.connection.ConnectionJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.EventObject;
import java.util.Optional;

public class HomeUserController {

    public void changeInformationUser(String id, String username, String phoneNumber, String email, String address) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String sql = "UPDATE users SET username = ?, phoneNumber = ?, email = ?, address = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, id);
            preparedStatement.executeUpdate();
            System.out.println("Update information successful!");
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePasswordUser(String id, String password) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Update password successful!");
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BoughtBook(String id) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String sql = "UPDATE books SET stokeQuantity = stokeQuantity - 1 WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Update stokeQuantity successful!");
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkStokeQuantity(String id) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        Connection connection = connectionJDBC.getConnection();
        String sql = "SELECT stokeQuantity FROM books WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            boolean result = preparedStatement.executeQuery().next();
            connection.close();
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void handleLogout(ActionEvent actionEvent) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Log out");
//        alert.setHeaderText("Do you want to log out?");
//        alert.setContentText("Are you sure?");
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectbooksalekhanhminh/Login.fxml"));
//                Parent loginRoot = fxmlLoader.load();
//
//                EventObject event = null;
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage.setScene(new Scene(loginRoot));
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
    }
}
