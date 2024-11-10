package com.example.oporto_olympics.DAO.Eventos;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.Models.Evento;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class EventosDAOImp implements DAO<Evento> {
    private static Connection connection;
    private ConnectionBD database;

    public EventosDAOImp(Connection connection) {
        this.connection = connection;
    }

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

    @Override
    public void update(Evento evento) {

    }

    @Override
    public void delete(Evento evento) {

    }

    @Override
    public Optional<Evento> get(int i) {
        return Optional.empty();
    }

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
