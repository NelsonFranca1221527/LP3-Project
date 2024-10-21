package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML;

import com.example.oporto_olympics.Controllers.Singleton.InserçãoXMLSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

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
    private Label tituloTipoXML;

    public void initialize() {
        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        switch (inserçãoXMLSingleton.getTipoXML()){
            case "Atleta":
                ModalidadeGroup.setVisible(false);
                EventoGroup.setVisible(false);
                tituloTipoXML.setText("um Atleta");
                break;
            case "Equipa":
                EventoGroup.setVisible(false);
                tituloTipoXML.setText("uma Equipa");
                break;
            case "Modalidade":
                ModalidadeGroup.setVisible(false);
                tituloTipoXML.setText("uma Modalidade");
                break;
        }
    }

    @FXML
    void OnClickInserirXMLButton(ActionEvent event) throws ParserConfigurationException, IOException, SAXException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o seu Ficheiro XML!!");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.xml")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) {
            return;
        }

        String AtletaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/athletes_xsd.xml";
        String EquipaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/athletes_xsd.xml";
        String ModalidadeXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/athletes_xsd.xml";

        System.out.println(AtletaXSDPath);

        LerXMLController lerXMLController = new LerXMLController();

        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        boolean valido = false;

        switch (inserçãoXMLSingleton.getTipoXML()){
            case "Atleta":
                valido =VerificarXML(selectedFile, AtletaXSDPath);
                if(valido) {
                    lerXMLController.LerXMLAtleta(selectedFile);
                }
                return;
            case "Equipa":
                valido =VerificarXML(selectedFile, EquipaXSDPath);
                if(valido) {
                    lerXMLController.LerXMLEquipa(selectedFile);
                }
                return;
            case "Modalidade":
                valido =VerificarXML(selectedFile, ModalidadeXSDPath);
                if(valido) {
                    lerXMLController.LerXMLModalidade(selectedFile);
                }
                return;
        }
    }

    private boolean VerificarXML(File xmlFile, String xsdFilePath){
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
}
