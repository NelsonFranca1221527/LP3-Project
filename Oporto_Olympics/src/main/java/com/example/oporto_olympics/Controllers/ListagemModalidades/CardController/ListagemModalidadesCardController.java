package com.example.oporto_olympics.Controllers.ListagemModalidades.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Models.Evento;
import com.example.oporto_olympics.Models.Modalidade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.SQLException;

public class ListagemModalidadesCardController {
    @FXML
    private Label DescricaoLabel;

    @FXML
    private Label RegrasLabel;

    @FXML
    private Label EventoLabel;

    @FXML
    private Label GeneroLabel;

    @FXML
    private Label JogosLabel;

    @FXML
    private Label NomeLabel;

    @FXML
    private Label TipoLabel;

    @FXML
    private Label MinpartLabel;

    @FXML
    private Label MedidaLabel;

    public void PreencherDados (Modalidade modalidade) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);
        EventosDAOImp eventosDAOImp = new EventosDAOImp(conexao);
        LocaisDAOImp locaisDAOImp = new LocaisDAOImp(conexao);

        NomeLabel.setText(modalidade.getNome());
        TipoLabel.setText(modalidade.getTipo());
        JogosLabel.setText(modalidade.getOneGame());
        GeneroLabel.setText(modalidade.getGenero());
        MinpartLabel.setText(String.valueOf(modalidade.getMinParticipantes()));
        MedidaLabel.setText(modalidade.getMedida());
        DescricaoLabel.setText(modalidade.getDescricao());
        RegrasLabel.setText(modalidade.getRegras());

        int eventoid = modalidadeDAOImp.getEventoID(modalidade.getId());
        Evento evento = eventosDAOImp.getById(eventoid);
        String localnome = locaisDAOImp.getNomeById(evento.getLocal_id());

        EventoLabel.setText(localnome + " - " + evento.getAno_edicao());
    }
}
