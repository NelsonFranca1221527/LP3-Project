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
    void verificarAtleta() throws NoSuchAlgorithmException, SQLException {
        Connection conexao = ConnectionBD.getInstance().getConexao();

        String user = "1000000";
        String password = stringToHash(user);
        UserDAOImp userDAO = new UserDAOImp(conexao);

        String tipoUser = userDAO.getUserType(Integer.parseInt(user), password);

        assertEquals("Atleta", tipoUser, "O utilizador devia ser atleta...");
    }

    /**
     * Testa se um utilizador do tipo "Gestor" é corretamente identificado.
     *
     * @throws NoSuchAlgorithmException Se o algoritmo de encriptação não estiver disponível.
     * @throws SQLException Se houver erro na conexão com a base de dados.
     */
    @Test
    void verificarGestor() throws NoSuchAlgorithmException, SQLException {
        Connection conexao = ConnectionBD.getInstance().getConexao();

        String user = "1000024";
        String password = stringToHash(user);
        UserDAOImp userDAO = new UserDAOImp(conexao);

        String tipoUser = userDAO.getUserType(Integer.parseInt(user), password);

        assertEquals("Gestor", tipoUser, "O utilizador devia ser gestor...");
    }

    /**
     * Converte uma string para um hash SHA-256.
     *
     * @param dado A string a ser encriptada.
     * @return O hash SHA-256 correspondente à string.
     * @throws NoSuchAlgorithmException Se o algoritmo de encriptação não estiver disponível.
     */
    private String stringToHash(String dado) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashDado = md.digest(dado.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashDado) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
