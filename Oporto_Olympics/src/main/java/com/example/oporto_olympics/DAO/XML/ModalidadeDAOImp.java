package com.example.oporto_olympics.DAO.XML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.HorarioModalidade;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoDistancia;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * A classe {@link ModalidadeDAOImp} implementa a interface {@link DAOXML} para manipulação de dados de modalidades
 * na base de dados. Ela fornece métodos para realizar operações de CRUD (criação, leitura, atualização e exclusão)
 * sobre as modalidades, bem como o histórico de recordes olímpicos e vencedores em diferentes modalidades esportivas.
 */
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
                        RegistoTempo recordeTempo = new RegistoTempo(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), parseTime(rs.getString("recorde_olimpico_resultado")), String.valueOf(rs.getInt("recorde_olimpico_medalhas")));
                        RegistoTempo vencedorTempo = new RegistoTempo(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), parseTime(rs.getString("vencedor_olimpico_resultado")), rs.getString("vencedor_olimpico_medalhas"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordeTempo, vencedorTempo, rs.getString("regras"));
                        break;
                    case "Pontos":
                        RegistoPontos recordePontos = new RegistoPontos(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), rs.getString("recorde_olimpico_resultado"));
                        RegistoPontos vencedorPontos = new RegistoPontos(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), rs.getString("vencedor_olimpico_resultado"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordePontos, vencedorPontos, rs.getString("regras"));
                        break;
                    case "Distância":
                        RegistoDistancia recordeDistancia = new RegistoDistancia(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), Double.valueOf(rs.getString("recorde_olimpico_resultado")), String.valueOf(rs.getInt("recorde_olimpico_medalhas")));
                        RegistoDistancia vencedorDistancia = new RegistoDistancia(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), Double.valueOf(rs.getString("vencedor_olimpico_resultado")), rs.getString("vencedor_olimpico_medalhas"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordeDistancia, vencedorDistancia, rs.getString("regras"));
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
        try (CallableStatement cs = conexao.prepareCall("{CALL SaveModalidade(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            // Definir os parâmetros do procedimento armazenado
            cs.setString(1, modalidade.getNome());
            cs.setString(2, modalidade.getTipo());
            cs.setString(3, modalidade.getDescricao());
            cs.setInt(4, modalidade.getMinParticipantes());
            cs.setString(5, modalidade.getMedida());
            cs.setBoolean(6, modalidade.getOneGame().equals("One"));
            cs.setString(7, modalidade.getRegras());
            cs.setInt(8, modalidade.getRecordeOlimpico().getAno());

            if (modalidade.getMedida().equals("Tempo")) {
                cs.setString(9, String.valueOf(modalidade.getRecordeOlimpico().getTempo()));
            } else if (modalidade.getMedida().equals("Distância")) {
                cs.setString(9, String.valueOf(modalidade.getRecordeOlimpico().getDistancia()));
            } else {
                cs.setNull(9, java.sql.Types.VARCHAR);
            }

            if (modalidade.getRecordeOlimpico().getMedalhas() != null) {
                cs.setInt(10, Integer.parseInt(modalidade.getRecordeOlimpico().getMedalhas()));
            } else {
                cs.setNull(10, java.sql.Types.INTEGER);
            }

            cs.setString(11, modalidade.getRecordeOlimpico().getVencedor());
            cs.setInt(12, modalidade.getVencedorOlimpico().getAno());

            if (modalidade.getMedida().equals("Tempo")) {
                cs.setString(13, String.valueOf(modalidade.getVencedorOlimpico().getTempo()));
            } else if (modalidade.getMedida().equals("Distância")) {
                cs.setString(13, String.valueOf(modalidade.getVencedorOlimpico().getDistancia()));
            } else {
                cs.setNull(13, java.sql.Types.VARCHAR);
            }

            if (modalidade.getVencedorOlimpico().getMedalhas() != null &&
                    !modalidade.getVencedorOlimpico().getMedalhas().isEmpty()) {
                cs.setString(14, modalidade.getVencedorOlimpico().getMedalhas());
            } else {
                cs.setNull(14, java.sql.Types.VARCHAR);
            }

            cs.setString(15, modalidade.getVencedorOlimpico().getVencedor());
            cs.setString(16, modalidade.getGenero());

            // Registrar o parâmetro de saída
            cs.registerOutParameter(17, java.sql.Types.INTEGER);

            // Executar o procedimento
            cs.execute();

            // Recuperar o ID gerado
            int newId = cs.getInt(17);
            modalidade.setId(newId); // Atualiza o ID da Modalidade

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a modalidade: " + ex.getMessage(), ex);
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
    public Modalidade getModalidadeByNomeGeneroTipo(String nome, String genero, String tipo, int minParticipantes) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM modalidades WHERE modalidades.nome = ? and modalidades.genero = ? and modalidades.tipo = ? and modalidades.min_participantes = ?");
            ps.setString(1, nome);
            ps.setString(2, genero);
            ps.setString(3, tipo);
            ps.setInt(4, minParticipantes);
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

                        RegistoTempo recordeTempo = new RegistoTempo(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), parseTime(rs.getString("recorde_olimpico_resultado")), String.valueOf(rs.getInt("recorde_olimpico_medalhas")));
                        RegistoTempo vencedorTempo = new RegistoTempo(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), parseTime(rs.getString("vencedor_olimpico_resultado")), rs.getString("vencedor_olimpico_medalhas"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordeTempo, vencedorTempo, rs.getString("regras"));
                        break;

                    case "Pontos":
                        RegistoPontos recordePontos = new RegistoPontos(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), rs.getString("vencedor_olimpico_resultado"));
                        RegistoPontos vencedorPontos = new RegistoPontos(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), rs.getString("vencedor_olimpico_resultado"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordePontos, vencedorPontos, rs.getString("regras"));
                        break;
                    case "Distância":
                        RegistoDistancia recordeDistancia = new RegistoDistancia(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), Double.valueOf(rs.getString("recorde_olimpico_resultado")), String.valueOf(rs.getInt("recorde_olimpico_medalhas")));
                        RegistoDistancia vencedorDistancia = new RegistoDistancia(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), Double.valueOf(rs.getString("vencedor_olimpico_resultado")), rs.getString("vencedor_olimpico_medalhas"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordeDistancia, vencedorDistancia, rs.getString("regras"));
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
     * Obtém todos os horários das modalidades da base de dados.
     *
     * Este método realiza uma consulta à tabela `eventos_modalidades` para obter os campos
     * `data_modalidade`, `duracao` e `local_id`. Os dados são convertidos para objetos da classe
     * {@link HorarioModalidade} e retornados numa lista.
     * Registos com valores inválidos (data ou duração nula, ou `local_id` igual a 0) são ignorados.
     *
     * @return Uma lista de objetos {@link HorarioModalidade} contendo os dados dos horários
     *         das modalidades presentes na base de dados.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL.
     */
    public List<HorarioModalidade> getAllHorarioModalidade() {
        try {

            PreparedStatement ps = conexao.prepareStatement("SELECT data_modalidade, duracao, local_id FROM eventos_modalidades");
            ResultSet rs = ps.executeQuery();

            List<HorarioModalidade> list = new ArrayList<>();

            while (rs.next()) {

                if (rs.getTimestamp("data_modalidade") == null ||
                        rs.getTime("duracao") == null ||
                        rs.getInt("local_id") == 0) {
                    continue;
                }

                list.add(new HorarioModalidade(rs.getTimestamp("data_modalidade").toLocalDateTime(), rs.getTime("duracao").toLocalTime(), rs.getInt("local_id")));
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
    public void saveEventos_Modalidades(int eventoID, int modalidadeID, LocalDateTime dataTempo, LocalTime duracao, int local_id){
        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO eventos_modalidades (evento_id , modalidade_id, data_modalidade, duracao, local_id) VALUES(?,?,?,?,?)");

            ps.setInt(1, eventoID);
            ps.setInt(2, modalidadeID);
            ps.setTimestamp(3, Timestamp.valueOf(dataTempo));
            ps.setTime(4, Time.valueOf(duracao));
            ps.setInt(5, local_id);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a evento_modalidade: " + ex.getMessage());
        }
    }

    /**
     * Atualiza o estado de uma modalidade específica num evento na tabela `eventos_modalidades`.
     *
     * Este método altera o estado (`modalidade_status`) de uma modalidade associada a um evento
     * na base de dados, com base nos IDs fornecidos do evento e da modalidade, e no novo estado.
     *
     * @param eventoID o ID do evento associado à modalidade.
     * @param modalidadeID o ID da modalidade cujo estado será alterado.
     * @param status o novo estado a ser atribuído à modalidade (1 para ativo, 0 para inativo).
     * @throws RuntimeException se ocorrer um erro ao executar a query SQL.
     */
    public void updateEventos_ModalidadesStatus(int eventoID, int modalidadeID, int status) {

        String updateQuery = "UPDATE eventos_modalidades SET modalidade_status = ? WHERE evento_id = ? and modalidade_id = ?";

        try (PreparedStatement pstmt = conexao.prepareStatement(updateQuery)) {
            pstmt.setInt(1, status);
            pstmt.setInt(2, eventoID);
            pstmt.setInt(3, modalidadeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao aprovar inscrição: " + e.getMessage());
        }
    }

    /**
     * Obtém o estado de uma modalidade associada a um evento na tabela `eventos_modalidades`.
     *
     * Este método verifica se uma modalidade específica num evento está aberta ou fechada,
     * consultando o campo `modalidade_status` na tabela.
     *
     * @param eventoID o ID do evento associado à modalidade.
     * @param modalidadeID o ID da modalidade cujo estado será consultado.
     * @return `true` se a modalidade está fechada (modalidade_status = 1), ou `false` se estiver aberta ou não encontrada.
     * @throws RuntimeException se ocorrer um erro ao executar a query SQL.
     */
    public boolean getStatusModalidade(int eventoID, int modalidadeID) {
        String sql = "SELECT modalidade_status FROM eventos_modalidades WHERE evento_id = ? AND modalidade_id = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, eventoID);
            ps.setInt(2, modalidadeID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("modalidade_status");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao procurar o status do evento_modalidade: " + ex);
        }

        return false;
    }

    /**
     * Obtém o total de participantes individuais de uma modalidade específica num evento.
     * Este método executa uma consulta SQL para contar o número de participantes individuais
     * associados a um determinado evento e modalidade, com base nos IDs fornecidos.
     *
     * @param eventoID o identificador único do evento.
     * @param modalidadeID o identificador único da modalidade.
     * @return o total de participantes individuais encontrados para o evento e modalidade especificados.
     *         Retorna 0 caso não existam participantes.
     * @throws RuntimeException se ocorrer um erro ao executar a consulta SQL.
     */
    public int getTotalParticipantesIndividual(int eventoID, int modalidadeID) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT Count(*) as 'totalParticipantes' FROM atletas_modalidades WHERE evento_id = ? and modalidade_id = ?");
            ps.setInt(1, eventoID);
            ps.setInt(2, modalidadeID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("totalParticipantes");
            }

            return 0;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar o atleta: " + ex.getMessage());
        }
    }

    /**
     * Obtém o total de equipas de uma modalidade específica num evento.
     * Este método executa uma consulta SQL para contar o número de equipas
     * associadas a um determinado evento e modalidade, com base nos IDs fornecidos.
     *
     * @param eventoID o identificador único do evento.
     * @param modalidadeID o identificador único da modalidade.
     * @return o total de participantes coletivos encontrados para o evento e modalidade especificados.
     *         Retorna 0 caso não existam participantes.
     * @throws RuntimeException se ocorrer um erro ao executar a consulta SQL.
     */
    public int getTotalParticipantesColetivo(int eventoID, int modalidadeID) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT Count(*) as 'totalParticipantes' FROM equipa_modalidade WHERE evento_id = ? and modalidade_id = ?");
            ps.setInt(1, eventoID);
            ps.setInt(2, modalidadeID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("totalParticipantes");
            }

            return 0;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em mostrar a equipa: " + ex.getMessage());
        }
    }

    /**
     * Insere um registo na base de dados para associar uma equipa a uma modalidade num evento.
     *
     * Este método regista a participação de uma equipa numa modalidade específica de um evento,
     * armazenando os dados na tabela `equipa_modalidade`.
     *
     * @param equipaID o identificador da equipa que será associada.
     * @param eventoID o identificador do evento ao qual a equipa será associada.
     * @param modalidadeID o identificador da modalidade à qual a equipa será associada.
     * @throws RuntimeException se ocorrer um erro durante a execução da consulta SQL.
     */
    public void saveParticipantesColetivo(int equipaID,int eventoID, int modalidadeID) {
        try {
            PreparedStatement ps = conexao.prepareStatement("Insert into equipa_modalidade (equipa_id,evento_id,modalidade_id) Values (?,?,?)");
            ps.setInt(1, equipaID);
            ps.setInt(2, eventoID);
            ps.setInt(3, modalidadeID);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro em associar a equipa à modalidade: " + ex.getMessage());
        }
    }

    /**
     * Obtém o nome de uma modalidade com base no seu ID.
     *
     * @param id o ID da modalidade.
     * @return o nome da modalidade correspondente ao ID, ou null se a modalidade não for encontrado.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public String getNomeById(int id) {
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT nome FROM modalidades WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nome");
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao obter o nome da modalidade: " + ex.getMessage());
        }
        return null;  // Retorna null se o local não for encontrado
    }
    /**
     * Obtém uma lista de modalidades pelo género.
     *
     * @param genero o género.
     * @return as modalidades correspondentes ao género ou null se não for encontrado.
     * @throws RuntimeException se ocorrer um erro durante a consulta à base de dados.
     */
    public List<Modalidade> getAllIndividualByGenero(String genero) {
        List<Modalidade> lst = new ArrayList<Modalidade>();
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM modalidades WHERE tipo = 'Individual' AND genero = ?");
            ps.setString(1, genero);
            ResultSet rs = ps.executeQuery();
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
                        RegistoTempo recordeTempo = new RegistoTempo(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), parseTime(rs.getString("recorde_olimpico_resultado")), String.valueOf(rs.getInt("recorde_olimpico_medalhas")));
                        RegistoTempo vencedorTempo = new RegistoTempo(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), parseTime(rs.getString("vencedor_olimpico_resultado")), rs.getString("vencedor_olimpico_medalhas"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordeTempo, vencedorTempo, rs.getString("regras"));
                        break;
                    case "Pontos":
                        RegistoPontos recordePontos = new RegistoPontos(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), rs.getString("vencedor_olimpico_resultado"));
                        RegistoPontos vencedorPontos = new RegistoPontos(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), rs.getString("vencedor_olimpico_resultado"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordePontos, vencedorPontos, rs.getString("regras"));
                        break;
                    case "Distância":
                        RegistoDistancia recordeDistancia = new RegistoDistancia(rs.getString("recorde_olimpico_nome"), rs.getInt("recorde_olimpico_ano"), Double.valueOf(rs.getString("recorde_olimpico_resultado")), String.valueOf(rs.getInt("recorde_olimpico_medalhas")));
                        RegistoDistancia vencedorDistancia = new RegistoDistancia(rs.getString("vencedor_olimpico_nome"), rs.getInt("vencedor_olimpico_ano"), Double.valueOf(rs.getString("vencedor_olimpico_resultado")), rs.getString("vencedor_olimpico_medalhas"));
                        modalidade = new Modalidade(rs.getInt("id"), rs.getString("tipo"), rs.getString("genero"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, ListeventoID, recordeDistancia, vencedorDistancia, rs.getString("regras"));
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

    /**
     * Obtém os atletas associados a um evento e modalidade específicos.
     *
     * Este método realiza uma consulta à base de dados para listar os atletas que participam
     * numa determinada modalidade e evento, retornando um mapa onde a chave é o ID do atleta
     * e o valor é o nome do atleta.
     *
     * @param eventoId     o ID do evento a ser utilizado no filtro.
     * @param modalidadeId o ID da modalidade a ser utilizado no filtro.
     * @return um mapa com os IDs dos atletas como chaves e os respetivos nomes como valores.
     * @throws SQLException caso ocorra um erro na execução da consulta SQL ou na conexão com a base de dados.
     */
    public Map<Integer, String> getAtletasPorEvento(int eventoId, int modalidadeId) throws SQLException {
        Map<Integer, String> atletas = new HashMap<>();

        String query = "SELECT am.atleta_id, a.nome FROM atletas_modalidades AS am JOIN modalidades AS m ON m.id = am.modalidade_id JOIN eventos_modalidades AS em ON em.evento_id = am.evento_id JOIN atletas AS a ON a.user_id = am.atleta_id WHERE am.evento_id = ? AND am.modalidade_id = ? GROUP BY am.atleta_id, a.nome";

        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            // Definir os parâmetros no SQL
            stmt.setInt(1, eventoId);
            stmt.setInt(2, modalidadeId);

            // Executar o comando e processar os resultados
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int atletaId = rs.getInt("atleta_id");
                    String nomeAtleta = rs.getString("nome");
                    atletas.put(atletaId, nomeAtleta);
                }
            }
        }

        return atletas;
    }

    /**
     * Obtém as equipas associados a um evento e modalidade específicos.
     *
     * Este método realiza uma consulta à base de dados para listar as equipas que participam
     * numa determinada modalidade e evento, retornando um mapa onde a chave é o ID da equipa
     * e o valor é o nome da equipa.
     *
     * @param eventoId     o ID do evento a ser utilizado no filtro.
     * @param modalidadeId o ID da modalidade a ser utilizado no filtro.
     * @return um mapa com os IDs das equipas como chaves e os respetivos nomes como valores.
     * @throws SQLException caso ocorra um erro na execução da consulta SQL ou na conexão com a base de dados.
     */
    public Map<Integer, String> getEquipasPorEvento(int eventoId, int modalidadeId) throws SQLException {
        Map<Integer, String> equipas = new HashMap<>();

        String query = "SELECT am.equipa_id, e.nome FROM equipa_modalidade AS am JOIN modalidades AS m ON m.id = am.modalidade_id JOIN eventos_modalidades AS em ON em.evento_id = am.evento_id JOIN equipas AS e ON e.id = am.equipa_id WHERE am.evento_id = ? AND am.modalidade_id = ? GROUP BY am.equipa_id, e.nome";

        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            // Definir os parâmetros no SQL
            stmt.setInt(1, eventoId);
            stmt.setInt(2, modalidadeId);

            // Executar o comando e processar os resultados
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int equipaId = rs.getInt("equipa_id");
                    String nomeEquipa = rs.getString("nome");
                    equipas.put(equipaId, nomeEquipa);
                }
            }
        }

        return equipas;
    }

    /**
     * Obtém os detalhes do horário de uma modalidade específica com base no seu ID.
     *
     * @param modalidadeId O ID da modalidade para a qual se pretende obter o horário.
     * @return Um objeto {@link HorarioModalidade} contendo os detalhes do horário da modalidade.
     * @throws SQLException Caso ocorra um erro ao executar a consulta à base de dados.
     */
    public HorarioModalidade getHorarioModalidadeById(int modalidadeId, int eventoId) throws SQLException {
        HorarioModalidade horariomodalidade = null;
        String query = "SELECT data_modalidade, duracao, local_id FROM eventos_modalidades WHERE modalidade_id = ? AND evento_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            stmt.setInt(1, modalidadeId);
            stmt.setInt(2, eventoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LocalDateTime dataHora = rs.getTimestamp("data_modalidade") != null
                        ? rs.getTimestamp("data_modalidade").toLocalDateTime()
                        : null;

                LocalTime duracao = rs.getTime("duracao") != null
                        ? rs.getTime("duracao").toLocalTime()
                        : null;

                Integer localId = rs.getInt("local_id");
                if (rs.wasNull()) {
                    localId = null;
                }

                if (dataHora != null && duracao != null && localId != null) {
                    horariomodalidade = new HorarioModalidade(dataHora, duracao, localId);
                }
            }
        }

        return horariomodalidade; // Retorna null se os valores necessários forem nulos ou não existirem.
    }

    /**
     * Obtém uma lista de horários de todas as modalidades em que uma equipa específica está inscrita.
     *
     * @param equipaId O ID da equipa cujos horários de modalidades devem ser obtidos.
     * @return Uma lista de objetos {@link HorarioModalidade} representando os horários das modalidades em que a equipa está inscrita.
     * @throws RuntimeException Caso ocorra um erro ao executar a consulta à base de dados.
     */
    public List<HorarioModalidade> getAllHorarioModalidadeByEquipa(int equipaId) {
        try {

            PreparedStatement ps = conexao.prepareStatement("select m.data_modalidade, m.duracao, m.local_id from eventos_modalidades as m JOIN equipa_modalidade as em ON em.modalidade_id = m.modalidade_id where em.evento_id=m.evento_id AND em.equipa_id= ? ");
            ps.setInt(1, equipaId);
            ResultSet rs = ps.executeQuery();

            List<HorarioModalidade> list = new ArrayList<>();

            while (rs.next()) {

                if (rs.getTimestamp("data_modalidade") == null ||
                        rs.getTime("duracao") == null ||
                        rs.getInt("local_id") == 0) {
                    continue;
                }

                list.add(new HorarioModalidade(rs.getTimestamp("data_modalidade").toLocalDateTime(), rs.getTime("duracao").toLocalTime(), rs.getInt("local_id")));
            }

            return list;

        } catch(SQLException ex){
            throw new RuntimeException("Erro ao listar Horarios pela Equipa: " + ex.getMessage());
        }
    }
}
