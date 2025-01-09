package com.example.oporto_olympics.DAO.Atleta;

import com.example.oporto_olympics.Models.AtletasModalidades;
import com.example.oporto_olympics.Models.EventosModalidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalendarioDAOImp implements CalendarioDAO {

    private Connection conexao;

    /**
     * Construtor que inicializa a conexão com a base de dados
     *
     * @param conexao conexão com a base de dados
     */
    public CalendarioDAOImp(Connection conexao) {
        this.conexao = conexao;  // Conectar corretamente
    }

    @Override
    public List<AtletasModalidades> getAtletaModalidade(int user_id){
        List<AtletasModalidades> lstR = new ArrayList<>();
        String sql = "SELECT * FROM atletas_modalidades WHERE atleta_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {  // Usar 'conexao', não 'connection'
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int atletaId = rs.getInt("atleta_id");
                int modalidadeId = rs.getInt("modalidade_id");
                int eventoId = rs.getInt("evento_id");

                AtletasModalidades atletasInscritos = new AtletasModalidades(atletaId, modalidadeId, eventoId);
                lstR.add(atletasInscritos);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao mostrar os resultados: " + ex.getMessage());
        }

        return lstR;
    }

    @Override
    public List<EventosModalidade> getEventosAtleta(int evento_id, int modalidade_id){
        List<EventosModalidade> lstR = new ArrayList<>();
        String sql = "SELECT * FROM eventos_modalidades WHERE evento_id = ? AND modalidade_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {  // Usar 'conexao', não 'connection'
            stmt.setInt(1, evento_id);
            stmt.setInt(2, modalidade_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int eventoId = rs.getInt("evento_id");
                int modalidadeId = rs.getInt("modalidade_id");
                byte modalidadeStatus = rs.getByte("modalidade_status");
                Date dataModalidade = rs.getDate("data_modalidade");
                Time duracao = rs.getTime("duracao");
                int localId = rs.getInt("local_id");

                EventosModalidade evento = new EventosModalidade(eventoId, modalidadeId, modalidadeStatus, dataModalidade, duracao, localId);
                lstR.add(evento);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao mostrar os resultados: " + ex.getMessage());
        }

        return lstR;
    }
}
