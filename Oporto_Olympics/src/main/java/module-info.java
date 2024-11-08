module com.example.oporto_olympics {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.compiler;


    opens com.example.oporto_olympics to javafx.fxml;
    exports com.example.oporto_olympics;
}