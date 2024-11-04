package com.example.oporto_olympics.Controllers.InserirAtletaEquipa;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.DAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.InscricaoEquipaDAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.InscricaonaEquipaDAOImp;
import com.example.oporto_olympics.Controllers.DAO.Equipas.Model.InscricaoEquipas;
import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.control.ListCell;
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
    private ListView<InscricaoEquipas> ListViewEquipas;

    @FXML
    private Pane PaneContainer;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDetalhes;

    @FXML
    private Label labelDesporto;

    @FXML
    private Label labelGenero;

    @FXML
    private Label labelMedalhas;

    @FXML
    private Label labelNome;


    private InscricaoEquipaDAO dao;

    @FXML
    public void initialize() {
        // Tente estabelecer a conexão com o banco de dados
        try {
            ConnectionBD conexaoBD = ConnectionBD.getInstance();
            Connection conexao = conexaoBD.getConexao();
            if (conexao == null) {
                System.out.println("Conexão com o banco de dados falhou!");
                return;
            } else {
                System.out.println("Conexão com o banco de dados bem-sucedida!");
            }

            // Inicialize o DAO com a conexão
            dao = new InscricaonaEquipaDAOImp(conexao);
            String pais = "USA"; // Troque pela sigla do país que deseja pesquisar
            carregarEquipas(pais);
        } catch (SQLException exception) {
            System.out.println("Ligação falhou: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar ao banco de dados: " + exception.getMessage());
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
                teamPane.setPrefHeight(84); // Altura do Pane
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

                // Adiciona um evento ao botão
                btnDetalhes.setOnAction(event -> {
                    System.out.println("Detalhes da equipa: " + equipa.getNome());
                });

                // Adiciona as Labels e o botão ao Pane da equipe
                teamPane.getChildren().addAll(nomeLabel, desportoLabel, generoLabel, medalhasLabel, btnDetalhes);

                // Adiciona o Pane da equipe ao ContainerEquipa (VBox)
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