package com.example.oporto_olympics.Controllers.ListagemAtletas.CardController;

import com.example.oporto_olympics.Models.Atleta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.lang.model.AnnotatedConstruct;

public class ListAtletaCardController {

    @FXML
    private Label NomeLabel;

    @FXML
    private Label DataNascLabel;

    @FXML
    private Label PaisLabel;

    @FXML
    private Label GeneroLabel;

    @FXML
    private Label AlturaLabel;

    @FXML
    private Label PesoLabel;

    public void PreencherDados (Atleta atleta){

        NomeLabel.setText(atleta.getNome());
        DataNascLabel.setText(String.valueOf(atleta.getDataNascimento()));
        PaisLabel.setText(atleta.getPais());
        GeneroLabel.setText(atleta.getGenero());
        AlturaLabel.setText(String.valueOf(atleta.getAltura()));
        PesoLabel.setText(String.valueOf(atleta.getPeso()));

    }
}
