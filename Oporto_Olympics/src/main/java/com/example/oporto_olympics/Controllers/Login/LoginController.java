package com.example.oporto_olympics.Controllers.Login;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.Gestor;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.DAO.UserDAO.UserDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Misc.Encriptacao;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Controlador da interface de utilizador para a funcionalidade de login. Esta classe é responsável por gerir o fluxo
 * de autenticação de utilizadores, incluindo a validação das credenciais e o redirecionamento para a página correspondente
 * ao tipo de utilizador (gestor ou atleta) após um login bem-sucedido.
 */
public class LoginController {
    /**
     * Botão para iniciar sessão.
     */
    @FXML
    private Button loginBtn;
    /**
     * Campo de texto para introduzir a senha do utilizador.
     */
    @FXML
    private PasswordField SenhaField;
    /**
     * Campo de texto para introduzir o nome de utilizador.
     */
    @FXML
    private TextField UserField;

    /**
     *
     * 1. Estabelece a conexão a base de dados com a ConnectionBD.
     * 2. Verifica se o username (UserField) e password (SenhaField) se estão preenchidos.
     * 3. Encripta a password usando a classe Encriptacao.
     * 4. Verifica na base de dados se os dados do utilizador estão corretos.
     * 5. Redireciona o utilizador para a página correta consoante a sua role (ex.: "Gestor", "Atleta").
     *
     * @throws SQLException se o acesso da base de dados der erro.
     * @throws NoSuchAlgorithmException se o algortimo para a encriptação da password não está disponível.
     */
    @FXML
    public void OnLoginButtonClick() throws SQLException, NoSuchAlgorithmException {

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        if(UserField.getText().equals("") || SenhaField.getText().equals("") || SenhaField.getText().equals(" ")){
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro na autenticação.",
                    "Preencha todos os campos.");
            alertHandler.getAlert().show();
            return;
        }

        Encriptacao Encrypt = new Encriptacao();
        UserDAOImp UserDAO = new UserDAOImp(conexao);
        String SenhaHash = Encrypt.StringtoHash(SenhaField.getText());
        String Role = UserDAO.getUserType(Integer.parseInt(UserField.getText()), SenhaHash);

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();

        if(Role == "Gestor") {
            Gestor gestor = UserDAO.getGestorInfo(Integer.parseInt(UserField.getText()), SenhaHash);
            GestorSingle.setGestor(gestor);
        } else {
            Atleta atleta = UserDAO.getAtletaInfo(Integer.parseInt(UserField.getText()),SenhaHash);
            AtletaSingle.setAtleta(atleta);
        }

        if (UserDAO.getUser(Integer.parseInt(UserField.getText()), SenhaHash)) {
            Stage s = (Stage) loginBtn.getScene().getWindow();

            switch (Role) {
                case "Gestor":
                    try {
                        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Atleta":
                    try {
                        RedirecionarHelper.GotoMenuPrincipalAtleta().switchScene(s);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro", "Este utilizador não existe.");
                    alertHandler.getAlert().show();
                    break;
            }
        } else {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro na autenticação.", "Nome de utilizador e palavra-passe incorretos.");
            alertHandler.getAlert().show();
        }
    }

}