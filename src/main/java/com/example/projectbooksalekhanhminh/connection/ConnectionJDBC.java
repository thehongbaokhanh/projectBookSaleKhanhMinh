package com.example.projectbooksalekhanhminh.connection;
import java.sql.Connection;


public class ConnectionJDBC {
//     private static String url = "jdbc:mysql://localhost:3306/booksalesmanager";
//     private static String username = "root";
//     private static String password = "Mot2ba4nam";
  
    private static String url = "jdbc:mysql://localhost:3306/booksalesmanager";
    private static String username = "root";
    private static String password = "khanhanhanmiu";

  
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = java.sql.DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}