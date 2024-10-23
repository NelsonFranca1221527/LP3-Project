package com.example.oporto_olympics.Controllers.DAO;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModalidadeDAOImp implements DAO<Modalidade> {


    private Connection conexao;

    private ConnectionBD database;

    public ModalidadeDAOImp(Connection conexao) {
        this.conexao=conexao;
    }

    @Override
    public List<Modalidade> getAll() {
        List<Modalidade> lst=new ArrayList<Modalidade>();
        try {

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("Select * From modalidades ");
            while (rs.next()) {

                Modalidade modalidade = null;

                String Onegame;

                if(rs.getInt("jogo_unico") == 1){
                    Onegame="One";
                }else{
                    Onegame="Multiple";
                }

                String pontuacao = rs.getString("pontuacao");

                switch (pontuacao) {
                    case "Time":


                        RegistoTempo recordeTempo = new RegistoTempo(rs.getString("recorde_olimpico_nome"),rs.getInt("recorde_olimpico_ano"), parseTime(rs.getString("recorde_olimpico_tempo")));
                        RegistoTempo vencedorTempo = new RegistoTempo(rs.getString("vencedor_olimpico_nome"),rs.getInt("vencedor_olimpico_ano"), parseTime(rs.getString("vencedor_olimpico_tempo")));

                        modalidade = new Modalidade(rs.getString("tipo"),rs.getString("nome"), rs.getString("genero"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, recordeTempo, vencedorTempo, rs.getString("regras"));
                        break;

                    case "Points":
                        RegistoPontos recordePontos = new RegistoPontos(rs.getString("recorde_olimpico_nome"),rs.getInt("recorde_olimpico_ano"), rs.getString("vencedor_olimpico_tempo"));
                        RegistoPontos vencedorPontos = new RegistoPontos(rs.getString("vencedor_olimpico_nome"),rs.getInt("vencedor_olimpico_ano"), rs.getString("vencedor_olimpico_tempo"));

                        modalidade = new Modalidade(rs.getString("tipo"), rs.getString("nome"), rs.getString("genero"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, recordePontos, vencedorPontos, rs.getString("regras"));
                        break;
                }
                lst.add(modalidade);
            }
            return lst;

        }catch (SQLException ex){
            throw new RuntimeException("Erro em mostrar a lista das modalidades: " + ex.getMessage());
        }
    }

    @Override
    public void save(Modalidade modalidade) {

        Optional<Modalidade> ModalidadeExiste = get(modalidade.getNome());

        if(ModalidadeExiste.isPresent() && ModalidadeExiste.get().getGenero().equals(modalidade.getGenero()) && ModalidadeExiste.get().getTipo().equals(modalidade.getTipo())){
            return;
        }

        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO modalidades (nome , tipo, descricao, min_participantes, pontuacao, jogo_unico, regras, local_id, recorde_olimpico_ano, recorde_olimpico_tempo, recorde_olimpico_nome, recorde_olimpico_nome, vencedor_olimpico_ano, vencedor_olimpico_tempo, vencedor_olimpico_nome) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            ps.setString(1, modalidade.getTipo());
            ps.setString(2,  modalidade.getGenero());
            ps.setString(3, modalidade.getNome());
            ps.setInt(4, modalidade.getMinParticipantes());
            ps.setString(5, modalidade.getMedida());

            if(modalidade.getOneGame() == "One"){
                ps.setInt(6, 1);
            }else {
                ps.setInt(6, 0);
            }

            ps.setString(7, modalidade.getRegras());

            ps.setInt(8, 1);

            switch (modalidade.getMedida()) {
                case "Time":
                    ps.setInt(9, modalidade.getRecordeOlimpico().getAno());
                    ps.setString(10, String.valueOf(modalidade.getRecordeOlimpico().getTempo()));
                    ps.setString(11, modalidade.getRecordeOlimpico().getVencedor());
                    ps.setInt(12, modalidade.getVencedorOlimpico().getAno());
                    ps.setString(13, String.valueOf(modalidade.getVencedorOlimpico().getTempo()));
                    ps.setString(14, modalidade.getVencedorOlimpico().getVencedor());
                    break;

                case "Points":
                    ps.setInt(9, modalidade.getRecordeOlimpico().getAno());
                    ps.setString(10, modalidade.getRecordeOlimpico().getMedalhas());
                    ps.setString(11, modalidade.getRecordeOlimpico().getVencedor());
                    ps.setInt(12, modalidade.getVencedorOlimpico().getAno());
                    ps.setString(13, modalidade.getVencedorOlimpico().getMedalhas());
                    ps.setString(14, modalidade.getVencedorOlimpico().getVencedor());
                    break;
            }

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a modalidade: " + ex.getMessage());
        }

    }

    @Override
    public void update(Modalidade modalidade) {

    }

    @Override
    public void delete(Modalidade modalidade) {

    }

    @Override
    public Optional<Modalidade> get(String nome) {
        try {

            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM modalidades WHERE nome = ?");
            ps.setString(1, nome);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Modalidade modalidade = null;

                String Onegame;

                if(rs.getInt("jogo_unico") == 1){
                    Onegame="One";
                }else {
                    Onegame="Multiple";
                }

                String pontuacao = rs.getString("pontuacao");

                switch (pontuacao) {
                    case "Time":

                        RegistoTempo recordeTempo = new RegistoTempo(rs.getString("recorde_olimpico_nome"),rs.getInt("recorde_olimpico_ano"), parseTime(rs.getString("recorde_olimpico_tempo")));
                        RegistoTempo vencedorTempo = new RegistoTempo(rs.getString("vencedor_olimpico_nome"),rs.getInt("vencedor_olimpico_ano"), parseTime(rs.getString("vencedor_olimpico_tempo")));

                        modalidade = new Modalidade(rs.getString("tipo"),rs.getString("nome"), rs.getString("genero"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, recordeTempo, vencedorTempo, rs.getString("regras"));
                        break;

                    case "Points":
                        RegistoPontos recordePontos = new RegistoPontos(rs.getString("recorde_olimpico_nome"),rs.getInt("recorde_olimpico_ano"), rs.getString("vencedor_olimpico_tempo"));
                        RegistoPontos vencedorPontos = new RegistoPontos(rs.getString("vencedor_olimpico_nome"),rs.getInt("vencedor_olimpico_ano"), rs.getString("vencedor_olimpico_tempo"));

                        modalidade = new Modalidade(rs.getString("tipo"), rs.getString("nome"), rs.getString("genero"), rs.getString("descricao"), rs.getInt("min_participantes"), pontuacao, Onegame, recordePontos, vencedorPontos, rs.getString("regras"));
                        break;
                }

                return Optional.of(modalidade);
            }
        }catch (SQLException ex){
            throw new RuntimeException("Erro em mostrar a modalidade: " + ex.getMessage());
        }

        return Optional.empty();
    }

    private LocalTime parseTime(String timeString) {
        DateTimeFormatter formatterWithMillis = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
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
