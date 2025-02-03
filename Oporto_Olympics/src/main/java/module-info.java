module com.example.oporto_olympics {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.compiler;
    requires com.fasterxml.jackson.databind;
    requires commons.email;
    requires javax.mail;


    opens com.example.oporto_olympics to javafx.fxml;
    exports com.example.oporto_olympics;
}