module com.example.projectbooksalekhanhminh {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projectbooksalekhanhminh to javafx.fxml;
    exports com.example.projectbooksalekhanhminh.Controller;
    opens com.example.projectbooksalekhanhminh.Controller to javafx.fxml;
    exports com.example.projectbooksalekhanhminh.connection;
    opens com.example.projectbooksalekhanhminh.connection to javafx.fxml;
    exports com.example.projectbooksalekhanhminh;
}