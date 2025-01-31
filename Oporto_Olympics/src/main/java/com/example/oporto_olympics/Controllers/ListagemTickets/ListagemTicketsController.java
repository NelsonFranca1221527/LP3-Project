package com.example.oporto_olympics.Controllers.ListagemTickets;

import com.example.oporto_olympics.API.ConnectAPI.ConnectionAPI;
import com.example.oporto_olympics.API.DAO.Jogos.JogosDAOImp;
import com.example.oporto_olympics.API.DAO.Tickets.TicketsDAOImp;
import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.API.Models.Jogo;
import com.example.oporto_olympics.API.Models.TicketInfo;
import com.example.oporto_olympics.Controllers.ListagemTickets.CardController.ListagemTicketsCardController;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.Local;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.*;

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
     * ChoiceBox para selecionar um jogo da lista.
     */
    @FXML
    private ChoiceBox<String> ChoiceJogo;
    /**
     * Mapa que armazena o nome dos jogos e os respetivos IDs.
     */
    private HashMap<String,String> jogoMap = new HashMap<>();
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

        JogosDAOImp jogosDAOImp = new JogosDAOImp(httpURLConnection);

        List<Jogo> jogoList = jogosDAOImp.getAll();

        for (Jogo jogo : jogoList) {
            jogoMap.put(jogo.getDesporto() + " - " + jogo.getLocal(), jogo.getId());
        }

        ChoiceJogo.setItems(FXCollections.observableArrayList(jogoMap.keySet()));
        ChoiceJogo.getItems().add("-------");
        ChoiceJogo.setValue("-------");

        TicketsDAOImp ticketsDAOImp = new TicketsDAOImp(httpURLConnection);

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        ClientSingleton ClienteSingle = ClientSingleton.getInstance();

        //Verifica se o jogo escolhido foi alterado
        ChoiceJogo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            Label SemBilhetes = new Label("Não possui bilhetes para este Jogo!");
            SemBilhetes.paddingProperty().set(new Insets(10));
            Label SemJogoSelecionado = new Label("Selecione um jogo para ver os bilhetes!!");
            SemJogoSelecionado.paddingProperty().set(new Insets(10));

            TicketsContainer.getChildren().clear();

            List<TicketInfo> list = new ArrayList<>();

            if(newValue.equals("-------")){
                TicketsContainer.getChildren().add(SemJogoSelecionado);
                return;
            }

            String gameID = jogoMap.get(newValue);

            try {
                Optional<List<TicketInfo>> ticketListByGame = ticketsDAOImp.getbyGame(gameID);

                if (!ticketListByGame.isPresent()) {
                    TicketsContainer.getChildren().add(SemBilhetes);
                    return;
                }

                //Verifica se o Utilizador é um Cliente
                if (ClienteSingle.getClient() != null) {

                    String ClienteID = ClienteSingle.getClient().getId();

                    //Lista de Todos os Tickets de todos Jogos
                    List<TicketInfo> gameTickets = ticketListByGame.get();

                    Optional<List<TicketInfo>> ticketList = ticketsDAOImp.getbyClient(ClienteID);

                    if (ticketList.isEmpty()) {
                        TicketsContainer.getChildren().add(SemBilhetes);
                        return;
                    }

                    //Lista de Todos os Tickets do Cliente
                    List<TicketInfo> clientTickets = ticketList.get();


                    Iterator<TicketInfo> iterator = gameTickets.iterator();
                    while (iterator.hasNext()) {
                        TicketInfo gameTicket = iterator.next();

                        boolean TicketEncontrado = false;

                        for (TicketInfo clientTicket : clientTickets) {
                            /*Se encontrar o ticket do cliente para o jogo indicado não o remove
                            caso contrário remove o ticket (que estava a ser lido) da lista de tickets de todos os jogos*/
                            if (gameTicket.getId().equals(clientTicket.getId())) {
                                TicketEncontrado = true;
                                break;
                            }
                        }

                        if (!TicketEncontrado) {
                            iterator.remove();
                        }
                    }

                    if (!gameTickets.isEmpty()) {
                        list = gameTickets;
                    }
                }

                //Verifica se o Utilizador é um Gestor
                if(GestorSingle.getGestor() != null ) {
                    if(!ticketListByGame.get().isEmpty()) {
                        list = ticketListByGame.get();
                    }
                }

                if(!list.isEmpty()) {
                    ListarCards(list);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Este método recebe uma lista de tickets e cria um card para cada ticket,
     * exibindo os dados do ticket na interface gráfica.
     *
     * @param listaTickets A lista de tickets a ser exibida como cards na interface.
     */
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
