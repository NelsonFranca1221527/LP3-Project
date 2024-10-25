module com.example.oporto_olympics {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.oporto_olympics to javafx.fxml;
    exports com.example.oporto_olympics;
}