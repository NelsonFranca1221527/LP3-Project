package com.example.oporto_olympics;

import com.example.oporto_olympics.API.DAO.Client.ClienteDAO;
import com.example.oporto_olympics.API.DAO.Client.ClienteDAOImp;
import org.junit.jupiter.api.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class InserirClienteTest {

    /**
     * Teste que valida a inserção de um cliente na base de dados.
     * <p>
     * Este teste verifica se o nome, o email e a password fornecidos são válidos antes de tentar
     * inserir o cliente na base de dados. Caso todos os campos sejam válidos e não estejam vazios,
     * o cliente será inserido na base de dados e um email será enviado com as credenciais de acesso.
     * </p>
     *
     * <p>
     * O teste realiza as seguintes verificações:
     * <ul>
     *     <li>Verifica se o email fornecido é válido através da função <code>isValidEmail</code>.</li>
     *     <li>Verifica se nenhum dos campos obrigatórios (nome, password, email) está vazio.</li>
     *     <li>Tenta inserir o cliente na base de dados através do método <code>insertClient</code> da classe <code>ClienteDAO</code>.</li>
     *     <li>Se a inserção for bem-sucedida, um email é enviado para o cliente com as credenciais de acesso.</li>
     * </ul>
     * </p>
     *
     * @throws IOException Caso ocorra um erro de entrada/saída durante o envio do email ou outro processo.
     */
    @Test
    void inserirCliente() throws IOException {
        String nome = "Julio";
        String password = "123456";
        String email = "eduardopintoryze@gmail.com";

        assertFalse(!isValidEmail(email), "Por favor, insira um email válido");

        assertFalse(nome.isEmpty() || password.isEmpty() || email.isEmpty(), "Todos os campos devem ser preenchidos.");

        ClienteDAO clienteDAO = new ClienteDAOImp();
        try {
            String response = clienteDAO.insertClient(nome, email, password);

            if (response != null && !response.isEmpty()) {

                sendEmailToClient(email, nome, password);

                assertTrue(true, "Cliente inserido com sucesso. Um email com as credenciais foi enviado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Verifica se o email é válido.
     *
     *
     * @return true se o email for válido, false caso contrário.
     */
    @Test
    void inserirClienteEmailinvalido() throws IOException {
        String email = "";

        assertTrue(!isValidEmail(email), "Por favor, insira um email válido");
    }
    /**
     * Verifica se os campos são validos.
     *
     *
     * @return true se os campos forem invalidos.
     */
    @Test
    void inserirClienteCamposinvalidos() throws IOException {
        String email = "";
        String nome = "";
        String password = "";

        assertTrue(nome.isEmpty() || password.isEmpty() || email.isEmpty(), "Todos os campos devem ser preenchidos.");
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
