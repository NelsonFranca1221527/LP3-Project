package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.UserDAO.UserDAOImp;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de testes para verificar o login de utilizadores no sistema.
 */
class LoginTest {

    /**
     * Testa se um utilizador do tipo "Atleta" é corretamente identificado.
     *
     * @throws NoSuchAlgorithmException Se o algoritmo de encriptação não estiver disponível.
     * @throws SQLException Se houver erro na conexão com a base de dados.
     */
    @Test
    void VerificarAtleta() throws NoSuchAlgorithmException, SQLException {

        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();

        String User = "1000025" ;
        String Password = StringtoHash("1000025");
        UserDAOImp userDAO = new UserDAOImp(conexao);

        String TipoUser = userDAO.getUserType(Integer.parseInt(User), Password);

        assertEquals("Atleta", TipoUser, "O utilizador devia ser atleta...");
    }

    /**
     * Testa se um utilizador do tipo "Gestor" é corretamente identificado.
     *
     * @throws NoSuchAlgorithmException Se o algoritmo de encriptação não estiver disponível.
     * @throws SQLException Se houver erro na conexão com a base de dados.
     */
    @Test
    void VerificarGestor() throws NoSuchAlgorithmException, SQLException {

        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();

        String User = "1000024" ;
        String Password = StringtoHash("1234");
        UserDAOImp userDAO = new UserDAOImp(conexao);

        assertEquals("Gestor", userDAO.getUserType(Integer.parseInt(User), Password), "O utilizador devia ser gestor...");
    }

    /**
     * Converte uma string para um hash SHA-256.
     *
     * @param dado A string a ser encriptada.
     * @return O hash SHA-256 correspondente à string.
     * @throws NoSuchAlgorithmException Se o algoritmo de encriptação não estiver disponível.
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
