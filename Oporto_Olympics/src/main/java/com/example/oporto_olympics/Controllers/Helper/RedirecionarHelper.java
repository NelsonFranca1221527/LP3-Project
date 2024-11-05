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

    public static RedirecionarHelper GotoListagemEvento() {
        return new RedirecionarHelper("Views/ListagemEvento/ListagemEvento.fxml", "Lista de Eventos");
    }

    public static RedirecionarHelper GotoDadosPessoais() {
        return new RedirecionarHelper("Views/DadosPessoais/DadosPessoais.fxml", "Perfil");
    }

    public static RedirecionarHelper GotoMenuPrincipalGestor() {
        return new RedirecionarHelper("Views/MenuPrincipal-Gestor/MenuGestor.fxml", "Menu Principal Gestor");
    }

    public static RedirecionarHelper GotoMenuPrincipalAtleta() {
        return new RedirecionarHelper("Views/MenuPrincipal-Atleta/MenuAtleta.fxml", "Menu Principal Atleta");
    }

    public static RedirecionarHelper GotoListagemAtleta() {
        return new RedirecionarHelper("Views/ListagemAtleta/ListagemAtleta.fxml", "Lista de Atletas");
    }

    public static RedirecionarHelper GotoListagemModalidades() {
        return new RedirecionarHelper("Views/ListagemModalidades/ListagemModalidades.fxml", "Lista de Modalidades");
    }

    public static RedirecionarHelper GotoListagemLocais() {
        return new RedirecionarHelper("Views/ListagemLocais/ListagemLocais.fxml", "Lista de Locais");
    }

    public static RedirecionarHelper GotoListagemEquipas() {
        return new RedirecionarHelper("Views/ListagemEquipas/ListagemEquipas.fxml", "Lista de Equipas");
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
}
