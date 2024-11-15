package com.example.oporto_olympics;

import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para a inserção de dados de equipas a partir de um ficheiro XML.
 * Verifica a leitura e validação do XML contra um esquema XSD, assim como a criação de objetos Atleta e ParticipaçõesAtleta.
 */
public class InsercaoXMLModalidadesTest {

    private File xmlValido;
    private File xmlInvalido;

    /**
     * Configuração inicial do teste. Define os caminhos para os ficheiros XML válidos e inválidos.
     */
    @BeforeEach
    public void setUp() {
        String xmlvalidoPath = System.getProperty("user.dir") + "/src/test/resources/ModalidadesXML/sports_valid.xml";
        String xmlinvalidoPath = System.getProperty("user.dir") + "/src/test/resources/ModalidadesXML/sports_invalid.xml";

        xmlValido = new File(xmlvalidoPath);
        xmlInvalido = new File(xmlinvalidoPath);
    }

    /**
     * Verifica se o ficheiro XML fornecido é válido em relação ao ficheiro XSD.
     *
     * @param xmlFile O ficheiro XML a ser validado.
     * @param xsdFilePath O caminho do ficheiro XSD para validação.
     * @return true se o ficheiro XML for válido, false caso contrário.
     */
    private boolean VerificarXML(File xmlFile, String xsdFilePath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new File(xsdFilePath)));
            schema.newValidator().validate(new StreamSource(xmlFile));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lê e processa o ficheiro XML de modalidades.
     *
     *
     * @param XMLFile O ficheiro XML que contém os dados das modalidades.
     */
    public List<Modalidade> LerXMLModalidade(File XMLFile, int IdEvento) throws ParserConfigurationException, IOException, SAXException, SQLException {

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
                    modalidade = new Modalidade(0, tipo, genero, nome, descricao, minParticipantes, medida, oneGame, IdEvento, recordeolimpicoTempo, vencedorolimpicoTempo, regras);
                    break;

                case "Points":
                    medida = "Pontos";
                    int medalhasRecorde = getIntValueFromElement(olympicRecord, "medals");
                    String medalhaVencedor = getElementTextContent(olympicWinner, "medal");
                    RegistoPontos recordeolimpicoPontos = new RegistoPontos(vencedorRecorde, anoRecorde, String.valueOf(medalhasRecorde));
                    RegistoPontos vencedorolimpicoPontos = new RegistoPontos(vencedorOlimpico, anoOlimpico, medalhaVencedor);
                    modalidade = new Modalidade(0, tipo, genero, nome, descricao, minParticipantes, medida, oneGame, IdEvento, recordeolimpicoPontos, vencedorolimpicoPontos, regras);
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
     * Analisa uma string de tempo em dois formatos possíveis (com ou sem milissegundos).
     *
     * @param timeString A string de tempo a ser analisada.
     * @return O objeto LocalTime correspondente ou null em caso de erro.
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

    /**
     * Testa a inserção de dados a partir de um XML válido.
     * Verifica se os dados do XML são lidos corretamente e se a lista de atletas resultante está preenchida.
     */
    @Test
    public void testInsercaoXMLValido() throws ParserConfigurationException, IOException, SAXException, SQLException {
        String ModalidadeXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/sports_xsd.xml";
        boolean xmlValidoResult = VerificarXML(xmlValido, ModalidadeXSDPath);

        assertTrue(xmlValidoResult, "O arquivo XML deve ser considerado válido.");

        List<Modalidade> modalidades = LerXMLModalidade(xmlValido, 12);
        assertNotNull(modalidades, "A lista de modalidades não deve ser nula para um XML válido.");
        assertFalse(modalidades.isEmpty(), "A lista de modalidades deve conter dados para um XML válido.");

        Modalidade modalidadeInserido = modalidades.get(0);
        assertEquals("100m Sprint", modalidadeInserido.getNome(), "O nome da modalidade inserida deve corresponder ao fornecido no XML.");
    }

    /**
     * Testa a inserção de dados a partir de um XML inválido.
     * Verifica se o ficheiro XML inválido é rejeitado e lança uma exceção SAXException.
     */
    @Test
    public void testInsercaoXMLMalformado() throws ParserConfigurationException, IOException, SAXException {
        String ModalidadeXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/sports_xsd.xml";

        // Validação do XML inválido
        boolean xmlInvalidoResult = VerificarXML(xmlInvalido, ModalidadeXSDPath);

        // Confirma que a validação falhou como esperado
        assertFalse(xmlInvalidoResult, "O XML inválido deve falhar na validação XSD.");
    }
}
