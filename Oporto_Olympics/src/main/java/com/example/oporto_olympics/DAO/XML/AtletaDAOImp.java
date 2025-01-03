package com.example.oporto_olympics.DAO.XML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.DAO;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Misc.Encriptacao;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.InscricaoEquipas;
import com.example.oporto_olympics.Models.ParticipaçõesAtleta;
import javafx.scene.control.Alert;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * A classe {@link AtletaDAOImp} implementa a interface {@link DAOXML} para manipulação de dados de atletas
 * na base de dados. Fornece métodos para obter, salvar, atualizar e excluir informações de atletas,
 * incluindo o histórico de participações em competições.
 */
public class AtletaDAOImp implements DAO<Atleta> {

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
            ResultSet rs = stmt.executeQuery("SELECT a.*, u.imagem FROM atletas as a, users as u where a.user_id=u.id");
            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                while (rs.next()) {

                    List<ParticipaçõesAtleta> lstParticipacoes = new ArrayList<>();

                    Statement stmt2 = conexao.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("Select * from historico_atletas_competicoes where atleta_id = " + rs.getInt("user_id"));
                        while (rs2.next()) {
                            lstParticipacoes.add(new ParticipaçõesAtleta(rs2.getInt("ano"),rs2.getInt("medalha_ouro"),rs2.getInt("medalha_prata"),rs2.getInt("medalha_bronze")));
                        }

                    lst.add(new Atleta(rs.getInt("user_id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getInt("altura_cm"), rs.getInt("peso_kg"), rs.getDate("data_nascimento"), lstParticipacoes, rs.getBytes("imagem")));
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

        AlertHandler alertHandler;

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

            PreparedStatement ps2 = conexao.prepareStatement("Select id as \"IdAtleta\" From roles Where nome = ?");
            ps2.setString(1, "Atleta");
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Utilizador Não Encontrado", "Tipo de Utilizador não encontrado!!");
                alertHandler.getAlert().showAndWait();
                return;
            }

            Encriptacao encriptacao = new Encriptacao();

            PreparedStatement ps3 = conexao.prepareStatement("INSERT INTO users (num_mecanografico, User_password, criado_em, role_id) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps3.setInt(1, numMecanografico);
            ps3.setString(2, encriptacao.StringtoHash(String.valueOf(numMecanografico)));
            ps3.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps3.setInt(4, rs2.getInt("IdAtleta"));
            ps3.executeUpdate();

            ResultSet generatedKeys = ps3.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    return;
                }

            atleta.setId(generatedKeys.getInt(1));
            ps3.close();

            PreparedStatement ps = conexao.prepareStatement("INSERT INTO atletas (nome, data_nascimento, genero, altura_cm, peso_kg , pais_sigla, user_id) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, atleta.getNome());
            ps.setDate(2, new Date(atleta.getDataNascimento().getTime()));
            ps.setString(3, atleta.getGenero());
            ps.setInt(4, atleta.getAltura());
            ps.setInt(5, atleta.getPeso());
            ps.setString(6, atleta.getPais());
            ps.setInt(7, atleta.getId());
            ps.executeUpdate();

            if(atleta.getParticipaçõesAtletas().isEmpty() || atleta.getParticipaçõesAtletas() == null){
                return;
            }

            for(ParticipaçõesAtleta participaçõesAtleta : atleta.getParticipaçõesAtletas()){
                saveHistorico(atleta.getId(), 0, participaçõesAtleta);
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
            PreparedStatement ps = conexao.prepareStatement("UPDATE atletas SET atletas.nome = ?, atletas.data_nascimento = ?, atletas.genero = ?, atletas.altura_cm = ?, atletas.peso_kg = ?, atletas.pais_sigla = ? WHERE user_id = ?");
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
     * Obtém um atleta com base no seu id.
     *
     * @param atletaID o id do atleta.
     * @return um Optional contendo o Atleta se encontrado, ou um Optional vazio.
     */
    @Override
    public Optional<Atleta> get(int atletaID) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT a.*, u.imagem FROM atletas as a, users as u WHERE a.user_id=u.id AND user_id = ?");
            ps.setInt(1, atletaID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                List<ParticipaçõesAtleta> lstParticipacoes = new ArrayList<>();

                PreparedStatement ps2 = conexao.prepareStatement("Select * from historico_atletas_competicoes where atleta_id = ?");
                ps2.setInt(1, rs.getInt("user_id"));
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    lstParticipacoes.add(new ParticipaçõesAtleta(rs2.getInt("ano"),rs2.getInt("medalha_ouro"),rs2.getInt("medalha_prata"),rs2.getInt("medalha_bronze")));
                }

                return Optional.of(new Atleta(rs.getInt("user_id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getInt("altura_cm"), rs.getInt("peso_kg"), rs.getDate("data_nascimento"), lstParticipacoes, rs.getBytes("imagem")));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar o atleta: " + ex.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Insere os dados de participação de um atleta num evento na tabela
     * `historico_atletas_competicoes` da base de dados.
     *
     * @param atletaID O identificador único do atleta na base de dados.
     * @param eventoID O identificador único do evento na base de dados.
     *                 Se for igual a 0, será registado como `NULL` na base de dados.
     * @param participaçõesAtleta Um objeto da classe {@code ParticipaçõesAtleta} que contém
     *                            os detalhes da participação, incluindo o ano e o número de
     *                            medalhas (ouro, prata e bronze).
     * @throws SQLException Lançada se ocorrer um erro ao executar a operação SQL.
     * @throws RuntimeException Envolvendo a exceção SQL se ocorrer algum erro no processo,
     *                          com uma mensagem detalhada do problema.
     */
    public void saveHistorico(int atletaID, int eventoID, ParticipaçõesAtleta participaçõesAtleta) throws SQLException {
        try{
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO historico_atletas_competicoes (atleta_id, evento_id, ano, medalha_ouro, medalha_prata, medalha_bronze) VALUES(?,?,?,?,?,?)");

            ps.setInt(1, atletaID);
            ps.setNull(2, java.sql.Types.INTEGER);

            if(eventoID != 0){
                ps.setInt(2, eventoID);
            }

            ps.setInt(3, participaçõesAtleta.getAno());
            ps.setInt(4, participaçõesAtleta.getOuro());
            ps.setInt(5, participaçõesAtleta.getPrata());
            ps.setInt(6, participaçõesAtleta.getBronze());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir histórico no atleta: " + ex.getMessage());
        }
    }

    /**
     * Atualiza a foto de perfil de um atleta na base de dados.
     *
     * Este método recebe o ID do atleta e o novo array de bytes da foto de perfil,
     * e atualiza o registo correspondente na tabela de utilizadores.
     *
     * @param atletaId   o ID do atleta cujo registo será atualizado.
     * @param fotoPerfil o array de bytes representando a nova foto de perfil.
     * @throws SQLException se ocorrer um erro durante a execução da query SQL.
     */
    public void updateFotoPerfil(int atletaId, byte[] fotoPerfil) throws SQLException {
        String sql = "UPDATE users SET imagem = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setBytes(1, fotoPerfil);
            stmt.setInt(2, atletaId);
            stmt.executeUpdate();
        }
    }

    public List<Atleta> getAtletas(String filtroNome) throws SQLException {
        List<Atleta> lst = new ArrayList<>();
        String query;

        if (filtroNome == null || filtroNome.trim().isEmpty()) {
            query = "SELECT a.*, u.imagem FROM atletas as a, users as u where a.user_id=u.id";
        } else {
            query = "SELECT a.*, u.imagem FROM atletas as a, users as u where a.user_id=u.id AND a.nome LIKE ?";
        }
        PreparedStatement pstmt = conexao.prepareStatement(query);
        try{

            if (filtroNome != null && !filtroNome.trim().isEmpty()) {
                pstmt.setString(1, "%" + filtroNome + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                while (rs.next()) {

                    lst.add(new Atleta(rs.getInt("user_id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getInt("altura_cm"), rs.getInt("peso_kg"), rs.getDate("data_nascimento"), null, rs.getBytes("imagem")));
                }
            }

            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
