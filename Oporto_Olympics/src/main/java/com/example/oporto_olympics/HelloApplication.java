package com.example.oporto_olympics;

import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class    HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        RedirecionarHelper.GotoInserirEventosOlimpicos().switchScene(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}