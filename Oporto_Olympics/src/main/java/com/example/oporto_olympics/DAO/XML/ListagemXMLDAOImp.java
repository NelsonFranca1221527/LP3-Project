package com.example.oporto_olympics.DAO.XML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.HistoricoXML;
import com.example.oporto_olympics.Models.InscricaoEquipas;
import com.example.oporto_olympics.Models.ListagemXML;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListagemXMLDAOImp implements ListagemXMLDAO {

    private Connection conexao;

    /**
     * Construtor que inicializa a conexão com a base de dados.
     *
     * @param conexao Conexão à base de dados.
     */
    public ListagemXMLDAOImp(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Recupera uma lista dos ficheiros XML da base de dados.
     *
     * Este método permite ir buscar todos os ficheiros XML na base de dados.
     *
     * @return Uma lista de objetivos {@link HistoricoXML}, representa os ficheiros XML recuperados da base de dados.
     * @throws RuntimeException Se ocorrer um erro ao carregar os ficheiros XML da base de dados.
     */
    @Override
    public List<ListagemXML> getAllXMLFile() {
        List<ListagemXML> lst = new ArrayList<>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM historicoXML");
            while (rs.next()) {
                ListagemXML historicoXML = new ListagemXML(
                        rs.getInt("user_id"),
                        rs.getTimestamp("entradaxml_date").toLocalDateTime(),
                        rs.getString("tipo"),
                        rs.getString("ficheiroXML")
                );
                lst.add(historicoXML);
            }
            return lst;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter histórico XML: " + e.getMessage());
        }
    }

    /**
     * Obtém o nome de um gestor com base no seu userId.
     *
     * @param userId O ID do utilizador do gestor.
     * @return O nome do gestor, ou {@code null} se não for encontrado.
     */
    public String getGestorNomeByUserId(int userId) {
        try {
            String query = "SELECT nome FROM gestores WHERE user_id = ?";
            PreparedStatement pstmt = conexao.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nome");
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter o nome do gestor: " + e.getMessage());
        }
    }





}
