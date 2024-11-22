package com.example.oporto_olympics.Controllers.Equipas;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Equipas.ListarEquipasDAO;
import com.example.oporto_olympics.DAO.Equipas.ListarEquipasDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.AtletaInfo;
import com.example.oporto_olympics.Models.InscricaoEquipas;
import com.example.oporto_olympics.Models.ResultadosEquipa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
/**
 * Controlador responsável pela listagem das equipas.
 * Esta classe é encarregada de listar as equipas carregadas na base de dados e exibi-las na interface gráfica.
 * Também permite visualizar detalhes adicionais sobre cada equipa.
 */
public class ListarEquipaController {
    /**
     * Contêiner de layout para exibição das equipas.
     */
    @FXML
    private VBox vboxEquipas;
    /**
     * Botão para voltar à tela anterior.
     */
    @FXML
    private Button VoltarButton;
    /**
     * Instância do objeto responsável pela listagem das equipas.
     */
    private ListarEquipasDAO dao;

    @FXML
    private TextField searchfield;

    /**
     * Método chamado durante a inicialização do controlador.
     * Este método estabelece uma ligação à base de dados e, caso a ligação seja bem-sucedida,
     * cria uma instância do DAO (Data Access Object) para listar as equipas e mostra na interface gráfica.
     * Caso a ligação à base de dados falhe, é mostra uma mensagem de erro na consola e um alerta na interface.
     */
    @FXML
    public void initialize() {
        try {
            ConnectionBD conexaoBD = ConnectionBD.getInstance();
            Connection conexao = conexaoBD.getConexao();

            if (conexao == null) {
                System.out.println("Conexão com a base de dados falhou!");
                return;
            } else {
                System.out.println("Conexão com a base de dados bem-sucedida!");
            }

            dao = new ListarEquipasDAOImp(conexao);

            // Configurar listener para o campo de pesquisa
            configurarSearchField();

            // Listar equipas inicialmente (sem filtro)
            listarEquipas(null);


        } catch (SQLException exception) {
            System.out.println("Ligação falhou: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar à base de dados: " + exception.getMessage());
            alert.show();
        }
    }

