package com.example.projectbooksalekhanhminh;
import java.sql.Connection;


public class ConnectionJDBC {
    private String url = "jdbc:mysql://localhost:3306/booksalesmanager";
    private String username = "root";
    private String password = "khanhanhanmiu";

    public Connection getConnection() {
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
