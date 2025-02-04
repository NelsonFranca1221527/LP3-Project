package com.example.oporto_olympics.Controllers.Client;

import com.example.oporto_olympics.API.DAO.Client.ClienteDAO;
import com.example.oporto_olympics.API.DAO.Client.ClienteDAOImp;
import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Controlador para a funcionalidade de alteração de palavra-passe.
 *
 * Esta classe gere a interface gráfica para permitir que o utilizador altere a sua palavra-passe,
 * verificando a validade dos dados introduzidos antes de proceder à alteração.
 */
public class ChangePasswordController {

    @FXML
    private Button VoltarBtn;

    @FXML
    private PasswordField NewPasswordField;

    @FXML
    private PasswordField ConfirmPasswordField;

    @FXML
    private Button ChangePasswordBtn;

    /**
     * Instância do DAO de clientes.
     *
     * O {@link ClienteDAO} é usado para interagir com a base de dados ou
     * com o serviço de clientes, obtendo, inserindo ou removendo dados de
     * clientes conforme necessário.
     */
    private ClienteDAO clienteDAO = new ClienteDAOImp();

    /**
     * Manipula o evento de clique no botão de alteração de palavra-passe.
     *
     * Verifica se os campos foram preenchidos corretamente, validando se as palavras-passe
     * coincidem e se não contêm espaços. Caso contrário, exibe mensagens de erro apropriadas.
     * Se os dados forem válidos, a nova palavra-passe é enviada para atualização no sistema.
     */
    @FXML
    void onAlterarPasswordClick(){
        String newPassword = NewPasswordField.getText().trim();
        String confirmPassword = ConfirmPasswordField.getText().trim();


        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Erro", "Ambos os campos de senha são obrigatórios.", Alert.AlertType.ERROR);
            return;
        }

        if (newPassword.contains(" ") || confirmPassword.contains(" ")) {
            showAlert("Erro", "As senhas não podem conter espaços.", Alert.AlertType.ERROR);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Erro", "As senhas não coincidem.", Alert.AlertType.ERROR);
            return;
        }
        Client client = ClientSingleton.getInstance().getClient();
        String userId = client.getId();
        try {
            clienteDAO.UpdatePassword(userId, newPassword);
            Stage s = (Stage) VoltarBtn.getScene().getWindow();
            RedirecionarHelper.GotoProfileCliente().switchScene(s);
        } catch (IOException e) {
            showAlert("Erro", "Erro ao conectar ao servidor: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    /**
     * Manipula o evento de clique no botão de voltar.
     *
     * Redireciona o utilizador de volta para o perfil do cliente.
     *
     * @param event O evento de clique no botão.
     */
    @FXML
    void onVoltarClick(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();
        RedirecionarHelper.GotoProfileCliente().switchScene(s);
    }
    /**
     * Exibe um alerta na interface gráfica.
     *
     * @param title O título da janela de alerta.
     * @param message A mensagem a ser exibida.
     * @param alertType O tipo de alerta.
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
