package com.example.oporto_olympics.DAO.Eventos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void inserirInscricao(String status, int eventoId, int atletaId) {
        String insertQuery = "INSERT INTO inscricoes_atletas (estado, evento_id, atleta_id) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, eventoId);
            pstmt.setInt(3, atletaId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir inscrição: " + e.getMessage());
        }
    }

    /**
     * Verifica se existe uma inscrição pendente para um atleta específico.
     *
     * @param atletaId o identificador único do atleta.
     * @param evento_id o identificador único do evento.
     * @return {@code true} se o atleta tiver pelo menos uma inscrição aprovada;
     *         {@code false} caso contrário.
     * @throws RuntimeException se ocorrer um erro ao executar a consulta na base de dados.
     */
    public boolean existeInscricaoPendente(int atletaId, int evento_id) {
        String query = "SELECT COUNT(*) FROM inscricoes_atletas WHERE atleta_id = ? AND evento_id = ? AND estado = 'Pendente'";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, atletaId);
            pstmt.setInt(2, evento_id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se o contador for maior que 0, indicando que existe um pedido pendente
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar pedido pendente: " + e.getMessage());
        }
        return false;
    }

    /**
     * Verifica se existe uma inscrição aprovada para um atleta específico.
     *
     * @param atletaId o identificador único do atleta.
     * @param evento_id o identificador único do evento.
     * @return {@code true} se o atleta tiver pelo menos uma inscrição aprovada;
     *         {@code false} caso contrário.
     * @throws RuntimeException se ocorrer um erro ao executar a consulta na base de dados.
     */
    public boolean existeInscricaoAprovada(int atletaId, int evento_id) {
        String query = "SELECT COUNT(*) FROM inscricoes_atletas WHERE atleta_id = ? AND evento_id = ? AND estado = 'Aprovado'";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, atletaId);
            pstmt.setInt(2, evento_id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar pedido aprovado: " + e.getMessage());
        }
        return false;
    }
}
