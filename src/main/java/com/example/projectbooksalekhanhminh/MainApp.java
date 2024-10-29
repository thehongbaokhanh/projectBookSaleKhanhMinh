package com.example.projectbooksalekhanhminh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInformation.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
