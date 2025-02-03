package com.example.oporto_olympics.Models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa o histórico de um ficheiro XML associado a um utilizador.
 *
 * Esta classe armazena as informações relativas a um ficheiro XML, incluindo o identificador do utilizador que inseriu o ficheiro,
 * a data de registo, o tipo dos dados do ficheiro e o próprio ficheiro XML.
 */
public class HistoricoXML {
    /**
     * Identificador único do utilizador.
     */
    private int userID;
    /**
     * Data e hora associadas à ação ou evento.
     */
    private LocalDateTime data;
    /**
     * Tipo do evento ou ação.
     */
    private String tipo;
    /**
     * Ficheiro XML associado.
     */
    private File ficheiroXML;

    /**
     * Constrói uma instância de {@code HistoricoXML} com os valores fornecidos.
     *
     * @param userID o identificador único do utilizador
     * @param data a data e hora em que o histórico foi registado
     * @param tipo o tipo de dados do ficheiro
     * @param ficheiroXML o ficheiro XML associado ao histórico
     */
    public HistoricoXML(int userID, LocalDateTime data, String tipo, File ficheiroXML) {
        this.userID = userID;
        this.data = data;
        this.tipo = tipo;
        this.ficheiroXML = ficheiroXML;
    }

    /**
     * Obtém o identificador do utilizador.
     *
     * @return o identificador único do utilizador
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Define o identificador do utilizador.
     *
     * @param userID o identificador do utilizador
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Obtém a data e hora do registo do histórico.
     *
     * @return a data e hora em que o histórico foi registado
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Define a data e hora do registo do histórico.
     *
     * @param data a data e hora a ser definida
     */
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    /**
     * Obtém o tipo de dados do ficheiro.
     *
     * @return o tipo de dados do ficheiro
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo de dados do ficheiro.
     *
     * @param tipo o tipo de dado a ser definida
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtém o ficheiro XML associado ao histórico.
     *
     * @return o ficheiro XML associado
     */
    public File getFicheiroXML() {
        return ficheiroXML;
    }

    /**
     * Define o ficheiro XML associado ao histórico.
     *
     * @param ficheiroXML o ficheiro XML a ser definido
     */
    public void setFicheiroXML(File ficheiroXML) {
        this.ficheiroXML = ficheiroXML;
    }
}