    /**
     * Configura o listener para o campo de texto de pesquisa.
     */
    private void configurarSearchField() {
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            listarEquipas(newValue.trim()); // Atualizar a lista conforme o texto digitado
        });
    }
    /**
     * Lista as equipas com base no texto de pesquisa.
     *
     * @param filtroNome Nome parcial da equipa para filtrar; se nulo ou vazio, lista todas as equipas.
     */
    public void listarEquipas(String filtroNome) {
        try {
            List<InscricaoEquipas> equipas = dao.getEquipas(filtroNome);
            System.out.println("Número total de equipas carregadas: " + equipas.size());

            vboxEquipas.getChildren().clear();

            if (equipas.isEmpty()) {
                Label noResultsLabel = new Label("Nenhuma equipa encontrada.");
                noResultsLabel.setStyle("-fx-font-size: 16px; -fx-font-style: italic; -fx-text-fill: grey; -fx-padding: 10 0 0 10;");
                vboxEquipas.getChildren().add(noResultsLabel);
                return;
            }

            for (InscricaoEquipas equipa : equipas) {
                HBox teamPane = new HBox(10);
                teamPane.setPrefWidth(vboxEquipas.getPrefWidth());
                teamPane.setStyle("-fx-background-color: #bab8b8; -fx-padding: 10; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;");

                Label nomeLabel = new Label(equipa.getNome());
                Label desportoLabel = new Label("Desporto: " + equipa.getDesporto());
                Label generoLabel = new Label("Género: " + equipa.getGenero());

                nomeLabel.setStyle("-fx-font-weight: bold;");
                desportoLabel.setStyle("-fx-font-size: 12px;");
                generoLabel.setStyle("-fx-font-size: 12px;");

                teamPane.getChildren().addAll(nomeLabel, desportoLabel, generoLabel);

                Button btnDetalhes = criarBotaoDetalhes(equipa);
                Button btnHistorico = criarBotaoHistorico(equipa);
                Button btnAtletas = criarBotaoAtletas(equipa);

                teamPane.getChildren().addAll(btnDetalhes, btnHistorico, btnAtletas);

                vboxEquipas.getChildren().add(teamPane);
            }


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar as equipas: " + e.getMessage());
            alert.show();
        }
    }
    /**
     * Cria o botão "Ver Detalhes" para uma equipa.
     *
     * @param equipa A equipa cujos detalhes serão exibidos.
     * @return O botão configurado.
     */
    private Button criarBotaoDetalhes(InscricaoEquipas equipa) {
        Button btnDetalhes = new Button("Ver Detalhes");
        btnDetalhes.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 0.5; -fx-border-radius: 4;");

        btnDetalhes.setOnAction(event -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle(equipa.getNome());

            VBox dialogContent = new VBox(10);
            dialogContent.setStyle("-fx-padding: 20; -fx-alignment: center-left;");

            dialogContent.getChildren().addAll(
                    new Label("Nome: " + equipa.getNome()),
                    new Label("Desporto: " + equipa.getDesporto()),
                    new Label("País: " + equipa.getPais_sigla()),
                    new Label("Ano de Fundação: " + equipa.getAno_fundacao()),
                    new Label("Gênero: " + equipa.getGenero())
            );

            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            dialog.showAndWait();
        });

        return btnDetalhes;
    }
    /**
     * Cria o botão "Consultar Histórico" para uma equipa.
     *
     * @param equipa A equipa cujo histórico será exibido.
     * @return O botão configurado.
     */
    private Button criarBotaoHistorico(InscricaoEquipas equipa) {
        Button btnHistorico = new Button("Consultar Histórico");
        btnHistorico.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 0.5; -fx-border-radius: 4;");

        btnHistorico.setOnAction(event -> {
            try {
                List<ResultadosEquipa> historico = dao.getHistorico(equipa.getId());

                if (historico.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nenhum histórico encontrado para esta equipa.");
                    alert.show();
                    return;
                }

                Dialog<ButtonType> historicoDialog = new Dialog<>();
                historicoDialog.setTitle("Histórico - " + equipa.getNome());

                GridPane historicoGrid = new GridPane();
                historicoGrid.setHgap(20);
                historicoGrid.setVgap(10);
                historicoGrid.setStyle("-fx-padding: 20; -fx-alignment: center-left;");

                historicoGrid.add(new Label("Ano"), 0, 0);
                historicoGrid.add(new Label("Resultado"), 1, 0);

                int row = 1;
                for (ResultadosEquipa resultado : historico) {
                    historicoGrid.add(new Label(String.valueOf(resultado.getAno())), 0, row);
                    historicoGrid.add(new Label(resultado.getResultado()), 1, row);
                    row++;
                }

                historicoDialog.getDialogPane().setContent(historicoGrid);
                historicoDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                historicoDialog.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar o histórico: " + ex.getMessage());
                alert.show();
            }
        });

        return btnHistorico;
    }
    /**
     * Cria o botão "Consultar Atletas" para uma equipa.
     *
     * @param equipa A equipa cujos atletas serão exibidos.
     * @return O botão configurado.
     */
    private Button criarBotaoAtletas(InscricaoEquipas equipa) {
        Button btnAtletas = new Button("Consultar Atletas");
        btnAtletas.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 0.5; -fx-border-radius: 4;");

        btnAtletas.setOnAction(event -> {
            try {
                List<AtletaInfo> atletas = dao.getAtletasByEquipaId(equipa.getId());

                if (atletas.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nenhum atleta encontrado para esta equipa.");
                    alert.show();
                    return;
                }

                Dialog<ButtonType> atletasDialog = new Dialog<>();
                atletasDialog.setTitle("Atletas - " + equipa.getNome());

                GridPane atletasGrid = new GridPane();
                atletasGrid.setHgap(20);
                atletasGrid.setVgap(10);
                atletasGrid.setStyle("-fx-padding: 20; -fx-alignment: center-left;");

                atletasGrid.add(new Label("Nome"), 1, 0);
                atletasGrid.add(new Label("Gênero"), 2, 0);
                atletasGrid.add(new Label("Altura"), 3, 0);
                atletasGrid.add(new Label("Peso"), 4, 0);

                int row = 1;
                for (AtletaInfo atleta : atletas) {
                    atletasGrid.add(new Label(atleta.getNome()), 1, row);
                    atletasGrid.add(new Label(atleta.getGenero()), 2, row);
                    atletasGrid.add(new Label(String.valueOf(atleta.getAltura())), 3, row);
                    atletasGrid.add(new Label(String.valueOf(atleta.getPeso())), 4, row);
                    row++;
                }

                atletasDialog.getDialogPane().setContent(atletasGrid);
                atletasDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                atletasDialog.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar os atletas: " + ex.getMessage());
                alert.show();
            }
        });

        return btnAtletas;
    }
    /**
     * Ação realizada quando o botão "Voltar" é clicado. Retorna à tela anterior.
     *
     * @param event o evento de ação
     */
    @FXML
    void OnClickVoltarButton(ActionEvent event) {
        Stage s = (Stage) VoltarButton.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
