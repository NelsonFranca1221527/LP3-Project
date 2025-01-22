package com.example.oporto_olympics.API.DAO.Jogos;

import com.example.oporto_olympics.API.ConnectAPI.ConnectionAPI;
import com.example.oporto_olympics.API.Models.Jogo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class JogosDAOImp implements JogosDAO<Jogo> {

    private HttpURLConnection connection;

    public JogosDAOImp(HttpURLConnection conection) {
        this.connection = conection;
    }

    /**
     * Obtém todos os objetos do tipo {@code T} armazenados.
     *
     * @return Uma lista de objetos {@code T} que representam todos os dados armazenados.
     */
    @Override
    public List<Jogo> getAll() {
        return List.of();
    }

    /**
     * Guarda um objeto do tipo {@code T} no armazenamento.
     *
     * @param jogo O objeto a ser guardado.
     */
    @Override
    public String save(Jogo jogo) throws IOException {

        String url = ConnectionAPI.getInstance().getURL() + "Game";

        URL obj = new URL(url);

        connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("POST");

        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");

        connection.setRequestProperty("Authorization", "Basic "+ ConnectionAPI.getInstance().getBase64Auth());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        if(jogo.getCapacidade() <=0){
            jogo.setCapacidade(999999999);
        }

        String corpo = "{"
                + "\"StartDate\":\"" + jogo.getDataInicio().format(formatter) + "\","
                + "\"EndDate\":\"" + jogo.getDataFim().format(formatter) + "\","
                + "\"Location\":\"" + jogo.getLocal() + "\","
                + "\"Sport\":\"" + jogo.getDesporto() + "\","
                + "\"Capacity\": " + jogo.getCapacidade() + ","
                + "\"EventId\": " + jogo.getEventoID()
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

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.toString());

        in.close();

        if (rootNode.has("Game")) {
            String gameId = rootNode.get("Game").asText();
            return gameId;
        }

        return null;
    }

    /**
     * Atualiza os dados de um objeto do tipo {@code T} no armazenamento.
     *
     * @param jogo O objeto com os novos dados a serem atualizados.
     */
    @Override
    public void update(Jogo jogo) {

    }

    /**
     * Elimina um objeto do tipo {@code T} do armazenamento.
     *
     * @param jogo O objeto a ser removido.
     */
    @Override
    public void delete(Jogo jogo) {

    }

    /**
     * Obtém um objeto do tipo {@code T} através de um identificador único.
     *
     * @param i O identificador único do objeto (geralmente o ID).
     * @return Um {@link Optional} contendo o objeto {@code T} se encontrado, ou vazio caso não exista.
     */
    @Override
    public Optional<Jogo> get(int i) {
        return Optional.empty();
    }
}
