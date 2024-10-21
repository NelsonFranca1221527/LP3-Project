package com.example.oporto_olympics.Controllers.ImportaçõesXML.SeleçãoXML;

import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.Singleton.InserçãoXMLSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SeleçãoXMLController {

    @FXML
    private Button InserirAtletaButton;

    @FXML
    private Button InserirEquipaButton;

    @FXML
    private Button InserirModalidadeButton;

    @FXML
    void OnClickInserirAtletaButton(ActionEvent event) {

        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        inserçãoXMLSingleton.setTipoXML("Atleta");

        Stage s = (Stage) InserirAtletaButton.getScene().getWindow();

        RedirecionarHelper.GotoInserçãoXML().switchScene(s);

    }

    @FXML
    void OnClickInserirEquipaButton(ActionEvent event) {

        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        inserçãoXMLSingleton.setTipoXML("Equipa");

        Stage s = (Stage) InserirEquipaButton.getScene().getWindow();

        RedirecionarHelper.GotoInserçãoXML().switchScene(s);
    }

    @FXML
    void OnClickInserirModalidadeButton(ActionEvent event) {
        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        inserçãoXMLSingleton.setTipoXML("Modalidade");

        Stage s = (Stage) InserirModalidadeButton.getScene().getWindow();

        RedirecionarHelper.GotoInserçãoXML().switchScene(s);

    }

}
