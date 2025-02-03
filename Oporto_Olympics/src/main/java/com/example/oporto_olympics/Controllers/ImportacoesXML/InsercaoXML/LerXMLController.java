package com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML;

import com.example.oporto_olympics.Models.*;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoDistancia;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;
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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
/**
 * Controlador responsável por ler e processar ficheiros XML contendo informações sobre atletas, equipas e modalidades.
 * Cada tipo de XML é lido e convertido em listas de objetos correspondentes (Atleta, Equipa, Modalidade).
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
    public List<Atleta> LerXMLAtleta(File XMLFile) throws ParserConfigurationException, IOException, SAXException {

        List<Atleta> lst = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(XMLFile);

        NodeList lineItemNodes = doc.getElementsByTagName("athlete");

        for (int i = 0; i < lineItemNodes.getLength(); i++) {

            Element lineItemElement = (Element) lineItemNodes.item(i);

            String nome = getElementTextContent(lineItemElement, "name");
            String pais = getElementTextContent(lineItemElement, "country");
            String genero = getElementTextContent(lineItemElement, "genre");
            int altura = getIntValueFromElement(lineItemElement, "height");
            int peso = getIntValueFromElement(lineItemElement, "weight");
            Date dataNascimento = getDateValueFromElement(lineItemElement, "dateOfBirth");

            List<ParticipaçõesAtleta> participacoesAtletaList = new ArrayList<>();

            NodeList participations = lineItemElement.getElementsByTagName("participation");

            if (participations.getLength() > 0) {
                for (int j = 0; j < participations.getLength(); j++) {
                    Element participation = (Element) participations.item(j);
                    int ano = getIntValueFromElement(participation, "year");
                    int ouro = getIntValueFromElement(participation, "gold");
                    int prata = getIntValueFromElement(participation, "silver");
                    int bronze = getIntValueFromElement(participation, "bronze");

                    participacoesAtletaList.add(new ParticipaçõesAtleta(ano, ouro, prata, bronze));
                }
            }

            lst.add(new Atleta(0,nome,pais,genero,altura,peso,dataNascimento, participacoesAtletaList, null));
        }

        return lst;
    }

    /**
     * Lê e processa o ficheiro XML de equipas, criando objetos do tipo Equipa e suas participações.
     *
     * @param XMLFile O ficheiro XML que contém os dados das equipas.
     * @throws ParserConfigurationException Se houver um erro de configuração no parser.
     * @throws IOException                  Se ocorrer um erro ao ler o ficheiro.
     * @throws SAXException                 Se ocorrer um erro durante a análise do XML.
     */
    public List<Equipa> LerXMLEquipa(File XMLFile) throws ParserConfigurationException, IOException, SAXException {

        List<Equipa> lst = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(XMLFile);

        NodeList lineItemNodes = doc.getElementsByTagName("team");

        for (int i = 0; i < lineItemNodes.getLength(); i++) {
            Element lineItemElement = (Element) lineItemNodes.item(i);

            String nome = getElementTextContent(lineItemElement, "name");
            String pais = getElementTextContent(lineItemElement, "country");
            String genero = getElementTextContent(lineItemElement, "genre");
            String desporto = getElementTextContent(lineItemElement, "sport");
            int anoFundacao = getIntValueFromElement(lineItemElement, "foundationYear");

            NodeList participations = lineItemElement.getElementsByTagName("participation");

            List<ParticipaçõesEquipa> participaocesEquipaList = new ArrayList<>();

            if (participations.getLength() > 0) {
                for (int j = 0; j < participations.getLength(); j++) {
                    Element participation = (Element) participations.item(j);
                    int ano = getIntValueFromElement(participation, "year");
                    String resultado = getElementTextContent(participation, "result");

                    participaocesEquipaList.add(new ParticipaçõesEquipa(ano, resultado));
                }
            }

            lst.add(new Equipa(0, nome, pais, genero, desporto, 0, anoFundacao, participaocesEquipaList));
        }

        return lst;
    }
    /**
     * Lê e processa o ficheiro XML de modalidades.
     *
     * @param XMLFile O ficheiro XML que contém os dados das modalidades.
     */
    public List<Modalidade> LerXMLModalidade(File XMLFile) throws ParserConfigurationException, IOException, SAXException, SQLException {

        List<Modalidade> lst = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(XMLFile);

        NodeList lineItemNodes = doc.getElementsByTagName("sport");

        for (int i = 0; i < lineItemNodes.getLength(); i++) {
            Element lineItemElement = (Element) lineItemNodes.item(i);

            String tipo = getElementTextContent(lineItemElement, "type");

            if(tipo.equals("Collective")){
                tipo = "Coletivo";
            }

            String genero = getElementTextContent(lineItemElement, "genre");
            String nome = getElementTextContent(lineItemElement, "name");
            String descricao = getElementTextContent(lineItemElement, "description");
            int minParticipantes = getIntValueFromElement(lineItemElement, "minParticipants");
            String medida = getElementTextContent(lineItemElement, "scoringMeasure");
            String oneGame = getElementTextContent(lineItemElement, "oneGame");

            NodeList Rules = lineItemElement.getElementsByTagName("rule");

            String regras = "";

            for (int j = 0; j < Rules.getLength(); j++) {

                Node rule = Rules.item(j);
                String ruleText = rule.getTextContent().trim();

                    if (regras.length() > 0) {
                        regras = regras + " ";
                    }
                    regras = regras + ruleText;
            }

            int anoRecorde = 0;
            String vencedorRecorde = "";

            int anoOlimpico = 0;
            String vencedorOlimpico = "";

            LocalTime tempoRecorde = null;
            LocalTime tempoVencedor = null;

            Double distanciaRecorde = 0.0;
            Double distanciaVencedor = 0.0;

            Modalidade modalidade = null;

            NodeList olympicRecords = lineItemElement.getElementsByTagName("olympicRecord");
            NodeList olympicWinners = lineItemElement.getElementsByTagName("winnerOlympic");

            Element olympicRecord = null;
            Element olympicWinner = null;

            int medalhasRecorde = 0;
            String medalhaVencedor = "";

                if(olympicRecords.getLength() > 0 && olympicWinners.getLength() > 0) {

                    olympicRecord = (Element) olympicRecords.item(0);
                    olympicWinner = (Element) olympicWinners.item(0);

                    anoRecorde = getIntValueFromElement(olympicRecord, "year");
                    vencedorRecorde = getElementTextContent(olympicRecord, "holder");

                    anoOlimpico = getIntValueFromElement(olympicWinner, "year");
                    vencedorOlimpico = getElementTextContent(olympicWinner, "holder");

                    medalhasRecorde = getIntValueFromElement(olympicRecord, "medals");
                    medalhaVencedor = getElementTextContent(olympicWinner, "medal");

                }

                switch (medida) {
                    case "Time":
                        medida = "Tempo";

                        String tempoRecordeString = getElementTextContent(olympicRecord, "time");

                        if (tempoRecordeString != null && !tempoRecordeString.trim().isEmpty()) {
                            tempoRecorde = parseTime(tempoRecordeString);
                        }

                        String tempoVencedorString = getElementTextContent(olympicWinner, "time");

                        if (tempoVencedorString != null && !tempoVencedorString.trim().isEmpty()) {
                            tempoVencedor = parseTime(tempoVencedorString);
                        }

                        RegistoTempo recordeolimpicoTempo = new RegistoTempo(vencedorRecorde, anoRecorde, tempoRecorde, String.valueOf(medalhasRecorde));
                        RegistoTempo vencedorolimpicoTempo = new RegistoTempo(vencedorOlimpico, anoOlimpico, tempoVencedor, medalhaVencedor);
                        modalidade = new Modalidade(0, tipo, genero, nome, descricao, minParticipantes, medida, oneGame, null, recordeolimpicoTempo, vencedorolimpicoTempo, regras);
                        break;

                    case "Points":
                        medida = "Pontos";

                        RegistoPontos recordeolimpicoPontos = new RegistoPontos(vencedorRecorde, anoRecorde, String.valueOf(medalhasRecorde));
                        RegistoPontos vencedorolimpicoPontos = new RegistoPontos(vencedorOlimpico, anoOlimpico, medalhaVencedor);
                        modalidade = new Modalidade(0, tipo, genero, nome, descricao, minParticipantes, medida, oneGame, null, recordeolimpicoPontos, vencedorolimpicoPontos, regras);
                        break;

                    case "Distance":
                        medida = "Distância";

                        String distanciaRecordeString = getElementTextContent(olympicRecord, "distance");

                        if (distanciaRecordeString != null && !distanciaRecordeString.trim().isEmpty()) {
                            distanciaRecorde = Double.parseDouble(distanciaRecordeString);
                        }

                        String distanciaVencedorString = getElementTextContent(olympicWinner, "distance");

                        if (distanciaVencedorString != null && !distanciaVencedorString.trim().isEmpty()) {
                            distanciaVencedor = Double.parseDouble(distanciaVencedorString);
                        }

                        RegistoDistancia recordeolimpicoDistancia = new RegistoDistancia(vencedorRecorde, anoRecorde, distanciaRecorde, String.valueOf(medalhasRecorde));
                        RegistoDistancia vencedorolimpicoDistancia = new RegistoDistancia(vencedorOlimpico, anoOlimpico, distanciaVencedor,medalhaVencedor);
                        modalidade = new Modalidade(0, tipo, genero, nome, descricao, minParticipantes, medida, oneGame, null, recordeolimpicoDistancia, vencedorolimpicoDistancia, regras);
                        break;

                }
                    lst.add(modalidade);
        }

        return lst;
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
                Date date = formatData.parse(textContent);
                return date;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    /**
     * Analisa uma string representando um tempo e converte-a para um objeto LocalTime.
     *
     * @param timeString A string representando o tempo a ser analisada.
     * @return O objeto LocalTime correspondente ao tempo analisado ou null se houver erro.
     */
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
