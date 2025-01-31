package com.example.oporto_olympics;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principal da aplicação Oporto Olympics.
 *
 * Esta classe inicia a aplicação JavaFX e redireciona o utilizador para a página de login.
 */
public class    HelloApplication extends Application {

    /**
     * Inicia a aplicação e carrega a interface de login.
     *
     * @param stage O palco principal da aplicação.
     * @throws IOException Se ocorrer um erro ao carregar a interface.
     */
    @Override
    public void start(Stage stage) throws IOException {
        RedirecionarHelper.GotoLogin().switchScene(stage);
    }

    /**
     * Método principal que inicia a aplicação JavaFX.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        launch();
    }
}