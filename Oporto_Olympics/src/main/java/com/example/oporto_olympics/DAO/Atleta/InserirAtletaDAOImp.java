package com.example.oporto_olympics.DAO.Atleta;

import com.example.oporto_olympics.Misc.AlertHandler;
import javafx.scene.control.Alert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;

/**
 * Implementação da interface {@link InserirAtletaDAO} para a gestão de operações de inserção e atualização de dados de atletas na base de dados.
 * Esta classe é responsável por guardar ou atualizar os dados de um atleta, verificar a existência de um país na tabela {@code paises},
 * e converter strings para um hash utilizando o algoritmo SHA-256.
 */
public class InserirAtletaDAOImp implements InserirAtletaDAO{
    /**
     * Objeto de conexão com a base de dados.
     */
    private Connection conexao;

    /**
     * Construtor que inicializa a conexão com a base de dados
     *
     * @param conexao conexão com a base de dados
     */
    public InserirAtletaDAOImp(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Método responsável por guardar ou atualizar os dados de um atleta na base de dados.
     * O fluxo:
     * 1. Gera o número mecanográfico do atleta.
     * 2. Obtém o ID de função para o tipo de utilizador 'Atleta'.
     * 3. Insere um novo utilizador na tabela 'users'.
     * 4. Recupera o ID do utilizador recém-criado.
     * 5. Atualiza os dados do atleta na tabela 'atletas', associando-o ao utilizador recém-criado.
     *
     * @param nome O nome do atleta a ser guardado/atualizado.
     * @param pais O país de origem do atleta.
     * @param altura A altura do atleta em centímetros.
     * @param peso O peso do atleta em quilogramas.
     * @param dataNascimento A data de nascimento do atleta.
     * @param genero O género do atleta.
     */
    @Override
    public void saveAtleta(String nome, String pais, double altura, double peso, LocalDate dataNascimento, String genero) {

        AlertHandler alertHandler;

        try {
            int numMecanografico = 0;
            PreparedStatement ps1 = conexao.prepareStatement("SELECT Max(num_mecanografico) as \"MaiorNumMecanografico\" FROM users");
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                int maiorNumMecanografico = rs1.getInt("MaiorNumMecanografico");
                numMecanografico = maiorNumMecanografico > 0 ? maiorNumMecanografico + 1 : 1000000;
            }

            PreparedStatement ps2 = conexao.prepareStatement("SELECT id as \"IdAtleta\" FROM roles WHERE nome = ?");
            ps2.setString(1, "Atleta");
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Utilizador Não Encontrado", "Tipo de Utilizador não encontrado!!");
                alertHandler.getAlert().showAndWait();
                return;
            }

            int idAtleta = rs2.getInt("IdAtleta");

            PreparedStatement ps3 = conexao.prepareStatement("INSERT INTO users (num_mecanografico, User_password, criado_em, role_id) VALUES(?,?,?,?)");
            ps3.setInt(1, numMecanografico);
            ps3.setString(2, StringtoHash(String.valueOf(numMecanografico)));
            ps3.setDate(3, Date.valueOf(LocalDate.now()));
            ps3.setInt(4, idAtleta);
            ps3.executeUpdate();
            ps3.close();


            PreparedStatement ps4 = conexao.prepareStatement("SELECT id FROM users WHERE num_mecanografico = ?");
            ps4.setInt(1, numMecanografico);
            ResultSet rs4 = ps4.executeQuery();

            if (rs4.next()) {
                int userId = rs4.getInt("id");


                String updateAtletaQuery = "INSERT INTO atletas (nome, data_nascimento, genero, altura_cm, peso_kg , pais_sigla, user_id) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps5 = conexao.prepareStatement(updateAtletaQuery)) {
                    ps5.setString(1, nome);
                    ps5.setDate(2, Date.valueOf(dataNascimento));
                    ps5.setString(3, genero);
                    ps5.setDouble(4, altura);
                    ps5.setDouble(5, peso);
                    ps5.setString(6, pais);
                    ps5.setInt(7, userId);
                    ps5.executeUpdate();
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro ao atualizar atleta", "Erro ao salvar o atleta na base de dados. Verifique o console.");
            alertHandler.getAlert().showAndWait();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro de Criptografia", "Erro ao criptografar a senha.");
            alertHandler.getAlert().showAndWait();
        }
    }

    /**
     * Verifica a existência de um país na tabela `paises` com base na sigla fornecida.
     *
     *
     * @param sigla A sigla do país a ser verificada, composta três letras.
     * @return {@code true} se a sigla do país estiver presente na tabela `paises`, {@code false} se não for encontrada.
     */
    @Override
    public boolean getPais(String sigla) {
        String query = "SELECT 1 FROM paises WHERE sigla = ?";
        try (PreparedStatement ps = conexao.prepareStatement(query)) {
            ps.setString(1, sigla);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
