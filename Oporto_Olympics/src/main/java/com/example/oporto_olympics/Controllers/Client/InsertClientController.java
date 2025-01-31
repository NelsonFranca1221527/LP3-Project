package com.example.oporto_olympics.Controllers.Client;

import com.example.oporto_olympics.API.DAO.Client.ClienteDAO;
import com.example.oporto_olympics.API.DAO.Client.ClienteDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Controlador da interface de inserção de clientes.
 *
 * Esta classe é responsável por gerenciar a interface gráfica para a inserção de um novo cliente,
 * incluindo os campos de entrada para os dados do cliente, como nome, e-mail e senha, além dos botões
 * para submeter os dados ou voltar à tela anterior.
 */
public class InsertClientController {
    /**
     * Campo de texto para inserção do e-mail do cliente.
     *
     * Este campo permite que o utilizador insira o e-mail do cliente que será adicionado.
     */
    @FXML
    private TextField txtEmail;
    /**
     * Campo de texto para inserção do nome do cliente.
     *
     * Este campo permite que o utilizador insira o nome do cliente que será adicionado.
     */
    @FXML
    private TextField txtName;
    /**
     * Campo de texto para inserção da senha do cliente.
     *
     * Este campo permite que o utilizador insira a senha do cliente.
     */
    @FXML
    private TextField txtpass;
    /**
     * Botão de navegação para voltar à tela anterior.
     *
     * Este botão é utilizado para retornar à tela anterior sem salvar os dados inseridos.
     */
    @FXML
    private Button VoltarBtn;
    /**
     * Botão para submeter o formulário de inserção do cliente.
     *
     * Este botão é utilizado para submeter os dados inseridos pelo utilizador e
     * adicionar um novo cliente.
     */
    @FXML
    private Button btnInsertClient;
    /**
     * Botão para gerar um valor de senha ou realizar alguma operação relacionada com a senha.
     *
     * Este botão pode ser utilizado, por exemplo, para gerar automaticamente uma senha segura
     * ou realizar outra ação relacionada com a criação da senha do cliente.
     */
    @FXML
    private Button btnGerar;

    /**
     * Evento para o botão "Voltar". Este método redireciona o utilizador para o menu de inserções.
     */
    @FXML
    void OnVoltarButtonClick(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();
        RedirecionarHelper.GotoSubMenuCliente().switchScene(s);
    }

    /**
     * Gera automaticamente uma senha de até 6 caracteres e preenche o campo de senha.
     */
    @FXML
    void onClickGerarPassword(ActionEvent event) {
        String generatedPassword = generatePassword(6);
        txtpass.setText(generatedPassword);
    }

    /**
     * Insere o cliente chamando a API e envia um email com as credenciais.
     */
    @FXML
    void onClickInserClient(ActionEvent event) {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtpass.getText().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro na validação",
                    "Todos os campos devem ser preenchidos.");
            alertHandler.getAlert().show();
            return;
        }

        if (!isValidEmail(email)) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro no email",
                    "Por favor, insira um email válido.");
            alertHandler.getAlert().show();
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAOImp();
        try {
            String response = clienteDAO.insertClient(name, email, password);

            if (response != null && !response.isEmpty()) {

                sendEmailToClient(email,name, password);

                AlertHandler alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso",
                        "Cliente inserido com sucesso. Um email com as credenciais foi enviado.");
                alertHandler.getAlert().show();

                txtName.clear();
                txtEmail.clear();
                txtpass.clear();
            } else {
                AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro",
                        "Falha ao inserir o cliente. Por favor, tente novamente.");
                alertHandler.getAlert().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro",
                    "Ocorreu um erro ao processar a solicitação.");
            alertHandler.getAlert().show();
        }
    }

    /**
     * Gera uma senha aleatória com o comprimento máximo especificado.
     *
     * @param maxLength Comprimento máximo da senha.
     * @return Uma senha gerada aleatoriamente.
     */
    private String generatePassword(int maxLength) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        int length = (int) (Math.random() * maxLength) + 1; // Comprimento entre 1 e maxLength
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    /**
     * Verifica se o email é válido.
     *
     * @param email Email a ser validado.
     * @return true se o email for válido, false caso contrário.
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    /**
     * Envia um email ao cliente com as credenciais de login.
     *
     * @param email    Email do cliente.
     * @param password Senha gerada para o cliente.
     */
    private void sendEmailToClient(String email,String Nome, String password) {
        final String smtpHost = "mail.smtp2go.com";
        final int smtpPort = 2525;
        final String systemEmail = "OPortoOlympics";
        final String systemPassword = "Cj7po5Tzqwf5f6SK";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(systemEmail, systemPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lp3g4projooportoolympics@outlook.com   "));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Bem-vindo à Oporto Olympics!");
            message.setText("Olá " + Nome    + ",\n\nAs suas credenciais foram criadas com sucesso!\n\n" +
                    "Email: " + email + "\nSenha: " + password + "\n\n" +
                    "Por favor, altere a senha após o primeiro login.\n\nObrigado.");

            Transport.send(message);

            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erro no envio do e-mail.");
        }
    }
}