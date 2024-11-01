package com.example.oporto_olympics.Controllers.InserirAtletaEquipa;

import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class InserirAtletaEquipaController {

    @FXML
    private Pane ContainerEquipa;

    @FXML
    private ListView<?> ListViewEquipas;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDetalhes;

    @FXML
    private Label labelDesporto;

    @FXML
    private Label labelGenero;

    @FXML
    private Label labelMedalhas;

    @FXML
    private Label labelNome;


    @FXML
    void onAction(ActionEvent event) {

    }

    /**
     *
     * Redireciona o utilizador de volta para a cena de seleção XML.
     *
     * @param event O evento de ação gerado ao clicar no botão.
     */
    @FXML
    void onActionBack(ActionEvent event) {
        Stage s = (Stage) btnBack.getScene().getWindow();
        RedirecionarHelper.GotoSeleçãoXML().switchScene(s);
    }

}
