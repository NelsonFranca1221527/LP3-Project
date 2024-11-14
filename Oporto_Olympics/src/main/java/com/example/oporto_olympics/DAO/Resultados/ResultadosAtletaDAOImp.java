package com.example.oporto_olympics.DAO.Resultados;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.Models.ResultadosAtleta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Implementação do DAO (Data Access Object) para a tabela de resultados de atletas.
 * Esta classe fornece métodos para interagir com a base de dados e manipular as informações dos resultados de atletas.
 */
public class ResultadosAtletaDAOImp implements DAO<ResultadosAtleta> {
    private static Connection connection;
    private ConnectionBD database;
    /**
     * Construtor da classe que inicializa a conexão com a base de dados.
     *
     * @param connection a conexão com a base de dados.
     */
    public ResultadosAtletaDAOImp(Connection connection) {
        this.connection = connection;
    }
    /**
     * Obtém todos os resultados de atletas registados na base de dados.
     *
     * @return uma lista de objetos {@link ResultadosAtleta} representando todos os resultados de atletas na base de dados.
     * @throws RuntimeException se ocorrer um erro ao obter os resultados de atletas.
     */
    @Override
    public List<ResultadosAtleta> getAll() {
        List<ResultadosAtleta> lst = new ArrayList<ResultadosAtleta>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM resultados where atleta_id IS NOT null");
            while (rs.next()) {
                lst.add(new ResultadosAtleta(rs.getInt("id"), rs.getDate("data"),
                        rs.getDouble("resultado"), rs.getString("tipo_resultado") , rs.getString("medalha") ,
                        rs.getInt("modalidade_id") , rs.getInt("atleta_id")));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar os locais: " + ex.getMessage());
        }
    }
    /**
     * Guarda um resultado de um atleta na base de dados.
     *
     * @param resultadosAtleta o resultado a ser guardado.
     * @throws RuntimeException se ocorrer um erro durante a inserção do resultado na base de dados.
     */
    @Override
    public void save(ResultadosAtleta resultadosAtleta) {

    }
    /**
     * Atualiza um resultado de um atleta na base de dados.
     *
     * @param resultadosAtleta o resultado a ser atualizado.
     */
    @Override
    public void update(ResultadosAtleta resultadosAtleta) {

    }
    /**
     * Exclui um resultado de um atleta da base de dados.
     *
     * @param resultadosAtleta o resultado a ser excluído.
     */
    @Override
    public void delete(ResultadosAtleta resultadosAtleta) {

    }

    /**
     * Obtém uma lista de resultados pelo ID do Atleta.
     *
     * @param id o ID do atleta.
     * @return os resultados correspondentes ao ID ou null se não for encontrado.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public List<ResultadosAtleta> getAllbyAthlete(int id) {
        List<ResultadosAtleta> lst = new ArrayList<ResultadosAtleta>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM resultados WHERE atleta_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lst.add(new ResultadosAtleta(rs.getInt("id"), rs.getDate("data"),
                        rs.getDouble("resultado"), rs.getString("tipo_resultado") , rs.getString("medalha") ,
                        rs.getInt("modalidade_id") , rs.getInt("atleta_id")));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao obter o resultado pelo ID: " + ex.getMessage());
        }
    }

    /**
     * Obtém uma lista de resultados filtrados para um atleta, com base no ano, modalidade e gênero selecionados.
     * Este método permite consultar os resultados do atleta, aplicando filtros de ano, modalidade e gênero.
     *
     * @param atletaId O ID do atleta cujos resultados serão consultados.
     * @param ano O ano a ser filtrado (pode ser null se não for especificado).
     * @param modalidade O nome da modalidade a ser filtrada (pode ser "Todas" para não aplicar filtro).
     * @param genero O gênero da modalidade a ser filtrado.
     *
     * @return Uma lista de objetos {@link ResultadosAtleta} que correspondem aos critérios de pesquisa.
     *
     * @throws RuntimeException Se ocorrer um erro ao acessar a base de dados ou ao realizar a consulta.
     */
    public List<ResultadosAtleta> getFilteredResultsByAthlete(int atletaId, Integer ano, String modalidade, String genero) {
        List<ResultadosAtleta> lst = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM resultados WHERE atleta_id = ?");

            if (ano != null) {
                query.append(" AND YEAR(data) = ?");
            }
            if (modalidade != null && !modalidade.equals("Todas")) {
                query.append(" AND modalidade_id = (SELECT id FROM modalidades WHERE nome = ? AND genero = ?)");
            }

            PreparedStatement ps = connection.prepareStatement(query.toString());
            ps.setInt(1, atletaId);

            int paramIndex = 2;
            if (ano != null) {
                ps.setInt(paramIndex++, ano);
            }
            if (modalidade != null && !modalidade.equals("Todas")) {
                ps.setString(paramIndex++, modalidade);
                ps.setString(paramIndex, genero);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lst.add(new ResultadosAtleta(rs.getInt("id"), rs.getDate("data"),
                        rs.getDouble("resultado"), rs.getString("tipo_resultado"), rs.getString("medalha"),
                        rs.getInt("modalidade_id"), rs.getInt("atleta_id")));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao obter resultados filtrados: " + ex.getMessage());
        }
    }

    /**
     * Obtém um resultado de um atleta com base no seu ID.
     *
     * @param i o ID do resultado a ser obtido.
     * @return um objeto {@link Optional} que pode conter o resultado encontrado ou estar vazio caso não encontrado.
     */
    @Override
    public Optional<ResultadosAtleta> get(int i) {
        return Optional.empty();
    }
}
