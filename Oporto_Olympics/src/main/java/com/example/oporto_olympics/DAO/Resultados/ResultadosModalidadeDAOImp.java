package com.example.oporto_olympics.DAO.Resultados;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.Models.ResultadosModalidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Implementação do DAO (Data Access Object) para a tabela de resultados de atletas.
 * Esta classe fornece métodos para interagir com a base de dados e manipular as informações dos resultados de atletas.
 */
public class ResultadosModalidadeDAOImp implements DAO<ResultadosModalidade> {
    private static Connection connection;
    private ConnectionBD database;
    /**
     * Construtor da classe que inicializa a conexão com a base de dados.
     *
     * @param connection a conexão com a base de dados.
     */
    public ResultadosModalidadeDAOImp(Connection connection) {
        this.connection = connection;
    }
    /**
     * Obtém o nome dos atletas registados na base de dados.
     *
     * @return uma lista de objetos {@link ResultadosModalidade} representando todos os resultados de atletas na base de dados.
     * @throws RuntimeException se ocorrer um erro ao obter os resultados de atletas.
     */
    public List<String> getAtletaNome (int id){
        List<String> nomes = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nome FROM atletas WHERE user_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nomes.add(rs.getString("nome"));
            }
            return nomes;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao obter o resultado pelo ID: " + ex.getMessage());
        }
    }
    /**
     * Obtém uma lista de resultados filtrados para um atleta, com base no ano, modalidade e gênero selecionados.
     * Este método permite consultar os resultados do atleta, aplicando filtros de ano, modalidade e gênero.
     *
     *
     * @param modalidade O nome da modalidade a ser filtrada (pode ser "Todas" para não aplicar filtro).
     * @return Uma lista de objetos {@link ResultadosModalidade} que correspondem aos critérios de pesquisa.
     *
     * @throws RuntimeException Se ocorrer um erro ao acessar a base de dados ou ao realizar a consulta.
     */
    public List<ResultadosModalidade> getFilteredResults(int modalidade) {
        List<ResultadosModalidade> lst = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM resultados WHERE modalidade_id = ?");

            if (modalidade != 0) {
                ps.setInt(1, modalidade);
            } else if (modalidade == 0){
                ps = connection.prepareStatement("SELECT * FROM resultados");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lst.add(new ResultadosModalidade(rs.getInt("id"), rs.getDate("data"),
                        rs.getDouble("resultado"), rs.getString("tipo_resultado"), rs.getString("medalha"),
                        rs.getInt("modalidade_id"), rs.getInt("atleta_id"), 0));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao obter resultados filtrados: " + ex.getMessage());
        }
    }
    /**
     * Obtém os top 10 resultados registados na base de dados.
     *
     * @return uma lista de objetos {@link ResultadosModalidade} representando os top 10 resultados resgitados na base de dados.
     * @throws RuntimeException se ocorrer um erro ao obter os resultados de atletas.
     */
    public List<ResultadosModalidade> getAllOrderedTopTen(int id) {
        List<ResultadosModalidade> lst = new ArrayList<>();
        try {

            PreparedStatement ps = connection.prepareStatement("SELECT TOP 10 * FROM resultados WHERE modalidade_id = ? ORDER BY resultado ASC");
            if (id != 0) {
                ps.setInt(1, id);
            } else {
                return null;
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lst.add(new ResultadosModalidade(rs.getInt("id"), rs.getDate("data"),
                        rs.getDouble("resultado"), rs.getString("tipo_resultado") , rs.getString("medalha") ,
                        rs.getInt("modalidade_id") , rs.getInt("atleta_id"), rs.getInt("equipa_id")));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar os locais: " + ex.getMessage());
        }
    }
    /**
     * Obtém todos os resultados de atletas registados na base de dados.
     *
     * @return uma lista de objetos {@link ResultadosModalidade} representando todos os resultados de atletas na base de dados.
     * @throws RuntimeException se ocorrer um erro ao obter os resultados de atletas.
     */
    @Override
    public List<ResultadosModalidade> getAll() {
        List<ResultadosModalidade> lst = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM resultados");
            while (rs.next()) {
                lst.add(new ResultadosModalidade(rs.getInt("id"), rs.getDate("data"),
                        rs.getDouble("resultado"), rs.getString("tipo_resultado") , rs.getString("medalha") ,
                        rs.getInt("modalidade_id") , rs.getInt("atleta_id"), rs.getInt("equipa_id")));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar os locais: " + ex.getMessage());
        }
    }
    /**
     * Guarda um resultado de um atleta na base de dados.
     *
     * Este método insere um novo resultado de modalidade para um atleta na base de dados.
     *
     * @param resultadosModalidade o objeto que contém os dados do resultado a ser guardado.
     * @throws RuntimeException se ocorrer um erro durante a inserção do resultado na base de dados.
     */
    @Override
    public void save(ResultadosModalidade resultadosModalidade) {
        String query = "INSERT INTO resultados (data, resultado, tipo_resultado, medalha, modalidade_id, atleta_id, equipa_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Definir os parâmetros do SQL
            stmt.setDate(1, new java.sql.Date(resultadosModalidade.getData().getTime()));
            stmt.setDouble(2, resultadosModalidade.getResultado());
            stmt.setString(3, resultadosModalidade.getTipo());
            stmt.setString(4, resultadosModalidade.getMedalha());
            stmt.setInt(5, resultadosModalidade.getModalidadeID());

            // Verificar e definir atleta_id e equipa_id
            if (resultadosModalidade.getAtletaID() == 0) {
                stmt.setNull(6, java.sql.Types.INTEGER);  // Define null para atleta_id
            } else {
                stmt.setInt(6, resultadosModalidade.getAtletaID());  // Define atleta_id
            }

            if (resultadosModalidade.getEquipaID() == 0) {
                stmt.setNull(7, java.sql.Types.INTEGER);  // Define null para equipa_id
            } else {
                stmt.setInt(7, resultadosModalidade.getEquipaID());  // Define equipa_id
            }

            // Executa a atualização
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao guardar o resultado: " + ex.getMessage());
        }
    }


    /**
     * Atualiza um resultado de um atleta na base de dados.
     *
     * @param resultadosAtleta o resultado a ser atualizado.
     */
    @Override
    public void update(ResultadosModalidade resultadosAtleta) {

    }
    /**
     * Exclui um resultado de um atleta da base de dados.
     *
     * @param resultadosAtleta o resultado a ser excluído.
     */
    @Override
    public void delete(ResultadosModalidade resultadosAtleta) {

    }

    /**
     * Obtém uma lista de resultados pelo ID do Atleta.
     *
     * @param id o ID do atleta.
     * @return os resultados correspondentes ao ID ou null se não for encontrado.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public List<ResultadosModalidade> getAllbyAthlete(int id) {
        List<ResultadosModalidade> lst = new ArrayList<ResultadosModalidade>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM resultados WHERE atleta_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lst.add(new ResultadosModalidade(rs.getInt("id"), rs.getDate("data"),
                        rs.getDouble("resultado"), rs.getString("tipo_resultado") , rs.getString("medalha") ,
                        rs.getInt("modalidade_id") , rs.getInt("atleta_id"), 0));
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
     * @return Uma lista de objetos {@link ResultadosModalidade} que correspondem aos critérios de pesquisa.
     *
     * @throws RuntimeException Se ocorrer um erro ao acessar a base de dados ou ao realizar a consulta.
     */
    public List<ResultadosModalidade> getFilteredResultsByAthlete(int atletaId, Integer ano, String modalidade, String genero) {
        List<ResultadosModalidade> lst = new ArrayList<>();
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
                lst.add(new ResultadosModalidade(rs.getInt("id"), rs.getDate("data"),
                        rs.getDouble("resultado"), rs.getString("tipo_resultado"), rs.getString("medalha"),
                        rs.getInt("modalidade_id"), rs.getInt("atleta_id"), 0));
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
    public Optional<ResultadosModalidade> get(int i) {
        return Optional.empty();
    }
}
