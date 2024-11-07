package com.example.oporto_olympics.Controllers.MenuGestor;

import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuGestorController {

    @FXML
    private Button AtletasBtn;

    @FXML
    private Button EquipasBtn;

    @FXML
    private Button EventoBtn;

    @FXML
    private Button LocalBtn;

    @FXML
    private Button ModalidadeBtn;

    @FXML
    private Button SairBtn;

    @FXML
    private Button XMLBtn;

    @FXML
    private Button PerfilBtn;


    @FXML
    protected void OnAtletasButtonClick() {
        try {
            Stage s = (Stage) AtletasBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemAtleta().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnEquipasButtonClick() {
        try {
            Stage s = (Stage) EquipasBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemEquipas().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnEventosButtonClick() {
        try {
            Stage s = (Stage) EventoBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemEvento().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnInserirXMLButtonClick() {
        try {
            Stage s = (Stage) XMLBtn.getScene().getWindow();

            RedirecionarHelper.GotoSeleçãoXML().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnLocaisButtonClick( ) {
        try {
            Stage s = (Stage) LocalBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemLocais().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnModalidadesButtonClick() {
        try {
            Stage s = (Stage) ModalidadeBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemModalidades().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnPerfilButtonCLick() {
        try {
            Stage s = (Stage) PerfilBtn.getScene().getWindow();

            RedirecionarHelper.GotoDadosPessoais().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnSairButtonClick() {
        try {
            Stage s = (Stage) SairBtn.getScene().getWindow();

            RedirecionarHelper.GotoLogin().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}