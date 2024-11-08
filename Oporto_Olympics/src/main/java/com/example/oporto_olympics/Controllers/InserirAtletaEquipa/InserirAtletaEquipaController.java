package com.example.oporto_olympics.Controllers.InserirAtletaEquipa;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.Equipas.InscricaoEquipaDAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.InscricaonaEquipaDAOImp;
import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import com.example.oporto_olympics.Models.InscricaoEquipas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InserirAtletaEquipaController {
    @FXML
    private VBox ContainerEquipa;

    @FXML
    private Button btnBack;

    private InscricaoEquipaDAO dao;

    /**
     * Método de inicialização da interface JavaFX, marcado com @FXML para ser automaticamente chamado
     * pelo JavaFX após o carregar a interface. Este método estabelece a ligação à base de dados
     * e inicializa o DAO para manipulação de inscrições em equipas. Em seguida, carrega as equipas de
     * um país específico para mostrar.
     *
     * Se a conexão à base de dados falhar, mostra uma mensagem de erro na consola e apresenta um alerta
     * ao utilizador. Caso a conexão seja bem-sucedida, inicializa o DAO com a conexão, define o ID do
     * atleta, o país e o género para filtrar as equipas, e invoca o método carregarEquipas para mostrar as equipas.
     *
     * @throws SQLException se ocorrer um erro ao estabelecer a ligação com a base de dados.
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

            dao = new InscricaonaEquipaDAOImp(conexao);
            int atleta_id = 148;
            String genero = "Men";
            String pais = "USA";
            carregarEquipas(pais, atleta_id, genero);
        } catch (SQLException exception) {
            System.out.println("Ligação falhou: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar a base de dados: " + exception.getMessage());
            alert.show();
        }
    }
    /**
     * Carrega e exibe as informações das equipas de um país e género específico num contêiner JavaFX.
     * O método obtém uma lista de equipas através do DAO e apresenta cada equipa num painel (Pane)
     * que inclui informações como nome, desporto, género, medalhas e um botão para ver os detalhes.
     * Ao clicar no botão "Ver Detalhes", abre um diálogo com mais informações da equipa, além de
     * uma opção para o utilizador se inscrever na equipa, caso não exista um pedido pendente.
     *
     * @param pais      O país das equipas a carregar.
     * @param atleta_id O ID do atleta que pretende inscrever-se numa equipa.
     */
    public void carregarEquipas(String pais, int atleta_id, String genero) {
        try {
            List<InscricaoEquipas> equipas = dao.getEquipas(pais, genero);
            System.out.println("Número de equipas carregadas: " + equipas.size());

            if (equipas.size() >= 4) {
                ContainerEquipa.setPrefWidth(574);
            } else {
                ContainerEquipa.setPrefWidth(586);
            }

            for (InscricaoEquipas equipa : equipas) {

                Pane teamPane = new Pane();
                teamPane.setPrefHeight(84);
                teamPane.setPrefWidth(ContainerEquipa.getPrefWidth());
                teamPane.setStyle("-fx-background-color: #bab8b8; -fx-padding: 10; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;");

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
                        try {
                            int modalidadeId = equipa.getModalidade_id();
                            int equipaId = equipa.getId();

                            if (dao.existePedidoPendente(atleta_id, equipaId)) {
                                Alert pendenteAlert = new Alert(Alert.AlertType.WARNING, "Já existe um pedido pendente para a equipa: " + equipa.getNome());
                                pendenteAlert.show();
                            } else {

                                if(dao.existePedidoAprovado(atleta_id,equipaId)) {
                                    Alert aprovadoAlert = new Alert(Alert.AlertType.WARNING,"Já está inscrito nesta equipa");
                                    aprovadoAlert.show();
                                } else {
                                    String status = "Pendente";
                                    dao.inserirInscricao(status, modalidadeId, atleta_id, equipaId);

                                    Alert inscricaoAlert = new Alert(Alert.AlertType.INFORMATION, "Inscrição pendente criada para a equipe: " + equipa.getNome());
                                    inscricaoAlert.showAndWait().ifPresent(response -> dialog.close());
                                }
                            }
                        } catch (RuntimeException e) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Erro ao realizar inscrição: " + e.getMessage());
                            errorAlert.show();
                        }
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

    /**
     * Evento para o botão "Voltar". Este método é chamado quando o utilizador clica no
     * botão, permitindo assim ao utilizador voltar para a página anterior.
     *
     * @param event O evento de ação que desencadeia o método, gerado pelo clique no botão.
     */
    @FXML
    private void onActionBack(ActionEvent event) {
        Stage s = (Stage) btnBack.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalAtleta().switchScene(s);
    }

}