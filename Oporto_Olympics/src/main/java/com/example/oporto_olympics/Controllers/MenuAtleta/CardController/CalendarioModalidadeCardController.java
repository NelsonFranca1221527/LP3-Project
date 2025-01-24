package com.example.oporto_olympics.Controllers.MenuAtleta.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Atleta.CalendarioDAOImp;
import com.example.oporto_olympics.Models.Evento;
import com.example.oporto_olympics.Models.EventosModalidade;
import com.example.oporto_olympics.Models.Local;
import com.example.oporto_olympics.Models.Modalidade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.SQLException;

public class CalendarioModalidadeCardController {

    @FXML
    private Label CidadeLabel;

    @FXML
    private Label DataLabel;

    @FXML
    private Label DescricaoLabel;

    @FXML
    private Label DuracaoLabel;

    @FXML
    private Label GeneroLabel;

    @FXML
    private Label MoradaLabel;

    @FXML
    private Label NomeLabel;

    @FXML
    private Label NomelocalLabel;

    @FXML
    private Label PaisLabel;

    @FXML
    private Label RegrasLabel;

    @FXML
    private Label TipoLabel;

    private CalendarioDAOImp dao;

    public void PreencherDados (EventosModalidade eventosModalidade) throws SQLException {
        Connection conexao = ConnectionBD.getInstance().getConexao();
        dao = new CalendarioDAOImp(conexao);

        Evento evento = dao.getById(eventosModalidade.getEvento_id());
        Modalidade modalidade = dao.getModalidadeById(eventosModalidade.getModalidade_id());
        Local local = dao.getLocalById(eventosModalidade.getLocal_id());
        PaisLabel.setText(evento.getPais());
        NomeLabel.setText(modalidade.getNome());
        RegrasLabel.setText(modalidade.getRegras());
        GeneroLabel.setText(modalidade.getGenero());
        DescricaoLabel.setText(modalidade.getDescricao());
        CidadeLabel.setText(local.getCidade());
        MoradaLabel.setText(local.getMorada());
        TipoLabel.setText(local.getTipo());
        NomelocalLabel.setText(local.getNome());
        DuracaoLabel.setText(String.valueOf(eventosModalidade.getDuracao()));
        DataLabel.setText(String.valueOf(eventosModalidade.getData_modalidade()));
    }
}
