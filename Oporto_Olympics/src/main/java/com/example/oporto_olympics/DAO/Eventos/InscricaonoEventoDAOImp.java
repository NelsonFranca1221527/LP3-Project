package com.example.oporto_olympics.DAO.Eventos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscricaonoEventoDAOImp {

    private Connection connection;

    public InscricaonoEventoDAOImp(Connection connection) {this.connection = connection;}

    /**
     * Insere uma nova inscrição na base de dados para um atleta num evento específico.
     *
     * @param status o estado inicial da inscrição (por exemplo, "Pendente" ou "Aprovado").
     * @param eventoId o identificador único do evento.
     * @param atletaId o identificador único do atleta.
     * @throws RuntimeException se ocorrer um erro ao inserir a inscrição na base de dados.
     */
    public void inserirInscricao(String status, int eventoId, int atletaId, int modalidade_id) {
        String insertQuery = "INSERT INTO inscricoes_atletas (estado, evento_id, atleta_id,modalidade_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, eventoId);
            pstmt.setInt(3, atletaId);
            pstmt.setInt(4, modalidade_id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir inscrição: " + e.getMessage());
        }
    }

    /**
     * Verifica se existe uma inscrição pendente para um atleta específico em uma modalidade.
     *
     * @param atletaId o identificador único do atleta.
     * @param eventoId o identificador único do evento.
     * @param modalidadeId o identificador único da modalidade.
     * @return {@code true} se o atleta tiver pelo menos uma inscrição pendente na modalidade;
     *         {@code false} caso contrário.
     * @throws RuntimeException se ocorrer um erro ao executar a consulta na base de dados.
     */
    public boolean existeInscricaoPendente(int atletaId, int eventoId, int modalidadeId) {
        String query = "SELECT COUNT(*) FROM inscricoes_atletas " +
                "WHERE atleta_id = ? AND evento_id = ? AND modalidade_id = ? AND estado = 'Pendente'";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, atletaId);
            pstmt.setInt(2, eventoId);
            pstmt.setInt(3, modalidadeId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se o contador for maior que 0
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar pedido pendente: " + e.getMessage());
        }
        return false;
    }


    /**
     * Verifica se existe uma inscrição aprovada para um atleta específico em uma modalidade.
     *
     * @param atletaId o identificador único do atleta.
     * @param eventoId o identificador único do evento.
     * @param modalidadeId o identificador único da modalidade.
     * @return {@code true} se o atleta tiver pelo menos uma inscrição aprovada na modalidade;
     *         {@code false} caso contrário.
     * @throws RuntimeException se ocorrer um erro ao executar a consulta na base de dados.
     */
    public boolean existeInscricaoAprovada(int atletaId, int eventoId, int modalidadeId) {
        String query = "SELECT COUNT(*) FROM inscricoes_atletas " +
                "WHERE atleta_id = ? AND evento_id = ? AND modalidade_id = ? AND estado = 'Aprovado'";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, atletaId);
            pstmt.setInt(2, eventoId);
            pstmt.setInt(3, modalidadeId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar pedido aprovado: " + e.getMessage());
        }
        return false;
    }

    /**
     * Obtém os identificadores das modalidades associadas a um evento específico.
     *
     * @param eventoId o identificador único do evento.
     * @return uma lista de identificadores das modalidades associadas ao evento.
     *         Se não houver modalidades associadas, retorna uma lista vazia.
     * @throws RuntimeException se ocorrer um erro ao executar a consulta na base de dados.
     */
    public List<Integer> getModalidadesPorEvento(int eventoId) {
        String sql = "SELECT modalidade_id FROM eventos_modalidades WHERE evento_id = ?";
        List<Integer> modalidadeIds = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, eventoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modalidadeIds.add(rs.getInt("modalidade_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao recuperar as modalidades associadas ao evento.", e);
        }
        return modalidadeIds;
    }

    /**
     * Obtém o nome de uma modalidade com base no seu identificador único.
     *
     * @param id o identificador único da modalidade.
     * @return o nome da modalidade, ou {@code null} se a modalidade não for encontrada.
     * @throws RuntimeException se ocorrer um erro ao executar a consulta na base de dados.
     */
    public String getModalidadeNomeById(int id) {
        String sql = "SELECT nome FROM modalidades WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao recuperar o nome da modalidade.", e);
        }
        return null;
    }




}