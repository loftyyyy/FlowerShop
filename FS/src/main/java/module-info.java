module com.example.fs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.fs to javafx.fxml;
    exports com.example.fs;
}