package com.example.oporto_olympics.DAO.Gestor;

import com.example.oporto_olympics.DAO.Gestor.InserirGestorDAO;
import com.example.oporto_olympics.Misc.AlertHandler;
import javafx.scene.control.Alert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;

/**
 * Implementação da interface {@link InserirGestorDAO} para a gestão de operações de inserção e atualização de dados de gestores na base de dados.
 * Esta classe é responsável por guardar ou atualizar os dados de um gestor,
 * e converter strings para um hash utilizando o algoritmo SHA-256.
 */
public class InserirGestorDAOImp implements InserirGestorDAO{

    private Connection conexao;

    /**
     * Construtor que inicializa a conexão com a base de dados
     *
     * @param conexao conexão com a base de dados
     */
    public InserirGestorDAOImp(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Método responsável por guardar ou atualizar os dados de um gestor na base de dados.
     * O fluxo:
     * 1. Gera o número mecanográfico do gestor.
     * 2. Obtém o ID de função para o tipo de utilizador 'Gestor'.
     * 3. Insere um novo utilizador na tabela 'users'.
     * 4. Recupera o ID do utilizador recém-criado.
     * 5. Atualiza os dados do gestor na tabela 'gestores', associando-o ao utilizador recém-criado.
     *
     * @param nome O nome do gestor a ser guardado/atualizado.
     */
    @Override
    public void saveGestor(String nome) {

        AlertHandler alertHandler;

        try {
            CallableStatement cs = conexao.prepareCall("{CALL SaveGestor(?)}");
            cs.setString(1, nome);
            cs.executeUpdate();
            cs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro ao atualizar gestor", "Erro ao salvar o gestor na base de dados. Verifique o console.");
            alertHandler.getAlert().showAndWait();
        }
    }


    /**
     * Converte uma string para um hash SHA-256.
     *
     * @param dado o dado a ser convertido em hash.
     * @return a string correspondente ao hash.
     * @throws NoSuchAlgorithmException se o algoritmo SHA-256 não estiver disponível.
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
