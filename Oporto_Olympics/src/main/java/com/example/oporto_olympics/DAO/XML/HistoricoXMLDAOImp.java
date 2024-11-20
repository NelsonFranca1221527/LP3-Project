package com.example.oporto_olympics.DAO.XML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.HistoricoXML;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class HistoricoXMLDAOImp implements DAOXML<HistoricoXML> {

    private Connection conexao;
    private ConnectionBD database;

    /**
     * Construtor que inicializa a conexão com a base de dados.
     *
     * @param conexao conexão com a base de dados.
     */
    public HistoricoXMLDAOImp(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Obtém todos os objetos do tipo {@code HistoricoXML} armazenados em formato XML.
     *
     * @return Uma lista de objetos {@code HistoricoXML} que representam todos os dados armazenados no XML.
     */
    @Override
    public List<HistoricoXML> getAll() {
        return List.of();
    }

    /**
     * Guarda um objeto do tipo {@code HistoricoXML} no formato XML.
     *
     * @param historicoXML O objeto a ser guardado no ficheiro XML.
     */
    @Override
    public void save(HistoricoXML historicoXML) {
        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO historicoXML (user_id, entradaxml_date, tipo, ficheiroXML) VALUES(?,?,?,?)");
            ps.setInt(1, historicoXML.getUserID());
            ps.setTimestamp(2, Timestamp.valueOf(historicoXML.getData()));
            ps.setString(3, historicoXML.getTipo());
            InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(historicoXML.getFicheiroXML().toPath()));
            ps.setBinaryStream(4, inputStream);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | IOException ex) {
            throw new RuntimeException("Erro ao inserir o ficheiro XML: " + ex.getMessage());
        }
    }

    /**
     * Atualiza os dados de um objeto do tipo {@code HistoricoXML} no formato XML.
     *
     * @param historicoXML O objeto com os novos dados a serem atualizados no XML.
     */
    @Override
    public void update(HistoricoXML historicoXML) {

    }

    /**
     * Elimina um objeto do tipo {@code HistoricoXML} do armazenamento XML.
     *
     * @param historicoXML O objeto a ser removido do ficheiro XML.
     */
    @Override
    public void delete(HistoricoXML historicoXML) {

    }

    /**
     * Obtém um objeto do tipo {@code HistoricoXML} a partir de uma chave identificadora.
     *
     * @param i A chave identificadora do objeto, podendo ser um campo único como um ID.
     * @return Um {@link Optional} contendo o objeto {@code HistoricoXML} se encontrado, ou vazio caso não exista.
     */
    @Override
    public Optional<HistoricoXML> get(String i) {
        return Optional.empty();
    }
}
