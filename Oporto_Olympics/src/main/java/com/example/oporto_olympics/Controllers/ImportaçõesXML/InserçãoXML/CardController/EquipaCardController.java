package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.CardController;

import com.example.oporto_olympics.Models.Equipa;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * Controlador responsável pela gestão dos dados da equipa na interface do utilizador.
 * Esta classe preenche os rótulos com as informações da equipa, como nome, país, género,
 * desporto e ano de fundação.
 */
public class EquipaCardController {
    /**
     * Rótulo para mostrar o ano de fundação.
     */
    @FXML
    private Label AnoFundacao;
    /**
     * Rótulo para mostrar o desporto.
     */
    @FXML
    private Label Desporto;
    /**
     * Rótulo para mostrar o género.
     */
    @FXML
    private Label Genero;
    /**
     * Rótulo para mostrar o nome.
     */
    @FXML
    private Label Nome;
    /**
     * Rótulo para mostrar o país.
     */
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
