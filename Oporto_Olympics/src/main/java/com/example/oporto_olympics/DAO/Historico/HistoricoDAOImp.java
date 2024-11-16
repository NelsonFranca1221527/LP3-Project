package com.example.oporto_olympics.DAO.Historico;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.DAO.UserDAO.UserDAOImp;
import com.example.oporto_olympics.Models.Resultados;
import com.example.oporto_olympics.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Implementação da interface {@link HistoricoDAO} responsável pela interação com os dados históricos
 * de competições dos atletas na base de dados.
 * Esta classe fornece a lógica de acesso aos dados de históricos de atletas.
 */
public class HistoricoDAOImp implements HistoricoDAO {

    private static Connection connection;
    private ConnectionBD database;
    /**
     * Construtor da classe {@link UserDAOImp}.
     * Inicializa a conexão com a base de dados para interagir com as tabelas associadas à entidade {@link User}.
     *
     * @param connection A conexão com a base de dados a ser utilizada pelas operações DAO.
     */
    public HistoricoDAOImp(Connection connection) {
        this.connection = connection;
    }

    /**
     * Obtém uma lista de resultados para um atleta específico a partir da base de dados.
     *
     * @param id O identificador único do atleta cujos resultados devem ser obtidos.
     * @return Uma lista de objetos {@link Resultados} que contém os resultados do atleta.
     *         Cada objeto inclui informações sobre o evento, ano e o número de medalhas de cada tipo (ouro, prata, bronze).
     * @throws RuntimeException Se ocorrer um erro ao aceder à base de dados, é lançada uma exceção
     *                          com uma mensagem a explicar o problema.
     */
    @Override
    public List<Resultados> getResultAtleta(int id) {
        List<Resultados> lstR = new ArrayList<>();
        String sql = "SELECT * FROM historico_atletas_competicoes WHERE atleta_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int resultadoId = rs.getInt("id");
                int atletaId = rs.getInt("atleta_id");
                int eventoId = rs.getInt("evento_id");
                int ano = rs.getInt("ano");
                int medalhasOuro = rs.getInt("medalha_ouro");
                int medalhasPrata = rs.getInt("medalha_prata");
                int medalhasBronze = rs.getInt("medalha_bronze");

                Resultados resultado = new Resultados(resultadoId, atletaId, eventoId, ano, medalhasOuro, medalhasPrata, medalhasBronze);
                lstR.add(resultado);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao mostrar os resultados: " + ex.getMessage());
        }

        return lstR;
    }

}
