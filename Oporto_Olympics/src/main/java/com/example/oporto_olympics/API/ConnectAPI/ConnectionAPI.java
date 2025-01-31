package com.example.oporto_olympics.API.ConnectAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Base64;

/**
 * A classe {@link ConnectionAPI} implementa um Singleton responsável por estabelecer uma conexão
 * com a API externa, utilizando autenticação básica com credenciais codificadas em Base64.
 * <p>
 * As credenciais e a URL da API são carregadas a partir de um ficheiro local chamado "PasswordAPI.txt".
 */
public class ConnectionAPI {

    private HttpURLConnection conexao = null;
    private String URL;
    private String Base64Auth;
    private static ConnectionAPI instancia = null;

    /**
     * Construtor que inicializa a conexão com a API, carregando as credenciais do ficheiro
     * e codificando-as em Base64 para autenticação.
     *
     * @throws IOException Se ocorrer um erro ao carregar as credenciais da API.
     */
    public ConnectionAPI() throws IOException {
        try {
            ConnectionAPI.APIInfo loadApi = load();

            // Definir as credenciais e URL
            String user = loadApi.user;
            String pass = loadApi.pass;
            setURL(loadApi.url);

            // Codificar as credenciais em Base64 para autenticação HTTP básica
            String valueToEncode = user + ":" + pass;
            setBase64Auth(Base64.getEncoder().encodeToString(valueToEncode.getBytes()));

            System.out.println("Conexão à API estabelecida com sucesso.");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    /**
     * Obtém a instância única do Singleton {@link ConnectionAPI}.
     * Se a instância ainda não existir, será criada uma nova.
     *
     * @return A instância única de {@link ConnectionAPI}.
     * @throws IOException Se ocorrer um erro ao inicializar a conexão.
     */
    public static ConnectionAPI getInstance() throws IOException {
        if (instancia == null) {
            instancia = new ConnectionAPI();
        }
        return instancia;
    }

    /**
     * Obtém a conexão atual com a API.
     *
     * @return A instância de {@link HttpURLConnection}, caso exista.
     */
    public HttpURLConnection getConexao() {
        return conexao;
    }

    /**
     * Obtém a URL da API.
     *
     * @return A URL da API.
     */
    public String getURL() {
        return URL;
    }

    /**
     * Define a URL da API.
     *
     * @param URL A URL da API.
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * Obtém a autenticação Base64 gerada a partir das credenciais da API.
     *
     * @return A string de autenticação codificada em Base64.
     */
    public String getBase64Auth() {
        return Base64Auth;
    }

    /**
     * Define a autenticação Base64 para a API.
     *
     * @param base64Auth A string de autenticação codificada em Base64.
     */
    public void setBase64Auth(String base64Auth) {
        Base64Auth = base64Auth;
    }

    /**
     * Classe interna para armazenar as informações da API (URL, utilizador e senha).
     */
    private static class APIInfo {
        String url;
        String user;
        String pass;

        /**
         * Construtor da classe {@link APIInfo}.
         *
         * @param url  A URL da API.
         * @param user O nome de utilizador para autenticação.
         * @param pass A senha para autenticação.
         */
        public APIInfo(String url, String user, String pass) {
            this.url = url;
            this.user = user;
            this.pass = pass;
        }
    }

    /**
     * Carrega as informações da API a partir do ficheiro "PasswordAPI.txt".
     *
     * @return Um objeto {@link APIInfo} contendo a URL, o utilizador e a senha.
     * @throws IOException Se ocorrer um erro ao ler o ficheiro.
     */
    private APIInfo load() throws IOException {
        String filepath = "./PasswordAPI.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filepath));

        String url = reader.readLine();
        String user = reader.readLine();
        String pass = reader.readLine();

        reader.close();

        return new ConnectionAPI.APIInfo(url, user, pass);
    }
}
