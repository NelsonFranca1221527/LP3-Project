package com.example.oporto_olympics.Controllers.ImportaçõesXML.SeleçãoXML;

import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.Singleton.InserçãoXMLSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controlador responsável pela seleção de tipos de importação XML
 * na aplicação dos Jogos Olímpicos do Porto.
 */
public class SeleçãoXMLController {

    @FXML
    private Button InserirAtletaButton;

    @FXML
    private Button InserirEquipaButton;

    @FXML
    private Button InserirModalidadeButton;

    @FXML
    private Button VoltarButton;

    /**
     *
     * Altera o tipo de XML para "Atleta" e redireciona para a
     * cena de inserção.
     *
     * @param event O evento de ação gerado ao clicar no botão.
     */
    @FXML
    void OnClickInserirAtletaButton(ActionEvent event) {
        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();
        inserçãoXMLSingleton.setTipoXML("Atleta");

        Stage s = (Stage) InserirAtletaButton.getScene().getWindow();
        RedirecionarHelper.GotoInserçãoXML().switchScene(s);
    }

    /**
     *
     * Altera o tipo de XML para "Equipa" e redireciona para a
     * cena de inserção.
     *
     * @param event O evento de ação gerado ao clicar no botão.
     */
    @FXML
    void OnClickInserirEquipaButton(ActionEvent event) {
        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();
        inserçãoXMLSingleton.setTipoXML("Equipa");

        Stage s = (Stage) InserirEquipaButton.getScene().getWindow();
        RedirecionarHelper.GotoInserçãoXML().switchScene(s);
    }

    /**
     *
     * Altera o tipo de XML para "Modalidade" e redireciona para a
     * cena de inserção.
     *
     * @param event O evento de ação gerado ao clicar no botão.
     */
    @FXML
    void OnClickInserirModalidadeButton(ActionEvent event) {
        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();
        inserçãoXMLSingleton.setTipoXML("Modalidade");

        Stage s = (Stage) InserirModalidadeButton.getScene().getWindow();
        RedirecionarHelper.GotoInserçãoXML().switchScene(s);
    }

    /**
     *
     * Irá redirecionar para a página do Gestor quando o botão for clicado
     *
     * @param event O evento de ação gerado ao clicar no botão.
     */
    @FXML
    void OnClickVoltarButton(ActionEvent event) {
    }
}
