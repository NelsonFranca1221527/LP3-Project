package com.example.oporto_olympics.Controllers.Client;

import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProfilePageController {

    @FXML
    private Label txtEmail;

    @FXML
    private Label txtNome;

    @FXML
    private Button btnResetPassword;

    @FXML
    private Button btnVoltar;

    @FXML
    public void initialize() {
        Client client = ClientSingleton.getInstance().getClient();
        txtNome.setText(client.getName());
        txtEmail.setText(client.getEmail());
    }

    /**
     * Evento para o botão "Voltar". Este método redireciona o utilizador para o menu de inserções.
     */
    @FXML
    void onClickVoltar(ActionEvent event) {
        Stage s = (Stage) btnVoltar.getScene().getWindow();
        RedirecionarHelper.GotoHomeClient().switchScene(s);
    }
    @FXML
    void onCllickResetPassword(ActionEvent event) {
        Stage s = (Stage) btnResetPassword.getScene().getWindow();
        RedirecionarHelper.GotoUpdatePasswordClient().switchScene(s);
    }
}
