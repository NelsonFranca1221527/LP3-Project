package com.example.oporto_olympics.Controllers.ListagemTickets;

import com.example.oporto_olympics.API.ConnectAPI.ConnectionAPI;
import com.example.oporto_olympics.API.DAO.Jogos.JogosDAOImp;
import com.example.oporto_olympics.API.DAO.Tickets.TicketsDAOImp;
import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.API.Models.Jogo;
import com.example.oporto_olympics.API.Models.TicketInfo;
import com.example.oporto_olympics.Controllers.ListagemTickets.CardController.ListagemTicketsCardController;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador responsável pela listagem de tickets de um cliente.
 * Este controlador é utilizado para buscar e exibir os tickets de um cliente na interface gráfica.
 */
public class ListagemTicketsController {

    /**
     * Container {@link VBox} que contém os cards de tickets.
     */
    @FXML
    private VBox TicketsContainer;

    /**
     * Botão que permite voltar à tela anterior.
     */
    @FXML
    private Button VoltarBtn;

    /**
     * Método de inicialização responsável por buscar e exibir os tickets do cliente.
     * Este método é chamado quando a tela é carregada. Ele busca os tickets do cliente
     * a partir da API e os exibe em forma de cards.
     *
     * @throws SQLException Se ocorrer um erro de SQL ao buscar os tickets.
     * @throws IOException Se ocorrer um erro de I/O ao carregar o card de ticket.
     */
    public void initialize() throws SQLException, IOException {
        ConnectionAPI connectionAPI = new ConnectionAPI();
        HttpURLConnection httpURLConnection = connectionAPI.getConexao();

        TicketsDAOImp ticketsDAOImp = new TicketsDAOImp(httpURLConnection);

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        ClientSingleton ClienteSingle = ClientSingleton.getInstance();

        List<TicketInfo> list = new ArrayList<>();

        //Verifica se o Utilizador é um Cliente
        if (ClienteSingle.getClient() != null) {

            String ClienteID = ClienteSingle.getClient().getId();

            Optional<List<TicketInfo>> ticketList = ticketsDAOImp.getbyClient(ClienteID);

            if(ticketList.isPresent()) {
                list = ticketList.get();
            }

        }

        //Verifica se o Utilizador é um Gestor
        if(GestorSingle.getGestor() != null ) {

            JogosDAOImp jogosDAOImp = new JogosDAOImp(httpURLConnection);

             List<Jogo> jogosList = jogosDAOImp.getAll();

             for (Jogo jogo : jogosList){

                 Optional<List<TicketInfo>> ticketList = ticketsDAOImp.getbyGame(jogo.getId());

                 if(ticketList.isPresent()) {
                     list = ticketList.get();
                 }
             }
        }

        if(!list.isEmpty()) {
            ListarCards(list);
        }
    }
    
    public void ListarCards(List<TicketInfo> listaTickets) {
        for (TicketInfo ticket : listaTickets) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/ListagemTickets/Cards/ListagemTicketsCard.fxml"));
                Pane Tickets = loader.load();
                ListagemTicketsCardController cardsController = loader.getController();
                cardsController.PreencherDados(ticket);

                Tickets.setUserData(ticket);

                TicketsContainer.getChildren().add(Tickets);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método acionado quando o botão "Voltar" é pressionado.
     * Este método redireciona o usuário para a tela anterior, dependendo do tipo de usuário (gestor ou cliente).
     *
     * @param event O evento de ação (pressionamento do botão).
     */
    @FXML
    void onActionBack(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        ClientSingleton ClienteSingle = ClientSingleton.getInstance();

        if (GestorSingle.getGestor() != null) {
            RedirecionarHelper.GotoSubMenuListagens().switchScene(s);
        }

        if (ClienteSingle.getClient() != null) {
            RedirecionarHelper.GotoHomeClient().switchScene(s);
        }
    }

}
