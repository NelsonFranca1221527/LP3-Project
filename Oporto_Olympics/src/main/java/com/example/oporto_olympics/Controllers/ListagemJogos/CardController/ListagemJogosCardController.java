package com.example.oporto_olympics.Controllers.ListagemJogos.CardController;

import com.example.oporto_olympics.API.ConnectAPI.ConnectionAPI;
import com.example.oporto_olympics.API.DAO.Tickets.TicketsDAOImp;
import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.API.Models.Jogo;
import com.example.oporto_olympics.API.Models.Ticket;
import com.example.oporto_olympics.API.Models.TicketInfo;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.*;

/**
 * Controlador para os cartões individuais de listagem de jogos.
 * Esta classe é responsável por preencher os dados dos jogos na interface e gerir a interação com o botão de inscrição.
 */
public class ListagemJogosCardController {

    /**
     * Etiqueta que exibe a capacidade do local onde o jogo será realizado.
     */
    @FXML
    private Label CapacidadeLocal;

    /**
     * Etiqueta que exibe a data de fim do jogo.
     */
    @FXML
    private Label DataFimLabel;

    /**
     * Etiqueta que exibe a data de início do jogo.
     */
    @FXML
    private Label DataInicioLabel;

    /**
     * Etiqueta que exibe o desporto associado ao jogo.
     */
    @FXML
    private Label DesportoLabel;

    /**
     * Botão para inscrever-se no jogo.
     */
    @FXML
    private Button InscreverJogoButton;

    /**
     * Etiqueta que exibe o local do jogo.
     */
    @FXML
    private Label LocalLabel;

    /**
     * Jogo específico associado ao cartão.
     */
    private Jogo jogoEspecifico;

    /**
     * Obtém o jogo específico associado ao cartão.
     *
     * @return o jogo específico.
     */
    public Jogo getJogoEspecifico() {
        return jogoEspecifico;
    }

    /**
     * Define o jogo específico associado ao cartão.
     *
     * @param jogoEspecifico o jogo a ser associado ao cartão.
     */
    public void setJogoEspecifico(Jogo jogoEspecifico) {
        this.jogoEspecifico = jogoEspecifico;
    }

    /**
     * Evento acionado ao clicar no botão de inscrição no jogo.
     * Este método verifica as condições de capacidade do evento e, se permitido,
     * inscreve o cliente no jogo.
     *
     * @param event evento de clique no botão.
     * @throws SQLException caso ocorra um erro na comunicação com a base de dados.
     * @throws IOException caso ocorra um erro de entrada/saída.
     */
    @FXML
    void onClickInscreverJogoButton(ActionEvent event) throws SQLException, IOException {

        AlertHandler alertHandler;

        alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Inscrever na Modalidade!!!", "Deseja inscrever-se para ver a modalidade " + DesportoLabel.getText() + " ? \n");
        alertHandler.getAlert().getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alertHandler.getAlert().getDialogPane().setMaxHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> rs = alertHandler.getAlert().showAndWait();

        if (rs.isPresent() && rs.get() != ButtonType.OK) {
            return;
        }

        ConnectionAPI connectionAPI = new ConnectionAPI();
        HttpURLConnection httpURLConnection = connectionAPI.getConexao();

        TicketsDAOImp ticketsDAOImp = new TicketsDAOImp(httpURLConnection);

        Jogo jogo = getJogoEspecifico();

        String GameID = jogo.getId();

        Optional<List<TicketInfo>> list = ticketsDAOImp.getbyGame(GameID);

        int quantidadeLugares = 0;

        if (list.isPresent()) {
            quantidadeLugares = list.get().size();
        }

        Client client = ClientSingleton.getInstance().getClient();

        String ClienteID = client.getId();

        //Verifica se o local é exterior, caso seja, permite criar um ticket
        if (Integer.parseInt(CapacidadeLocal.getText()) <= 0) {

            ticketsDAOImp.save(new Ticket("",ClienteID, GameID, quantidadeLugares + 1));
            alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Inscrição Bem Sucedida!!", "A inscrição para o jogo " + DesportoLabel.getText() + " foi realizada com Sucesso!!");
            alertHandler.getAlert().showAndWait();
            return;
        }

        //Verifica se ao gerar mais 1 ticket ultrapaçasse a capacidade
        if (quantidadeLugares + 1 > Integer.parseInt(CapacidadeLocal.getText())) {
            alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Capacidade Máxima Alcançada!!", "Lamento mas o evento selecionado já está cheio!!");
            alertHandler.getAlert().showAndWait();
            return;
        }

        ticketsDAOImp.save(new Ticket("",ClienteID, GameID, quantidadeLugares + 1));
        alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Inscrição Bem Sucedida!!", "A inscrição para o jogo " + DesportoLabel.getText() + " foi realizada com Sucesso!!");
        alertHandler.getAlert().showAndWait();
    }

    /**
     * Preenche os dados do cartão com as informações de um jogo.
     * Este método é chamado durante a inicialização para exibir os detalhes do jogo na interface.
     *
     * @param jogo o jogo cujos dados serão exibidos.
     * @throws SQLException caso ocorra um erro ao processar os dados.
     */
    public void PreencherDados(Jogo jogo) throws SQLException {

        DataInicioLabel.setText(jogo.getDataInicio().toString());
        DataFimLabel.setText(jogo.getDataFim().toString());
        LocalLabel.setText(jogo.getLocal());
        CapacidadeLocal.setText(String.valueOf(jogo.getCapacidade()));
        DesportoLabel.setText(jogo.getDesporto());

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        ClientSingleton ClienteSingle = ClientSingleton.getInstance();

        setJogoEspecifico(jogo);

        //Verifica se o Utilizador é um Gestor
        if (GestorSingle.getGestor() != null && ClienteSingle.getClient() == null) {
            InscreverJogoButton.setDisable(true);
            InscreverJogoButton.setVisible(false);
        }
    }
}
