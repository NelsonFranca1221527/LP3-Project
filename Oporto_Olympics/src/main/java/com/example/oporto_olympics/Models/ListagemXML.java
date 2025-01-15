package com.example.oporto_olympics.Models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 * Representa uma listagem de ficheiros XML associada a um utilizador.
 * Armazena informações sobre o ID do utilizador, a data e hora de entrada do XML,
 * o tipo do ficheiro e o conteúdo completo do XML como uma string.
 */
public class  ListagemXML {
    /**
     * Identificador único do utilizador associado.
     */
    private int userId;
    /**
     * Data e hora em que o registo foi criado ou atualizado.
     */
    private LocalDateTime data;
    /**
     * Tipo de registo ou ação representada.
     */
    private String tipo;
    /**
     * Conteúdo XML associado ao registo.
     */
    private String conteudoXML;
    /**
     * Construtor completo da classe.
     *
     * @param userId O ID do utilizador associado ao ficheiro XML.
     * @param data A data e hora em que o ficheiro XML foi registado.
     * @param tipo O tipo ou descrição do ficheiro XML.
     * @param conteudoXML O conteúdo completo do ficheiro XML em formato de string.
     */
    public ListagemXML(int userId, LocalDateTime data, String tipo, String conteudoXML) {
        this.userId = userId;
        this.data = data;
        this.tipo = tipo;
        this.conteudoXML = conteudoXML;
    }
    /**
     * Obtém o ID do utilizador associado ao ficheiro XML.
     * @return O ID do utilizador.
     */
    public int getUserId() {
        return userId;
    }
    /**
     * Define o ID do utilizador associado ao ficheiro XML.
     * @param userId O ID do utilizador.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * Obtém a data e hora de entrada do ficheiro XML.
     * @return A data e hora.
     */
    public LocalDateTime getData() {
        return data;
    }
    /**
     * Define a data e hora de entrada do ficheiro XML.
     * @param data A data e hora.
     */
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    /**
     * Obtém o tipo ou descrição do ficheiro XML.
     * @return O tipo do ficheiro.
     */
    public String getTipo() {
        return tipo;
    }
    /**
     * Define o tipo ou descrição do ficheiro XML.
     * @param tipo O tipo do ficheiro.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    /**
     * Obtém o conteúdo completo do ficheiro XML.
     * @return O conteúdo do ficheiro XML em formato de string.
     */
    public String getConteudoXML() {
        return conteudoXML;
    }
    /**
     * Define o conteúdo completo do ficheiro XML.
     * @param conteudoXML O conteúdo do ficheiro XML em formato de string.
     */
    public void setConteudoXML(String conteudoXML) {
        this.conteudoXML = conteudoXML;
    }

}
