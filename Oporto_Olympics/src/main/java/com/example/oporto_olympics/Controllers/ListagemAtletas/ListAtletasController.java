package com.example.oporto_olympics.Controllers.ListagemAtletas;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.ListagemAtletas.CardController.ListAtletaCardController;
import com.example.oporto_olympics.HelloApplication;
import com.example.oporto_olympics.Models.Atleta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListAtletasController {

    @FXML
    private VBox AtletasContainer;

    @FXML
    private Button VoltarBtn;


    /**
     *
     * Esta função estabelece uma conexão com a base de dados, busca todos os atletas
     *  e carrega e popula dinamicamente nos cards de atletas num contêiner VBox.
     *
     * @throws SQLException se ocorrer um erro de acesso a base de dados
     */
    public void initialize() throws SQLException{

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        AtletaDAOImp AtletaDAO = new AtletaDAOImp(conexao);

        List<Atleta> AllAtletas = AtletaDAO.getAll();

        for (Atleta atleta : AllAtletas) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/ListagemAtletas/Cards/ListAtletaCard.fxml"));
                Pane Atletas = loader.load();
                ListAtletaCardController cardsController = loader.getController();
                cardsController.PreencherDados(atleta);

                Atletas.setUserData(atleta);

                AtletasContainer.getChildren().add(Atletas);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Evento para o botão "Voltar". Este método é chamado quando o utilizador clica no
     * botão, permitindo assim ao utilizador voltar para a página anterior.
     *
     * @param event O evento de ação que desencadeia o método, gerado pelo clique no botão.
     */
    @FXML
    private void onActionBack(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
