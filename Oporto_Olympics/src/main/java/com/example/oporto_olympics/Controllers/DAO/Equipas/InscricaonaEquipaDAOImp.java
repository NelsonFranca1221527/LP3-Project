package com.example.oporto_olympics.Controllers.DAO.Equipas;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.DAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.Model.InscricaoEquipas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InscricaonaEquipaDAOImp implements DAO<InscricaoEquipaDAO> {
    private static Connection connection;

    public InscricaonaEquipaDAOImp(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves a list of pending team registrations.
     *
     * @return a list of InscricaoEquipas objects representing the team registrations that are pending approval.
     * @throws RuntimeException if there is an error accessing the database or retrieving the data.
     */
    public List<InscricaoEquipas> getEquipas(String pais) {
        List<InscricaoEquipas> lst = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM equipa WHERE pais_sigla = ?");
            pstmt.setString(1, pais);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lst.add(new InscricaoEquipas(
                        rs.getInt("id"),
                        rs.getString("pais_sigla"),
                        rs.getInt("ano_fundacao"),
                        rs.getInt("modalidade_id"),
                        rs.getInt("participacoes"),
                        rs.getInt("medalhas"),
                        rs.getString("nome"),
                        rs.getString("genero"),
                        rs.getString("desporto")
                ));
            }
            return lst;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao mostrar as Inscrições pendentes: " + e.getMessage());
        }
    }


    @Override
    public List<InscricaoEquipaDAO> getAll() {
        return List.of();
    }

    @Override
    public void save(InscricaoEquipaDAO inscricaoEquipaDAO) {

    }

    @Override
    public void update(InscricaoEquipaDAO inscricaoEquipaDAO) {

    }

    @Override
    public void delete(InscricaoEquipaDAO inscricaoEquipaDAO) {

    }

    @Override
    public Optional<InscricaoEquipaDAO> get(int i) {
        return Optional.empty();
    }
}
