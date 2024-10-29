package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Controllers.Misc.AlertHandler;
import com.example.oporto_olympics.Models.*;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;
import javafx.scene.control.Alert;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class LerXMLController {

    /**
     * Lê e processa o ficheiro XML de atletas, criando objetos do tipo Atleta e suas participações.
     *
     * @param XMLFile O ficheiro XML que contém os dados dos atletas.
     * @throws ParserConfigurationException Se houver um erro de configuração no parser.
     * @throws IOException Se ocorrer um erro ao ler o ficheiro.
     * @throws SAXException Se ocorrer um erro durante a análise do XML.
     */
    public List<Atleta> LerXMLAtleta(File XMLFile) throws ParserConfigurationException, IOException, SAXException, SQLException {

        List<Atleta> lst = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(XMLFile);

        NodeList lineItemNodes = doc.getElementsByTagName("athlete");

        for (int i = 0; i < lineItemNodes.getLength(); i++) {

            List<ParticipaçõesAtleta> participaçõesAtletaList = new ArrayList<>();

            Element lineItemElement = (Element) lineItemNodes.item(i);

            String nome = getElementTextContent(lineItemElement, "name");
            String pais = getElementTextContent(lineItemElement, "country");
            String genero = getElementTextContent(lineItemElement, "genre");
            int altura = getIntValueFromElement(lineItemElement, "height");
            int peso = getIntValueFromElement(lineItemElement, "weight");
            Date dataNascimento = getDateValueFromElement(lineItemElement, "dateOfBirth");

            NodeList participations = lineItemElement.getElementsByTagName("participation");
            for (int j = 0; j < participations.getLength(); j++) {
                Element participation = (Element) participations.item(j);
                int ano = getIntValueFromElement(participation, "year");
                int ouro = getIntValueFromElement(participation, "gold");
                int prata = getIntValueFromElement(participation, "silver");
                int bronze = getIntValueFromElement(participation, "bronze");

                participaçõesAtletaList.add(new ParticipaçõesAtleta(ano, ouro, prata, bronze));
            }

            lst.add(new Atleta(0,nome,pais,genero,altura,peso,dataNascimento,participaçõesAtletaList));
        }

        return lst;
    }

    /**
     * Lê e processa o ficheiro XML de equipas, criando objetos do tipo Equipa e suas participações.
     *
     * @param XMLFile O ficheiro XML que contém os dados das equipas.
     * @throws ParserConfigurationException Se houver um erro de configuração no parser.
     * @throws IOException Se ocorrer um erro ao ler o ficheiro.
     * @throws SAXException Se ocorrer um erro durante a análise do XML.
     */
    public List<Equipa> LerXMLEquipa(File XMLFile, int IdLocal) throws ParserConfigurationException, IOException, SAXException, SQLException {

        AlertHandler alertHandler;

        List<Equipa> lst = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(XMLFile);

        NodeList lineItemNodes = doc.getElementsByTagName("team");

        List<ParticipaçõesEquipa> participaçõesEquipaList = new ArrayList<>();

        for (int i = 0; i < lineItemNodes.getLength(); i++) {
            Element lineItemElement = (Element) lineItemNodes.item(i);

            String nome = getElementTextContent(lineItemElement, "name");
            String pais = getElementTextContent(lineItemElement, "country");
            String genero = getElementTextContent(lineItemElement, "genre");
            String desporto = getElementTextContent(lineItemElement, "sport");
            int anoFundacao = getIntValueFromElement(lineItemElement, "foundationYear");

            NodeList participations = lineItemElement.getElementsByTagName("participation");
            for (int j = 0; j < participations.getLength(); j++) {
                Element participation = (Element) participations.item(j);
                int ano = getIntValueFromElement(participation, "year");
                String resultado = getElementTextContent(participation, "result");

                participaçõesEquipaList.add(new ParticipaçõesEquipa(ano, resultado));
            }

            ConnectionBD conexaoBD = ConnectionBD.getInstance();
            Connection conexao = conexaoBD.getConexao();

            ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

            List<Modalidade> modalidadeList = modalidadeDAOImp.getAll();

            int modalidade = 0;

            for (int k = 0; k < modalidadeList.size(); k++) {

                Modalidade modalidadeAtual = modalidadeList.get(k);

                if (modalidadeAtual.getNome().equals(desporto) && modalidadeAtual.getGenero().equals(genero) && modalidadeAtual.getLocalID() == IdLocal) {
                    modalidade = modalidadeAtual.getId();
                }
            }

            if (modalidade == 0) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Modalidade Não Encontrada", "" + nome + ", Género: " + genero + ", Desporto: " + desporto);
                alertHandler.getAlert().showAndWait();
            } else {
                lst.add(new Equipa(0, nome, pais, genero, desporto, modalidade, anoFundacao, participaçõesEquipaList));
            }
        }

        return lst;
    }
    /**
     * Lê e processa o ficheiro XML de modalidades.
     *
     *
     * @param XMLFile O ficheiro XML que contém os dados das modalidades.
     */
    public List<Modalidade> LerXMLModalidade(File XMLFile, int IdLocal) throws ParserConfigurationException, IOException, SAXException, SQLException {

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

            NodeList olympicRecords = lineItemElement.getElementsByTagName("olympicRecord");
            NodeList olympicWinners = lineItemElement.getElementsByTagName("winnerOlympic");

            int anoRecorde = 0;
            String vencedorRecorde = "";

            int anoOlimpico = 0;
            String vencedorOlimpico = "";

            LocalTime tempoRecorde = null;
            LocalTime tempoVencedor = null;

                    Element olympicRecord = (Element) olympicRecords.item(0);
                    Element olympicWinner = (Element) olympicWinners.item(0);

                    Modalidade modalidade = null;

                    if (olympicRecord != null) {
                        anoRecorde = getIntValueFromElement(olympicRecord, "year");
                        vencedorRecorde = getElementTextContent(olympicRecord, "holder");
                        String tempoRecordeString = getElementTextContent(olympicRecord, "time");

                        if (tempoRecordeString != null && !tempoRecordeString.trim().isEmpty()) {
                            tempoRecorde = parseTime(tempoRecordeString);
                        }

                        anoOlimpico = getIntValueFromElement(olympicWinner, "year");
                        vencedorOlimpico = getElementTextContent(olympicWinner, "holder");
                        String tempoVencedorString = getElementTextContent(olympicWinner, "time");

                        if (tempoVencedorString != null && !tempoVencedorString.trim().isEmpty()) {
                            tempoVencedor = parseTime(tempoVencedorString);
                        }
                    }

            switch (medida) {
                case "Time":
                    medida = "Tempo";
                    RegistoTempo recordeolimpicoTempo = new RegistoTempo(vencedorRecorde, anoRecorde, tempoRecorde);
                    RegistoTempo vencedorolimpicoTempo = new RegistoTempo(vencedorOlimpico, anoOlimpico, tempoVencedor);
                    modalidade = new Modalidade(0, tipo, genero, nome, descricao, minParticipantes, medida, oneGame, IdLocal, recordeolimpicoTempo, vencedorolimpicoTempo, regras);
                    break;

                case "Points":
                    medida = "Pontos";
                    int medalhasRecorde = getIntValueFromElement(olympicRecord, "medals");
                    String medalhaVencedor = getElementTextContent(olympicWinner, "medal");
                    RegistoPontos recordeolimpicoPontos = new RegistoPontos(vencedorRecorde, anoRecorde, String.valueOf(medalhasRecorde));
                    RegistoPontos vencedorolimpicoPontos = new RegistoPontos(vencedorOlimpico, anoOlimpico, medalhaVencedor);
                    modalidade = new Modalidade(0, tipo, genero, nome, descricao, minParticipantes, medida, oneGame, IdLocal, recordeolimpicoPontos, vencedorolimpicoPontos, regras);
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
