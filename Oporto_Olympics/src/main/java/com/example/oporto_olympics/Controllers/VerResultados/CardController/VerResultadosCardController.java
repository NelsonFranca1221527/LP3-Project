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
 * Controlador para exibição de resultados.
 */
public class VerResultadosCardController {
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
     * Rótulo para mostrar o nome do titular.
     */
    @FXML
    private Label TitularLabel;

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
        ModalidadeDAOImp modalidadeDAO = new ModalidadeDAOImp(conexao);

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

        TitularLabel.setText(Titular);

        dataLabel.setText(String.valueOf(resultado.getData()));
        medalhaLabel.setText(resultado.getMedalha());
        resultadoLabel.setText(String.valueOf(resultado.getResultado()));
        tipoLabel.setText(resultado.getTipo());

        // Define o modalidadeLabel com o nome da modalidade usando o ID
        String nomeModalidade = modalidadeDAO.getNomeById(resultado.getModalidadeID());
        if (nomeModalidade != null) {
            modalidadeLabel.setText(nomeModalidade);
        } else {
            modalidadeLabel.setText("Local não encontrado");
        }
    }
}
