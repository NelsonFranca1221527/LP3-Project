package com.example.oporto_olympics.Controllers.MenuAtleta;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Controlador da interface de utilizador do menu do Atleta. Esta classe é responsável por gerir as ações e interações
 * realizadas no menu do Atleta, incluindo navegação para outras partes da aplicação e manipulação de dados específicos
 * do atleta.
 */
public class MenuAtletaController {
    /**
     * Botão para visualizar os resultados.
     */
    @FXML
    private Button ResultadosBtn;
    /**
     * Botão para aceder à secção de equipas.
     */
    @FXML
    private Button EquipasBtn;
    /**
     * Botão para aceder à secção de modalidades.
     */
    @FXML
    private Button ModalidadeBtn;
    /**
     * Botão para aceder à secção de eventos.
     */
    @FXML
    private Button EventoBtn;
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
     *
     * Redireciona para página de Resultados dos eventos
     *
     */
    @FXML
    protected void OnResultadosButtonClick() {
        try {
            Stage s = (Stage) ResultadosBtn.getScene().getWindow();

            RedirecionarHelper.GotoVerResultadosAtleta().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * Redireciona para a página das equipas que estão disponíveis
     *
     */
    @FXML
    protected void OnEquipasButtonClick() {
        try {
            Stage s = (Stage) EquipasBtn.getScene().getWindow();

            RedirecionarHelper.GotoInscreverEquipa().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * Redireciona para a página das modalidades que o utilizador pode se inscrever
     *
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
     *
     * Redireciona para a página dos eventos que o utilizador pode se inscrever
     *
     */
    @FXML
    protected void OnEventosButtonClick() {
        try {
            Stage s = (Stage) EventoBtn.getScene().getWindow();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * Redireciona para a página dos dados pessoais do utilizador a que fez login
     *
     */
    @FXML
    protected void OnPerfilButtonCLick() {
        try {
            Stage s = (Stage) PerfilBtn.getScene().getWindow();

            RedirecionarHelper.GotoDadosPessoais().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * Redireciona devolta para o Login
     *
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