package com.example.oporto_olympics.Controllers.ListagemTickets.CardController;

import com.example.oporto_olympics.API.ConnectAPI.ConnectionAPI;
import com.example.oporto_olympics.API.DAO.Tickets.TicketsDAOImp;
import com.example.oporto_olympics.API.Models.Ticket;
import com.example.oporto_olympics.API.Models.TicketInfo;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

/**
 * Controlador responsável pela exibição de informações de um ticket no formato de um card.
 * Este controlador é usado para preencher os dados de um ticket e exibi-los em uma interface gráfica.
 */
public class ListagemTicketsCardController {

    /**
     * Label que exibe a data de início do jogo.
     */
    @FXML
    private Label DataInicioLabel;

    /**
     * Label que exibe a data de fim do jogo.
     */
    @FXML
    private Label DataFimLabel;

    /**
     * Label que exibe o local do evento do ticket.
     */
    @FXML
    private Label LocalLabel;

    /**
     * Label que exibe o número do assento ou lugar do ticket.
     */
    @FXML
    private Label LugarLabel;

    /**
     * Botão para eliminar um bilhete.
     */
    @FXML
    private Button EliminarTicketButton;

    /**
     * ImageView que exibe o código QR do ticket.
     */
    @FXML
    private ImageView CodigoQR;

    /**
     * Armazena as informações de um bilhete específico.
     */
    private TicketInfo ticketEspecifico;

    /**
     * Retorna o bilhete selecionado.
     *
     * @return O bilhete específico.
     */
    public TicketInfo getTicketEspecifico() {
        return ticketEspecifico;
    }

    /**
     * Define o bilhete selecionado.
     *
     * @param ticketEspecifico O bilhete a ser definido.
     */
    public void setTicketEspecifico(TicketInfo ticketEspecifico) {
        this.ticketEspecifico = ticketEspecifico;
    }
    /**
     * Preenche os dados do card com as informações do ticket fornecido.
     * Este método é responsável por definir os valores das labels de data, local, lugar
     * e exibir o código QR do ticket.
     *
     * @param ticket o objeto {@link TicketInfo} contendo as informações do ticket.
     */
    public void PreencherDados(TicketInfo ticket) {

        DataInicioLabel.setText(ticket.getDataInicio());
        DataFimLabel.setText(ticket.getDataFim());
        LocalLabel.setText(ticket.getLocal());
        LugarLabel.setText(String.valueOf(ticket.getLugar()));

        String base64Image = ticket.getCodigoQR();

        if (base64Image.startsWith("data:image/bmp;base64,")) {
            base64Image = base64Image.replace("data:image/bmp;base64,", "");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
        Image image = new Image(inputStream);

        CodigoQR.setImage(image);
        CodigoQR.setPreserveRatio(true);

        setTicketEspecifico(ticket);
    }

    /**
     * Exclui o bilhete selecionado após confirmação e atualiza a página.
     *
     * @param event Evento do clique no botão.
     * @throws IOException  Se ocorrer um erro ao redirecionar a tela.
     * @throws SQLException Se ocorrer um erro ao excluir o bilhete do banco.
     */
    @FXML
    void OnClickEliminarTicketButton(ActionEvent event) throws IOException, SQLException {

        AlertHandler alertHandler;

        TicketInfo ticketInfo = getTicketEspecifico();

        alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Eliminar Bilhete!!!", "Deseja eliminar o bilhete com o local " + ticketInfo.getLocal() + "?");
        Optional<ButtonType> rs = alertHandler.getAlert().showAndWait();

        if (rs.isPresent() && rs.get() != ButtonType.OK) {
            return;
        }

        ConnectionAPI connectionAPI = new ConnectionAPI();
        HttpURLConnection httpURLConnection = connectionAPI.getConexao();

        TicketsDAOImp ticketsDAOImp = new TicketsDAOImp(httpURLConnection);

        ticketsDAOImp.delete(new Ticket(ticketInfo.getId(),"","",ticketInfo.getLugar()));

        alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Eliminado com Sucesso!!!", "Bilhete eliminado com Sucesso!");
        alertHandler.getAlert().showAndWait();

        Stage s = (Stage) EliminarTicketButton.getScene().getWindow();

        RedirecionarHelper.GotoListagemTickets().switchScene(s);
    }
}
