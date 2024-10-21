package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML;

import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.ParticipaçõesAtleta;
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

public class LerXMLController {

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

            String pais = getElementTextContent(parentElement, "country");;

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

            AtletaList.add(new Atleta(nome,pais,genero,altura,peso,dataNascimento, participaçõesAtletaList));

        }
    }

    public void LerXMLEquipa(File XMLFile) {

    }

    public void LerXMLModalidade(File XMLFile) {

    }

    private String getElementTextContent(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return (node != null) ? node.getTextContent() : "";
    }

    private int getIntValueFromElement(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        if (node != null) {
            String textContent = node.getTextContent().trim(); // Remove espaços em branco
            try {
                return Integer.parseInt(textContent);
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

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
