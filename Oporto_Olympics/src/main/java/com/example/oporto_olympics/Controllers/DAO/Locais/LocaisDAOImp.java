package com.example.oporto_olympics.Controllers.DAO.Locais;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.DAO;
import com.example.oporto_olympics.Models.Local;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocaisDAOImp implements DAO<Local> {
    private static Connection connection;
    private ConnectionBD database;

    public LocaisDAOImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Local> getAll() {
        List<Local> lst = new ArrayList<Local>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locais");
            while (rs.next()) {
                lst.add(new Local(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("tipo"), rs.getString("morada") , rs.getString("cidade") ,
                        rs.getString("pais_sigla") , rs.getInt("capacidade") , rs.getInt("ano_construcao")));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar os locais: " + ex.getMessage());
        }
    }

    @Override
    public void save(Local local) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO locais (nome, tipo, morada, cidade, capacidade, ano_construcao, pais_sigla) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, local.getNome());
            ps.setString(2, local.getTipo());
            ps.setString(3, local.getMorada());
            ps.setString(4, local.getCidade());
            ps.setInt(5, local.getCapacidade());
            ps.setInt(6, local.getAno_construcao());
            ps.setString(7, local.getPais());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir o local: " + ex.getMessage());
        }
    }

    @Override
    public void update(Local local) {

    }

    @Override
    public void delete(Local local) {

    }

    @Override
    public Optional<Local> get(int i) {
        return Optional.empty();
    }
}
