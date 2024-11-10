package com.example.oporto_olympics.Controllers.EventosOlimpicos;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Evento;
import com.example.oporto_olympics.Models.Local;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Controlador para a inserção de eventos olímpicos.
 * Este controlador gerencia a interação do utilizador para criar novos eventos,
 * incluindo a seleção de locais, a inserção de dados relevantes e o carregamento
 * de imagens de logotipos e mascotes.
 */
public class InserirEventosOlimpicosController {

    @FXML
    private Button CriarEventoButton;

    @FXML
    private Button LocalRedirectButton;

    @FXML
    private Button LogoButton;

    @FXML
    private Button MascoteButton;

    @FXML
    private Button VoltarButton;

    @FXML
    private TextField anoedicaoField;

    @FXML
    private ComboBox<String> localCombo;

    @FXML
    private Label logoURL;

    @FXML
    private Label mascoteURL;

    @FXML
    private TextField paisField;

    @FXML
    private Label tituloEvento;

    /**
     * Inicializa o controlador. Este metodo é chamado quando a interface é carregada.
     * Estabelece a conexão com a base de dados e preenche a ComboBox com os locais disponíveis.
     *
     * @throws SQLException se ocorrer um erro ao conectar à base de dados
     */
    @FXML
    public void initialize() throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();
        LocaisDAOImp locaisDAO = new LocaisDAOImp(conexao);

        // Adicionar os nomes dos locais à ComboBox
        locaisDAO.getAll().forEach(local -> localCombo.getItems().add(local.getNome()));

        // Permitir apenas números no campo anoedicaoField
        anoedicaoField.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d*")) ? change : null
        ));

        // Limitar o paisField para 3 caracteres
        paisField.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().length() <= 3) ? change : null
        ));
    }

    // Função que verifica se o campo é vazio ou contém apenas espaços
    private boolean isBlankOrEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * Metodo chamado quando o botão "Criar Evento" é clicado.
     * Valida os dados de entrada e cria um novo evento se tudo estiver correto.
     *
     * @param event o evento gerado pelo clique do botão
     * @throws IOException se ocorrer um erro ao ler as imagens
     * @throws SQLException se ocorrer um erro ao conectar à base de dados
     */
    @FXML
    void OnClickCriarEventoButton(ActionEvent event) throws IOException, SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();
        LocaisDAOImp locaisDAO = new LocaisDAOImp(conexao);
        EventosDAOImp eventosDAO = new EventosDAOImp(conexao);

        String nomeLocal = String.valueOf(localCombo.getValue());
        Optional<Local> localSelecionado = locaisDAO.getAll().stream()
                .filter(local -> local.getNome().equals(nomeLocal))
                .findFirst();

        if (!localSelecionado.isPresent()) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Local Inválido", "O local selecionado não é válido.");
            AH1.getAlert().show();
            return;
        }

        int localId = localSelecionado.get().getId();
        String pais = paisField.getText();
        int anoEdicao;

        try {
            anoEdicao = Integer.parseInt(anoedicaoField.getText());
        } catch (NumberFormatException e) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Ano de Edição Inválido", "O ano de edição deve ser um número.");
            AH1.getAlert().show();
            return;
        }

        if (pais.trim().isEmpty() || logoURL.getText().trim().isEmpty() || mascoteURL.getText().trim().isEmpty()) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Dados Não Preenchidos", "É necessário preencher todos os dados e selecionar as imagens.");
            AH1.getAlert().show();
            return;
        }

        // Verificar se já existe um evento com o mesmo ano de edição
        if (eventosDAO.existsByAnoEdicao(anoEdicao)) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Ano de Edição Duplicado", "Já existe um evento com o mesmo ano de edição.");
            AH1.getAlert().show();
            return;
        }

        if (!eventosDAO.getSigla(pais)){
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Pais Inválido", "Insira a sigla de um País válido!");
            AH1.getAlert().show();
            return;
        }

        // Carregar as imagens
        File fileLogo = new File(logoURL.getText());
        File fileMascote = new File(mascoteURL.getText());
        byte[] logo = Files.readAllBytes(fileLogo.toPath());
        byte[] mascote = Files.readAllBytes(fileMascote.toPath());

        // Criar o evento
        Evento evento = new Evento(0, anoEdicao, pais, logo, mascote, localId);

        if (isBlankOrEmpty(String.valueOf(anoEdicao)) || isBlankOrEmpty(pais)) {
            AlertHandler AH1 = new AlertHandler(Alert.AlertType.ERROR, "Dados Não Preenchidos", "É necessário preencher todos os dados, para que possa criar um novo Evento.");
            AH1.getAlert().show();
            return;
        }

        AlertHandler confirmacao = new AlertHandler(Alert.AlertType.CONFIRMATION, "Criar um Evento?", "Tem a certeza que deseja criar este Evento?");
        Optional<ButtonType> result = confirmacao.getAlert().showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                eventosDAO.save(evento);
                AlertHandler sucesso = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso!", "Evento criado com sucesso!");
                sucesso.getAlert().show();
            } catch (RuntimeException e) {
                AlertHandler erro = new AlertHandler(Alert.AlertType.ERROR, "Erro ao criar evento", e.getMessage());
                erro.getAlert().show();
            }
            System.out.println(anoEdicao);
            System.out.println(pais);
            System.out.println(logo);
            System.out.println(mascote);
            System.out.println(localId);
        }
    }

    /**
     * Metodo chamado quando o botão para selecionar o logotipo é clicado.
     * Permite ao utilizador escolher uma imagem do logotipo a partir do sistema de ficheiros.
     *
     * @param event o evento gerado pelo clique do botão
     */
    @FXML
    void onClickLogoButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione uma imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                ImageView imgview = new ImageView(image);
                imgview.setFitHeight(150);
                imgview.setFitWidth(150);
                LogoButton.setGraphic(imgview);
                LogoButton.setText("");
                logoURL.setText(selectedFile.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo chamado quando o botão para selecionar a mascote é clicado.
     * Permite ao utilizador escolher uma imagem da mascote a partir do sistema de ficheiros.
     *
     * @param event o evento gerado pelo clique do botão
     */
    @FXML
    void onClickMascoteButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione uma imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                ImageView imgview = new ImageView(image);
                imgview.setFitHeight(150);
                imgview.setFitWidth(150);
                MascoteButton.setGraphic(imgview);
                MascoteButton.setText("");
                mascoteURL.setText(selectedFile.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onClickLocalRedirectButton(ActionEvent event) {
        Stage s = (Stage) LocalRedirectButton.getScene().getWindow();
        RedirecionarHelper.GotoInserirLocal().switchScene(s);
    }

    @FXML
    void OnClickVoltarButton(ActionEvent event) {
        Stage s = (Stage) VoltarButton.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
