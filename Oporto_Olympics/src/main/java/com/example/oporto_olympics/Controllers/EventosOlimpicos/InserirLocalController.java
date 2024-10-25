package com.example.oporto_olympics.Controllers.EventosOlimpicos;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.Controllers.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Local;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Controlador para a inserção de locais na aplicação de eventos olímpicos.
 * Esta classe é responsável por gerir a interface do utilizador e a lógica de
 * inserção de novos locais na base de dados.
 */
public class InserirLocalController {

    @FXML
    private Button InserirLocalButton;

    @FXML
    private Button VoltarButton;

    @FXML
    private TextField anoconstrucaoField;

    @FXML
    private Label anoconstrucaoLabel;

    @FXML
    private TextField capacidadeField;

    @FXML
    private Label capacidadeLabel;

    @FXML
    private TextField cidadeField;

    @FXML
    private TextField moradaField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField paisField;

    @FXML
    private ComboBox<String> tipolocalCombo;

    @FXML
    private Label tituloLocal;

    /**
     * Inicializa os componentes da interface do utilizador.
     * Este metodo é chamado automaticamente após a inicialização do controlador.
     * Define os tipos de locais na ComboBox e configura os campos visíveis
     * com base na seleção do utilizador.
     */
    @FXML
    public void initialize() {
        tipolocalCombo.getItems().addAll("interior", "exterior");

        // Define os campos como invisíveis por padrão
        capacidadeField.setVisible(false);
        anoconstrucaoField.setVisible(false);
        capacidadeLabel.setVisible(false);
        anoconstrucaoLabel.setVisible(false);

        // Listener para monitorar mudanças na seleção da ComboBox
        tipolocalCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("interior".equals(newValue)) {
                // Torna os campos visíveis se a opção for "interior"
                capacidadeField.setVisible(true);
                anoconstrucaoField.setVisible(true);
                capacidadeLabel.setVisible(true);
                anoconstrucaoLabel.setVisible(true);
            } else {
                // Torna os campos invisíveis se a opção for "exterior"
                capacidadeField.clear();
                anoconstrucaoField.clear();
                capacidadeField.setVisible(false);
                anoconstrucaoField.setVisible(false);
                capacidadeLabel.setVisible(false);
                anoconstrucaoLabel.setVisible(false);
            }
        });

        // Configurar os TextFields para aceitar apenas números
        setNumericTextField(capacidadeField);
        setNumericTextField(anoconstrucaoField);

        // Configurar paisField para aceitar no máximo 3 caracteres
        paisField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 3 ? change : null));
    }

    /**
     * Configura um campo de texto para aceitar apenas números.
     *
     * @param textField O campo de texto a ser configurado.
     */
    private void setNumericTextField(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };
        textField.setTextFormatter(new TextFormatter<>(filter));
    }

    /**
     * Trata o evento de clique no botão para inserir um novo local.
     * Este metodo valida os dados inseridos pelo utilizador e,
     * caso sejam válidos, insere o novo local na base de dados.
     *
     * @param event O evento de clique no botão.
     * @throws IOException Se ocorrer um erro de entrada/saída.
     * @throws SQLException Se ocorrer um erro ao conectar à base de dados.
     */
    @FXML
    void OnClickInserirLocalButton(ActionEvent event) throws IOException, SQLException {

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        if (conexao == null || conexao.isClosed()) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Erro de Conexão", "Não foi possível conectar a base de dados.");
            AH1.getAlert().show();
            return;
        }

        String Tipo = String.valueOf(tipolocalCombo.getValue());
        String Nome = String.valueOf(nomeField.getText());
        String Morada = String.valueOf(moradaField.getText());
        String Cidade = String.valueOf(cidadeField.getText());
        String Pais = String.valueOf(paisField.getText());

        // Verificar se os campos estão vazios e definir as variáveis como null se necessário
        Integer Capacidade = capacidadeField.getText().isEmpty() ? 0 : Integer.valueOf(capacidadeField.getText());
        Integer Ano_construcao = anoconstrucaoField.getText().isEmpty() ? 0 : Integer.valueOf(anoconstrucaoField.getText());

        Local L1 = new Local(0, Nome, Tipo, Morada, Cidade, Pais, Capacidade, Ano_construcao);

        LocaisDAOImp LDI1 = new LocaisDAOImp(conexao);

        if ((Tipo.equals("") || Nome.equals("") || Morada.equals("") || Cidade.equals("") || Pais.equals("")) ||
                (Tipo.equals(" ") || Nome.equals(" ") || Morada.equals(" ") || Cidade.equals(" ") || Pais.equals(" "))) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Dados Não Preenchidos", "É necessário preencher todos os dados, para que possa inserir um novo Local.");
            AH1.getAlert().show();
            return;
        }

        // Verificar se o local já existe
        if (LDI1.existsByLocal(Nome, Tipo, Morada, Cidade, Pais)) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Local Duplicado", "Este local já existe.");
            AH1.getAlert().show();
            return;
        }

        AlertHandler AH2 = new AlertHandler(Alert.AlertType.CONFIRMATION, "Inserir um Local?", "Tem a certeza que deseja inserir este Local?");
        Optional<ButtonType> result = AH2.getAlert().showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                LDI1.save(L1);
                AlertHandler AH3 = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso !!", "Local Inserido com Sucesso !");
                AH3.getAlert().show();
            } catch (RuntimeException e) {
                AlertHandler erro = new AlertHandler(Alert.AlertType.ERROR, "Erro ao inserir Local", e.getMessage());
                erro.getAlert().show();
            }
        }
    }


    @FXML
    void OnClickVoltarButton(ActionEvent event) {

    }

}
