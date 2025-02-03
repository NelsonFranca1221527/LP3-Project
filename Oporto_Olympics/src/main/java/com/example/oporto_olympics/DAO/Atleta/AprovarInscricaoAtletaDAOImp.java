package com.example.oporto_olympics.DAO.Atleta;

import com.example.oporto_olympics.Models.InscricaoAtletaEvento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementação da interface {@link AprovarInscricaoAtletaDAO} para gerir
 * a aprovação das inscrições de atletas.
 */
public class AprovarInscricaoAtletaDAOImp implements AprovarInscricaoAtletaDAO {
    /**
     * Objeto de conexão com a base de dados.
     */
    private Connection connection;

    /**
     * Construtor da classe AprovarInscricaoAtletaDAOImp.
     *
     * Este construtor inicializa uma nova instância da classe, recebendo uma conexão com a base de dados
     * que será utilizada para interagir com a base de dados ao longo da execução de operações do DAO (Data Access Object).
     *
     * @param connection A conexão com a base de dados a ser utilizada para realizar as operações de consulta, inserção, atualização e exclusão.
     */
    public AprovarInscricaoAtletaDAOImp(Connection connection) { this.connection = connection; }

    /**
     * Método responsável por rejeitar uma inscrição na tabela "inscricoes_modalidades".
     *
     * Este método atualiza o campo "estado" da tabela para "Rejeitado" com base no ID da inscrição.
     * Caso a atualização seja bem-sucedida, uma mensagem é exibida na console.
     * Caso contrário, é informado que nenhuma inscrição foi encontrada com o ID fornecido.
     *
     * @throws RuntimeException Se ocorrer um erro ao executar a consulta SQL.
     */
    public void rejeitarInscricao(int atletaId, int modalidadeId, int eventoId) {
        String updateQuery = "UPDATE inscricoes_atletas SET estado = 'Rejeitado' WHERE atleta_id = ? AND modalidade_id = ? AND evento_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            // Setando os valores no PreparedStatement
            pstmt.setInt(1, atletaId);      // ID do Atleta
            pstmt.setInt(2, modalidadeId);  // ID da Modalidade
            pstmt.setInt(3, eventoId);      // ID do Evento

            // Executando o update
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Inscrição rejeitada com sucesso!");
            } else {
                System.out.println("Nenhuma inscrição encontrada com os dados fornecidos.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao rejeitar inscrição: " + e.getMessage());
        }
    }

    /**
     * Associa um atleta a uma modalidade e a um evento.
     *
     * Este método insere uma nova linha na tabela 'atletas_modalidade' associando o atleta à modalidade
     * e ao evento após a aprovação da inscrição.
     *
     * @param atletaId   O ID do atleta a ser associado.
     * @param modalidadeId O ID da modalidade com a qual o atleta será associado.
     * @param eventoId O ID do evento ao qual o atleta será associado.
     * @throws SQLException Se ocorrer um erro durante a execução da inserção.
     */
    @Override
    public void associarAtletaAoEvento(int atletaId, int modalidadeId, int eventoId) {
        String insertQuery = "INSERT INTO atletas_modalidades (atleta_id, modalidade_id, evento_id) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setInt(1, atletaId);
            pstmt.setInt(2, modalidadeId);
            pstmt.setInt(3, eventoId);
            pstmt.executeUpdate();
            System.out.println("Atleta associado ao evento e modalidade com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao associar o atleta ao evento e modalidade: " + e.getMessage());
        }
    }

    /**
     * Método responsável por aprovar uma inscrição na tabela "inscricoes_modalidades".
     *
     * Este método atualiza o campo "estado" da tabela para "Aprovado" com base no ID da inscrição.
     * Caso a atualização seja bem-sucedida, uma mensagem é exibida na console.
     * Caso contrário, é informado que nenhuma inscrição foi encontrada com o ID fornecido.
     *
     * @throws RuntimeException Se ocorrer um erro ao executar a consulta SQL.
     */
    public void aprovarInscricao(int atletaId, int modalidadeId, int eventoId) {
        String updateQuery = "UPDATE inscricoes_atletas SET estado = 'Aprovado' WHERE atleta_id = ? AND modalidade_id = ? AND evento_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            // Setando os valores no PreparedStatement
            pstmt.setInt(1, atletaId);      // ID do Atleta
            pstmt.setInt(2, modalidadeId);  // ID da Modalidade
            pstmt.setInt(3, eventoId);      // ID do Evento

            // Executando o update
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Inscrição aprovada com sucesso!");
            } else {
                System.out.println("Nenhuma inscrição encontrada com os dados fornecidos.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao aprovar inscrição: " + e.getMessage());
        }
    }

    /**
     * Lista todas as inscrições pendentes.
     *
     * @return Uma lista de objetos InscricaoAtletaEvento representando as inscrições pendentes.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL.
     */
    @Override
    public List<InscricaoAtletaEvento> listarInscricoesPendentes() {
        List<InscricaoAtletaEvento> inscricoesPendentes = new ArrayList<>();
        String query = "SELECT * FROM inscricoes_atletas WHERE estado = 'Pendente'";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int atletaId = rs.getInt("atleta_id");
                int modalidadeId = rs.getInt("modalidade_id");
                int eventoId = rs.getInt("evento_id");
                String estado = rs.getString("estado");

                // Criar um objeto InscricaoAtletaEvento e adicionar à lista
                InscricaoAtletaEvento inscricao = new InscricaoAtletaEvento(id,estado,eventoId ,atletaId , modalidadeId);
                inscricoesPendentes.add(inscricao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar inscrições pendentes: " + e.getMessage(), e);
        }
        return inscricoesPendentes;
    }
    /**
     * Retorna o nome do atleta com base no seu ID.
     *
     * @param atletaId o ID do atleta
     * @return o nome do atleta ou {@code null} se não encontrado
     */
    public String getAtletaNome(int atletaId) {
        String query = "SELECT nome FROM atletas WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, atletaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nome");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o nome do atleta: " + e.getMessage(), e);
        }
    }
    /**
     * Retorna o nome da modalidade com base no seu ID.
     *
     * @param modalidadeId o ID da modalidade
     * @return o nome da modalidade ou {@code null} se não encontrado
     */
    public String getModalidadeNome(int modalidadeId) {
        String query = "SELECT nome FROM modalidades WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, modalidadeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nome");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o nome da modalidade: " + e.getMessage(), e);
        }
    }
    /**
     * Retorna o nome do local associado a um evento com base no seu ID.
     *
     * @param eventoId o ID do evento
     * @return o nome do local ou {@code null} se não encontrado
     */
    public String getLocalNome(int eventoId) {
        String queryEvento = "SELECT local_id FROM eventos WHERE id = ?";
        String queryLocal = "SELECT nome FROM locais WHERE id = ?";

        try (PreparedStatement pstmtEvento = connection.prepareStatement(queryEvento)) {
            pstmtEvento.setInt(1, eventoId);
            try (ResultSet rsEvento = pstmtEvento.executeQuery()) {
                if (rsEvento.next()) {
                    int localId = rsEvento.getInt("local_id");

                    try (PreparedStatement pstmtLocal = connection.prepareStatement(queryLocal)) {
                        pstmtLocal.setInt(1, localId);
                        try (ResultSet rsLocal = pstmtLocal.executeQuery()) {
                            if (rsLocal.next()) {
                                return rsLocal.getString("nome");
                            } else {
                                return null;
                            }
                        }
                    }
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o nome do local: " + e.getMessage(), e);
        }
    }


}
