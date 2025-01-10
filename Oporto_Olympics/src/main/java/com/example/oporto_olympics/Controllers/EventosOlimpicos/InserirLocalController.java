package com.example.oporto_olympics.Controllers.EventosOlimpicos;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Local;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Controlador para a inserção de locais na aplicação de eventos olímpicos.
 * Esta classe é responsável por gerir a interface do utilizador e a lógica de
 * inserção de novos locais na base de dados.
 */
public class InserirLocalController {
    /**
     * Botão para inserir um novo local.
     */
    @FXML
    private Button InserirLocalButton;
    /**
     * Botão para voltar à tela anterior.
     */
    @FXML
    private Button VoltarButton;
    /**
     * Campo de texto para introdução do ano de construção do local.
     */
    @FXML
    private DatePicker anoconstrucaoPicker;
    /**
     * Rótulo para mostrar o ano de construção do local.
     */
    @FXML
    private Label anoconstrucaoLabel;
    /**
     * Campo de texto para introdução da capacidade do local.
     */
    @FXML
    private TextField capacidadeField;
    /**
     * Rótulo para mostrar a capacidade do local.
     */
    @FXML
    private Label capacidadeLabel;
    /**
     * Campo de texto para introdução da cidade onde o local está situado.
     */
    @FXML
    private TextField cidadeField;
    /**
     * Campo de texto para introdução da morada do local.
     */
    @FXML
    private TextField moradaField;
    /**
     * Campo de texto para introdução do nome do local.
     */
    @FXML
    private TextField nomeField;
    /**
     * Campo de texto para introdução do país onde o local está situado.
     */
    @FXML
    private TextField paisField;
    /**
     * Caixa de combinação para selecionar o tipo de local.
     */
    @FXML
    private ComboBox<String> tipolocalCombo;
    /**
     * Rótulo para mostrar o título do local.
     */
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
        anoconstrucaoPicker.setVisible(false);
        capacidadeLabel.setVisible(false);
        anoconstrucaoLabel.setVisible(false);

        // Listener para monitorar mudanças na seleção da ComboBox
        tipolocalCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("interior".equals(newValue)) {
                // Torna os campos visíveis se a opção for "interior"
                capacidadeField.setVisible(true);
                anoconstrucaoPicker.setVisible(true);
                capacidadeLabel.setVisible(true);
                anoconstrucaoLabel.setVisible(true);
            } else {
                // Torna os campos invisíveis se a opção for "exterior"
                capacidadeField.clear();
                anoconstrucaoPicker.setValue(null);
                capacidadeField.setVisible(false);
                anoconstrucaoPicker.setVisible(false);
                capacidadeLabel.setVisible(false);
                anoconstrucaoLabel.setVisible(false);
            }
        });

        // Configurar os TextFields para aceitar apenas números
        setNumericTextField(capacidadeField);

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

    // Função que verifica se o campo é vazio ou contém apenas espaços
    private boolean isBlankOrEmpty(String text) {
        return text == null || text.trim().isEmpty();
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

        String Tipo = tipolocalCombo.getValue() != null ? tipolocalCombo.getValue() : "";
        String Nome = nomeField.getText().trim();
        String Morada = moradaField.getText().trim();
        String Cidade = cidadeField.getText().trim();
        String Pais = paisField.getText().trim();

        if (isBlankOrEmpty(Tipo) || isBlankOrEmpty(Nome) || isBlankOrEmpty(Morada) || isBlankOrEmpty(Cidade) || isBlankOrEmpty(Pais)) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Dados Não Preenchidos", "É necessário preencher todos os dados, para que possa inserir um novo Local.");
            AH1.getAlert().show();
            return;
        }

        Integer Capacidade = 0;
        if (!capacidadeField.getText().isEmpty()) {
            Capacidade = Integer.parseInt(capacidadeField.getText());
        }

        LocalDate AnoPicker = anoconstrucaoPicker.getValue();
        Date Ano_construcao = null;
        if ("interior".equalsIgnoreCase(Tipo)) {
            try {

                if (AnoPicker == null) {
                    AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Ano Construção Inválido", "Tem de inserir um ano de contrução...");
                    AH1.getAlert().show();
                    return;
                }

                Ano_construcao = Date.from(AnoPicker.atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (Ano_construcao.getYear() < 1000) {
                    AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Ano Construção Inválido", "O ano de construção deve ser superior a 1000!");
                    AH1.getAlert().show();
                    return;
                }

            } catch (IllegalArgumentException e) {
                AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Formato Inválido", "O formato da data de construção está inválido.");
                AH1.getAlert().show();
                return;
            }
        }

        Local L1 = new Local(0, Nome, Tipo, Morada, Cidade, Pais, Capacidade, Ano_construcao);

        LocaisDAOImp LDI1 = new LocaisDAOImp(conexao);

        if (LDI1.existsByLocal(Nome, Tipo, Morada, Cidade, Pais)) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Local Duplicado", "Este local já existe.");
            AH1.getAlert().show();
            return;
        }

        if (!LDI1.getSigla(Pais)) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Pais Inválido", "Insira a sigla de um País válido!");
            AH1.getAlert().show();
            return;
        }

        if ("interior".equalsIgnoreCase(Tipo) && Capacidade <= 0) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Capacidade Inválida", "Um local interior deve possuir uma capacidade superior a 0!");
            AH1.getAlert().show();
            return;
        }

        // Solicitar confirmação antes de salvar
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
        Stage s = (Stage) VoltarButton.getScene().getWindow();

        RedirecionarHelper.GotoInserirEventosOlimpicos().switchScene(s);
    }

}
