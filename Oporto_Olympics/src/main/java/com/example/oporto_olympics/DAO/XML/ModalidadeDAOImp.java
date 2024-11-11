package com.example.oporto_olympics.DAO.XML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModalidadeDAOImp implements DAOXML<Modalidade> {

    private Connection conexao;
    private ConnectionBD database;

    /**
     * Construtor que inicializa a conexão com a base de dados.
     *
     * @param conexao Conexão à base de dados.
     */
    public ModalidadeDAOImp(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Obtém uma lista de todas as modalidades registadas na base de dados.
     *
     * @return lista de objetos Modalidade.
     */
    @Override
    public List<Modalidade> getAll() {
        List<Modalidade> lst = new ArrayList<Modalidade>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("Select * From modalidades ");
            while (rs.next()) {
                Modalidade modalidade = null;
                String Onegame;

                if (rs.getInt("jogo_unico") == 1) {
                    Onegame = "One";
                } else {
                    Onegame = "Multiple";
                }

                String pontuacao = rs.getString("pontuacao");

                List<Integer> ListeventoID = getListEventoID(rs.getInt("id"));

                switch (pontuacao) {
                    case "Tempo":

                        RegistoTempo recordeTempo = new RegistoTempo(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), parseTime(rs.getString("recorde_olimpico_resultado")));
                        RegistoTempo vencedorTempo = new RegistoTempo(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), parseTime(rs.getString("vencedor_olimpico_resultado")));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordeTempo, vencedorTempo, rs.getString("regras"));
                        break;

                    case "Pontos":
                        RegistoPontos recordePontos = new RegistoPontos(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), rs.getString("vencedor_olimpico_resultado"));
                        RegistoPontos vencedorPontos = new RegistoPontos(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), rs.getString("vencedor_olimpico_resultado"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordePontos, vencedorPontos, rs.getString("regras"));
                        break;
                }

                lst.add(modalidade);
            }
            return lst;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a lista das modalidades: " + ex.getMessage());
        }
    }

    /**
     * Guarda uma nova modalidade na base de dados. Se a modalidade já existir, a operação será ignorada.
     *
     * @param modalidade O objeto Modalidade a ser inserido na base de dados.
     */
    @Override
    public void save(Modalidade modalidade) {

        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO modalidades (nome , tipo, descricao, min_participantes, pontuacao, jogo_unico, regras, recorde_olimpico_ano, recorde_olimpico_resultado, recorde_olimpico_nome, vencedor_olimpico_ano, vencedor_olimpico_resultado, vencedor_olimpico_nome, genero) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            ps.setString(1, modalidade.getNome());
            ps.setString(2, modalidade.getTipo());
            ps.setString(3, modalidade.getDescricao());
            ps.setInt(4, modalidade.getMinParticipantes());
            ps.setString(5, modalidade.getMedida());

            ps.setInt(6, modalidade.getOneGame().equals("One") ? 1 : 0);
            ps.setString(7, modalidade.getRegras());

            if (modalidade.getMedida().equals("Tempo")) {
                ps.setInt(8, modalidade.getRecordeOlimpico().getAno());
                ps.setString(9, String.valueOf(modalidade.getRecordeOlimpico().getTempo()));
                ps.setString(10, modalidade.getRecordeOlimpico().getVencedor());
                ps.setInt(11, modalidade.getVencedorOlimpico().getAno());
                ps.setString(12, String.valueOf(modalidade.getVencedorOlimpico().getTempo()));
                ps.setString(13, modalidade.getVencedorOlimpico().getVencedor());
            } else if (modalidade.getMedida().equals("Pontos")) {
                ps.setInt(8, modalidade.getRecordeOlimpico().getAno());
                ps.setString(9, modalidade.getRecordeOlimpico().getMedalhas());
                ps.setString(10, modalidade.getRecordeOlimpico().getVencedor());
                ps.setInt(11, modalidade.getVencedorOlimpico().getAno());
                ps.setString(12, modalidade.getVencedorOlimpico().getMedalhas());
                ps.setString(13, modalidade.getVencedorOlimpico().getVencedor());
            }

            ps.setString(14, modalidade.getGenero());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a modalidade: " + ex.getMessage());
        }
    }

    /**
     * Atualiza os dados de uma modalidade na base de dados.
     *
     * @param modalidade O objeto Modalidade com os novos dados a serem atualizados.
     */
    @Override
    public void update(Modalidade modalidade) {

    }

    /**
     * Elimina uma modalidade da base de dados.
     *
     * @param modalidade O objeto Modalidade a ser eliminado.
     */
    @Override
    public void delete(Modalidade modalidade) {

    }

    /**
     * Obtém uma modalidade com base no nome.
     *
     * @param nome O nome da modalidade a ser pesquisada.
     * @return Um Optional com a Modalidade, caso encontrada, ou um Optional vazio.
     */
    @Override
    public Optional<Modalidade> get(String nome) {
        return Optional.empty();
    }

    /**
     * Obtém uma modalidade na base de dados a partir do nome, género e tipo.
     *
     * @param nome   Nome da modalidade a ser pesquisada.
     * @param genero Género da modalidade (e.g., masculino, feminino).
     * @param tipo   Tipo de modalidade (e.g., individual, coletivo).
     * @return A instância da Modalidade correspondente aos critérios fornecidos,
     *         ou null se não for encontrada nenhuma modalidade correspondente.
     * @throws RuntimeException Se ocorrer um erro na consulta à base de dados.
     */
    public Modalidade getModalidadeByNomeGeneroTipo(String nome, String genero, String tipo) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM modalidades WHERE modalidades.nome = ? and modalidades.genero = ? and modalidades.tipo = ?");
            ps.setString(1, nome);
            ps.setString(2, genero);
            ps.setString(3, tipo);
            ResultSet rs = ps.executeQuery();

            Modalidade modalidade = null;

            if (rs.next()) {
                String Onegame;

                if (rs.getInt("jogo_unico") == 1) {
                    Onegame = "One";
                } else {
                    Onegame = "Multiple";
                }

                String pontuacao = rs.getString("pontuacao");

                List<Integer> ListeventoID = getListEventoID(rs.getInt("id"));

                switch (pontuacao) {
                    case "Tempo":
                        RegistoTempo recordeTempo = new RegistoTempo(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), parseTime(rs.getString("recorde_olimpico_resultado")));
                        RegistoTempo vencedorTempo = new RegistoTempo(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), parseTime(rs.getString("vencedor_olimpico_resultado")));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordeTempo, vencedorTempo, rs.getString("regras"));
                        break;

                    case "Pontos":
                        RegistoPontos recordePontos = new RegistoPontos(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), rs.getString("vencedor_olimpico_resultado"));
                        RegistoPontos vencedorPontos = new RegistoPontos(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), rs.getString("vencedor_olimpico_resultado"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordePontos, vencedorPontos, rs.getString("regras"));
                        break;
                }

            }

            return modalidade;

        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a modalidade: " + ex.getMessage());
        }
    }

    /**
     * Obtém os IDs dos eventos associados a uma modalidade específica.
     *
     * @param idModalidade ID da modalidade para a qual os IDs dos eventos devem ser recuperados.
     * @return List de IDs dos eventos associados à modalidade.
     * @throws RuntimeException Se ocorrer um erro na consulta à base de dados.
     */
    public List<Integer> getListEventoID(int idModalidade) {
        try {

            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM eventos_modalidades WHERE modalidade_id = ?");
            ps.setInt(1, idModalidade);
            ResultSet rs = ps.executeQuery();

            List<Integer> list = new ArrayList<>();

            while (rs.next()) {
                list.add(rs.getInt("evento_id"));
            }

            return list;

        } catch(SQLException ex){
            throw new RuntimeException("Erro ao inserir a evento_modalidade: " + ex.getMessage());
        }
    }

    /**
     * Guarda a relação entre um evento e uma modalidade na base de dados.
     *
     * @param eventoID      ID do evento a ser associado.
     * @param modalidadeID  ID da modalidade a ser associada ao evento.
     * @throws RuntimeException Se ocorrer um erro ao inserir a relação na base de dados.
     */
    public void saveEventos_Modalidades(int eventoID, int modalidadeID){
        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO eventos_modalidades (evento_id , modalidade_id) VALUES(?,?)");

            ps.setInt(1, eventoID);
            ps.setInt(2, modalidadeID);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a evento_modalidade: " + ex.getMessage());
        }
    }

    /**
     * Analisa uma string representando um tempo e converte-a para um objeto LocalTime.
     *
     * @param timeString A string representando o tempo a ser analisada.
     * @return O objeto LocalTime correspondente ao tempo analisado ou null se houver erro.
     */
    private LocalTime parseTime(String timeString) {
        DateTimeFormatter formatterWithMillis = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        DateTimeFormatter formatterWithoutMillis = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            return LocalTime.parse(timeString, formatterWithMillis);
        } catch (DateTimeParseException e) {
            try {
                return LocalTime.parse(timeString, formatterWithoutMillis);
            } catch (DateTimeParseException ex) {
                System.out.println("Erro ao analisar o tempo: " + timeString);
                return null;
            }
        }
    }
}
