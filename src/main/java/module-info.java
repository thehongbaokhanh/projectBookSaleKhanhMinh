module com.example.projectbooksalekhanhminh {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projectbooksalekhanhminh to javafx.fxml;
    exports com.example.projectbooksalekhanhminh;
}