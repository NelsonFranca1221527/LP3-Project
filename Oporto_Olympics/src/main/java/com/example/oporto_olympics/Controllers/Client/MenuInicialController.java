package com.example.oporto_olympics.Controllers.Client;

import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import javafx.fxml.FXML;
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
     * Botão (Pane) que permite ao utilizador sair da aplicação.
     */
    @FXML
    private Pane SairBtn;
    /**
     * Botão (Pane) que redireciona para a secção de jogos.
     */
    @FXML
    private Pane btnJogos;
    /**
     * Botão (Pane) que redireciona para a secção de bilhetes.
     */
    @FXML
    private Pane btnBilhetes;
    /**
     * Botão (Pane) que redireciona para a secção do perfil do cliente.
     */
    @FXML
    private Pane btnPerfil;
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
