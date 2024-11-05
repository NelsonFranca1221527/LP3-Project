package com.example.oporto_olympics.Controllers.ListagemModalidades;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Controllers.ListagemModalidades.CardController.ListagemModalidadesCardController;
import com.example.oporto_olympics.Models.Modalidade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListagemModalidadesController {

    @FXML
    private VBox ModalidadesContainer;

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

        ModalidadeDAOImp ModalidadeDAO = new ModalidadeDAOImp(conexao);

        List<Modalidade> AllModalidades = ModalidadeDAO.getAll();

        for (Modalidade modalidade : AllModalidades) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/ListagemModalidades/Cards/ListagemModalidadesCard.fxml"));
                Pane Locais = loader.load();
                ListagemModalidadesCardController cardsController = loader.getController();
                cardsController.PreencherDados(modalidade);

                Locais.setUserData(modalidade);

                ModalidadesContainer.getChildren().add(Locais);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
