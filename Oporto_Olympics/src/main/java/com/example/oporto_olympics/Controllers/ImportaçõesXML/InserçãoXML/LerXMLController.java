package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML;

import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Equipa;
import com.example.oporto_olympics.Models.ParticipaçõesAtleta;
import com.example.oporto_olympics.Models.ParticipaçõesEquipa;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controlador responsável pela leitura de ficheiros XML relacionados com
 * atletas, equipas e modalidades.
 */
public class LerXMLController {

    /**
     * Lê e processa o ficheiro XML de atletas, criando objetos do tipo Atleta e suas participações.
     *
     * @param XMLFile O ficheiro XML que contém os dados dos atletas.
     * @throws ParserConfigurationException Se houver um erro de configuração no parser.
     * @throws IOException Se ocorrer um erro ao ler o ficheiro.
     * @throws SAXException Se ocorrer um erro durante a análise do XML.
     */
    public void LerXMLAtleta(File XMLFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(XMLFile);

        NodeList lineItemNodes = doc.getElementsByTagName("athlete");

        List<Atleta> AtletaList = new ArrayList<>();
        List<ParticipaçõesAtleta> participaçõesAtletaList = new ArrayList<>();

        for (int i = 0; i < lineItemNodes.getLength(); i++) {
            Element lineItemElement = (Element) lineItemNodes.item(i);

            Element parentElement = (Element) lineItemElement.getParentNode();

            String nome = getElementTextContent(parentElement, "name");
            String pais = getElementTextContent(parentElement, "country");
            String genero = getElementTextContent(parentElement, "genre");
            int altura = getIntValueFromElement(parentElement, "height");
            int peso = getIntValueFromElement(parentElement, "weight");
            Date dataNascimento = getDateValueFromElement(parentElement, "dateOfBirth");

            NodeList participations = lineItemElement.getElementsByTagName("participation");
            for (int j = 0; j < participations.getLength(); j++) {
                Element participation = (Element) participations.item(j);
                int ano = getIntValueFromElement(participation, "year");
                int ouro = getIntValueFromElement(participation, "gold");
                int prata = getIntValueFromElement(participation, "silver");
                int bronze = getIntValueFromElement(participation, "bronze");

                participaçõesAtletaList.add(new ParticipaçõesAtleta(ano, ouro, prata, bronze));
            }

            AtletaList.add(new Atleta(nome, pais, genero, altura, peso, dataNascimento, participaçõesAtletaList));
        }
    }

    /**
     * Lê e processa o ficheiro XML de equipas, criando objetos do tipo Equipa e suas participações.
     *
     * @param XMLFile O ficheiro XML que contém os dados das equipas.
     * @throws ParserConfigurationException Se houver um erro de configuração no parser.
     * @throws IOException Se ocorrer um erro ao ler o ficheiro.
     * @throws SAXException Se ocorrer um erro durante a análise do XML.
     */
    public void LerXMLEquipa(File XMLFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(XMLFile);

        NodeList lineItemNodes = doc.getElementsByTagName("team");

        List<Equipa> EquipaList = new ArrayList<>();
        List<ParticipaçõesEquipa> participaçõesEquipaList = new ArrayList<>();

        for (int i = 0; i < lineItemNodes.getLength(); i++) {
            Element lineItemElement = (Element) lineItemNodes.item(i);

            Element parentElement = (Element) lineItemElement.getParentNode();

            String nome = getElementTextContent(parentElement, "name");
            String pais = getElementTextContent(parentElement, "country");
            String genero = getElementTextContent(parentElement, "genre");
            String desporto = getElementTextContent(parentElement, "sport");
            int anoFundacao = getIntValueFromElement(parentElement, "foundationYear");

            NodeList participations = lineItemElement.getElementsByTagName("participation");
            for (int j = 0; j < participations.getLength(); j++) {
                Element participation = (Element) participations.item(j);
                int ano = getIntValueFromElement(participation, "year");
                String resultado = getElementTextContent(participation, "result");

                participaçõesEquipaList.add(new ParticipaçõesEquipa(ano, resultado));
            }

            EquipaList.add(new Equipa(nome, pais, genero, desporto, anoFundacao, participaçõesEquipaList));
        }
    }

    /**
     * Lê e processa o ficheiro XML de modalidades.
     *
     *
     * @param XMLFile O ficheiro XML que contém os dados das modalidades.
     */
    public void LerXMLModalidade(File XMLFile) {
    }

    /**
     * Obtém o texto de um elemento XML com base no nome da tag.
     *
     * @param parentElement O elemento pai que contém a tag.
     * @param tagName O nome da tag cujo valor será obtido.
     * @return O conteúdo textual da tag, ou uma string vazia se não for encontrado.
     */
    private String getElementTextContent(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return (node != null) ? node.getTextContent() : "";
    }

    /**
     * Obtém o valor inteiro de um elemento XML com base no nome da tag.
     *
     * @param parentElement O elemento pai que contém a tag.
     * @param tagName O nome da tag cujo valor inteiro será obtido.
     * @return O valor inteiro da tag, ou 0 se houver erro na conversão.
     */
    private int getIntValueFromElement(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        if (node != null) {
            String textContent = node.getTextContent().trim();
            try {
                return Integer.parseInt(textContent);
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Obtém o valor double de um elemento XML com base no nome da tag.
     *
     * @param parentElement O elemento pai que contém a tag.
     * @param tagName O nome da tag cujo valor double será obtido.
     * @return O valor double da tag, ou 0.0 se houver erro na conversão.
     */
    private double getDoubleValueFromElement(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            String textContent = nodeList.item(0).getTextContent().trim();
            try {
                return Double.parseDouble(textContent);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        } else {
            return 0.0;
        }
    }

    /**
     * Obtém o valor de data de um elemento XML com base no nome da tag.
     *
     * @param parentElement O elemento pai que contém a tag.
     * @param tagName O nome da tag cujo valor de data será obtido.
     * @return A data obtida da tag, ou null se houver erro na conversão.
     */
    private Date getDateValueFromElement(Element parentElement, String tagName) {
        SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            String textContent = nodeList.item(0).getTextContent().trim();
            try {
                return formatData.parse(textContent);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
