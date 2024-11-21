package com.example.oporto_olympics.DAO.Equipas;

import com.example.oporto_olympics.Models.AprovarInscricaoEquipa;
import com.example.oporto_olympics.Models.AtletaInfo;
import com.example.oporto_olympics.Models.InscricaoEquipas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * A classe {@link AprovarInscricaoEquipaDAOImp} implementa a interface {@link AprovarInscricaoEquipaDAO},
 * fornecendo a implementação concreta dos métodos de acesso aos dados das inscrições de equipas, atletas
 * e o processo de aprovação ou reprovação das inscrições.
 * Utiliza uma conexão com a base de dados para realizar operações de consulta, inserção, atualização e exclusão.
 */
public class AprovarInscricaoEquipaDAOImp implements AprovarInscricaoEquipaDAO {
    private Connection connection;

    /**
     * Construtor da classe AprovarInscricaoEquipaDAOImp.
     *
     * Este construtor inicializa uma nova instância da classe, recebendo uma conexão com a base de dados
     * que será utilizada para interagir com a base de dados ao longo da execução de operações do DAO (Data Access Object).
     *
     * @param connection A conexão com a base de dados a ser utilizada para realizar as operações de consulta, inserção, atualização e exclusão.
     */
    public AprovarInscricaoEquipaDAOImp(Connection connection) {
        this.connection = connection;
    }

    /**
     * Método responsável por obter todas as inscrições com status "Pendente" da base de dados.
     *
     * Este método executa uma consulta SQL para recuperar todas as inscrições cuja coluna `status`
     * seja igual a "Pendente" e retorna como uma lista de objetos {@link AprovarInscricaoEquipa}.
     * A consulta é executada através de uma conexão com a base de dados utilizando um {@link Statement}.
     *
     * @return Uma lista de objetos {@link AprovarInscricaoEquipa} representando todas as inscrições
     *         com status "Pendente" encontradas na base de dados.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL ou ao processar
     *                          os resultados.
     */
    @Override
    public List<AprovarInscricaoEquipa> getAll(){
        List<AprovarInscricaoEquipa> lista = new ArrayList<AprovarInscricaoEquipa>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inscricoes WHERE status = 'Pendente'");
            while (rs.next()) {
                lista.add(new AprovarInscricaoEquipa(rs.getInt("id"), rs.getString("status"),
                        rs.getInt("modalidade_id"),rs.getInt("atleta_id"), rs.getInt("equipa_id")));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método responsável por recuperar uma equipa da base de dados com base no seu ID.
     *
     * Este método executa uma consulta SQL utilizando um {@link PreparedStatement} para buscar os dados
     * de uma equipa na tabela "equipas", com base no ID fornecido como parâmetro.
     *
     * @param id O ID da equipa a ser recuperada da base de dados.
     * @return O objeto contendo os dados da equipa correspondente ao ID fornecido,
     *
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL ou ao processar os dados.
     */
    @Override
    public InscricaoEquipas getEquipa(int id) {
        InscricaoEquipas equipa = null;
        String query = "SELECT * FROM equipas WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                equipa = new InscricaoEquipas(
                        rs.getInt("id"),
                        rs.getString("pais_sigla"),
                        rs.getInt("ano_fundacao"),
                        rs.getInt("modalidade_id"),
                        rs.getString("nome"),
                        rs.getString("genero"),
                        rs.getString("desporto")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar equipa: " + e.getMessage());
        }

        return equipa;
    }

    /**
     * Método responsável por recuperar as informações de um atleta da base de dados com base no seu ID do utilizador.
     *
     * Este método executa uma consulta SQL utilizando um {@link PreparedStatement} para buscar os dados de um atleta
     * na tabela "atletas", com base no ID do utilizador fornecido como parâmetro.
     *
     * @param id O ID do utilizador do atleta a ser recuperado da base de dados.
     * @return O objeto contendo as informações do atleta correspondente ao ID fornecido.
     *
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL ou ao processar os dados.
     */
    @Override
    public AtletaInfo getAtletaInfo(int id) {
        AtletaInfo atletaInfo = null;
        String query = "SELECT * FROM atletas WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                atletaInfo = new AtletaInfo(
                        rs.getInt("user_id"),
                        rs.getString("nome"),
                        rs.getString("pais_sigla"),
                        rs.getString("genero"),
                        rs.getInt("altura_cm"),
                        rs.getInt("peso_kg"),
                        rs.getDate("data_nascimento")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar informações do atleta: " + e.getMessage());
        }

        return atletaInfo;
    }

    /**
     * Método responsável por aprovar uma inscrição na base de dados com base no seu ID.
     *
     * Este método executa uma consulta SQL para atualizar o status de uma inscrição na tabela "inscricoes",
     * alterando o seu status para "Aprovado" com base no ID fornecido como parâmetro.
     * Caso a atualização seja bem-sucedida, uma mensagem indicando o sucesso será exibida na console.
     * Caso contrário, uma mensagem informando que nenhuma inscrição foi encontrada com o ID fornecido será exibida.
     *
     * @param id O ID da inscrição a ser aprovada.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL ou ao processar os dados.
     */
    public void aprovarInscricao(int id) {
        String updateQuery = "UPDATE inscricoes SET status = 'Aprovado' WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Inscrição aprovada com sucesso!");
            } else {
                System.out.println("Nenhuma inscrição encontrada com esse ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao aprovar inscrição: " + e.getMessage());
        }
    }

    /**
     * Método responsável por reprovar uma inscrição na base de dados com base no seu ID.
     *
     * Este método executa uma consulta SQL para atualizar o status de uma inscrição na tabela "inscricoes",
     * alterando o seu status para "Rejeitado" com base no ID fornecido como parâmetro.
     * Caso a atualização seja bem-sucedida, uma mensagem indicando o sucesso será exibida na console.
     * Caso contrário, uma mensagem informando que nenhuma inscrição foi encontrada com o ID fornecido será exibida.
     *
     * @param id O ID da inscrição a ser reprovada.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL ou ao processar os dados.
     */
    public void reprovarInscricao(int id) {
        String updateQuery = "UPDATE inscricoes SET status = 'Rejeitado' WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Inscrição reprovada com sucesso!");
            } else {
                System.out.println("Nenhuma inscrição encontrada com esse ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao reprovar inscrição: " + e.getMessage());
        }
    }

    /**
     * Insere uma ligação entre um atleta e uma equipa na tabela atletas_equipas.
     *
     * Este método utiliza uma instrução SQL preparada para inserir os IDs do atleta e da equipa
     * na tabela `atletas_equipas`. A ligação é criada associando o atleta especificado à
     * equipa indicada, caso os IDs fornecidos sejam válidos e a conexão com a base de dados esteja ativa.
     *
     * @param atletaId o ID do atleta a ser associado.
     * @param equipaId o ID da equipa à qual o atleta será associado.
     * @throws SQLException se ocorrer um erro ao tentar inserir os dados na base de dados.
     */
    public void inserirAtletaEquipa(int atletaId, int equipaId) {
        String insertQuery = "INSERT INTO atletas_equipas (atleta_id, equipa_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setInt(1, atletaId);
            pstmt.setInt(2, equipaId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Linhas inseridas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dados: " + e.getMessage());
        }
    }
}
