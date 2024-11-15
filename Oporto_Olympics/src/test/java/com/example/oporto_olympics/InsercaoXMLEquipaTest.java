package com.example.oporto_olympics;

import com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.InserçãoXMLController;
import com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.LerXMLController;
import com.example.oporto_olympics.Models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import java.sql.SQLException;
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
     * Testa a inserção de dados a partir de um XML válido.
     * Verifica se os dados do XML são lidos corretamente e se a lista de atletas resultante está preenchida.
     */
    @Test
    public void testInsercaoXMLValido() throws ParserConfigurationException, IOException, SAXException, SQLException {
        InserçãoXMLController insercaoxml = new InserçãoXMLController();
        LerXMLController lerxml = new LerXMLController();

        String EquipaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/teams_xsd.xml";

        // Verifica se o XML é válido de acordo com o XSD
        boolean xmlValidoResult = insercaoxml.VerificarXML(xmlValido, EquipaXSDPath);

        // A validação deve retornar true para XML válido
        assertTrue(xmlValidoResult, "O arquivo XML deve ser considerado válido.");

        // Lê e processa o XML para garantir que os dados são extraídos corretamente
        List<Equipa> equipas = lerxml.LerXMLEquipa(xmlValido, 12);
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
        InserçãoXMLController insercaoxml = new InserçãoXMLController();

        String EquipaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/teams_xsd.xml";

        // Validação do XML inválido
        boolean xmlInvalidoResult = insercaoxml.VerificarXML(xmlInvalido, EquipaXSDPath);

        // Confirma que a validação falhou como esperado
        assertFalse(xmlInvalidoResult, "O XML inválido deve falhar na validação XSD.");
    }

}
