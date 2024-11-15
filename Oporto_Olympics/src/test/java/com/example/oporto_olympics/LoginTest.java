package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.UserDAO.UserDAOImp;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginTest {

    /**
     * Teste 1: Verificar login
     *
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     */
    @Test
    void VerificarAtleta() throws NoSuchAlgorithmException, SQLException {

        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();

        String User = "1000000" ;
        String Password = StringtoHash("1000000");
        UserDAOImp userDAO = new UserDAOImp(conexao);

        String TipoUser = userDAO.getUserType(Integer.parseInt(User), Password);

        assertEquals("Atleta", TipoUser, "O utilizador devia ser atleta...");
    }

    @Test
    void VerificarGestor() throws NoSuchAlgorithmException, SQLException {

        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();

        String User = "1000024" ;
        String Password = StringtoHash("1000024");
        UserDAOImp userDAO = new UserDAOImp(conexao);

        String TipoUser = userDAO.getUserType(Integer.parseInt(User), Password);

        assertEquals("Gestor", TipoUser, "O utilizador devia ser gestor...");
    }

    /**
     *
     * Encripta dados recebidos em hash ("SHA-256")
     *
     * @param dado Recebe o dado para ser encriptado
     * @return Retorna o dado encriptado
     * @throws NoSuchAlgorithmException se o algortimo para encriptação não está disponível.
     */

    public String StringtoHash(String dado) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hashdado = md.digest(dado.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashdado) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();

    }
}