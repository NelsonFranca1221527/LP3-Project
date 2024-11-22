package com.example.oporto_olympics.Controllers.VerResultados.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Resultados.ResultadosModalidadeDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Models.ResultadosModalidade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VerTopTenCardController {

    /**
     * Rótulo para mostrar o resultado.
     */
    @FXML
    private Label resultadoLabel;
    /**
     * Rótulo para mostrar o nome do atleta.
     */
    @FXML
    private Label AtletaLabel;

    /**
     * Preenche os dados do resultado nos rótulos e imagens correspondentes.
     *
     * @param resultadosAtleta O objeto {@link ResultadosModalidade} contendo os dados do resultado a serem preenchidos.
     * @throws SQLException Se ocorrer um erro na consulta da base de dados.
     */
    public void PreencherDados (ResultadosModalidade resultadosAtleta) throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();

        ResultadosModalidadeDAOImp resultadosDao = new ResultadosModalidadeDAOImp(conexao);

        List<String> Atleta = resultadosDao.getAtletaNome(resultadosAtleta.getAtletaID());

        for (String nomeAtleta : Atleta) {
            AtletaLabel.setText(nomeAtleta);
        }
        resultadoLabel.setText(String.valueOf(resultadosAtleta.getResultado()));

    }
}
