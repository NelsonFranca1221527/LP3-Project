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
            int numMecanografico = 0;
            PreparedStatement ps1 = conexao.prepareStatement("SELECT Max(num_mecanografico) as \"MaiorNumMecanografico\" FROM users");
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                int maiorNumMecanografico = rs1.getInt("MaiorNumMecanografico");
                numMecanografico = maiorNumMecanografico > 0 ? maiorNumMecanografico + 1 : 1000000;
            }

            PreparedStatement ps2 = conexao.prepareStatement("SELECT id as \"IdGestor\" FROM roles WHERE nome = ?");
            ps2.setString(1, "Gestor");
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Utilizador Não Encontrado", "Tipo de Utilizador não encontrado!!");
                alertHandler.getAlert().showAndWait();
                return;
            }

            int idGestor = rs2.getInt("IdGestor");

            PreparedStatement ps3 = conexao.prepareStatement("INSERT INTO users (num_mecanografico, User_password, criado_em, role_id) VALUES(?,?,?,?)");
            ps3.setInt(1, numMecanografico);
            ps3.setString(2, StringtoHash(String.valueOf(numMecanografico)));
            ps3.setDate(3, Date.valueOf(LocalDate.now()));
            ps3.setInt(4, idGestor);
            ps3.executeUpdate();
            ps3.close();


            PreparedStatement ps4 = conexao.prepareStatement("SELECT id FROM users WHERE num_mecanografico = ?");
            ps4.setInt(1, numMecanografico);
            ResultSet rs4 = ps4.executeQuery();

            if (rs4.next()) {
                int userId = rs4.getInt("id");

                String insertGestorQuery = "INSERT INTO gestores (nome, user_id) VALUES(?,?)";
                try (PreparedStatement ps5 = conexao.prepareStatement(insertGestorQuery)) {
                    ps5.setString(1, nome);
                    ps5.setInt(2, userId);
                    ps5.executeUpdate();
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro ao atualizar gestor", "Erro ao salvar o gestor na base de dados. Verifique o console.");
            alertHandler.getAlert().showAndWait();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro de Criptografia", "Erro ao criptografar a senha.");
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
