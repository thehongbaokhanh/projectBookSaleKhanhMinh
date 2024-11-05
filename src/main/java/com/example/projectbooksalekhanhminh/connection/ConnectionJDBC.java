package com.example.projectbooksalekhanhminh.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {
// private static String url = "jdbc:mysql://localhost:3306/booksalesmanager";
// private static String username = "root";
// private static String password = "khanhanhanmiu";
    private static String url = "jdbc:mysql://localhost:3306/booksalesmanager";
    private static String username = "root";
    private static String password = "Mot2ba4nam";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected successfully");
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to MySQL database.");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Connection connection = ConnectionJDBC.getConnection();
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
    }
}

