package com.example.oporto_olympics.Controllers.MenuGestor;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Controlador da interface de utilizador do menu do Gestor. Esta classe é responsável por gerir as ações e interações
 * realizadas no menu do Gestor, incluindo navegação para outras partes da aplicação e manipulação de dados.
 */
public class MenuGestorController {
    /**
     * Botão para aceder à secção de atletas.
     */
    @FXML
    private Button ListagensBtn;
    /**
     * Botão para aceder à secção de inscrições.
     */
    @FXML
    private Button XMLBtn;
    /**
     * Botão para aceder à secção de equipas.
     */
    @FXML
    private Button InsercoesBtn;
    /**
     * Botão para aceder à secção de eventos.
     */
    @FXML
    private Button ClienteBtn;
    /**
     * Botão para terminar a sessão e sair.
     */
    @FXML
    private Button SairBtn;
    /**
     * Botão para aceder ao perfil do utilizador.
     */
    @FXML
    private Button PerfilBtn;
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de equipas.
     */
    @FXML
    protected void OnListagensButtonClick(){
        try {
            Stage s = (Stage) ListagensBtn.getScene().getWindow();

            RedirecionarHelper.GotoSubMenuListagens().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de modalidades.
     */
    @FXML
    protected void OnXMLButtonClick(){
        try {
            Stage s = (Stage) XMLBtn.getScene().getWindow();

            RedirecionarHelper.GotoSubMenuXML().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de eventos.
     */
    @FXML
    protected void OnInsercoesButtonClick(){
        try {
            Stage s = (Stage) InsercoesBtn.getScene().getWindow();

            RedirecionarHelper.GotoSubMenuInsercoes().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de atletas.
     */
    @FXML
    protected void OnClienteButtonClick(){
        try {
            Stage s = (Stage) ClienteBtn.getScene().getWindow();

            RedirecionarHelper.GotoSubMenuCliente().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para o perfil pessoal.
     */
    @FXML
    protected void OnPerfilButtonCLick() {
        try {
            Stage s = (Stage) PerfilBtn.getScene().getWindow();

            RedirecionarHelper.GotoDadosPessoaisGestor().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para terminar a sessão e redirecionar o utilizador para o ecrã de login.
     */
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