package com.example.oporto_olympics.DAO.XML;

import com.example.oporto_olympics.Models.HistoricoXML;
import com.example.oporto_olympics.Models.ListagemXML;

import java.util.List;
/**
 * Interface responsável pela definição de operações relacionadas à manipulação de ficheiros XML
 * no sistema. Representa o contrato para implementação de classes DAO que interagem com a base de dados.
 */
public interface ListagemXMLDAO {
    /**
     * Obtém uma lista de todos os ficheiros XML registados no sistema.
     *
     * @return Uma lista de objetos {@link ListagemXML} representando os ficheiros XML armazenados.
     */
    List<ListagemXML> getAllXMLFile();
    /**
     * Obtém o nome de um gestor com base no seu userId.
     *
     * @param userId O ID do utilizador do gestor.
     * @return O nome do gestor, ou {@code null} se não for encontrado.
     */
    String getGestorNomeByUserId(int userId);


}
