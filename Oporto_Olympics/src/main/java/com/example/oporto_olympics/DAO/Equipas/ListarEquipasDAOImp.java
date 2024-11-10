package com.example.oporto_olympics.DAO.Equipas;

import com.example.oporto_olympics.Models.InscricaoEquipas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListarEquipasDAOImp implements ListarEquipasDAO {
    private Connection connection;

    /**
     * Construtor que inicializa a instância com a conexão à base de dados.
     *
     * @param connection A conexão com a base de dados que será utilizada para executar as consultas.
     */
    public ListarEquipasDAOImp(Connection connection) {
        this.connection = connection;
    }


    /**
     * Este método recupera todas as equipas registadas na base de dados e retorna uma lista de objetos {@link InscricaoEquipas}.
     *
     * Ele executa a consulta SQL "SELECT * FROM equipas" para obter todas as equipas da tabela "equipas" na base de dados,
     * e cria uma lista de objeto com os dados obtidos.
     *
     * @return Uma lista que contem todas as equipas carregadas da base de dados.
     * @throws RuntimeException Se ocorrer um erro ao carregar as equipas da base de dados.
     */
    @Override
    public List<InscricaoEquipas> getEquipas() {
        List<InscricaoEquipas> equipas = new ArrayList<>();
        String query = "SELECT * FROM equipas";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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
