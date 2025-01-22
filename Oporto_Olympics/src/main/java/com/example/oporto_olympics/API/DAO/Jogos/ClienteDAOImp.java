package com.example.oporto_olympics.API.DAO.Jogos;

import com.example.oporto_olympics.API.ConnectAPI.ConnectionAPI;
import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementação da interface ClienteDAO para validar as credenciais do cliente.
 * Esta classe utiliza uma conexão HTTP para comunicar-se com a API de autenticação de clientes.
 */
public class ClienteDAOImp implements ClienteDAO {

    private HttpURLConnection connection;
    /**
     * Construtor que aceita uma ligação HTTP existente.
     *
     * @param conection Uma instância de HttpURLConnection.
     */
    public ClienteDAOImp(HttpURLConnection conection) {
        this.connection = conection;
    }
    /**
     * Construtor padrão sem parâmetros.
     */
    public ClienteDAOImp() {
    }


    /**
     * Valida as credenciais de um cliente enviando uma solicitação POST para a API.
     *
     * @param email    O e-mail do cliente.
     * @param password A palavra-passe do cliente.
     * @return Uma mensagem indicando o sucesso da validação das credenciais.
     * @throws IOException Se ocorrer um erro durante a comunicação com a API.
     */
    @Override
    public String ValidateCredentials(String email, String password) throws IOException {
        String baseUrl = ConnectionAPI.getInstance().getURL();
        String url = baseUrl + "client/login";


        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + ConnectionAPI.getInstance().getBase64Auth());

        String jsonInputString = "{ \"Email\": \"" + email + "\", \"Password\": \"" + password + "\" }";
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(jsonInputString);
            wr.flush();
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Código de resposta HTTP: " + responseCode);

        BufferedReader in;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.toString());


        if (rootNode.has("Status") && "OK".equals(rootNode.get("Status").asText())) {
            JsonNode clientNode = rootNode.get("Client").get(0);

            Client client = new Client(
                    clientNode.get("Id").asText(),
                    clientNode.get("GroupId").asText(),
                    clientNode.get("Name").asText(),
                    clientNode.get("Email").asText(),
                    clientNode.get("Active").asBoolean()
            );

            ClientSingleton.getInstance().setClient(client);
            return "Credenciais validadas com sucesso";
        } else {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Credenciais inválidas.",
                    "As credenciais que inseriu são inválidas!");
            alertHandler.getAlert().show();
            throw new IOException("Erro ao validar credenciais: Status não OK.");
        }
    }
}
