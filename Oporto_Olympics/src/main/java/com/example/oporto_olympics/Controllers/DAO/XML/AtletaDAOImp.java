package com.example.oporto_olympics.Controllers.DAO.XML;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.DAO;
import com.example.oporto_olympics.Models.Atleta;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtletaDAOImp implements DAOXML<Atleta> {

    private Connection conexao;
    private ConnectionBD database;

    /**
     * Construtor que inicializa a conexão com a base de dados.
     *
     * @param conexao conexão com a base de dados.
     */
    public AtletaDAOImp(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Obtém uma lista de todos os atletas registados na base de dados.
     *
     * @return lista de objetos Atleta ou null se não houver resultados.
     */
    @Override
    public List<Atleta> getAll() {
        List<Atleta> lst = new ArrayList<Atleta>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from atletas");
            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                while (rs.next()) {
                    lst.add(new Atleta(rs.getInt("user_id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getInt("altura_cm"), rs.getInt("peso_kg"), rs.getDate("data_nascimento"), null));
                }
            }

            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Guarda um novo atleta na base de dados. Se o atleta já existir, a operação é ignorada.
     * Também adiciona informações sobre o histórico de participações em competições.
     *
     * @param atleta o objeto Atleta a ser inserido na base de dados.
     */
    @Override
    public void save(Atleta atleta) {
        Optional<Atleta> AtletaExiste = get(atleta.getNome());

        if (AtletaExiste.isPresent() && atleta.getPais().equals(AtletaExiste.get().getPais()) && atleta.getGenero().equals(AtletaExiste.get().getGenero())) {
            return;
        }

        try {
            int numMecanografico = 0;

            PreparedStatement ps1 = conexao.prepareStatement("SELECT Max(num_mecanografico) as \"MaiorNumMecanografico\" FROM users");
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                int maiorNumMecanografico = rs1.getInt("MaiorNumMecanografico");

                if (maiorNumMecanografico > 0) {
                    numMecanografico = maiorNumMecanografico + 1;
                } else {
                    numMecanografico = 1000000;
                }
            }

            PreparedStatement ps = conexao.prepareStatement("INSERT INTO users (num_mecanografico, User_password, role, criado_em) VALUES(?,?,?,?)");
            ps.setInt(1, numMecanografico);
            ps.setString(2, StringtoHash(String.valueOf(numMecanografico)));
            ps.setString(3, "Atleta");
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();
            ps.close();

            atleta.setId(numMecanografico);
            update(atleta);

            for (int i = 0; i < atleta.getParticipaçõesAtletas().size(); i++) {
                PreparedStatement ps2 = conexao.prepareStatement("INSERT INTO historico_atletas_competicoes (atleta_id, evento_id, ano, medalha_ouro, medalha_prata, medalha_bronze) VALUES(?,?,?,?,?,?)");

                int id_atleta = get(atleta.getNome()).get().getId();

                ps2.setInt(1, id_atleta);
                ps2.setNull(2, java.sql.Types.INTEGER);
                ps2.setInt(3, atleta.getParticipaçõesAtletas().get(i).getAno());
                ps2.setInt(4, atleta.getParticipaçõesAtletas().get(i).getOuro());
                ps2.setInt(5, atleta.getParticipaçõesAtletas().get(i).getPrata());
                ps2.setInt(6, atleta.getParticipaçõesAtletas().get(i).getBronze());
                ps2.executeUpdate();
                ps2.close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir o atleta: " + ex.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Atualiza as informações de um atleta na base de dados.
     *
     * @param atleta o objeto Atleta a ser atualizado.
     */
    @Override
    public void update(Atleta atleta) {
        try {
            PreparedStatement ps = conexao.prepareStatement("UPDATE atletas SET atletas.nome = ?, atletas.data_nascimento = ?, atletas.genero = ?, atletas.altura_cm = ?, atletas.peso_kg = ?, atletas.pais_sigla = ? WHERE user_id = (SELECT id FROM users WHERE num_mecanografico = ?)");
            ps.setString(1, atleta.getNome());
            ps.setDate(2, new Date(atleta.getDataNascimento().getTime()));
            ps.setString(3, atleta.getGenero());
            ps.setInt(4, atleta.getAltura());
            ps.setInt(5, atleta.getPeso());
            ps.setString(6, atleta.getPais());
            ps.setInt(7, atleta.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar o atleta: " + ex.getMessage());
        }
    }

    /**
     * Elimina um atleta da base de dados.
     *
     * @param atleta o objeto Atleta a ser eliminado.
     */
    @Override
    public void delete(Atleta atleta) {

    }

    /**
     * Obtém um atleta com base no seu nome.
     *
     * @param nome o nome do atleta.
     * @return um Optional contendo o Atleta se encontrado, ou um Optional vazio.
     */
    @Override
    public Optional<Atleta> get(String nome) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM atletas WHERE nome = ?");
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Atleta(rs.getInt("user_id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getInt("altura_cm"), rs.getInt("peso_kg"), rs.getDate("data_nascimento"), null));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar o atleta: " + ex.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Converte uma string para um hash SHA-256.
     *
     * @param dado o dado a ser convertido em hash.
     * @return a string correspondente ao hash.
     * @throws NoSuchAlgorithmException se o algoritmo SHA-256 não estiver disponível.
     */
    public String StringtoHash(String dado) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashdado = md.digest(dado.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashdado) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
