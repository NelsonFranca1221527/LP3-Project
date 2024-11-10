package com.example.oporto_olympics.ConnectBD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * Uma classe singleton para gerenciar uma conexão de banco de dados com um banco de dados SQL Server.
 */
public class ConnectionBD {

    private static ConnectionBD instancia = null;
    private Connection conexao = null;

    /**
     * Construtor privado para criar uma conexão de banco de dados.
     * @throws SQLException se ocorrer um erro de conexão com o banco de dados.
     */
    private ConnectionBD() throws SQLException {

        try {
            DatabaseInfo loaddb = load();

            //Conexão à base de dados
            String serverName = loaddb.svname;
            String databaseName = loaddb.dbname;
            String user = loaddb.user;
            String password = loaddb.pass;
            String url = "jdbc:sqlserver://" + serverName + ";databaseName=" + databaseName + ";encrypt=true;trustServerCertificate=true";

            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso.");
        } catch (SQLException ex) {

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtenha uma instância da classe ConnectionBD (padrão Singleton).
     * @return A única instância do ConnectionBD.
     * @throws SQLException se ocorrer um erro de conexão com a base de dados.
     */
    public static ConnectionBD getInstance() throws SQLException {
        if (instancia == null) {

            instancia = new ConnectionBD();

        }

        return instancia;

    }

    /**
     * Obtenha a conexão com a base de dados.
     * @return A conexão da base de dados.
     */
    public Connection getConexao(){

        return conexao;

    }

    private static class DatabaseInfo {
        String svname;
        String dbname;
        String user;
        String pass;

        public DatabaseInfo(String svname, String dbname, String user, String pass) {
            this.svname = svname;
            this.dbname = dbname;
            this.user = user;
            this.pass = pass;
        }
    }

    private static DatabaseInfo load() throws IOException {
        String filepath = "./PasswordBD.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filepath));

        String svname = null;
        String dbname = null;
        String user = null;
        String pass = null;

        // Lê as linhas do arquivo e atribui às variáveis
        svname = reader.readLine();
        dbname = reader.readLine();
        user = reader.readLine();
        pass = reader.readLine();

        reader.close();

        // Retorna as informações encapsuladas em um objeto DatabaseInfo
        return new DatabaseInfo(svname, dbname, user, pass);
    }
}
