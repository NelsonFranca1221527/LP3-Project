package com.example.oporto_olympics.Controllers.ListagemLocais.CardController;

import com.example.oporto_olympics.Models.Local;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;
/**
 * Controlador responsável por preencher e exibir os dados de um local em um card na interface gráfica.
 * Esta classe preenche os rótulos da interface com informações sobre um local específico,
 * como nome, capacidade, cidade, país, tipo e ano de construção.
 */
public class  ListagemLocaisCardController {
    /**
     * Rótulo para mostrar o ano de construção do local.
     */
    @FXML
    private Label AnoconstrucaoLabel;
    /**
     * Texto de apoio para o ano de construção.
     */
    @FXML
    private Label AnoconstrucaoText;
    /**
     * Rótulo para mostrar a capacidade do local.
     */
    @FXML
    private Label CapacidadeLabel;
    /**
     * Texto de apoio para a capacidade.
     */
    @FXML
    private Label CapacidadeText;
    /**
     * Rótulo para mostrar a cidade onde o local se situa.
     */
    @FXML
    private Label CidadeLabel;
    /**
     * Rótulo para mostrar a morada do local.
     */
    @FXML
    private Label MoradaLabel;
    /**
     * Rótulo para mostrar o nome do local.
     */
    @FXML
    private Label NomeLabel;
    /**
     * Rótulo para mostrar o país onde o local se situa.
     */
    @FXML
    private Label PaisLabel;
    /**
     * Rótulo para mostrar o tipo do local (exterior/interior).
     */
    @FXML
    private Label TipoLabel;
    /**
     * Preenche os dados do local nos rótulos correspondentes.
     *
     * @param local O objeto {@link Local} contendo os dados do local a serem preenchidos.
     * @throws SQLException Se ocorrer um erro na obtenção de dados do local.
     */
    public void PreencherDados (Local local) throws SQLException {
        NomeLabel.setText(local.getNome());
        PaisLabel.setText(local.getPais());
        CidadeLabel.setText(local.getCidade());
        MoradaLabel.setText(local.getMorada());
        TipoLabel.setText(local.getTipo());
        CapacidadeLabel.setText(String.valueOf(local.getCapacidade()));
        AnoconstrucaoLabel.setText(String.valueOf(local.getAno_construcao()));

        if ("exterior".equals(local.getTipo())) {
            CapacidadeText.setVisible(false);
            AnoconstrucaoText.setVisible(false);
            CapacidadeLabel.setVisible(false);
            AnoconstrucaoLabel.setVisible(false);
        }
    }
}
