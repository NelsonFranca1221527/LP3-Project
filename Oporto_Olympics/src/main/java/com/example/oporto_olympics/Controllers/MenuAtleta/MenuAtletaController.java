package com.example.oporto_olympics.Controllers.MenuAtleta;

import com.example.oporto_olympics.Controllers.DadosPessoais.DadosPessoaisController;
import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Models.Atleta;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuAtletaController {

    @FXML
    private Button ResultadosBtn;

    @FXML
    private Button EquipasBtn;

    @FXML
    private Button ModalidadeBtn;

    @FXML
    private Button EventoBtn;

    @FXML
    private Button SairBtn;

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

            //Adiocionar redirecionar para resultados
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

            //Adicionar redirecionar para equipas
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

            //Adicionar redirecionar para equipas
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

            //Adicionar redirecionar para Eventos
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