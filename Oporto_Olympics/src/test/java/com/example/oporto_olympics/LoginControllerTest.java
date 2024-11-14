package com.example.oporto_olympics;

import com.example.oporto_olympics.Controllers.Login.LoginController;
import com.example.oporto_olympics.DAO.UserDAO.UserDAOImp;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserDAOImp userDAO;

    @Mock
    private TextField UserField;

    @Mock
    private PasswordField SenhaField;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void onLoginButtonClick() throws NoSuchAlgorithmException, SQLException {
        // Cenário 1: Verificar login com campos vazios
        when(UserField.getText()).thenReturn("");
        when(SenhaField.getText()).thenReturn("");

        // Executa o método
        loginController.OnLoginButtonClick();


        // Cenário 2: Verificar login para role "Gestor"
        when(UserField.getText()).thenReturn("10000024");
        when(SenhaField.getText()).thenReturn("10000024");

        String SenhaHash = StringtoHash(SenhaField.getText());

        when(userDAO.getUserType(10000024, SenhaHash)).thenReturn("Gestor");
        when(userDAO.getUser(10000024, SenhaHash)).thenReturn(true);

        // Executa o método
        loginController.OnLoginButtonClick();

        // Cenário 3: Verificar login para role "Atleta"
        when(UserField.getText()).thenReturn("10000000");
        when(SenhaField.getText()).thenReturn("10000000");

        SenhaHash = StringtoHash(SenhaField.getText());

        when(userDAO.getUserType(10000000, SenhaHash)).thenReturn("Atleta");
        when(userDAO.getUser(10000000, SenhaHash)).thenReturn(true);

        // Executa o método
        loginController.OnLoginButtonClick();

    }

    /**
     *
     * Encripta dados recebidos em hash ("SHA-256")
     *
     * @param dado Recebe o dado para ser encriptado
     * @return Retorna o dado encriptado
     * @throws NoSuchAlgorithmException se o algortimo para encriptação não está disponível.
     */

    public String StringtoHash(String dado) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hashdado = md.digest(dado.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashdado) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();

    }
}