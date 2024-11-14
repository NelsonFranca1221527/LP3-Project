package com.example.oporto_olympics.Controllers.ListagemAtletas.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Historico.HistoricoDAOImp;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Resultados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.lang.model.AnnotatedConstruct;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador responsável por gerir os dados de um atleta exibidos em um card.
 * Esta classe preenche os rótulos de um card com as informações de um atleta.
 */
public class ListAtletaCardController {
    /**
     * Objeto Atleta, armazenado para acesso ao seu id no método btnOnClickDetalhes.
     */
    private Atleta atleta;

    @FXML
    private Label NomeLabel;

    @FXML
    private Label DataNascLabel;

    @FXML
    private Label PaisLabel;

    @FXML
    private Label GeneroLabel;

    @FXML
    private Label AlturaLabel;

    @FXML
    private Label PesoLabel;

    @FXML
    private Button btnDetalhes;

    /**
     * Preenche os dados do atleta nos rótulos correspondentes e armazena o objeto atleta.
     *
     * @param atleta O objeto {@link Atleta} contendo os dados a serem preenchidos.
     */
    public void PreencherDados(Atleta atleta) {
        this.atleta = atleta; // Armazena o objeto atleta

        NomeLabel.setText(atleta.getNome());
        DataNascLabel.setText(String.valueOf(atleta.getDataNascimento()));
        PaisLabel.setText(atleta.getPais());
        GeneroLabel.setText(atleta.getGenero());
        AlturaLabel.setText(String.valueOf(atleta.getAltura()));
        PesoLabel.setText(String.valueOf(atleta.getPeso()));
    }
    /**
     * Método acionado ao clicar no botão de detalhes. Este método recupera o histórico de competições de um atleta,
     * e exibe-o numa nova janela. Caso o atleta não tenha histórico, uma mensagem informativa é exibida.
     *
     * @param event O evento de clique no botão que chama este método.
     * @throws SQLException Caso haja falhas ao recuperar os dados do banco de dados.
     */
    @FXML
    private void btnOnClickDetalhes(ActionEvent event) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        if (atleta == null) {
            throw new IllegalStateException("Atleta não foi inicializado.");
        }

        Stage stageAtual = (Stage) btnDetalhes.getScene().getWindow();

        int atletaId = atleta.getId();
        HistoricoDAOImp AtletaDAO = new HistoricoDAOImp(conexao);

        List<Resultados> historico = AtletaDAO.getResultAtleta(atletaId);

        if (historico.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sem Histórico");
            alert.setHeaderText(null);
            alert.setContentText("O atleta não possui histórico.");
            alert.showAndWait();
        } else {
            Stage novoStage = new Stage();
            novoStage.setTitle("Histórico do Atleta");

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(15));

            Label tituloLabel = new Label("Histórico de Competições");
            tituloLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            layout.getChildren().add(tituloLabel);

            ListView<String> listView = new ListView<>();
            for (Resultados resultado : historico) {
                String detalhes = String.format("Ano: %d | Medalhas: Ouro: %d, Prata: %d, Bronze: %d",
                        resultado.getAno(), resultado.getMedalhas_ouro(),
                        resultado.getMedalhas_prata(), resultado.getMedalhas_bronze());
                listView.getItems().add(detalhes);
            }

            layout.getChildren().add(listView);

            Scene scene = new Scene(layout, 400, 300);
            novoStage.setScene(scene);
            novoStage.initModality(Modality.WINDOW_MODAL);
            novoStage.initOwner(stageAtual);
            novoStage.show();
        }
    }
}
