package com.example.oporto_olympics.Controllers.MenuGestor.SubMenuGestor;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SubMenuGestorListagensController {
    /**
     * Botão para aceder à secção de atletas.
     */
    @FXML
    private Button AtletasBtn;
    /**
     * Botão para aceder à secção de inscrições.
     */
    @FXML
    private Button InscricoesBtn;
    /**
     * Botão para aceder à secção de equipas.
     */
    @FXML
    private Button EquipasBtn;
    /**
     * Botão para aceder à secção de eventos.
     */
    @FXML
    private Button EventoBtn;
    /**
     * Botão para aceder à secção de locais.
     */
    @FXML
    private Button LocalBtn;
    /**
     * Botão para aceder à secção de modalidades.
     */
    @FXML
    private Button ModalidadeBtn;
    /**
     * Botão para terminar a sessão e sair.
     */
    @FXML
    private Button VoltarBtn;
    /**
     * Botão para aceder à secção de resultados.
     */
    @FXML
    private Button ResultadosBtn;
    /**
     * Ação para redirecionar o utilizador para a secção de inscrições.
     */
    @FXML
    protected void OnInscricoesButtonClick(){
        try {
            Stage s = (Stage) InscricoesBtn.getScene().getWindow();

            RedirecionarHelper.GotoInscricoesEquipa().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de atletas.
     */
    @FXML
    protected void OnAtletasButtonClick() {
        try {
            Stage s = (Stage) AtletasBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemAtleta().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de equipas.
     */
    @FXML
    protected void OnEquipasButtonClick() {
        try {
            Stage s = (Stage) EquipasBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemEquipas().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de eventos.
     */
    @FXML
    protected void OnEventosButtonClick() {
        try {
            Stage s = (Stage) EventoBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemEventos().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de locais.
     */
    @FXML
    protected void OnLocaisButtonClick( ) {
        try {
            Stage s = (Stage) LocalBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemLocais().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de modalidades.
     */
    @FXML
    protected void OnModalidadesButtonClick() {
        try {
            Stage s = (Stage) ModalidadeBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemModalidades().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para os resultados.
     */
    @FXML
    protected void OnVerResultadosButtonClick(){
        try{
            Stage s = (Stage) ResultadosBtn.getScene().getWindow();

            RedirecionarHelper.GotoVerResultados().switchScene(s);
        } catch (Exception e){
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

}
