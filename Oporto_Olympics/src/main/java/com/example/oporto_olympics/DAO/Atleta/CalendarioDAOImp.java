package com.example.oporto_olympics.DAO.Atleta;

import com.example.oporto_olympics.Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementação da interface {@link CalendarioDAO}.
 *
 * Esta classe fornece a implementação dos métodos para interagir com a base de dados,
 * permitindo operações relacionadas ao calendário, como a obtenção de eventos, modalidades e
 * informações associadas a atletas.
 */
public class CalendarioDAOImp implements CalendarioDAO {
    /**
     * Objeto de conexão com a base de dados.
     */
    private Connection conexao;

    /**
     * Construtor que inicializa a conexão com a base de dados
     *
     * @param conexao conexão com a base de dados
     */
    public CalendarioDAOImp(Connection conexao) {
        this.conexao = conexao;  // Conectar corretamente
    }
    /**
     * Obtém uma lista de modalidades associadas a um atleta específico.
     *
     * Este método executa uma consulta à base de dados para recuperar todos os registros da tabela
     * `atletas_modalidades` que correspondem ao `atleta_id` fornecido. Cada registro é mapeado para
     * um objeto {@link AtletasModalidades} e adicionado a uma lista.
     *
     * @param user_id O ID do atleta para o qual se pretende obter as modalidades associadas.
     * @return Uma lista de objetos {@link AtletasModalidades} contendo os dados das modalidades encontradas.
     *         Se nenhum registro for encontrado, a lista retornada estará vazia.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL.
     */
    @Override
    public List<AtletasModalidades> getAtletaModalidade(int user_id){
        List<AtletasModalidades> lstR = new ArrayList<>();
        String sql = "SELECT * FROM atletas_modalidades WHERE atleta_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {  // Usar 'conexao', não 'connection'
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int atletaId = rs.getInt("atleta_id");
                int modalidadeId = rs.getInt("modalidade_id");
                int eventoId = rs.getInt("evento_id");

                AtletasModalidades atletasInscritos = new AtletasModalidades(atletaId, modalidadeId, eventoId);
                lstR.add(atletasInscritos);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao mostrar os resultados: " + ex.getMessage());
        }

