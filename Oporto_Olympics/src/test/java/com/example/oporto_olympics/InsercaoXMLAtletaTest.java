package com.example.oporto_olympics;

import com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.InserçãoXMLController;
import com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.LerXMLController;
import com.example.oporto_olympics.Models.Atleta;
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
 * Classe de teste para a inserção de dados de atletas a partir de um ficheiro XML.
 * Verifica a leitura e validação do XML contra um esquema XSD, assim como a criação de objetos Atleta e ParticipaçõesAtleta.
 */
public class InsercaoXMLAtletaTest {

    private File xmlValido;
    private File xmlInvalido;

    /**
     * Configuração inicial do teste. Define os caminhos para os ficheiros XML válidos e inválidos.
     */
    @BeforeEach
    public void setUp() {
        String xmlvalidoPath = System.getProperty("user.dir") + "/src/test/resources/AtletasXML/athletes_valid.xml";
        String xmlinvalidoPath = System.getProperty("user.dir") + "/src/test/resources/AtletasXML/athletes_invalid.xml";

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

        String AtletaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/athletes_xsd.xml";
        boolean xmlValidoResult = insercaoxml.VerificarXML(xmlValido, AtletaXSDPath);

        assertTrue(xmlValidoResult, "O arquivo XML deve ser considerado válido.");

        List<Atleta> atletas = lerxml.LerXMLAtleta(xmlValido);
        assertNotNull(atletas, "A lista de atletas não deve ser nula para um XML válido.");
        assertFalse(atletas.isEmpty(), "A lista de atletas deve conter dados para um XML válido.");

        Atleta atletaInserido = atletas.get(0);
        assertEquals("Usain Bolt", atletaInserido.getNome(), "O nome do atleta inserido deve corresponder ao fornecido no XML.");
    }

    /**
     * Testa a inserção de dados a partir de um XML inválido.
     * Verifica se o ficheiro XML inválido é rejeitado e lança uma exceção SAXException.
     */
    @Test
    public void testInsercaoXMLMalformado() {
        InserçãoXMLController insercaoxml = new InserçãoXMLController();

        String AtletaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/athletes_xsd.xml";

        // Validação do XML inválido
        boolean xmlInvalidoResult = insercaoxml.VerificarXML(xmlInvalido, AtletaXSDPath);

        // Confirma que a validação falhou como esperado
        assertFalse(xmlInvalidoResult, "O XML inválido deve falhar na validação XSD.");
    }

}
