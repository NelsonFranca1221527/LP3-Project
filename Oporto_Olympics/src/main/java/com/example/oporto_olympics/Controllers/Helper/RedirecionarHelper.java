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

    public static RedirecionarHelper GotoLogin() {
        return new RedirecionarHelper("Views/Login.fxml", "Login");
    }

    public static RedirecionarHelper GotoInserirEventosOlimpicos() {
        return new RedirecionarHelper("Views/EventosOlimpicos/InserirEventosOlimpicos.fxml", "Criar Eventos Olímpicos");
    }

    public static RedirecionarHelper GotoInserçãoXML() {
        return new RedirecionarHelper("Views/ImportaçõesXML/InserçãoXML/InserçãoXML.fxml", "Inserir XML");
    }

    public static RedirecionarHelper GotoInserirLocal() {
        return new RedirecionarHelper("Views/EventosOlimpicos/InserirLocal.fxml", "Inserir Local");
    }

    public static RedirecionarHelper GotoSeleçãoXML() {
        return new RedirecionarHelper("Views/ImportaçõesXML/SeleçãoXML/SeleçãoXML.fxml", "Selecionar XML");
    }

    public static RedirecionarHelper GotoInserirModalidade() {
        return new RedirecionarHelper("Views/Modalidades/InserirModalidades.fxml", "Inserir Modalidades");
    }

    public static RedirecionarHelper GotoInserirEquipa() {
        return new RedirecionarHelper("Views/Equipas/InserirEquipas.fxml", "Inserir Equipas");
    }
}
