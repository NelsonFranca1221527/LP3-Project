package com.example.oporto_olympics.Controllers.DAO;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.DAO;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Evento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
}
