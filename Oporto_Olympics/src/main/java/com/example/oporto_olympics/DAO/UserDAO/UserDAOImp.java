package com.example.oporto_olympics.DAO.UserDAO;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.User;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImp implements DAO<User> {

    private static Connection connection;
    private ConnectionBD database;

    public UserDAOImp(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * Verifica os dados inseridos com os dados gravados na base de dados
     *
     * @param Num_Mecanografico
     * @param senha
     * @param Role
     * @return
     * @throws SQLException
     */
    public boolean getUser(int Num_Mecanografico, String senha, String Role) throws SQLException {
        try {
            database = ConnectionBD.getInstance();
            connection = database.getConexao();

            String getUtilizadorQuery = "SELECT * FROM users WHERE num_mecanografico = '" + Num_Mecanografico + "' AND User_password = '" + senha + "'";;
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
     * Vai a base de dados a permissão do utizador inserido no Login
     *
     * @param Num_Mecanografico
     * @param senha
     * @return UserType
     * @throws SQLException
     */
    public String getUserType(int Num_Mecanografico, String senha) throws SQLException {
        try {
            database = ConnectionBD.getInstance();
            connection = database.getConexao();

            String getUserTypeQuery = "SELECT role FROM users WHERE num_mecanografico = '" + Num_Mecanografico + "' AND User_password = '" + senha + "'";
            Statement stmtGetUserType = connection.createStatement();
            ResultSet resultSetUserType = stmtGetUserType.executeQuery(getUserTypeQuery);

            if (!resultSetUserType.next()) {
                connection.close();
                return "";

            }

            // Obtém o tipo de permissão da DB
            String UserType = resultSetUserType.getString("role");

            return UserType;
        } catch (SQLException e) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", e.getMessage());
            alertHandler.getAlert().show();
        }
        return "";
    }

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
