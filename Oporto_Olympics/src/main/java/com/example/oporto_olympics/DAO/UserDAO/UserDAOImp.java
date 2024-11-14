package com.example.oporto_olympics.DAO.UserDAO;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Resultados;
import com.example.oporto_olympics.Models.User;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Classe responsável pela implementação dos métodos da interface {@link DAO} para a entidade {@link User}.
 * Esta classe realiza operações CRUD (Create, Read, Update, Delete) na base de dados para manipular utilizadores.
 * A comunicação com a base de dados é feita através de uma conexão {@link Connection}.
 */
public class UserDAOImp implements DAO<User> {

    private static Connection connection;
    private ConnectionBD database;
    /**
     * Construtor da classe {@link UserDAOImp}.
     * Inicializa a conexão com a base de dados para interagir com as tabelas associadas à entidade {@link User}.
     *
     * @param connection A conexão com a base de dados a ser utilizada pelas operações DAO.
     */
    public UserDAOImp(Connection connection) {
        this.connection = connection;
    }
    /**
     * Obtém as informações de um atleta a partir do seu ID.
     * Esta função faz uma junção entre as tabelas "atletas" e "users" para obter os dados completos do atleta.
     *
     * @param id O ID do utilizador para obter as informações do atleta.
     * @return O objeto {@link Atleta} contendo as informações do atleta, ou null se não encontrado.
     * @throws SQLException Caso haja erro na consulta à base de dados.
     */
    public Atleta getAtletaInfo(int id) throws SQLException {
        try {
            database = ConnectionBD.getInstance();
            connection = database.getConexao();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM atletas a JOIN users u ON a.user_id = u.id WHERE u.id = ?");
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            System.out.println(id);

            if (rs.next()) {

                System.out.println(rs.getString("nome"));
                return new Atleta(
                        rs.getInt("user_id"),
                        rs.getString("nome"),
                        rs.getString("pais_sigla"),
                        rs.getString("genero"),
                        rs.getInt("altura_cm"),
                        rs.getInt("peso_kg"),
                        rs.getDate("data_nascimento"),
                        null
                );
            } else {

                System.err.println("No atleta found with ID: " + id);
                return null;
            }

        } catch (SQLException e) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", e.getMessage());
            alertHandler.getAlert().show();
        }

        return null;
    }
    /**
     * Obtém o ID de um utilizador com base no seu número mecanográfico.
     *
     * @param Num_Mecanografico O número mecanográfico do utilizador.
     * @return O ID do utilizador correspondente ao número mecanográfico fornecido.
     * @throws SQLException Caso haja erro na consulta à base de dados.
     */
    public int getID(int Num_Mecanografico) throws SQLException {
        try {
            database = ConnectionBD.getInstance();
            connection = database.getConexao();

            PreparedStatement ps = connection.prepareStatement("SELECT id FROM users WHERE num_mecanografico = ?");
            ps.setInt(1, Num_Mecanografico);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            return rs.getInt("id");

        } catch (SQLException e) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", e.getMessage());
            alertHandler.getAlert().show();
        }

        return 0;
    }

    /**
     * Verifica se um utilizador existe na base de dados, comparando o número mecanográfico e a senha fornecida.
     *
     * @param Num_Mecanografico O número mecanográfico do utilizador.
     * @param senha A senha fornecida pelo utilizador.
     * @return true se o utilizador existir com a senha correta, false caso contrário.
     * @throws SQLException Caso haja erro na consulta à base de dados.
     */
    public boolean getUser(int Num_Mecanografico, String senha) throws SQLException {
        try {
            database = ConnectionBD.getInstance();
            connection = database.getConexao();

            String getUtilizadorQuery = "SELECT * FROM users WHERE num_mecanografico = '" + Num_Mecanografico + "' AND User_password = '" + senha + "'";
            Statement statementGetUtilizador = connection.createStatement();
            ResultSet resultSetUtilizador = statementGetUtilizador.executeQuery(getUtilizadorQuery);

            if(!resultSetUtilizador.next()){
                connection.close();
                return false;
            }

            return true;

        } catch (SQLException e) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", e.getMessage());
            alertHandler.getAlert().show();
        }

        return false;
    }

    /**
     * Obtém o tipo de permissão (role) de um utilizador, verificando o número mecanográfico e a senha.
     *
     * @param Num_Mecanografico O número mecanográfico do utilizador.
     * @param senha A senha fornecida pelo utilizador.
     * @return O nome do tipo de permissão do utilizador (por exemplo, "Admin", "Atleta").
     * @throws SQLException Caso haja erro na consulta à base de dados.
     */
    public String getUserType(int Num_Mecanografico, String senha) throws SQLException {
        try {
            database = ConnectionBD.getInstance();
            connection = database.getConexao();

            PreparedStatement ps = connection.prepareStatement("SELECT r.nome FROM roles r, users u WHERE u.num_mecanografico = ? AND u.User_password = ? AND u.role_id = r.id");
            ps.setInt(1, Num_Mecanografico);
            ps.setString(2, senha);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            if (!rs.next()) {
                connection.close();
                return "";

            }

            // Obtém o tipo de permissão da DB
            String UserType = rs.getString("nome");

            return UserType;
        } catch (SQLException e) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", e.getMessage());
            alertHandler.getAlert().show();
        }
        return "";
    }
    /**
     * Obtém todos os utilizadores registrados na base de dados.
     *
     * @return Lista de objetos {@link User} representando todos os utilizadores da base de dados.
     */
    @Override
    public List<User> getAll() {
        List<User> lst = new ArrayList<User>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                lst.add(new User(rs.getInt("id"), rs.getInt("num_mecanografico"),
                        rs.getString("password"), rs.getString("role"), rs.getDate("criado_em")));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar os utilizadores: " + ex.getMessage());
        }
    }


    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public Optional<User> get(int i) {
        return Optional.empty();
    }
}
