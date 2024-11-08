package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.CardController;

import com.example.oporto_olympics.Models.Equipa;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EquipaCardController {

    @FXML
    private Label AnoFundacao;

    @FXML
    private Label Desporto;

    @FXML
    private Label Genero;

    @FXML
    private Label Nome;

    @FXML
    private Label Pais;

    /**
     * Preenche os dados da equipa nos rótulos correspondentes.
     *
     * @param equipa O objeto Equipa cujos dados serão preenchidos nos rótulos.
     */
    public void preencherDados(Equipa equipa) {
        Nome.setText(equipa.getNome());
        Pais.setText(equipa.getPais());
        Genero.setText(equipa.getGenero());
        Desporto.setText(equipa.getDesporto());
        AnoFundacao.setText(String.valueOf(equipa.getAnoFundacao()));
    }
}
