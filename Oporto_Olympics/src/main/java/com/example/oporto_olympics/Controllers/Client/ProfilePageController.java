package com.example.oporto_olympics.Controllers.Client;

import com.example.oporto_olympics.API.DAO.Client.ClienteDAO;
import com.example.oporto_olympics.API.DAO.Client.ClienteDAOImp;
import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador para a página de perfil do cliente.
 *
 * Esta classe gere a interface gráfica da página de perfil do utilizador,
 * exibindo as informações do cliente e fornecendo opções para alterar a palavra-passe
 * ou voltar ao menu principal.
 */
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
    private Button btnDelete;

    private ClienteDAO clienteDAO = new ClienteDAOImp();


    /**
     * Inicializa a página de perfil carregando as informações do cliente.
     *
     * Obtém os dados do cliente autenticado através do {@link ClientSingleton}
     * e exibe o nome e o e-mail na interface gráfica.
     */
    @FXML
    public void initialize() {
        Client client = ClientSingleton.getInstance().getClient();
        txtNome.setText(client.getName());
        txtEmail.setText(client.getEmail());
    }

    /**
     * Manipula o evento de clique no botão "Voltar".
     *
     * Redireciona o utilizador de volta para o menu principal.
     *
     * @param event O evento de clique no botão.
     */
    @FXML
    void onClickVoltar(ActionEvent event) {
        Stage s = (Stage) btnVoltar.getScene().getWindow();
        RedirecionarHelper.GotoHomeClient().switchScene(s);
    }
    /**
     * Manipula o evento de clique no botão "Alterar Palavra-Passe".
     *
     * Redireciona o utilizador para a página de atualização da palavra-passe.
     *
     * @param event O evento de clique no botão.
     */
    @FXML
    void onCllickResetPassword(ActionEvent event) {
        Stage s = (Stage) btnResetPassword.getScene().getWindow();
        RedirecionarHelper.GotoUpdatePasswordClient().switchScene(s);
    }
    /**
     * Manipula o evento de clique no botão "Apagar Conta".
     *
     * Redireciona o utilizador para a página do login após confirmar apagar conta.
     *
     * @param event O evento de clique no botão.
     */
    @FXML
    void onClickDelete(ActionEvent event) {
        try {
            Client client = ClientSingleton.getInstance().getClient();
            clienteDAO.removeClient(client.getId());


        } catch (IOException e) {
            System.out.println("Erro ao apagar a conta");
        }


    }
}
