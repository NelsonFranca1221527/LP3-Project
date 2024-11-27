package com.example.oporto_olympics.DAO.XML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.HistoricoXML;
import com.example.oporto_olympics.Models.InscricaoEquipas;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public List<HistoricoXML> getAllXMLFile() {
        List<HistoricoXML> lst = new ArrayList<>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM historicoXML");
            while (rs.next()) {
                HistoricoXML historicoXML = new HistoricoXML(
                        rs.getInt("user_id"),
                        rs.getTimestamp("entradaxml_date").toLocalDateTime(),
                        rs.getString("tipo"),
                        new File(rs.getString("ficheiroXML"))
                );
                lst.add(historicoXML);
            }
            return lst;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter histórico XML: " + e.getMessage());
        }
    }





}
