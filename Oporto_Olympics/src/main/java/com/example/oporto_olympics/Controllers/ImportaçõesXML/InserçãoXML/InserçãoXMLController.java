package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML;

import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.Singleton.InserçãoXMLSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controlador responsável pela inserção de ficheiros XML
 */
public class InserçãoXMLController {

    @FXML
    private ChoiceBox<?> EventoChoice;

    @FXML
    private Group EventoGroup;

    @FXML
    private Button InserirXMLButton;

    @FXML
    private ChoiceBox<?> ModalidadeChoice;

    @FXML
    private Group ModalidadeGroup;

    @FXML
    private Button VoltarButton;

    @FXML
    private Label tituloTipoXML;

    /**
     * Inicializa o controlador, ajustando a interface com base no tipo de XML
     * a ser inserido (Atleta, Equipa ou Modalidade).
     */
    public void initialize() {
        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        switch (inserçãoXMLSingleton.getTipoXML()){
            case "Atleta":
                ModalidadeGroup.setVisible(false);
                EventoGroup.setVisible(false);
                tituloTipoXML.setText(tituloTipoXML.getText() + " um Atleta");
                break;
            case "Equipa":
                EventoGroup.setVisible(false);
                tituloTipoXML.setText(tituloTipoXML.getText() + " uma Equipa");
                break;
            case "Modalidade":
                ModalidadeGroup.setVisible(false);
                tituloTipoXML.setText(tituloTipoXML.getText() + " uma Modalidade");
                break;
        }
    }

    /**
     *
     * Abre um seletor de ficheiros para escolher um ficheiro XML e valida
     * o ficheiro com base no tipo de XSD correspondente.
     *
     * @param event O evento de ação gerado ao clicar no botão.
     * @throws ParserConfigurationException Se houver um erro na configuração do parser.
     * @throws IOException Se ocorrer um erro ao ler o ficheiro.
     * @throws SAXException Se ocorrer um erro durante a validação do XML.
     */
    @FXML
    void OnClickInserirXMLButton(ActionEvent event) throws ParserConfigurationException, IOException, SAXException, SQLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o seu Ficheiro XML!");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.xml")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) {
            return;
        }

        String AtletaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/athletes_xsd.xml";
        String EquipaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/teams_xsd.xml";
        String ModalidadeXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/sports_xsd.xml";

        LerXMLController lerXMLController = new LerXMLController();

        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        boolean valido = false;

        switch (inserçãoXMLSingleton.getTipoXML()){
            case "Atleta":
                valido = VerificarXML(selectedFile, AtletaXSDPath);
                if (valido) {
                    lerXMLController.LerXMLAtleta(selectedFile);
                }
                break;
            case "Equipa":
                valido = VerificarXML(selectedFile, EquipaXSDPath);
                if (valido) {
                    lerXMLController.LerXMLEquipa(selectedFile);
                }
                break;
            case "Modalidade":
                valido = VerificarXML(selectedFile, ModalidadeXSDPath);
                if (valido) {
                    lerXMLController.LerXMLModalidade(selectedFile);
                }
        }

        Stage s = (Stage) InserirXMLButton.getScene().getWindow();
        RedirecionarHelper.GotoSeleçãoXML().switchScene(s);
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
     *
     * Redireciona o utilizador de volta para a cena de seleção XML.
     *
     * @param event O evento de ação gerado ao clicar no botão.
     */
    @FXML
    void OnClickVoltarButton(ActionEvent event) {
        Stage s = (Stage) VoltarButton.getScene().getWindow();
        RedirecionarHelper.GotoSeleçãoXML().switchScene(s);
    }
}
