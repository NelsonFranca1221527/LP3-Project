package com.example.oporto_olympics.Controllers.MenuGestor.SubMenuGestor;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SubMenuGestorInsercoesController {
    /**
     * Botão para inserir novos atletas.
     */
    @FXML
    private Button InsertAtletasBtn;
    /**
     * Botão para inserir novos gestores.
     */
    @FXML
    private Button InsertGestorBtn;
    /**
     * Botão para terminar a sessão e sair.
     */
    @FXML
    private Button VoltarBtn;
    /**
     * Botão para inserir novos eventos.
     */
    @FXML
    private Button InsertEventoBtn;
    /**
     * Botão para inserir novas modalidades.
     */
    @FXML
    private Button InsertModalidadeBtn;
    /**
     * Botão para inserir novas equipas.
     */
    @FXML
    private Button InsertEquipasBtn;

    /**
     * Ação para redirecionar o utilizador para a secção de inserção de equipas.
     */
    @FXML
    protected void OnInsertEquipasButtonClick(){
        try {
            Stage s = (Stage) InsertEquipasBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirEquipa().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de modalidades.
     */
    @FXML
    protected void OnInsertModalidadesButtonClick(){
        try {
            Stage s = (Stage) InsertModalidadeBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirModalidade().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de eventos.
     */
    @FXML
    protected void OnInsertEventosButtonClick(){
        try {
            Stage s = (Stage) InsertEventoBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirEventosOlimpicos().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de atletas.
     */
    @FXML
    protected void OnInsertAtletasButtonClick(){
        try {
            Stage s = (Stage) InsertAtletasBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirAtleta().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de gestores.
     */
    @FXML
    protected void OnInsertGestorButtonClick(){
        try {
            Stage s = (Stage) InsertGestorBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirGestor().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o gestor para o seu menu principal.
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