        return lstR;
    }
    /**
     * Obtém uma lista de eventos-modalidade associados a um evento e modalidade específicos.
     *
     * Este método executa uma consulta à base de dados para recuperar todos os registros da tabela
     * `eventos_modalidades` que correspondem ao `evento_id` e `modalidade_id` fornecidos.
     * Cada registro é mapeado para um objeto {@link EventosModalidade} e adicionado a uma lista.
     *
     * @param evento_id O ID do evento a ser pesquisado.
     * @param modalidade_id O ID da modalidade a ser pesquisada.
     * @return Uma lista de objetos {@link EventosModalidade} contendo os dados dos eventos-modalidade encontrados.
     *         Se nenhum registro for encontrado, a lista retornada estará vazia.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL.
     */
    @Override
    public List<EventosModalidade> getEventosAtleta(int evento_id, int modalidade_id){
        List<EventosModalidade> lstR = new ArrayList<>();
        String sql = "SELECT * FROM eventos_modalidades WHERE evento_id = ? AND modalidade_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {  // Usar 'conexao', não 'connection'
            stmt.setInt(1, evento_id);
            stmt.setInt(2, modalidade_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int eventoId = rs.getInt("evento_id");
                int modalidadeId = rs.getInt("modalidade_id");
                byte modalidadeStatus = rs.getByte("modalidade_status");
                Timestamp dataModalidade = rs.getTimestamp("data_modalidade");
                Time duracao = rs.getTime("duracao");
                int localId = rs.getInt("local_id");

                EventosModalidade evento = new EventosModalidade(eventoId, modalidadeId, modalidadeStatus, dataModalidade, duracao, localId);
                lstR.add(evento);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao mostrar os resultados: " + ex.getMessage());
        }

        return lstR;
    }
    /**
     * Obtém os detalhes de um evento com base no seu ID.
     *
     * Este método realiza uma consulta na base de dados para obter os detalhes de um evento com o ID
     * fornecido. Se o evento for encontrado, um objeto {@link Evento} é retornado contendo as informações.
     * Caso contrário, o método retorna `null`.
     *
     * @param eventoid O ID do evento a ser pesquisado.
     * @return Um objeto {@link Evento} com os dados do evento encontrado, ou `null` se não houver
     *         nenhum evento com o ID fornecido.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL.
     */
    @Override
    public Evento getById(int eventoid) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM eventos WHERE id = ?");
            ps.setInt(1, eventoid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Evento evento = new Evento(
                        rs.getInt("id"),
                        rs.getInt("ano_edicao"),
                        rs.getString("pais_anfitriao_sigla"),
                        rs.getBytes("logo"),
                        rs.getBytes("mascote"),
                        rs.getInt("local_id")
                );
                rs.close();
                ps.close();
                return evento;
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao obter o evento pelo ID: " + ex.getMessage());
        }
        return null;  // Retorna Optional vazio se o evento não for encontrado
    }

    /**
     * Obtém uma modalidade completa com base no seu ID.
     *
     * @param id o ID da modalidade.
     * @return um objeto {@link Modalidade} correspondente ao ID, ou null se a modalidade não for encontrada.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public Modalidade getModalidadeById(int id) {
        try {
            PreparedStatement ps = conexao.prepareStatement(
                    "SELECT * FROM modalidades WHERE id = ?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Modalidade modalidade = new Modalidade(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getString("genero"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("min_participantes"),
                        rs.getString("pontuacao"),
                        rs.getString("jogo_unico"),
                        null,
                        null,
                        null,
                        rs.getString("regras")
                );

                rs.close();
                ps.close();
                return modalidade;
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao obter a modalidade: " + ex.getMessage(), ex);
        }
        return null; // Retorna null se a modalidade não for encontrada
    }
    /**
     * Obtém os detalhes de um evento-modalidade com base nos IDs do evento e da modalidade.
     *
     * Este método executa uma consulta SQL para buscar os dados do evento-modalidade associados
     * aos parâmetros fornecidos: o ID do evento e o ID da modalidade. Se os dados forem encontrados,
     * um objeto {@link EventosModalidade} é retornado, contendo as informações do evento e da modalidade.
     * Caso contrário, o método retorna `null`.
     *
     * @param eventoId O ID do evento a ser pesquisado.
     * @param modalidadeId O ID da modalidade a ser pesquisada.
     * @return Um objeto {@link EventosModalidade} com os dados do evento-modalidade, ou `null`
     *         se nenhum dado for encontrado para os IDs fornecidos.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL.
     */
    @Override
    public EventosModalidade getEventoModalidade(int eventoId, int modalidadeId) {
        String sql = "SELECT * FROM eventos_modalidades WHERE evento_id = ? AND modalidade_id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, eventoId);
            stmt.setInt(2, modalidadeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new EventosModalidade(
                        rs.getInt("evento_id"),
                        rs.getInt("modalidade_id"),
                        rs.getByte("modalidade_status"),
                        rs.getTimestamp("data_modalidade"),
                        rs.getTime("duracao"),
                        rs.getInt("local_id")
                );
            }
            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao obter dados de eventos_modalidade: " + ex.getMessage());
        }
        return null; // Retorna null se nenhum dado for encontrado
    }
    /**
     * Obtém os detalhes de um local específico com base no seu ID.
     *
     * Este método executa uma consulta à tabela `locais` da base de dados, procurando
     * um registo que corresponda ao ID fornecido. Se o local for encontrado, retorna
     * um objeto {@code Local} com os detalhes do registo. Caso contrário, lança uma
     * exceção para indicar que o local não foi encontrado.
     *
     * @param id O ID do local a ser pesquisado.
     * @return Um objeto {@code Local} que representa o local correspondente ao ID fornecido.
     * @throws RuntimeException Se ocorrer um erro ao aceder à base de dados ou se o local não for encontrado.
     */
    @Override
    public Local getLocalById(int id) {
        try {
            String query = "SELECT * FROM locais WHERE id = ?";
            PreparedStatement pstmt = conexao.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Local(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getString("morada"),
                        rs.getString("cidade"),
                        rs.getString("pais_sigla"),
                        rs.getInt("capacidade"),
                        rs.getDate("data_construcao")
                );
            } else {
                throw new RuntimeException("Local com ID " + id + " não encontrado.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar local por ID: " + ex.getMessage());
        }
    }



}
