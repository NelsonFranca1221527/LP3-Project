package com.example.oporto_olympics.API.ConnectAPI;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;

import javax.swing.text.html.HTML;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;

public class ConnectionAPI {

    private HttpURLConnection conexao = null;

    private String URL;

    private String Base64Auth;

    private static ConnectionAPI instancia = null;

    public ConnectionAPI() throws IOException {

        try {
            ConnectionAPI.APIInfo loadApi = load();

            //Conexão à API
            String user = loadApi.user;
            String pass = loadApi.pass;
            setURL(loadApi.url);

            String valueToEncode = user + ":" + pass;

            setBase64Auth(Base64.getEncoder().encodeToString(valueToEncode.getBytes()));

            System.out.println("Conexão à API estabelecida com sucesso.");
        } catch (Exception ex) {

            System.out.println("Exception: " + ex.getMessage());

        }
    }

    public static ConnectionAPI getInstance() throws IOException {
        if (instancia == null) {

            instancia = new ConnectionAPI();

        }

        return instancia;

    }

    public HttpURLConnection getConexao() {
        return conexao;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getBase64Auth() {
        return Base64Auth;
    }

    public void setBase64Auth(String base64Auth) {
        Base64Auth = base64Auth;
    }

    private static class APIInfo {
        String url;
        String user;
        String pass;

        public APIInfo(String url, String user, String pass) {
            this.url = url;
            this.user = user;
            this.pass = pass;
        }
    }

    private APIInfo load() throws IOException {
        String filepath = "./PasswordAPI.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filepath));

        String url = null;
        String user = null;
        String pass = null;

        url = reader.readLine();
        user = reader.readLine();
        pass = reader.readLine();

        reader.close();

        return new ConnectionAPI.APIInfo(url, user, pass);
    }
}
