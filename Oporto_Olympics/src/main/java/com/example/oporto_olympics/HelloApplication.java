package com.example.oporto_olympics;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class    HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        RedirecionarHelper.GotoLogin().switchScene(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}