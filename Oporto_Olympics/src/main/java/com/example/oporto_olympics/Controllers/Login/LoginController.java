package com.example.oporto_olympics.Controllers.Login;

import com.example.oporto_olympics.DAO.UserDAO.UserDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Misc.Encriptação;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private PasswordField SenhaField;

    @FXML
    private TextField UserField;

    @FXML
    private Button loginBtn;

    @FXML
    protected void OnLoginButtonClick() throws SQLException, NoSuchAlgorithmException {

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        if(UserField.getText().equals("") || SenhaField.getText().equals("") || SenhaField.getText().equals(" ")){
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro na autenticação.",
                    "Preencha todos os campos.");
            alertHandler.getAlert().show();
            return;
        }

        Encriptação Encrypt = new Encriptação();
        UserDAOImp UserDAO = new UserDAOImp(conexao);
        String SenhaHash = Encrypt.StringtoHash(SenhaField.getText());
        String Role = UserDAO.getUserType(Integer.parseInt(UserField.getText()), SenhaHash);


        if (UserDAO.getUser(Integer.parseInt(UserField.getText()), SenhaHash, Role)) {
            Stage s = (Stage) loginBtn.getScene().getWindow();

            switch (Role) {
                case "Gestor":
                    System.out.println("Entrou como gestor!!");
                    break;
                case "Atleta":
                    System.out.println("Entrou como atleta!!");
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