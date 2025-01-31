package com.example.oporto_olympics.Controllers.ListagemTickets.CardController;

import com.example.oporto_olympics.API.Models.Ticket;
import com.example.oporto_olympics.API.Models.TicketInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.util.Base64;

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
     * ImageView que exibe o código QR do ticket.
     */
    @FXML
    private ImageView CodigoQR;

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
    }
}
