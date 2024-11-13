package com.example.oporto_olympics.DAO.XML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.Equipa;
import com.example.oporto_olympics.Models.ParticipaçõesAtleta;
import com.example.oporto_olympics.Models.ParticipaçõesEquipa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipaDAOImp implements DAOXML<Equipa> {

    private Connection conexao;
    private ConnectionBD database;

    /**
     * Construtor que inicializa a conexão com a base de dados.
     *
     * @param conexao Conexão à base de dados.
     */
    public EquipaDAOImp(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Obtém uma lista de todas as equipas registadas na base de dados.
     *
     * @return lista de objetos Equipa.
     */
    @Override
    public List<Equipa> getAll() {
        List<Equipa> lst = new ArrayList<Equipa>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("Select equipas.* From equipas, modalidades where equipas.modalidade_id = modalidades.id");
            while (rs.next()) {

                List<ParticipaçõesEquipa> lstParticipacoes = new ArrayList<>();

                Statement stmt2 = conexao.createStatement();
                ResultSet rs2 = stmt2.executeQuery("Select * from historico_equipas_competicoes where equipa_id = " + rs.getInt("id"));
                while (rs2.next()) {
                    lstParticipacoes.add(new ParticipaçõesEquipa(rs2.getInt("ano"), rs2.getString("resultado")));
                }

                lst.add(new Equipa(rs.getInt("id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getString("descricao"), rs.getInt("modalidade_id"), rs.getInt("ano_fundacao"), lstParticipacoes));
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a lista das equipas: " + ex.getMessage());
        }
    }

    /**
     * Guarda uma nova equipa na base de dados. Se a equipa já existir, a operação será ignorada.
     * Também guarda o histórico de participações da equipa.
     *
     * @param equipa O objeto Equipa a ser inserido na base de dados.
     */
    @Override
    public void save(Equipa equipa) {

        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO equipas (pais_sigla, ano_fundacao, modalidade_id, nome, genero, desporto) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, equipa.getPais());
            ps.setInt(2, equipa.getAnoFundacao());
            ps.setInt(3, equipa.getModalidadeID());
            ps.setString(4, equipa.getNome());
            ps.setString(5, equipa.getGenero());
            ps.setString(6, equipa.getDesporto());
            ps.executeUpdate();

            if(equipa.getParticipaçõesEquipa().isEmpty() || equipa.getParticipaçõesEquipa() == null){
                return;
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (!generatedKeys.next()) {
                return;
            }

            int id_equipa = generatedKeys.getInt(1);

            ps.close();

            for (int i = 0; i < equipa.getParticipaçõesEquipa().size(); i++) {
                PreparedStatement ps2 = conexao.prepareStatement("INSERT INTO historico_equipas_competicoes (equipa_id, evento_id, ano, resultado) VALUES(?,?,?,?)");
                ps2.setInt(1, id_equipa);
                ps2.setNull(2, java.sql.Types.INTEGER);
                ps2.setInt(3, equipa.getParticipaçõesEquipa().get(i).getAnoParticipacao());
                ps2.setString(4, equipa.getParticipaçõesEquipa().get(i).getResultado());
                ps2.executeUpdate();
                ps2.close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a equipa: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Atualiza os dados de uma equipa na base de dados.
     *
     * @param equipa O objeto Equipa com os novos dados a serem atualizados.
     */
    @Override
    public void update(Equipa equipa) {

    }

    /**
     * Elimina uma equipa da base de dados.
     *
     * @param equipa O objeto Equipa a ser eliminado.
     */
    @Override
    public void delete(Equipa equipa) {

    }

    /**
     * Obtém uma equipa com base no nome.
     *
     * @param nome O nome da equipa a ser pesquisada.
     * @return Um Optional com a Equipa, caso encontrada, ou um Optional vazio.
     */
    @Override
    public Optional<Equipa> get(String nome) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM equipas WHERE nome = ?");
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                List<ParticipaçõesEquipa> lstParticipacoes = new ArrayList<>();

                Statement stmt2 = conexao.createStatement();
                ResultSet rs2 = stmt2.executeQuery("Select * from historico_equipas_competicoes where equipa_id = " + rs.getInt("id"));
                while (rs2.next()) {
                    lstParticipacoes.add(new ParticipaçõesEquipa(rs2.getInt("ano"), rs2.getString("resultado")));
                }

                return Optional.of(new Equipa(rs.getInt("id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getString("desporto"), rs.getInt("modalidade_id"), rs.getInt("ano_fundacao"), lstParticipacoes));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a equipa: " + ex.getMessage());
        }

        return Optional.empty();
    }

    public boolean getSigla(String sigla) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT nome FROM paises WHERE sigla = ?");
            ps.setString(1, sigla); 
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a sigla: " + ex.getMessage());
        }
    }
}
