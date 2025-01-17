package com.example.oporto_olympics.DAO.Locais;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.Models.Local;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Implementação do DAO (Data Access Object) para a tabela de locais.
 * Esta classe fornece métodos para interagir com a base de dados e manipular as informações dos locais.
 */
public class LocaisDAOImp implements DAO<Local> {
    private static Connection connection;
    private ConnectionBD database;
    /**
     * Construtor da classe que inicializa a conexão com a base de dados.
     *
     * @param connection a conexão com a base de dados.
     */
    public LocaisDAOImp(Connection connection) {
        this.connection = connection;
    }
    /**
     * Obtém todos os locais registados na base de dados.
     *
     * @return uma lista de objetos {@link Local} representando todos os locais na base de dados.
     * @throws RuntimeException se ocorrer um erro ao obter os locais.
     */
    @Override
    public List<Local> getAll() {
        List<Local> lst = new ArrayList<Local>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locais");
            while (rs.next()) {
                lst.add(new Local(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("tipo"), rs.getString("morada") , rs.getString("cidade") ,
                        rs.getString("pais_sigla") , rs.getInt("capacidade") , rs.getDate(  "data_construcao")));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar os locais: " + ex.getMessage());
        }
    }
    /**
     * Guarda um local na base de dados.
     *
     * @param local o local a ser guardado.
     * @throws RuntimeException se ocorrer um erro durante a inserção do local na base de dados.
     */
    @Override
    public void save(Local local) {
        try {

            String tipoLocal = local.getTipo();
            System.out.println("Tipo de local: " + tipoLocal);

            if (tipoLocal == null || tipoLocal.trim().isEmpty()) {
                System.out.println("O tipo de local é nulo ou vazio!");
                throw new IllegalArgumentException("Tipo de local não pode ser nulo ou vazio.");
            }

            CallableStatement cs;

            if ("interior".equalsIgnoreCase(tipoLocal)) {
                cs = connection.prepareCall("{CALL SaveInteriorLocal(?, ?, ?, ?, ?, ?, ?)}");

                cs.setString(1, local.getNome());
                cs.setString(2, local.getTipo());
                cs.setString(3, local.getMorada());
                cs.setString(4, local.getCidade());
                cs.setInt(5, local.getCapacidade());

                String anoConstrucaoString = String.valueOf(local.getAno_construcao());
                java.sql.Date anoConstrucaoDate = java.sql.Date.valueOf(anoConstrucaoString);
                cs.setDate(6, anoConstrucaoDate);

                cs.setString(7, local.getPais());

            } else if ("exterior".equalsIgnoreCase(tipoLocal)) {
                cs = connection.prepareCall("{CALL SaveExteriorLocal(?, ?, ?, ?, ?)}");

                cs.setString(1, local.getNome());
                cs.setString(2, local.getTipo());
                cs.setString(3, local.getMorada());
                cs.setString(4, local.getCidade());
                cs.setString(5, local.getPais());

            } else {

                System.out.println("Tipo de local inválido: " + tipoLocal);
                throw new IllegalArgumentException("Tipo de local inválido: " + tipoLocal);
            }

            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir o local usando a stored procedure: " + ex.getMessage());
        }
    }





    /**
     * Atualiza um local na base de dados.
     *
     * @param local o local a ser atualizado.
     */
    @Override
    public void update(Local local) {

    }
    /**
     * Exclui um local da base de dados.
     *
     * @param local o local a ser excluído.
     */
    @Override
    public void delete(Local local) {

    }
    /**
     * Verifica se já existe um local na base de dados com as mesmas informações fornecidas.
     *
     * @param nome o nome do local.
     * @param tipo o tipo do local.
     * @param morada a morada do local.
     * @param cidade a cidade do local.
     * @param pais a sigla do país onde o local está situado.
     * @return verdadeiro se o local existir, caso contrário falso.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public boolean existsByLocal(String nome, String tipo, String morada, String cidade, String pais) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) FROM locais WHERE nome = ? AND tipo = ?"
            );
            ps.setString(1, nome);
            ps.setString(2, tipo);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            ps.close();
            return count > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao verificar a existência do local: " + ex.getMessage());
        }
    }
    /**
     * Obtém o nome de um local com base no seu ID.
     *
     * @param id o ID do local.
     * @return o nome do local correspondente ao ID, ou null se o local não for encontrado.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public String getNomeById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nome FROM locais WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nome");
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao obter o nome do local: " + ex.getMessage());
        }
        return null;  // Retorna null se o local não for encontrado
    }
    /**
     * Verifica se uma sigla de país existe na base de dados.
     *
     * @param sigla a sigla do país a ser verificada.
     * @return verdadeiro se a sigla for encontrada, caso contrário falso.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public boolean getSigla(String sigla) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nome FROM paises WHERE sigla = ?");
            ps.setString(1, sigla);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a sigla: " + ex.getMessage());
        }
    }
    /**
     * Obtém um local com base no seu ID.
     *
     * @param i o ID do local a ser obtido.
     * @return um objeto {@link Optional} que pode conter o local encontrado ou estar vazio caso não encontrado.
     */
    @Override
    public Optional<Local> get(int i) {
        return Optional.empty();
    }
}
