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
/**
 * Controlador para mostar o top 10.
 */
public class VerTopTenCardController {

    /**
     * Rótulo para mostrar o resultado.
     */
    @FXML
    private Label resultadoLabel;
    /**
     * Rótulo para mostrar a medida.
     */
    @FXML
    private Label medidaLabel;
    /**
     * Rótulo para mostrar o nome do atleta.
     */
    @FXML
    private Label AtletaLabel;

    /**
     * Preenche os dados do resultado nos rótulos e imagens correspondentes.
     *
     * @param resultado O objeto {@link ResultadosModalidade} contendo os dados do resultado a serem preenchidos.
     * @throws SQLException Se ocorrer um erro na consulta da base de dados.
     */
    public void PreencherDados (ResultadosModalidade resultado) throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();

        ResultadosModalidadeDAOImp resultadosDao = new ResultadosModalidadeDAOImp(conexao);

        int idTitular = 0;

        String tipoTitular = "";

        if (resultado.getAtletaID() != 0 && resultado.getEquipaID() == 0) {
            idTitular = resultado.getAtletaID();
            tipoTitular = "Atleta";
        }

        if (resultado.getAtletaID() == 0 && resultado.getEquipaID() != 0) {
            idTitular = resultado.getEquipaID();
            tipoTitular = "Equipa";
        }

        String Titular = resultadosDao.getTitularNome(idTitular,tipoTitular);

        AtletaLabel.setText(Titular);

        resultadoLabel.setText(String.valueOf(resultado.getResultado()));

        medidaLabel.setText(resultado.getTipo());
    }
}
