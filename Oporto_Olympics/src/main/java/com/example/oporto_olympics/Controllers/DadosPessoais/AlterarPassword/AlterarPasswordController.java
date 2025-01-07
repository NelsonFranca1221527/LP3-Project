package com.example.oporto_olympics.Controllers.DadosPessoais.AlterarPassword;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.UserDAO.UserDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Misc.Encriptacao;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Gestor;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Controlador para a interface de alteração de palavra-passe.
 *
 * Esta classe gere os eventos e ações relacionados com a alteração de palavra-passe
 * para utilizadores do tipo Atleta e Gestor. Inclui validação de campos, encriptação
 * da palavra-passe e interação com a base de dados.
 */
public class AlterarPasswordController {

    @FXML
    private PasswordField ConfirmPasswordField;

    @FXML
    private PasswordField NewPasswordField;

    @FXML
    private Button ChangePasswordBtn;

    @FXML
    private Button VoltarBtn;

    /**
     * Método acionado ao clicar no botão para alterar a palavra-passe.
     *
     * Este método verifica se os campos de nova palavra-passe e confirmação estão preenchidos
     * e se coincidem. Caso estejam válidos, a nova palavra-passe é encriptada e atualizada no
     * registo correspondente do utilizador na base de dados. O método suporta tanto utilizadores
     * do tipo Atleta quanto Gestor.
     *
     * @throws NoSuchAlgorithmException se ocorrer um erro durante o processo de encriptação da palavra-passe.
     * @throws RuntimeException se ocorrer um erro de SQL durante a interação com a base de dados.
     */
    @FXML
    void onAlterarPasswordClick() throws NoSuchAlgorithmException {
        try {
            ConnectionBD connectionBD = ConnectionBD.getInstance();
            Connection conexao = connectionBD.getConexao();

            Stage s = (Stage) ChangePasswordBtn.getScene().getWindow();

            UserDAOImp dao = new UserDAOImp(conexao);
            AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();
            GestorSingleton GestorSingle = GestorSingleton.getInstance();

            Gestor gestor = GestorSingle.getGestor();
            Atleta atleta = AtletaSingle.getAtleta();
            Encriptacao encrypt = new Encriptacao();

            if (!NewPasswordField.getText().trim().isEmpty() && !ConfirmPasswordField.getText().trim().isEmpty()){

                if (!NewPasswordField.getText().trim().isEmpty() || !ConfirmPasswordField.getText().trim().isEmpty()) {
                    String SenhaEncriptada = encrypt.StringtoHash(NewPasswordField.getText());

                    if (atleta != null) {
                        if (NewPasswordField.getText().equals(ConfirmPasswordField.getText())) {
                            if(!dao.VerificarPasswordIgual(SenhaEncriptada, atleta.getId())){
                                dao.UpdatePassword(atleta.getId(), SenhaEncriptada);
                                RedirecionarHelper.GotoDadosPessoais().switchScene(s);
                            }
                        } else {
                            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", "Verifique se as passwords estão iguais..");
                            alertHandler.getAlert().show();
                        }
                    }

                    if (gestor != null) {
                        if (NewPasswordField.getText().equals(ConfirmPasswordField.getText())) {
                            if(!dao.VerificarPasswordIgual(SenhaEncriptada, gestor.getId())){
                                dao.UpdatePassword(gestor.getId(), SenhaEncriptada);
                                RedirecionarHelper.GotoDadosPessoaisGestor().switchScene(s);
                            }
                        } else {
                            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", "Verifique se as passwords estão iguais..");
                            alertHandler.getAlert().show();
                        }
                    }
                }
            } else {
                AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", "Preencha os campos vazios..");
                alertHandler.getAlert().show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Manipulador de eventos para o botão "Voltar".
     * <p>
     * Este método é invocado quando o utilizador clica no botão "Voltar".
     * Redireciona o utilizador para o menu principal do atleta ou gestor utilizando
     * a classe {@code RedirecionarHelper}.
     */
    @FXML
    void onVoltarClick(){
        Stage s = (Stage) VoltarBtn.getScene().getWindow();

        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();
        GestorSingleton GestorSingle = GestorSingleton.getInstance();

        Gestor gestor = GestorSingle.getGestor();
        Atleta atleta = AtletaSingle.getAtleta();

        if (atleta != null){
            RedirecionarHelper.GotoDadosPessoais().switchScene(s);
        }

        if (gestor != null){
            RedirecionarHelper.GotoDadosPessoaisGestor().switchScene(s);
        }
    }

}
