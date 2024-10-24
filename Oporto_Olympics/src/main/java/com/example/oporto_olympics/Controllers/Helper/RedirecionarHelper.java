package com.example.oporto_olympics.Controllers.Helper;

import com.example.oporto_olympics.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RedirecionarHelper {


    private String url;
    private String titulo;

    public RedirecionarHelper(String url, String titulo) {
        this.url = url;
        this.titulo = titulo;
    }

    public void switchScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(url));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RedirecionarHelper GotoInserçãoXML() {
        return new RedirecionarHelper("Views/ImportaçõesXML/InserçãoXML/InserçãoXML.fxml", "Inserir XML");
    }

    public static RedirecionarHelper GotoSeleçãoXML() {
        return new RedirecionarHelper("Views/ImportaçõesXML/SeleçãoXML/SeleçãoXML.fxml", "Selecionar XML");
    }

}
