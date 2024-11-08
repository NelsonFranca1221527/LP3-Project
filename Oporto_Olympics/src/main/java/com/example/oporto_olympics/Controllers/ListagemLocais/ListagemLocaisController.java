package com.example.oporto_olympics.Controllers.ListagemLocais;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.Controllers.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.Controllers.ListagemLocais.CardController.ListagemLocaisCardController;
import com.example.oporto_olympics.Models.Local;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListagemLocaisController {

    @FXML
    private VBox LocaisContainer;

    @FXML
    private Button VoltarBtn;


    /**
     *
     * Esta função estabelece uma conexão com a base de dados, busca todos os eventos
     *  e carrega e popula dinamicamente nos cards de eventos num contêiner VBox.
     *
     * @throws SQLException se ocorrer um erro de acesso a base de dados
     */
    public void initialize() throws SQLException{

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        LocaisDAOImp LocalDAO = new LocaisDAOImp(conexao);

        List<Local> AllLocais = LocalDAO.getAll();

        for (Local local : AllLocais) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/ListagemLocais/Cards/ListagemLocaisCard.fxml"));
                Pane Locais = loader.load();
                ListagemLocaisCardController cardsController = loader.getController();
                cardsController.PreencherDados(local);

                Locais.setUserData(local);

                LocaisContainer.getChildren().add(Locais);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
