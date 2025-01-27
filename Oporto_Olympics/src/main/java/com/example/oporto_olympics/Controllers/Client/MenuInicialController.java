package com.example.oporto_olympics.Controllers.Client;

import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Controlador para o Menu Inicial da aplicação.
 * Este controlador é responsável por gerir as interações do utilizador no menu inicial,
 * bem como inicializar e apresentar os dados relevantes do cliente.
 */
public class MenuInicialController {
    /**
     * Label que exibe o nome do cliente no menu inicial.
     */
    @FXML
    private Label txtNome;
    /**
     * Botão que permite ao utilizador sair da aplicação.
     */
    @FXML
    private Button SairBtn;
    /**
     * Botão que redireciona para a secção de jogos.
     */
    @FXML
    private Button btnJogos;
    /**
     * Botão que redireciona para a secção de bilhetes.
     */
    @FXML
    private Button btnBilhetes;
    /**
     * Botão que redireciona para a secção do perfil do cliente.
     */
    @FXML
    private Button PerfilBtn;
    /**
     * Método de inicialização do controlador.
     * Este método é executado automaticamente ao carregar a interface gráfica,
     * e atualiza o nome do cliente no menu inicial com os dados do cliente atual.
     */
    @FXML
    public void initialize() {
        Client client = ClientSingleton.getInstance().getClient();
        txtNome.setText(client.getName());
    }
    /**
     * Ação associada ao botão "Bilhetes".
     * Este método redireciona o utilizador para a janela de Bilhetes.
     */
    @FXML
    protected void OnBilhetesButtonClick(){
        try {
            Stage s = (Stage) btnBilhetes.getScene().getWindow();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação associada ao botão "Jogos".
     * Este método redireciona o utilizador para a janela de Jogos.
     */
    @FXML
    protected void OnJogosButtonClick() {
        try {
            Stage s = (Stage) btnJogos.getScene().getWindow();

            RedirecionarHelper.GotoListagemJogos().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação associada ao botão "Perfil".
     * Este método redireciona o utilizador para a janela do perfil do utilizador.
     */
    @FXML
    protected void OnPerfilButtonClick(){
        try {
            Stage s = (Stage) PerfilBtn.getScene().getWindow();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação associada ao botão "Sair".
     * Este método redireciona o utilizador para a janela de login e termina a sessão atual.
     */
    @FXML
    private void OnActionSair() {
        try {
            Stage s = (Stage) SairBtn.getScene().getWindow();

            RedirecionarHelper.GotoLogin().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
