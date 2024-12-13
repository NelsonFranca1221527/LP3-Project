package com.example.oporto_olympics.DAO.XML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.Equipa;
import com.example.oporto_olympics.Models.ParticipaocesEquipa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * A classe {@link EquipaDAOImp} implementa a interface {@link DAOXML} para manipulação de dados de equipas
 * na base de dados. Ela fornece métodos para realizar operações de CRUD (criação, leitura, atualização e exclusão)
 * sobre as equipas, bem como o histórico de participações e resultados em competições.
 */
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

                List<ParticipaocesEquipa> lstParticipacoes = new ArrayList<>();

                Statement stmt2 = conexao.createStatement();
                ResultSet rs2 = stmt2.executeQuery("Select * from historico_equipas_competicoes where equipa_id = " + rs.getInt("id"));
                while (rs2.next()) {
                    lstParticipacoes.add(new ParticipaocesEquipa(rs2.getInt("ano"), rs2.getString("resultado")));
                }

                lst.add(new Equipa(rs.getInt("id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getString("desporto"), rs.getInt("modalidade_id"), rs.getInt("ano_fundacao"), lstParticipacoes));
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

            for (ParticipaocesEquipa participaocesEquipa : equipa.getParticipaçõesEquipa()){
                saveHistorico(id_equipa, 0, participaocesEquipa);
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

                List<ParticipaocesEquipa> lstParticipacoes = new ArrayList<>();

                Statement stmt2 = conexao.createStatement();
                ResultSet rs2 = stmt2.executeQuery("Select * from historico_equipas_competicoes where equipa_id = " + rs.getInt("id"));
                while (rs2.next()) {
                    lstParticipacoes.add(new ParticipaocesEquipa(rs2.getInt("ano"), rs2.getString("resultado")));
                }

                return Optional.of(new Equipa(rs.getInt("id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getString("desporto"), rs.getInt("modalidade_id"), rs.getInt("ano_fundacao"), lstParticipacoes));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a equipa: " + ex.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Insere os dados de participação de uma equipa num evento na tabela
     * `historico_equipas_competicoes` da base de dados.
     *
     * @param id_equipa O identificador único da equipa na base de dados.
     * @param evento_id O identificador único do evento na base de dados.
     *                  Se for igual a 0, será registado como `NULL` na base de dados.
     * @param participaocesEquipa Um objeto da classe {@code ParticipaçõesEquipa} que contém
     *                            os detalhes da participação, incluindo o ano de participação
     *                            e o resultado obtido pela equipa.
     * @throws SQLException Lançada se ocorrer um erro ao executar a operação SQL.
     * @throws RuntimeException Envolvendo a exceção SQL se ocorrer algum erro no processo,
     *                          com uma mensagem detalhada do problema.
     */
    public void saveHistorico(int id_equipa, int evento_id, ParticipaocesEquipa participaocesEquipa) throws SQLException {
        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO historico_equipas_competicoes (equipa_id, evento_id, ano, resultado) VALUES(?,?,?,?)");
            ps.setInt(1, id_equipa);
            ps.setNull(2, java.sql.Types.INTEGER);

            if(evento_id != 0){
                ps.setInt(2, evento_id);
            }

            ps.setInt(3, participaocesEquipa.getAnoParticipacao());
            ps.setString(4, participaocesEquipa.getResultado());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir histórico na equipa: " + ex.getMessage());
        }
    }

    /**
     * Verifica o estado de uma equipa com base no seu ID.
     *
     * Este método executa uma consulta à base de dados para determinar o estado de uma equipa
     * específica, utilizando o seu identificador único. O estado da equipa é obtido a partir
     * da coluna "equipa_status" na tabela "equipas".
     *
     * @param equipaID o identificador único da equipa a ser verificada.
     * @return {@code true} se o estado da equipa for verdadeiro (fechada); {@code false} caso contrário
     *         ou se a equipa não for encontrada.
     * @throws RuntimeException se ocorrer um erro durante a execução da consulta à base de dados.
     */
    public boolean getStatus(int equipaID) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT equipa_status FROM equipas WHERE id = ?");
            ps.setInt(1, equipaID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("equipa_status");
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a equipa: " + ex.getMessage());
        }
        return false;
    }

    /**
     * Atualiza o estado de uma equipa na tabela `equipas`.
     *
     * Este método altera o estado (`equipa_status`) de uma equipa específica na base de dados,
     * com base no ID da equipa e no novo estado fornecidos.
     *
     * @param equipaID o ID da equipa cujo estado será alterado.
     * @param status o novo estado a ser atribuído à equipa (1 para ativo, 0 para inativo).
     * @throws RuntimeException se ocorrer um erro ao executar a query SQL.
     */
    public void updateStatus(int equipaID, int status) {
        String updateQuery = "UPDATE equipas SET equipa_status = ? WHERE id = ?";

        try (PreparedStatement pstmt = conexao.prepareStatement(updateQuery)) {
            pstmt.setInt(1, status);
            pstmt.setInt(2, equipaID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o status da equipa: " + e.getMessage());
        }
    }

    /**
     * Verifica se uma sigla de país existe na tabela `paises`.
     *
     * Este método consulta a tabela `paises` para verificar se uma determinada sigla de país
     * está registada na base de dados.
     *
     * @param sigla a sigla do país a ser verificada (por exemplo, "PT", "BR").
     * @return `true` se a sigla existir na tabela, ou `false` caso contrário.
     * @throws RuntimeException se ocorrer um erro ao executar a query SQL.
     */
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
