package com.example.oporto_olympics.Controllers.Gestor;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Gestor.InserirGestorDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controlador responsável pela criação de um novo gestor.
 * Esta classe lida com a interface gráfica de inserção de dados de um gestor,
 * incluindo validação dos campos e armazenamento dos dados na base de dados.
 */
public class InserirGestorController {
    /**
     * Botão para criar um novo gestor.
     */
    @FXML
    private Button CriarGestorButton;
    /**
     * Campo de texto para introdução do nome.
     */
    @FXML
    private TextField Nome;
    /**
     * Botão para voltar.
     */
    @FXML
    private Button VoltarButton;
    /**
     * Instância do objeto responsável pela inserção de dados do gestor.
     */
    private InserirGestorDAOImp dao;

    /**
     * Método de inicialização do controlador, executado automaticamente após o carregamento da interface FXML.
     * Este método configura a conexão com a base de dados e inicializa a instância de DAO.
     *
     */
    @FXML
    void initialize() {

        try {
            ConnectionBD conexaoBD = ConnectionBD.getInstance();
            Connection conexao = conexaoBD.getConexao();

            if (conexao == null) {
                System.out.println("Conexão com a base de dados falhou!");
                return;
            } else {
                System.out.println("Conexão com a base de dados bem-sucedida!");
                dao = new InserirGestorDAOImp(conexao);
            }
        }catch (SQLException exception) {
            System.out.println("Ligação falhou: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar à base de dados: " + exception.getMessage());
            alert.show();
        }
    }

    /**
     * Método associado ao botão de criação de gestor. Este método é acionado ao clicar no botão de "Criar Gestor"
     * na interface gráfica, obtendo os valores inseridos pelo utilizador nos campos de texto e de seleção.
     * Realiza a validação dos dados, conversões de tipo necessárias, e em seguida invoca o método do DAO
     * para armazenar os dados do gestor na base de dados.
     *
     * Fluxo do método:
     * 1. Obtém os valores inseridos nos campos da interface.
     * 2. Valida se todos os campos obrigatórios estão preenchidos.
     * 3. Se todos os dados forem válidos, invoca o método `saveGestor` do DAO para guardar o gestor.
     * 4. Exibe uma mensagem de sucesso ou erro consoante o resultado da operação.
     *
     *
     */
    @FXML
    public void OnClickCriarGestorButton() {
        String nome = Nome.getText();

        if (nome.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos obrigatórios", "Todos os campos devem ser preenchidos.");
            return;
        }

        try {

            dao.saveGestor(nome);

            refreshPage();


        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao criar gestor", "Erro ao salvar o gestor na base de dados.");
        }
    }
    /**
     * Reseta os campos do formulário de criação do gestor para os valores padrão.
     *
     */
    private void refreshPage() {
        Nome.clear();
    }

    @FXML
    void OnClickVoltarButton(ActionEvent event) {
        Stage s = (Stage) VoltarButton.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
    /**
     * Mostra um alerta com o tipo, título e mensagem especificados.
     * Este método é utilizado para informar o utilizador sobre o resultado de uma operação,
     * seja uma mensagem de sucesso, aviso ou erro.
     *
     * @param alertType O tipo de alerta a ser exibido, que define o ícone e o estilo da janela.
     *                  Pode ser `Alert.AlertType.INFORMATION`, `Alert.AlertType.WARNING` ou `Alert.AlertType.ERROR`.
     * @param title     O título da janela de alerta.
     * @param message   A mensagem de conteúdo do alerta que será exibida ao utilizador.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
