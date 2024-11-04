package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.CardController;

import com.example.oporto_olympics.Models.Atleta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;

public class AtletaCardController {

    @FXML
    private Label Altura;

    @FXML
    private Label DataAniversario;

    @FXML
    private Label Genero;

    @FXML
    private Label Nome;

    @FXML
    private Label Pais;

    @FXML
    private Label Peso;

    /**
     * Preenche os dados do atleta nos rótulos correspondentes.
     *
     * @param atleta O objeto Atleta cujos dados serão preenchidos nos rótulos.
     */
    public void preencherDados(Atleta atleta) {
        Nome.setText(atleta.getNome());
        Pais.setText(atleta.getPais());
        Peso.setText(String.valueOf(atleta.getPeso()) + " kg.");
        Altura.setText(String.valueOf(atleta.getAltura()) + " cm.");
        Genero.setText(atleta.getGenero());
        DataAniversario.setText(new SimpleDateFormat("dd-MM-yyyy").format(atleta.getDataNascimento()));
    }
}
