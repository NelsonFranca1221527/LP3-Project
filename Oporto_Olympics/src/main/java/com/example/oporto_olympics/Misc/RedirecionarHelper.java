package com.example.oporto_olympics.Misc;

import com.example.oporto_olympics.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public static RedirecionarHelper GotoDadosPessoais() {
        return new RedirecionarHelper("Views/DadosPessoais/VerDadosPessoais.fxml", "Dados Pessoais");
    }

    public static RedirecionarHelper GotoMenuPrincipalGestor() {
        return new RedirecionarHelper("Views/MenuPrincipal-Gestor/MenuGestor.fxml", "Menu Principal Gestor");
    }

    public static RedirecionarHelper GotoMenuPrincipalAtleta() {
        return new RedirecionarHelper("Views/MenuPrincipal-Atleta/MenuAtleta.fxml", "Menu Principal Atleta");
    }

    public static RedirecionarHelper GotoListagemAtleta() {
        return new RedirecionarHelper("Views/ListagemAtletas/ListAtletas.fxml", "Lista de Atletas");
    }

    public static RedirecionarHelper GotoListagemModalidades() {
        return new RedirecionarHelper("Views/ListagemModalidades/ListagemModalidades.fxml", "Lista de Modalidades");
    }

    public static RedirecionarHelper GotoListagemLocais() {
        return new RedirecionarHelper("Views/ListagemLocais/ListagemLocais.fxml", "Lista de Locais");
    }

    public static RedirecionarHelper GotoListagemEquipas() {
        return new RedirecionarHelper("Views/Equipas/ListarEquipas.fxml", "Lista de Equipas");
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

    public static RedirecionarHelper GotoListagemEventos() {
        return new RedirecionarHelper("Views/ListagemEventos/ListagemEventos.fxml", "Listagem de Eventos");
    }

    public static RedirecionarHelper GotoInserirModalidade() {
        return new RedirecionarHelper("Views/Modalidades/InserirModalidades.fxml", "Inserir Modalidades");
    }

    public static RedirecionarHelper GotoInserirEquipa() {
        return new RedirecionarHelper("Views/Equipas/InserirEquipas.fxml", "Inserir Equipas");
    }

    public static RedirecionarHelper GotoInserirAtleta() {
        return new RedirecionarHelper("Views/Atleta/InserirAtleta.fxml", "Inserir Atletas");
    }

    public static RedirecionarHelper GotoInscricoesEquipa() {
        return new RedirecionarHelper("Views/AprovarInscricaoEquipa.fxml", "Inscrições em Equipas");
    }

    public static RedirecionarHelper GotoInscreverEquipa() {
        return new RedirecionarHelper("Views/InscreverEquipas.fxml", "Inscrever numa Equipas");
    }
}