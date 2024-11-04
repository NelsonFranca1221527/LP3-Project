package com.example.oporto_olympics.Controllers.DAO.Equipas;

import com.example.oporto_olympics.Controllers.DAO.DAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.Model.InscricaoEquipas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InscricaonaEquipaDAOImp implements InscricaoEquipaDAO {
    private Connection connection;

    public InscricaonaEquipaDAOImp(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves a list of team registrations based on the country.
     *
     * @param pais the country code to filter teams by.
     * @return a list of InscricaoEquipas objects representing the teams.
     * @throws RuntimeException if there is an error accessing the database or retrieving the data.
     */
    @Override
    public List<InscricaoEquipas> getEquipas(String pais) {
        List<InscricaoEquipas> equipas = new ArrayList<>();
        String query = "SELECT * FROM equipas WHERE pais_sigla = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, pais);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                equipas.add(new InscricaoEquipas(
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
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar equipas: " + e.getMessage());
        }
        return equipas;
    }
}