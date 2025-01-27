package com.example.oporto_olympics.DAO.Eventos;

import com.example.oporto_olympics.Models.HorarioModalidade;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementação para gestão de inscrições em eventos.
 */
public class InscricaonoEventoDAOImp {
    /**
     * Objeto de conexão com a base de dados.
     */
    private Connection connection;
    /**
     * Construtor que inicializa a conexão com a base de dados.
     *
     * @param connection a conexão com a base de dados
     */
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
     * Obtém uma lista de horários de modalidades associados a um atleta específico.
     *
     * Este método consulta o base de dados para buscar todas as modalidades em que um atleta está inscrito,
     * retornando os detalhes de horário, duração e local das modalidades. Apenas modalidades com informações
     * completas (data, duração e local) são incluídas na lista.
     *
     * @param atletaId o identificador único do atleta.
     * @return uma lista de objetos {@link HorarioModalidade} contendo as informações das modalidades.
     * @throws RuntimeException se ocorrer um erro de SQL ao buscar os dados.
     */
    public List<HorarioModalidade> getAllHorarioModalidadeByAtleta(int atletaId) {
        try {

            PreparedStatement ps = connection.prepareStatement("select m.data_modalidade, m.duracao, m.local_id from eventos_modalidades as m JOIN atletas_modalidades as em ON em.modalidade_id = m.modalidade_id where em.evento_id=m.evento_id AND em.atleta_id= ? ");
            ps.setInt(1, atletaId);
            ResultSet rs = ps.executeQuery();

            List<HorarioModalidade> list = new ArrayList<>();

            while (rs.next()) {

                if (rs.getTimestamp("data_modalidade") == null ||
                        rs.getTime("duracao") == null ||
                        rs.getInt("local_id") == 0) {
                    continue;
                }

                list.add(new HorarioModalidade(rs.getTimestamp("data_modalidade").toLocalDateTime(), rs.getTime("duracao").toLocalTime(), rs.getInt("local_id")));
            }

            return list;

        } catch(SQLException ex){
            throw new RuntimeException("Erro ao listar Horarios pelo atleta: " + ex.getMessage());
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
