package com.example.oporto_olympics.Controllers.ListagemLocais.CardController;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.Models.Local;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class ListagemLocaisCardController {
    @FXML
    private Label AnoconstrucaoLabel;

    @FXML
    private Label AnoconstrucaoText;

    @FXML
    private Label CapacidadeLabel;

    @FXML
    private Label CapacidadeText;

    @FXML
    private Label CidadeLabel;

    @FXML
    private Label MoradaLabel;

    @FXML
    private Label NomeLabel;

    @FXML
    private Label PaisLabel;

    @FXML
    private Label TipoLabel;

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
