package com.example.oporto_olympics.Controllers.MenuGestor.SubMenuGestor;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SubMenuGestorXMLController {
    /**
     * Botão para terminar a sessão e sair.
     */
    @FXML
    private Button VoltarBtn;
    /**
     * Botão para listar os ficheiros XML.
     */
    @FXML
    private Button btnFXML;
    /**
     * Botão para aceder à secção de importação de dados via XML.
     */
    @FXML
    private Button XMLBtn;
    /**
     * Ação para redirecionar o utilizador para a secção de seleção de XML.
     */
    @FXML
    protected void OnInserirXMLButtonClick() {
        try {
            Stage s = (Stage) XMLBtn.getScene().getWindow();

            RedirecionarHelper.GotoSeleçãoXML().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para o menu principal (Gestor).
     */
    @FXML
    protected void OnVoltarButtonClick() {
        try {
            Stage s = (Stage) VoltarBtn.getScene().getWindow();

            RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Método chamado ao clicar no botão para redirecionar para a tela de listar XML.
     */
    @FXML
    protected void OnListarXMLButtonClick(){
        try{
            Stage s = (Stage) btnFXML.getScene().getWindow();

            RedirecionarHelper.GotoListarXML().switchScene(s);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
