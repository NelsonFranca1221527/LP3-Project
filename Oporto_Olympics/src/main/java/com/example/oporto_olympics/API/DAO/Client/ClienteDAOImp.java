package com.example.oporto_olympics.API.DAO.Client;

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
import java.util.ArrayList;
import java.util.List;

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




    /**
     * Insere um novo cliente no sistema.
     *
     * @param name     Nome do cliente.
     * @param email    Endereço de e-mail do cliente.
     * @param password Palavra-passe do cliente.
     * @return Mensagem indicando o sucesso ou falha da operação.
     * @throws IOException Se ocorrer um erro durante a comunicação com o servidor.
     */
    public String insertClient(String name, String email, String password) throws IOException {
        String baseUrl = ConnectionAPI.getInstance().getURL();
        String url = baseUrl + "client/insert";

        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + ConnectionAPI.getInstance().getBase64Auth());

        String jsonInputString = String.format("{\"Name\":\"%s\", \"Email\":\"%s\", \"Password\":\"%s\"}", name, email, password);

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(jsonInputString);
            wr.flush();
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Código de resposta HTTP: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            return "Cliente inserido com sucesso!";
        } else {
            return "Erro ao inserir cliente: " + connection.getResponseMessage();
        }
    }
    /**
     * Recupera a lista de todos os clientes a partir da API.
     *
     * Este método envia uma solicitação GET para a API, recebe a resposta em formato JSON
     * e a converte para uma lista de objetos {@link Client}. Se a resposta da API for bem-sucedida
     * e contiver a chave "Status" com valor "OK", os dados dos clientes serão extraídos e retornados.
     * Caso contrário, será gerado um erro e uma exceção {@link IOException} será lançada.
     *
     * @return Uma lista de objetos {@link Client} com os dados dos clientes recuperados da API.
     *
     * @throws IOException Se ocorrer um erro ao fazer a solicitação ou se o status da resposta não for "OK".
     */
    @Override
    public List<Client> getAllClients() throws IOException {

        String baseUrl = ConnectionAPI.getInstance().getURL();
        String url = baseUrl + "client";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + ConnectionAPI.getInstance().getBase64Auth());

        int responseCode = connection.getResponseCode();
        System.out.println("Código de resposta HTTP: " + responseCode);

        BufferedReader in;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.toString());

        if (rootNode.has("Status") && "OK".equals(rootNode.get("Status").asText())) {
            JsonNode clientsNode = rootNode.get("Clients");

            List<Client> clients = new ArrayList<>();
            for (JsonNode clientNode : clientsNode) {
                Client client = new Client(
                        clientNode.get("Id").asText(),
                        clientNode.get("GroupId").asText(),
                        clientNode.get("Name").asText(),
                        clientNode.get("Email").asText(),
                        clientNode.get("Active").asBoolean()
                );
                clients.add(client);
            }

            return clients;
        } else {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Erro ao buscar clientes.",
                    "Não foi possível recuperar os clientes.");
            alertHandler.getAlert().show();
            throw new IOException("Erro ao obter clientes: Status não OK.");
        }
    }
    /**
     * Remove um cliente da API com base no ID fornecido.
     *
     * Este método envia uma solicitação DELETE para a API, com o ID do cliente que se deseja remover.
     * Se a resposta da API for bem-sucedida (código de resposta HTTP 200), o cliente será removido e
     * uma mensagem de sucesso será retornada. Caso contrário, uma mensagem de erro será retornada.
     *
     * @param id O ID do cliente a ser removido.
     *
     * @return Uma mensagem indicando o sucesso ou falha na remoção do cliente.
     *
     * @throws IOException Se ocorrer um erro ao fazer a solicitação ou ao processar a resposta.
     */
    @Override
    public String removeClient(String id) throws IOException {
        String baseUrl = ConnectionAPI.getInstance().getURL();
        String url = baseUrl + "client/" + id;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + ConnectionAPI.getInstance().getBase64Auth());

        int responseCode = connection.getResponseCode();
        System.out.println("Código de resposta HTTP: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Cliente removido com sucesso!";

        } else if(responseCode == 406){
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", "O Cliente tem um ticket associado, por isso não é permitido remover!");
            alertHandler.getAlert().show();
        }else {
            return "Erro ao remover cliente: " + connection.getResponseMessage();
        }

        return null;
    }
    /**
     * Remove um cliente da API com base no ID fornecido.
     *
     * Este método envia uma solicitação DELETE para a API, com o ID do cliente que se deseja remover.
     * Se a resposta da API for bem-sucedida (código de resposta HTTP 200), o cliente será removido e
     * uma mensagem de sucesso será retornada. Caso contrário, uma mensagem de erro será retornada.
     *
     * @param id O ID do cliente a ser removido.
     *
     * @return Uma mensagem indicando o sucesso ou falha na remoção do cliente.
     *
     * @throws IOException Se ocorrer um erro ao fazer a solicitação ou ao processar a resposta.
     */
    @Override
    public String UpdatePassword(String id, String password) throws IOException {
        String baseUrl = ConnectionAPI.getInstance().getURL();
        String url = baseUrl + "client/" + id;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + ConnectionAPI.getInstance().getBase64Auth());

        String jsonInputString = String.format("{\"Password\":\"%s\"}", password);
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(jsonInputString);
            wr.flush();
        }
        int responseCode = connection.getResponseCode();
        System.out.println("Código de resposta HTTP: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Password alterada com sucesso!";
        } else if(responseCode == 202){
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Password alterada", "A sua password foi atualizada com sucesso!");
            alertHandler.getAlert().show();
        }else {
            return "Erro ao alterar password: " + connection.getResponseMessage();
        }
        return null;
    }

    @Override
    public String BanClient(String id) throws IOException {
        String baseUrl = ConnectionAPI.getInstance().getURL();
        String url = baseUrl + "client/" + id;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + ConnectionAPI.getInstance().getBase64Auth());

        String jsonInputString = String.format("{\"Active\":\"%s\"}", "false");
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(jsonInputString);
            wr.flush();
        }
        int responseCode = connection.getResponseCode();
        System.out.println("Código de resposta HTTP: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Concluído", "O espectador foi desativado com sucesso");
            alertHandler.getAlert().show();
            return "Password alterada com sucesso!";
        } else {
            return "Erro ao ao banir espectador: " + connection.getResponseMessage();
        }
    }

    @Override
    public String UnBanClient(String id) throws IOException {
        String baseUrl = ConnectionAPI.getInstance().getURL();
        String url = baseUrl + "client/" + id;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + ConnectionAPI.getInstance().getBase64Auth());

        String jsonInputString = String.format("{\"Active\":\"%s\"}", "true");
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(jsonInputString);
            wr.flush();
        }
        int responseCode = connection.getResponseCode();
        System.out.println("Código de resposta HTTP: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Concluído", "O espectador foi desativado com sucesso");
            alertHandler.getAlert().show();
            return "Password alterada com sucesso!";
        } else {
            return "Erro ao ao banir espectador: " + connection.getResponseMessage();
        }
    }



}
