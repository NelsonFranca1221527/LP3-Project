package com.example.oporto_olympics.Controllers.Login;

import com.example.oporto_olympics.API.DAO.Jogos.ClienteDAO;
import com.example.oporto_olympics.API.DAO.Jogos.ClienteDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.Gestor;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.DAO.UserDAO.UserDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Misc.Encriptacao;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Controlador da interface de utilizador para a funcionalidade de login.
 * Responsável pela autenticação dos utilizadores, validando as credenciais
 * e redirecionando-os para o menu adequado, dependendo do seu tipo de utilizador (gestor, atleta ou cliente).
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
     * Inicializa o estado dos singletons {@link GestorSingleton}, {@link AtletaSingleton} e {@link ClientSingleton}.
     * Este método é automaticamente invocado pela JavaFX ao carregar a interface gráfica.
     *
     * Redefine os valores dos singletons para null, garantindo que os dados das sessões anteriores são limpos.
     *
     * @see GestorSingleton
     * @see AtletaSingleton
     * @see ClientSingleton
     */
    public void initialize() {
        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();
        ClientSingleton ClienteSingle = ClientSingleton.getInstance();

        GestorSingle.setGestor(null);
        AtletaSingle.setAtleta(null);
        ClienteSingle.setClient(null);
    }

    /**
     * Método chamado ao clicar no botão de login.
     * Este método realiza as seguintes ações:
     * <ul>
     *   <li>Estabelece uma ligação à base de dados utilizando {@link ConnectionBD}.</li>
     *   <li>Valida se os campos de nome de utilizador e senha foram preenchidos.</li>
     *   <li>Verifica se o nome de utilizador é um número mecanográfico ou um e-mail válido.</li>
     *   <li>Encripta a senha do utilizador e valida as credenciais na base de dados ou API.</li>
     *   <li>Redireciona o utilizador para o menu principal apropriado (gestor, atleta ou cliente) em caso de sucesso.</li>
     *   <li>Apresenta alertas em caso de erro ou credenciais inválidas.</li>
     * </ul>
     *
     * @throws SQLException se ocorrer um erro na ligação ou consulta à base de dados.
     * @throws NoSuchAlgorithmException se o algoritmo de encriptação de senha não estiver disponível.
     * @throws IOException se ocorrer um erro de comunicação com a API.
     */
    @FXML
    public void OnLoginButtonClick() throws SQLException, NoSuchAlgorithmException, IOException {

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        String userInput = UserField.getText().trim();
        String senhaInput = SenhaField.getText().trim();

        if (userInput.isEmpty() || senhaInput.isEmpty()) {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro na autenticação.",
                    "Preencha todos os campos.");
            alertHandler.getAlert().show();
            return;
        }

        boolean isNumeroMecanografico = userInput.matches("\\d+");
        boolean isEmail = userInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        if (isNumeroMecanografico) {

            Encriptacao Encrypt = new Encriptacao();
            UserDAOImp UserDAO = new UserDAOImp(conexao);

            String SenhaHash = Encrypt.StringtoHash(senhaInput);
            int numMecano = Integer.parseInt(userInput);

            String Role = UserDAO.getUserType(numMecano, SenhaHash);

            GestorSingleton GestorSingle = GestorSingleton.getInstance();
            AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();

            if (Role.equals("Gestor")) {
                Gestor gestor = UserDAO.getGestorInfo(numMecano, SenhaHash);
                GestorSingle.setGestor(gestor);
            } else if (Role.equals("Atleta")) {
                Atleta atleta = UserDAO.getAtletaInfo(numMecano, SenhaHash);
                AtletaSingle.setAtleta(atleta);
            } else {
                showInvalidCredentialsAlert();
                return;
            }

            if (UserDAO.getUser(numMecano, SenhaHash)) {
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
                        showInvalidCredentialsAlert();
                        break;
                }
            } else {
                showInvalidCredentialsAlert();
            }
        } else if (isEmail) {
            ClienteDAO clienteDAO = new ClienteDAOImp();
            Stage s = (Stage) loginBtn.getScene().getWindow();

            String response = clienteDAO.ValidateCredentials(userInput, senhaInput);

            if (response != null && !response.isEmpty()) {
                RedirecionarHelper.GotoHomeClient().switchScene(s);
            } else {
                AlertHandler alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Credenciais inválidas.",
                        "As credenciais que inseriu são inválidas!");
                alertHandler.getAlert().show();
            }
        } else {
            AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro na autenticação.",
                    "O nome de utilizador deve ser um número mecanográfico ou um e-mail válido.");
            alertHandler.getAlert().show();
        }
    }


    /**
     * Apresenta um alerta de erro para credenciais inválidas.
     */
    private void showInvalidCredentialsAlert() {
        AlertHandler alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro na autenticação.",
                "Nome de utilizador e palavra-passe incorretos.");
        alertHandler.getAlert().show();
    }

}