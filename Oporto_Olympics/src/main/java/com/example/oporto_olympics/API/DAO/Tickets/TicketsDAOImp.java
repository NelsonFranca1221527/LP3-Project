package com.example.oporto_olympics.API.DAO.Tickets;

import com.example.oporto_olympics.API.ConnectAPI.ConnectionAPI;
import com.example.oporto_olympics.API.Models.Ticket;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketsDAOImp implements TicketsDAO<Ticket> {

    private HttpURLConnection connection;

    public TicketsDAOImp(HttpURLConnection conection) {
        this.connection = conection;
    }


    /**
     * Obtém todos os objetos do tipo {@code T} armazenados.
     *
     * @return Uma lista de objetos {@code T} que representam todos os dados armazenados.
     */
    @Override
    public List<Ticket> getAll() {
        return List.of();
    }

    /**
     * Guarda um objeto do tipo {@code T} no armazenamento.
     *
     * @param ticket O objeto a ser guardado.
     */
    @Override
    public void save(Ticket ticket) throws IOException {

        String url = ConnectionAPI.getInstance().getURL() + "Ticket";

        URL obj = new URL(url);

        connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("POST");

        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");

        connection.setRequestProperty("Authorization", "Basic "+ ConnectionAPI.getInstance().getBase64Auth());

        String corpo = "{"
                + "\"ClientId\":\"" + ticket.getClientID() + "\","
                + "\"GameId\":\"" + ticket.getGameID() + "\","
                + "\"Seat\":\"" + ticket.getLugar() + "\","
                + "}";

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(corpo);
        wr.flush();
        wr.close();

        int responseCode = connection.getResponseCode();
        System.out.println("Código de resposta: " + responseCode);

        BufferedReader in;
        if (responseCode == 201) {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        System.out.println(response);
        in.close();
    }

    /**
     * Atualiza os dados de um objeto do tipo {@code T} no armazenamento.
     *
     * @param ticket O objeto com os novos dados a serem atualizados.
     */
    @Override
    public void update(Ticket ticket) {

    }

    /**
     * Elimina um objeto do tipo {@code T} do armazenamento.
     *
     * @param ticket O objeto a ser removido.
     */
    @Override
    public void delete(Ticket ticket) {

    }

    /**
     * Obtém um objeto do tipo {@code T} através de um identificador único de um Cliente.
     *
     * @param i O identificador único do objeto (geralmente o ID).
     * @return Um {@link Optional} contendo o objeto {@code T} se encontrado, ou vazio caso não exista.
     */
    @Override
    public Optional<Ticket> getbyClient(int i) {
        return Optional.empty();
    }

    /**
     * Obtém um objeto do tipo {@code T} através de um identificador único de um Jogo.
     *
     * @param i O identificador único do objeto (geralmente o ID).
     * @return Um {@link Optional} contendo o objeto {@code T} se encontrado, ou vazio caso não exista.
     */
    @Override
    public Optional<List<Ticket>> getbyGame(String i) throws IOException {

        String url = ConnectionAPI.getInstance().getURL() + "ticket/game/" + i;

        URL obj = new URL(url);

        connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");

        connection.setRequestProperty("Authorization", "Basic " + ConnectionAPI.getInstance().getBase64Auth());

        int responseCode = connection.getResponseCode();
        System.out.println("Código de Resposta: " + responseCode);

        //Verifica se o código de resposta é igual a 200
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.toString());

            List<Ticket> tickets = new ArrayList<>();

            if (rootNode.has("TicketInfo") && rootNode.get("TicketInfo").isArray()) {

                ArrayNode ticketInfoArray = (ArrayNode) rootNode.get("TicketInfo");

                for (JsonNode node : ticketInfoArray) {
                    if (node.has("Seat")) {
                        int seat = node.get("Seat").asInt();
                        Ticket ticket = new Ticket(null, null, seat);
                        tickets.add(ticket);
                    }
                }

                return Optional.of(tickets);
            }
        }

        return Optional.empty();
    }
}
