package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.*;
import javafx.scene.control.Alert;
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
import java.sql.Connection;
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
public class InsercaoXMLEquipaTest {

    private File xmlValido;
    private File xmlInvalido;

    /**
     * Configuração inicial do teste. Define os caminhos para os ficheiros XML válidos e inválidos.
     */
    @BeforeEach
    public void setUp() {
        String xmlvalidoPath = System.getProperty("user.dir") + "/src/test/resources/EquipasXML/teams_valid.xml";
        String xmlinvalidoPath = System.getProperty("user.dir") + "/src/test/resources/EquipasXML/teams_invalid.xml";

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
     * Lê e processa o ficheiro XML de equipas, criando objetos do tipo Equipa e suas participações.
     *
     * @param XMLFile O ficheiro XML que contém os dados das equipas.
     * @throws ParserConfigurationException Se houver um erro de configuração no parser.
     * @throws IOException Se ocorrer um erro ao ler o ficheiro.
     * @throws SAXException Se ocorrer um erro durante a análise do XML.
     */
    public List<Equipa> LerXMLEquipa(File XMLFile, int IdEvento) throws ParserConfigurationException, IOException, SAXException, SQLException {

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

                if (modalidadeAtual.getNome().equals(desporto) && modalidadeAtual.getGenero().equals(genero) && modalidadeAtual.getEventoID() == IdEvento) {
                    modalidade = modalidadeAtual.getId();
                }
            }

            lst.add(new Equipa(0, nome, pais, genero, desporto, modalidade, anoFundacao, participaçõesEquipaList));

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
        String EquipaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/teams_xsd.xml";

        // Verifica se o XML é válido de acordo com o XSD
        boolean xmlValidoResult = VerificarXML(xmlValido, EquipaXSDPath);

        // A validação deve retornar true para XML válido
        assertTrue(xmlValidoResult, "O arquivo XML deve ser considerado válido.");

        // Lê e processa o XML para garantir que os dados são extraídos corretamente
        List<Equipa> equipas = LerXMLEquipa(xmlValido, 12);
        assertNotNull(equipas, "A lista de equipas não deve ser nula para um XML válido.");
        assertFalse(equipas.isEmpty(), "A lista de equipas deve conter dados para um XML válido.");

        // Verifica se o nome da equipa inserida é o esperado
        Equipa equipaInserido = equipas.get(0);
        assertEquals("USA Men's Basketball Team", equipaInserido.getNome(), "O nome da equipa inserida deve corresponder ao fornecido no XML.");
    }


    /**
     * Testa a inserção de dados a partir de um XML inválido.
     * Verifica se o ficheiro XML inválido é rejeitado e lança uma exceção SAXException.
     */
    @Test
    public void testInsercaoXMLMalformado() throws ParserConfigurationException, IOException, SAXException {
        String EquipaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/teams_xsd.xml";

        // Validação do XML inválido
        boolean xmlInvalidoResult = VerificarXML(xmlInvalido, EquipaXSDPath);

        // Confirma que a validação falhou como esperado
        assertFalse(xmlInvalidoResult, "O XML inválido deve falhar na validação XSD.");
    }

}
