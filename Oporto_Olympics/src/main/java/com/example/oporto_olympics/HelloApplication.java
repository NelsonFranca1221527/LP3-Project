package com.example.oporto_olympics;

import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class    HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        RedirecionarHelper.GotoSeleçãoXML().switchScene(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}