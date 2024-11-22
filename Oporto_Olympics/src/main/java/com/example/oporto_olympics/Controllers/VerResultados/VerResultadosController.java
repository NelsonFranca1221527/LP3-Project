package com.example.oporto_olympics.Controllers.VerResultados;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.VerResultados.CardController.VerResultadosAtletaCardController;
import com.example.oporto_olympics.Controllers.VerResultados.CardController.VerResultadosCardController;
import com.example.oporto_olympics.Controllers.VerResultados.CardController.VerTopTenCardController;
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

public class VerResultadosController {
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
     * Contêiner de layout para a exibição dos top 10.
     */
    @FXML
    private VBox TopDezContainer;

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

        ResultadosModalidadeDAOImp ResultadosAtletaDAO = new ResultadosModalidadeDAOImp(conexao);

        List<ResultadosModalidade> AllResultados = ResultadosAtletaDAO.getAll();
        List<ResultadosModalidade> TopTenResultados = ResultadosAtletaDAO.getAllOrderedTopTen();

        for (ResultadosModalidade resultadosAtleta : AllResultados) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/VerResultados/Cards/VerResultadosCard.fxml"));
                Pane Resultados = loader.load();
                VerResultadosCardController cardsController = loader.getController();

                cardsController.PreencherDados(resultadosAtleta);
                Resultados.setUserData(resultadosAtleta);

                ResultadosContainer.getChildren().add(Resultados);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (ResultadosModalidade resultadosTen : TopTenResultados) {
            try {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/VerResultados/Cards/VerTopTen.fxml"));
                Pane TopTen = loader2.load();
                VerTopTenCardController TopTenController = loader2.getController();

                TopTenController.PreencherDados(resultadosTen);
                TopTen.setUserData(resultadosTen);

                TopDezContainer.getChildren().add(TopTen);

            } catch (IOException e) {
                throw new RuntimeException(e);
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

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
