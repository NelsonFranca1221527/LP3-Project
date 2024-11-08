package com.example.oporto_olympics.Controllers.MenuGestor;

import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuGestorController {

    @FXML
    private Button AtletasBtn;

    @FXML
    private Button InscricoesBtn;

    @FXML
    private Button EquipasBtn;

    @FXML
    private Button EventoBtn;

    @FXML
    private Button LocalBtn;

    @FXML
    private Button InsertAtletasBtn;

    @FXML
    private Button ModalidadeBtn;

    @FXML
    private Button SairBtn;

    @FXML
    private Button InsertEventoBtn;

    @FXML
    private Button InsertModalidadeBtn;

    @FXML
    private Button XMLBtn;

    @FXML
    private Button InsertEquipasBtn;

    @FXML
    private Button PerfilBtn;

    @FXML
    protected void OnInsertEquipasButtonClick(){
        try {
            Stage s = (Stage) InsertEquipasBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirEquipa().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnInsertModalidadesButtonClick(){
        try {
            Stage s = (Stage) InsertModalidadeBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirModalidade().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnInsertEventosButtonClick(){
        try {
            Stage s = (Stage) InsertEventoBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirEventosOlimpicos().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnInsertAtletasButtonClick(){
        try {
            Stage s = (Stage) InsertAtletasBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirAtleta().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnInscricoesButtonClick(){
        try {
            Stage s = (Stage) InscricoesBtn.getScene().getWindow();

            RedirecionarHelper.GotoInscricoesEquipa().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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

            RedirecionarHelper.GotoListagemEventos().switchScene(s);
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