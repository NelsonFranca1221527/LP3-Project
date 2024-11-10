package com.example.oporto_olympics.Controllers.ListagemEventos;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.ListagemEventos.CardController.ListagemEventosCardController;
import com.example.oporto_olympics.Models.Evento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListagemEventosController {

    @FXML
    private VBox EventosContainer;

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

        EventosDAOImp EventoDAO = new EventosDAOImp(conexao);

        List<Evento> AllEventos = EventoDAO.getAll();

        for (Evento evento : AllEventos) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/ListagemEventos/Cards/ListagemEventoCard.fxml"));
                Pane Eventos = loader.load();
                ListagemEventosCardController cardsController = loader.getController();
                cardsController.PreencherDados(evento);

                Eventos.setUserData(evento);

                EventosContainer.getChildren().add(Eventos);

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
