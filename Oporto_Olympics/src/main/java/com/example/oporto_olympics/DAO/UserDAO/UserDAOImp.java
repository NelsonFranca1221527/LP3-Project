package com.example.oporto_olympics.DAO.UserDAO;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Gestor;
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
     * Atualiza a palavra-passe de um utilizador no sistema.
     *
     * Este método recebe o ID de um utilizador e uma nova palavra-passe,
     * atualizando o registo correspondente na base de dados. Caso a operação
     * seja bem-sucedida, apresenta uma mensagem de sucesso ao utilizador.
     * Se não forem encontrados registos para atualizar ou ocorrer um erro,
     * uma mensagem de aviso ou erro será exibida.
     *
     * @param id           o ID do utilizador cuja palavra-passe será atualizada.
     * @param newPassword  a nova palavra-passe a ser atribuída ao utilizador.
     *
     * @throws SQLException se ocorrer um erro durante a execução da consulta SQL.
     */
    public void UpdatePassword(int id , String newPassword) {
        try {
            database = ConnectionBD.getInstance();
            connection = database.getConexao();

            PreparedStatement ps = connection.prepareStatement("UPDATE users SET User_password = ? WHERE id = ?");
            ps.setString(1, newPassword);
            ps.setInt(2, id);


            int rs = ps.executeUpdate();

            if (rs > 0) {
                AlertHandler alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Alteração da Password", "A sua password foi alterada com sucesso!!");
                alertHandler.getAlert().show();
            } else {
                AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Alteração da Password", "A sua password não foi alterada verifique os campos ou o id..");
                alertHandler.getAlert().show();
            }

        } catch (SQLException e) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", e.getMessage());
            alertHandler.getAlert().show();
        }


    }

    /**
     * Obtém as informações de um atleta a partir do seu número mecanográfico e password.
     * Esta função faz uma junção entre as tabelas "atletas" e "users" para obter os dados completos do atleta.
     *
     * @param num_mecanografico O número mecanográfico do utilizador para obter as informações do atleta.
     * @param senha A senha para obter as informações do atleta.
     * @return O objeto {@link Atleta} contendo as informações do atleta, ou null se não encontrado.
     * @throws SQLException Caso haja erro na consulta à base de dados.
     */
    public Atleta getAtletaInfo(int num_mecanografico, String senha) throws SQLException {
        try {
            database = ConnectionBD.getInstance();
            connection = database.getConexao();

            PreparedStatement ps = connection.prepareStatement("SELECT a.*, u.imagem FROM atletas a, users u WHERE a.user_id = u.id AND u.num_mecanografico = ? AND u.User_password = ?");
            ps.setInt(1, num_mecanografico);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Atleta(
                        rs.getInt("user_id"),
                        rs.getString("nome"),
                        rs.getString("pais_sigla"),
                        rs.getString("genero"),
                        rs.getInt("altura_cm"),
                        rs.getInt("peso_kg"),
                        rs.getDate("data_nascimento"),
                        null,
                        rs.getBytes("imagem")
                );
            }

        } catch (SQLException e) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", e.getMessage());
            alertHandler.getAlert().show();
        }

        return null;
    }

    /**
     * Obtém as informações de um gestor a partir do seu número mecanográfico e password.
     * Esta função faz uma junção entre as tabelas "gestores" e "users" para obter os dados completos do gestor.
     *
     * @param Num_Mecanografico O número mecanográfico do utilizador para obter informações do Gestor.
     * @return O objeto {@link Gestor} contendo as informações do Gestor, ou null se não encontrado.
     * @throws SQLException Caso haja erro na consulta à base de dados.
     */
    public Gestor getGestorInfo(int Num_Mecanografico, String senha) throws SQLException {
        try {
            database = ConnectionBD.getInstance();
            connection = database.getConexao();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM gestores a, users u WHERE a.user_id = u.id AND u.num_mecanografico = ? AND u.User_password = ?");
            ps.setInt(1, Num_Mecanografico);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Gestor(
                        rs.getInt("user_id"),
                        rs.getString("nome")
                );
            }

        } catch (SQLException e) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", e.getMessage());
            alertHandler.getAlert().show();
        }

        return null;
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
