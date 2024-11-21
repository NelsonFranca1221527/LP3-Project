package com.example.oporto_olympics.Controllers.VerResultados;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.VerResultados.CardController.VerResultadosAtletaCardController;
import com.example.oporto_olympics.DAO.Resultados.ResultadosModalidadeDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.ResultadosModalidade;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VerResultadosAtletaController {
    /**
     * Contêiner de layout para a exibição dos resultados.
     */
    @FXML
    private VBox ResultadosContainer;
    /**
     * Botão para voltar à tela anterior.
     */
    @FXML
    private Button VoltarBtn;
    /**
     * Campo de texto para introdução do ano do filtro.
     */
    @FXML
    private TextField anoField;
    /**
     * Caixa de combinação para escolher a modalidade do resultado.
     */
    @FXML
    private ComboBox<String> modalidadeCombo;
    /**
     * Botão para procurar resultados tendo em conta o filtro.
     */
    @FXML
    private Button procurarButton;

    /**
     *
     * Esta função estabelece uma conexão com a base de dados, busca todos os resultados do atleta
     *  e carrega e popula dinamicamente nos cards de eventos num contêiner VBox.
     *
     * @throws SQLException se ocorrer um erro de acesso a base de dados
     */
    public void initialize() throws SQLException{
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();
        ModalidadeDAOImp modalidadeDAO = new ModalidadeDAOImp(conexao);

        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();
        Atleta atleta = AtletaSingle.getAtleta();

        // Adicionar os nomes das modalidades à ComboBox
        modalidadeCombo.getItems().add("Todas");
        modalidadeDAO.getAllIndividualByGenero(atleta.getGenero()).forEach(modalidade -> modalidadeCombo.getItems().add(modalidade.getNome()));

        // Permitir apenas números no campo anoField
        anoField.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d*")) ? change : null
        ));

        ResultadosModalidadeDAOImp ResultadosAtletaDAO = new ResultadosModalidadeDAOImp(conexao);

        List<ResultadosModalidade> AllResultados = ResultadosAtletaDAO.getAllbyAthlete(atleta.getId());

        for (ResultadosModalidade resultadosAtleta : AllResultados) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/VerResultados/Cards/VerResultadosAtletaCard.fxml"));
                Pane Resultados = loader.load();
                VerResultadosAtletaCardController cardsController = loader.getController();
                cardsController.PreencherDados(resultadosAtleta);

                Resultados.setUserData(resultadosAtleta);

                ResultadosContainer.getChildren().add(Resultados);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método chamado quando o utilizador clica no botão de "Procurar" para buscar os resultados filtrados do atleta.
     * O método limpa os cartões existentes e carrega novos cartões com os resultados filtrados, com base no ano e na modalidade
     * selecionados pelo utilizador.
     *
     * @param event O evento gerado pelo clique no botão de "Procurar".
     * @throws SQLException Se ocorrer um erro ao acessar a base de dados durante a busca dos resultados filtrados.
     */
    @FXML
    void OnClickProcurarButton(ActionEvent event) throws SQLException {
        // Limpar cartões anteriores
        ResultadosContainer.getChildren().clear();

        // Recuperar filtros
        String modalidade = modalidadeCombo.getValue();
        Integer ano = anoField.getText().isEmpty() ? null : Integer.parseInt(anoField.getText());

        Atleta atleta = AtletaSingleton.getInstance().getAtleta();
        ResultadosModalidadeDAOImp resultadosAtletaDAO = new ResultadosModalidadeDAOImp(ConnectionBD.getInstance().getConexao());

        // Buscar resultados filtrados
        List<ResultadosModalidade> filteredResults = resultadosAtletaDAO.getFilteredResultsByAthlete(atleta.getId(), ano, modalidade, atleta.getGenero());

        // Criar novos cartões com os resultados filtrados
        for (ResultadosModalidade resultado : filteredResults) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/VerResultados/Cards/VerResultadosAtletaCard.fxml"));
                Pane Resultados = loader.load();
                VerResultadosAtletaCardController cardsController = loader.getController();
                cardsController.PreencherDados(resultado);

                Resultados.setUserData(resultado);

                ResultadosContainer.getChildren().add(Resultados);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        Stage s = (Stage) VoltarBtn.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalAtleta().switchScene(s);
    }
}
