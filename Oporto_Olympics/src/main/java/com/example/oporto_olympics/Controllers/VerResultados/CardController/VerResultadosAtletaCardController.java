package com.example.oporto_olympics.Controllers.VerResultados.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Models.Evento;
import com.example.oporto_olympics.Models.ResultadosAtleta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class VerResultadosAtletaCardController {
    /**
     * Rótulo para mostrar a data do resultado.
     */
    @FXML
    private Label dataLabel;
    /**
     * Rótulo para mostrar a medalha dada pelo resultado.
     */
    @FXML
    private Label medalhaLabel;
    /**
     * Rótulo para mostrar a modalidade do resultado.
     */
    @FXML
    private Label modalidadeLabel;
    /**
     * Rótulo para mostrar o resultado.
     */
    @FXML
    private Label resultadoLabel;
    /**
     * Rótulo para mostrar o tipo do resultado.
     */
    @FXML
    private Label tipoLabel;

    /**
     * Preenche os dados do resultado nos rótulos e imagens correspondentes.
     *
     * @param resultadosAtleta O objeto {@link ResultadosAtleta} contendo os dados do resultado a serem preenchidos.
     * @throws SQLException Se ocorrer um erro na consulta da base de dados.
     */
    public void PreencherDados (ResultadosAtleta resultadosAtleta) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();
        ModalidadeDAOImp modalidadeDAO = new ModalidadeDAOImp(conexao);

        dataLabel.setText(String.valueOf(resultadosAtleta.getData()));
        medalhaLabel.setText(resultadosAtleta.getMedalha());
        resultadoLabel.setText(String.valueOf(resultadosAtleta.getResultado()));
        tipoLabel.setText(resultadosAtleta.getTipo());

        // Define o modalidadeLabel com o nome da modalidade usando o ID
        String nomeModalidade = modalidadeDAO.getNomeById(resultadosAtleta.getModalidadeID());
        if (nomeModalidade != null) {
            modalidadeLabel.setText(nomeModalidade);
        } else {
            modalidadeLabel.setText("Local não encontrado");
        }
    }
}
