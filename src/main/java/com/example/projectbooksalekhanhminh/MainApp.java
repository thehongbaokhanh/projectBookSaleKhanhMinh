package com.example.projectbooksalekhanhminh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Fxml/HomeAdmin.fxml"));
        primaryStage.setTitle("Book Store");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
