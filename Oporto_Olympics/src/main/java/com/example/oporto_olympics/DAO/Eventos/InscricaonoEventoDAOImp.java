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

    public List<String> getModalidadesPorEvento(int eventoId) {
        List<String> modalidades = new ArrayList<>();
        String sql = "SELECT modalidade FROM eventos_modalidades WHERE evento_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, eventoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modalidades.add(rs.getString("modalidade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar modalidades para o evento: " + eventoId, e);
        }

        return modalidades;
    }

    public int getModalidadeIdByNome(String nome) {
        String sql = "SELECT nome FROM modalidades WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao receber a modalidade.", e);
        }
        return -1; // Caso não encontre a modalidade
    }


}
