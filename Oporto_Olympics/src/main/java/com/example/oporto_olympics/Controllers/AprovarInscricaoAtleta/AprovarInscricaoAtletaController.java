package com.example.oporto_olympics.Controllers.AprovarInscricaoAtleta;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Atleta.AprovarInscricaoAtletaDAO;
import com.example.oporto_olympics.DAO.Atleta.AprovarInscricaoAtletaDAOImp;
import com.example.oporto_olympics.Models.InscricaoAtletaEvento;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class AprovarInscricaoAtletaController {

    @FXML
    private Button VoltarBtn;

    @FXML
    private VBox ModalidadesContainer;

    private AprovarInscricaoAtletaDAO dao;
    /**
     * Inicializa o controlador da interface gráfica.
     *
     * Este método é chamado automaticamente pelo JavaFX ao carregar a interface associada.
     * Ele estabelece uma ligação com a base de dados através da classe {@link ConnectionBD},
     * verifica a sua validade, e inicializa o DAO para gerir as inscrições pendentes.
     * Caso a ligação falhe, apresenta uma mensagem de erro ao utilizador.
     *
     *
     * @throws SQLException se ocorrer um erro ao tentar conectar à base de dados.
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

            dao = new AprovarInscricaoAtletaDAOImp(conexao);

            carregarInscricoesPendentes();

        } catch (SQLException exception) {
            System.out.println("Ligação falhou: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar a base de dados: " + exception.getMessage());
            alert.show();
        }
    }
    /**
     * Carrega as inscrições pendentes no contêiner de modalidades da interface gráfica.
     * Este método obtém a lista de inscrições pendentes através do DAO, cria um cartão
     * para cada inscrição e adiciona-os ao contêiner visual correspondente. Em caso de erro,
     * apresenta uma mensagem de alerta ao utilizador.
     *
     * @throws RuntimeException se ocorrer um erro ao obter ou processar as inscrições.
     */
    private void carregarInscricoesPendentes() {
        try {
            ModalidadesContainer.getChildren().clear();

            ObservableList<InscricaoAtletaEvento> inscricoes = FXCollections.observableArrayList(dao.listarInscricoesPendentes());

            for (InscricaoAtletaEvento inscricao : inscricoes) {
                HBox card = criarCard(inscricao);
                ModalidadesContainer.getChildren().add(card);
            }

        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar inscrições: " + e.getMessage());
            alert.show();
        }
    }
    /**
     * Cria um card visual para exibir as informações de uma inscrição de atleta.
     *
     * @param inscricao A inscrição que contém os dados do atleta, modalidade e evento.
     * @return O HBox contendo o card visual da inscrição.
     */
    private HBox criarCard(InscricaoAtletaEvento inscricao) {

        HBox card = new HBox(10);
        card.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-radius: 5;");
        card.setPrefWidth(790);

        VBox textContainer = new VBox(5);
        textContainer.setStyle("-fx-alignment: center-left;");

        textContainer.getChildren().addAll(
                new javafx.scene.control.Label("Atleta: " + dao.getAtletaNome(inscricao.getAtleta_id())),
                new javafx.scene.control.Label("Modalidade: " + dao.getModalidadeNome(inscricao.getModalidade_id())),
                new javafx.scene.control.Label("Evento: " + dao.getLocalNome(inscricao.getEvento_id()))
        );

        Button aprovarBtn = new Button("Aprovar");
        Button rejeitarBtn = new Button("Rejeitar");

        aprovarBtn.setOnAction(e -> aprovarInscricao(inscricao.getEvento_id(), inscricao.getAtleta_id(), inscricao.getModalidade_id()));
        rejeitarBtn.setOnAction(e -> rejeitarInscricao(inscricao.getAtleta_id(), inscricao.getModalidade_id(), inscricao.getEvento_id()));

        VBox buttonContainer = new VBox(5);
        buttonContainer.getChildren().addAll(aprovarBtn, rejeitarBtn);

        card.getChildren().addAll(textContainer, buttonContainer);

        return card;
    }

    /**
     * Aprova uma inscrição, atualizando o estado para "Aprovado" no banco de dados.
     *
     * @param atletaId     O ID do atleta da inscrição.
     * @param modalidadeId O ID da modalidade da inscrição.
     * @param eventoId     O ID do evento da inscrição.
     */
    private void aprovarInscricao(int eventoId, int atletaId, int modalidadeId) {
        try {

            dao.aprovarInscricao(atletaId, modalidadeId, eventoId);

            dao.associarAtletaAoEvento(atletaId, modalidadeId, eventoId);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Inscrição aprovada e atleta associado ao evento com sucesso!");
            alert.show();

            carregarInscricoesPendentes();
        } catch (RuntimeException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao aprovar inscrição: " + e.getMessage());
            alert.show();
        }
    }

    /**
     * Rejeita uma inscrição, atualizando o estado para "Rejeitado" no banco de dados.
     *
     * @param atletaId     O ID do atleta da inscrição.
     * @param modalidadeId O ID da modalidade da inscrição.
     * @param eventoId     O ID do evento da inscrição.
     */
    private void rejeitarInscricao(int atletaId, int modalidadeId, int eventoId) {
        try {
            dao.rejeitarInscricao(atletaId, modalidadeId, eventoId);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Inscrição rejeitada com sucesso!");
            alert.show();

            carregarInscricoesPendentes();

        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao rejeitar inscrição: " + e.getMessage());
            alert.show();
        }
    }

    /**
     * Trata o evento de ação desencadeado ao clicar no botão "Voltar".
     *
     * Este método obtém a janela (stage) atual a partir da cena do botão e redireciona
     * o utilizador para o menu principal do gestor utilizando o {@link RedirecionarHelper}.
     *
     * @param event o {@link ActionEvent} desencadeado pelo clique no botão
     */
    @FXML
    void onActionBack(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();
        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
