package com.example.oporto_olympics.DAO.Eventos;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.Models.Evento;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
/**
 * Implementação do DAO (Data Access Object) para a tabela de eventos.
 * Esta classe fornece métodos para interagir com a base de dados e manipular as informações dos eventos.
 */
public class EventosDAOImp implements DAO<Evento> {
    private static Connection connection;
    private ConnectionBD database;
    /**
     * Construtor da classe que inicializa a conexão com a base de dados.
     *
     * @param connection a conexão com a base de dados.
     */
    public EventosDAOImp(Connection connection) {
        this.connection = connection;
    }
    /**
     * Guarda um evento na base de dados.
     *
     * @param evento o evento a ser guardado.
     * @throws RuntimeException se ocorrer um erro durante a inserção do evento na base de dados.
     */
    @Override
    public void save(Evento evento) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO eventos (ano_edicao, pais_anfitriao_sigla, logo, mascote, local_id) VALUES(?,?,?,?,?)");
            ps.setInt(1, evento.getAno_edicao());
            ps.setString(2, evento.getPais());
            ps.setBytes(3, evento.getLogo());
            ps.setBytes(4, evento.getMascote());
            ps.setInt(5, evento.getLocal_id());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir o evento: " + ex.getMessage());
        }
    }
    /**
     * Verifica se já existe um evento na base de dados com o ano de edição fornecido.
     *
     * @param anoEdicao o ano da edição do evento.
     * @return verdadeiro se o evento existir, caso contrário falso.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public boolean existsByAnoEdicao(int anoEdicao) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM eventos WHERE ano_edicao = ?");
            ps.setInt(1, anoEdicao);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            ps.close();
            return count > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao verificar o ano de edição: " + ex.getMessage());
        }
    }
    /**
     * Atualiza um evento na base de dados.
     *
     * @param evento o evento a ser atualizado.
     */
    @Override
    public void update(Evento evento) {

    }
    /**
     * Exclui um evento da base de dados.
     *
     * @param evento o evento a ser excluído.
     */
    @Override
    public void delete(Evento evento) {

    }
    /**
     * Obtém um evento a partir do seu ID.
     *
     * @param i o ID do evento a ser obtido.
     * @return um objeto {@link Optional} que pode conter o evento encontrado ou estar vazio caso não encontrado.
     */
    @Override
    public Optional<Evento> get(int i) {
        return Optional.empty();
    }
    /**
     * Obtém todos os eventos registados na base de dados.
     *
     * @return uma lista de objetos {@link Evento} representando todos os eventos na base de dados.
     * @throws RuntimeException se ocorrer um erro ao obter os eventos.
     */
    @Override
    public List<Evento> getAll() {
        List<Evento> lst = new ArrayList<Evento>();
        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from eventos");
            while (rs.next()) {
                lst.add(new Evento(rs.getInt("id"),rs.getInt("ano_edicao"),rs.getString("pais_anfitriao_sigla"), rs.getBytes("logo"),rs.getBytes("mascote"),rs.getInt("local_id")));
            }

            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Obtém um evento pelo seu ID.
     *
     * @param id o ID do evento.
     * @return o evento correspondente ao ID ou null se não for encontrado.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public Evento getById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM eventos WHERE id = ?");
            ps.setInt(1, id);
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
     * Verifica se uma sigla de país existe na base de dados.
     *
     * @param sigla a sigla do país a ser verificada.
     * @return verdadeiro se a sigla for encontrada, caso contrário falso.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public boolean getSigla(String sigla) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nome FROM paises WHERE sigla = ?");
            ps.setString(1, sigla);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a sigla: " + ex.getMessage());
        }
    }
}
