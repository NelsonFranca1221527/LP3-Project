package com.example.oporto_olympics.Controllers.ListagemAtletas.CardController;

import com.example.oporto_olympics.Models.Atleta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.lang.model.AnnotatedConstruct;
/**
 * Controlador responsável por gerir os dados de um atleta exibidos em um card.
 * Esta classe preenche os rótulos de um card com as informações de um atleta.
 */
public class ListAtletaCardController {
    /**
     * Rótulo para mostrar o nome do atleta.
     */
    @FXML
    private Label NomeLabel;
    /**
     * Rótulo para mostrar a data de nascimento do atleta.
     */
    @FXML
    private Label DataNascLabel;
    /**
     * Rótulo para mostrar o país do atleta.
     */
    @FXML
    private Label PaisLabel;
    /**
     * Rótulo para mostrar o género do atleta.
     */
    @FXML
    private Label GeneroLabel;
    /**
     * Rótulo para mostrar a altura do atleta.
     */
    @FXML
    private Label AlturaLabel;
    /**
     * Rótulo para mostrar o peso do atleta.
     */
    @FXML
    private Label PesoLabel;
    /**
     * Preenche os dados do atleta nos rótulos correspondentes.
     *
     * @param atleta O objeto {@link Atleta} contendo os dados a serem preenchidos.
     */
    public void PreencherDados (Atleta atleta){

        NomeLabel.setText(atleta.getNome());
        DataNascLabel.setText(String.valueOf(atleta.getDataNascimento()));
        PaisLabel.setText(atleta.getPais());
        GeneroLabel.setText(atleta.getGenero());
        AlturaLabel.setText(String.valueOf(atleta.getAltura()));
        PesoLabel.setText(String.valueOf(atleta.getPeso()));

    }
}
