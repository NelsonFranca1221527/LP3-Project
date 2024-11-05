package com.example.oporto_olympics;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        RedirecionarHelper.GotoInserirEquipa().switchScene(stage);
    }

    public static void main(String[] args) {

        try{

            ConnectionBD conexaoBD = ConnectionBD.getInstance();
            Connection conexao = conexaoBD.getConexao();

        }catch(SQLException exception) {

            System.out.print("Ligação falhou");

        }

        launch();
    }
}