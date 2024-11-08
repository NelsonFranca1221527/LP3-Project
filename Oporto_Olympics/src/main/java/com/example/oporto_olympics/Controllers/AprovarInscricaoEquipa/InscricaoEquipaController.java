package com.example.oporto_olympics.Controllers.AprovarInscricaoEquipa;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.Equipas.AprovarInscricaoEquipaDAO;
import com.example.oporto_olympics.Controllers.DAO.Equipas.AprovarInscricaoEquipaDAOImp;
import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import com.example.oporto_olympics.Models.AprovarInscricaoEquipa;
import com.example.oporto_olympics.Models.AtletaInfo;
import com.example.oporto_olympics.Models.InscricaoEquipas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InscricaoEquipaController {

    @FXML
    private Button btnBack;

    @FXML
    private VBox ContainerInscricao;

    private AprovarInscricaoEquipaDAO dao;

    /**
     * Método responsável por iniciar o controlador.
     * Este método estabelece a conexão com a base de dados e carrega as inscrições.
     * Caso a conexão falhe, mostra uma mensagem de erro ao utilizador.
     *
     * @throws SQLException Se ocorrer um erro ao tentar estabelecer a conexão com a base de dados.
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

            dao = new AprovarInscricaoEquipaDAOImp(conexao);
            carregarInscricoes();
        } catch (SQLException exception) {
            System.out.println("Ligação falhou: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar a base de dados: " + exception.getMessage());
            alert.show();
        }
    }

    /**
     * Método responsável por carregar as inscrições e exibi-las na interface gráfica.
     * Este método consulta a base de dados para obter a lista de inscrições e, para cada inscrição,
     * cria um painel visual contendo as informações detalhadas, incluindo o ID da inscrição, o status
     * e um botão para ver os detalhes.
     * Caso haja erro ao carregar as inscrições, mostra uma mensagem de erro.
     */
    public void carregarInscricoes() {
        try {
            List<AprovarInscricaoEquipa> inscricoes = dao.getAll();
            System.out.println("Número de inscrições carregadas: " + inscricoes.size());

            if (inscricoes.size() >= 4) {
                ContainerInscricao.setPrefHeight(890);
            } else {
                ContainerInscricao.setPrefHeight(880);
            }

            for (AprovarInscricaoEquipa inscricao : inscricoes) {
                Pane teamPane = new Pane();
                teamPane.setPrefHeight(84);
                teamPane.setPrefWidth(ContainerInscricao.getPrefWidth());
                teamPane.setStyle("-fx-background-color: #bab8b8; -fx-padding: 10; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;");

                Label labelInfo = new Label("Inscrição ID: " + inscricao.getId() + " - Status: " + inscricao.getStatus());
                labelInfo.setLayoutX(10);
                labelInfo.setLayoutY(10);

                Button btnVerDetalhes = new Button("Ver Detalhes");
                btnVerDetalhes.setLayoutX(10);
                btnVerDetalhes.setLayoutY(40);

                final int teamId = inscricao.getEquipa_id();
                final int userId = inscricao.getAtleta_id();
                final int insc = inscricao.getId();
                btnVerDetalhes.setOnAction(e -> abrirDetalhes(teamId,userId,insc));

                teamPane.getChildren().addAll(labelInfo, btnVerDetalhes);
                ContainerInscricao.getChildren().add(teamPane);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar as inscrições: " + e.getMessage());
            alert.show();
        }
    }

    /**
     * Método responsável por abrir uma janela de detalhes da inscrição, mostrando as informações
     * sobre a equipa e o atleta associados à inscrição.
     *
     * O método consulta obtém os dados para obter as informações da equipa e do atleta com base
     * nos IDs fornecidos. Se as informações forem encontradas, cria uma nova janela que mostra
     * detalhes como o nome, país, ano de fundação, participações e medalhas da equipa, além de
     * informações sobre o atleta, como nome, país, gênero, altura, peso e data de nascimento.
     * Também são exibidos botões para aprovar ou reprovar a inscrição.
     *
     * Caso haja algum erro na consulta ou nos dados, mostra um alerta informando o problema.
     *
     * @param teamId ID da equipa para a qual a inscrição pertence.
     * @param userId ID do atleta associado à inscrição.
     * @param insc ID da inscrição que está sendo visualizada.
     */
    private void abrirDetalhes(int teamId, int userId, int insc) {

        InscricaoEquipas equipa = dao.getEquipa(teamId);

        if (equipa != null) {
            AtletaInfo atleta = dao.getAtletaInfo(userId);

            if (atleta != null) {
                VBox detalhesBox = new VBox(10);
                detalhesBox.setStyle("-fx-padding: 20; -fx-background-color: #ffffff; -fx-border-color: black; -fx-border-width: 1;");

                HBox hBox = new HBox(20);
                hBox.setStyle("-fx-padding: 10; -fx-spacing: 20;");

                VBox infoBox = new VBox(10);
                infoBox.setStyle("-fx-spacing: 10;");
                Label labelNomeEquipa = new Label("Nome da Equipa: " + equipa.getNome());
                labelNomeEquipa.setWrapText(true);
                Label labelPaisEquipa = new Label("País: " + equipa.getPais_sigla());
                labelPaisEquipa.setWrapText(true);
                Label labelAnoFundacao = new Label("Ano de Fundação: " + equipa.getAno_fundacao());
                labelAnoFundacao.setWrapText(true);
                Label labelParticipacoes = new Label("Participações: " + equipa.getParticipacoes());
                labelParticipacoes.setWrapText(true);
                Label labelMedalhas = new Label("Medalhas: " + equipa.getMedalhas());
                labelMedalhas.setWrapText(true);

                infoBox.getChildren().addAll(labelNomeEquipa, labelPaisEquipa, labelAnoFundacao, labelParticipacoes, labelMedalhas);

                VBox atletaBox = new VBox(10);
                atletaBox.setStyle("-fx-spacing: 10;");
                Label labelNomeAtleta = new Label("Nome do Atleta: " + atleta.getNome());
                labelNomeAtleta.setWrapText(true);
                Label labelPaisAtleta = new Label("País: " + atleta.getPais());
                labelPaisAtleta.setWrapText(true);
                Label labelGeneroAtleta = new Label("Gênero: " + atleta.getGenero());
                labelGeneroAtleta.setWrapText(true);
                Label labelAlturaAtleta = new Label("Altura: " + atleta.getAltura() + " cm");
                labelAlturaAtleta.setWrapText(true);
                Label labelPesoAtleta = new Label("Peso: " + atleta.getPeso() + " kg");
                labelPesoAtleta.setWrapText(true);
                Label labelDataNascimentoAtleta = new Label("Data de Nascimento: " + atleta.getDataNascimento().toString());
                labelDataNascimentoAtleta.setWrapText(true);

                atletaBox.getChildren().addAll(labelNomeAtleta, labelPaisAtleta, labelGeneroAtleta, labelAlturaAtleta, labelPesoAtleta, labelDataNascimentoAtleta);

                hBox.getChildren().addAll(infoBox, atletaBox);

                Region spacer = new Region();
                VBox.setVgrow(spacer, Priority.ALWAYS);

                HBox buttonBox = new HBox(20);
                buttonBox.setStyle("-fx-spacing: 10; -fx-padding: 10;");

                Button btnAprovar = new Button("Aprovar");
                btnAprovar.setMaxWidth(100);
                btnAprovar.setMinWidth(100);
                btnAprovar.setOnAction(e -> {
                    dao.aprovarInscricao(insc);
                    atualizarStatus();
                    Stage stage = (Stage) btnAprovar.getScene().getWindow();
                    stage.close();

                });

                Button btnReprovar = new Button("Reprovar");
                btnReprovar.setMaxWidth(100);
                btnReprovar.setMinWidth(100);
                btnReprovar.setOnAction(e -> {
                    dao.reprovarInscricao(insc);
                    atualizarStatus();
                    Stage stage = (Stage) btnReprovar.getScene().getWindow();
                    stage.close();

                });

                buttonBox.getChildren().addAll(btnAprovar, btnReprovar);

                VBox vBoxContent = new VBox(10);
                vBoxContent.getChildren().addAll(hBox, spacer, buttonBox);

                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setContent(vBoxContent);
                scrollPane.setFitToWidth(true);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                Stage detalhesStage = new Stage();
                detalhesStage.setScene(new Scene(scrollPane, 600, 400));
                detalhesStage.setTitle("Detalhes da Equipa e Atleta");
                detalhesStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Atleta não encontrado.");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Equipe não encontrada.");
            alert.show();
        }
    }

    /**
     * Método responsável por atualizar o status de uma inscrição e recarregar a lista de inscrições exibida.
     *
     * Este método é chamado para limpar o conteúdo atual da lista de inscrições e, em seguida, recarregar
     * as inscrições da base de dados para refletir qualquer atualização no status de uma inscrição.
     */
    private void atualizarStatus() {

        ContainerInscricao.getChildren().clear();
        carregarInscricoes();
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

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
