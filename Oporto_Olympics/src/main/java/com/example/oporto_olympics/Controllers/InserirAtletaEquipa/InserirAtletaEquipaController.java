package com.example.oporto_olympics.Controllers.InserirAtletaEquipa;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.DAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.InscricaoEquipaDAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.InscricaonaEquipaDAOImp;
import com.example.oporto_olympics.Controllers.DAO.Equipas.Model.InscricaoEquipas;
import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.List;
public class InserirAtletaEquipaController {
    @FXML
    private VBox ContainerEquipa;

    @FXML
    private Button btnBack;

    private InscricaoEquipaDAO dao;

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

            // Inicialize o DAO com a conexão
            dao = new InscricaonaEquipaDAOImp(conexao);
            String pais = "USA";
            carregarEquipas(pais);
        } catch (SQLException exception) {
            System.out.println("Ligação falhou: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar a base de dados: " + exception.getMessage());
            alert.show();
        }
    }

    public void carregarEquipas(String pais) {
        try {
            List<InscricaoEquipas> equipas = dao.getEquipas(pais);
            System.out.println("Número de equipas carregadas: " + equipas.size());

            ContainerEquipa.getChildren().clear();

            if (equipas.size() >= 4) {
                ContainerEquipa.setPrefWidth(574);
            } else {
                ContainerEquipa.setPrefWidth(586);
            }

            for (InscricaoEquipas equipa : equipas) {

                Pane teamPane = new Pane();
                teamPane.setPrefHeight(84);
                teamPane.setPrefWidth(ContainerEquipa.getPrefWidth()); // Usar a largura definida do ContainerEquipa
                teamPane.setStyle("-fx-background-color: #bab8b8; -fx-padding: 10; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;"); // Estilo do Pane

                Label nomeLabel = new Label(equipa.getNome());
                Label desportoLabel = new Label("Desporto: " + equipa.getDesporto());
                Label generoLabel = new Label("Género: " + equipa.getGenero());
                Label medalhasLabel = new Label("Medalhas: " + equipa.getMedalhas());

                nomeLabel.setLayoutX(8.0);
                nomeLabel.setLayoutY(6.0);
                desportoLabel.setLayoutX(19.0);
                desportoLabel.setLayoutY(28.0);
                generoLabel.setLayoutX(327.0);
                generoLabel.setLayoutY(28.0);
                medalhasLabel.setLayoutX(221.0);
                medalhasLabel.setLayoutY(28.0);

                Button btnDetalhes = new Button("Ver Detalhes");
                btnDetalhes.setLayoutY(27.0);
                btnDetalhes.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 0.5; -fx-border-radius: 4;");

                if (equipas.size() >= 4) {
                    btnDetalhes.setLayoutX(475);
                } else {
                    btnDetalhes.setLayoutX(486.0);
                }

                btnDetalhes.setOnAction(event -> {
                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setTitle(equipa.getNome());

                    VBox dialogContent = new VBox(10);
                    dialogContent.setStyle("-fx-padding: 20; -fx-alignment: center-left;");

                    // Adiciona cada campo ao conteúdo do diálogo
                    dialogContent.getChildren().addAll(
                            new Label("Nome: " + equipa.getNome()),
                            new Label("Desporto: " + equipa.getDesporto()),
                            new Label("País: " + equipa.getPais_sigla()),
                            new Label("Ano de Fundação: " + equipa.getAno_fundacao()),
                            new Label("Participações: " + equipa.getParticipacoes()),
                            new Label("Medalhas: " + equipa.getMedalhas()),
                            new Label("Gênero: " + equipa.getGenero())
                    );

                    Button btnInscrever = new Button("Inscrever");
                    btnInscrever.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 4;");
                    btnInscrever.setOnAction(inscricaoEvent -> {
                        Alert inscricaoAlert = new Alert(Alert.AlertType.INFORMATION, "Inscrição realizada com sucesso para a equipe: " + equipa.getNome());
                        inscricaoAlert.showAndWait().ifPresent(response -> dialog.close());
                    });

                    dialogContent.getChildren().add(btnInscrever);

                    dialog.getDialogPane().setContent(dialogContent);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                    dialog.showAndWait();
                });

                teamPane.getChildren().addAll(nomeLabel, desportoLabel, generoLabel, medalhasLabel, btnDetalhes);

                ContainerEquipa.getChildren().add(teamPane);
            }

            if (equipas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nenhuma equipa encontrada para o país: " + pais);
                alert.show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar as equipas: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void onActionBack(ActionEvent event) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        // Chame seu método para mudar de cena
    }
    @FXML
    private void onClickDetalhesButton(ActionEvent event) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        // Chame seu método para mudar de cena
    }

}