package com.example.oporto_olympics.Controllers.Equipas;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.Equipas.InscricaoEquipaDAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.InscricaonaEquipaDAOImp;
import com.example.oporto_olympics.Controllers.DAO.Equipas.ListarEquipasDAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.ListarEquipasDAOImp;
import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import com.example.oporto_olympics.Models.InscricaoEquipas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListarEquipaController {
    @FXML
    private VBox vboxEquipas;

    @FXML
    private Button VoltarButton;

    private ListarEquipasDAO dao;

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

            listarEquipas();

        } catch (SQLException exception) {
            System.out.println("Ligação falhou: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar à base de dados: " + exception.getMessage());
            alert.show();
        }
    }

    /**
     * Este método lista todas as equipas na interface gráfica. Para cada equipa, é gerado um painel com informações
     * sobre o nome, desporto, género e medalhas, além de um botão para ver mais detalhes.
     * Caso não haja equipas carregadas, mostra uma mensagem informando o utilizador.
     *
     * */
    public void listarEquipas() {
        try {
            List<InscricaoEquipas> equipas = dao.getEquipas();
            System.out.println("Número total de equipas carregadas: " + equipas.size());

            vboxEquipas.getChildren().clear();

            for (InscricaoEquipas equipa : equipas) {


                HBox teamPane = new HBox(10);
                teamPane.setPrefWidth(vboxEquipas.getPrefWidth());
                teamPane.setStyle("-fx-background-color: #bab8b8; -fx-padding: 10; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;");


                Label nomeLabel = new Label(equipa.getNome());
                Label desportoLabel = new Label("Desporto: " + equipa.getDesporto());
                Label generoLabel = new Label("Género: " + equipa.getGenero());
                Label medalhasLabel = new Label("Medalhas: " + equipa.getMedalhas());

                nomeLabel.setStyle("-fx-font-weight: bold;");
                desportoLabel.setStyle("-fx-font-size: 12px;");
                generoLabel.setStyle("-fx-font-size: 12px;");
                medalhasLabel.setStyle("-fx-font-size: 12px;");

                teamPane.getChildren().addAll(nomeLabel, desportoLabel, generoLabel, medalhasLabel);

                Button btnDetalhes = new Button("Ver Detalhes");
                btnDetalhes.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 0.5; -fx-border-radius: 4;");

                HBox.setHgrow(btnDetalhes, Priority.ALWAYS);

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
                            new Label("Participações: " + equipa.getParticipacoes()),
                            new Label("Medalhas: " + equipa.getMedalhas()),
                            new Label("Gênero: " + equipa.getGenero())
                    );

                    dialog.getDialogPane().setContent(dialogContent);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                    dialog.showAndWait();
                });

                teamPane.getChildren().add(btnDetalhes);

                vboxEquipas.getChildren().add(teamPane);
            }

            if (equipas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nenhuma equipa encontrada.");
                alert.show();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar as equipas: " + e.getMessage());
            alert.show();
        }
    }





    @FXML
    void OnClickVoltarButton(ActionEvent event) {
        Stage s = (Stage) VoltarButton.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
