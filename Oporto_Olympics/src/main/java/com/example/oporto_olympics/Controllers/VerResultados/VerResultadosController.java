package com.example.oporto_olympics.Controllers.VerResultados;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.VerResultados.CardController.VerResultadosAtletaCardController;
import com.example.oporto_olympics.Controllers.VerResultados.CardController.VerResultadosCardController;
import com.example.oporto_olympics.Controllers.VerResultados.CardController.VerTopTenCardController;
import com.example.oporto_olympics.DAO.Resultados.ResultadosModalidadeDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.ResultadosModalidade;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import javafx.collections.FXCollections;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Controlador para exibição dos resultados de um atleta.
 */
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
     * Caixa de combinação para escolher a modalidade do resultado.
     */
    @FXML
    private ComboBox<String> modalidadeCombo;
    /**
     * Mapa que armazena modalidades associadas a um valor inteiro.
     * A chave é uma String representando o nome da modalidade e o valor é um Integer.
     */
    private HashMap<String, Integer> ModalidadeHashMap = new HashMap<>();

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

        List<Modalidade> lst = modalidadeDAO.getAll();
        for(Modalidade modalidade : lst){
            ModalidadeHashMap.put(modalidade.getNome() + " - " + modalidade.getTipo() + " - " + modalidade.getGenero(), modalidade.getId());
        }

        modalidadeCombo.setItems(FXCollections.observableArrayList(ModalidadeHashMap.keySet()));
        modalidadeCombo.getItems().add("--------");

        modalidadeCombo.setValue("--------");

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
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        ResultadosModalidadeDAOImp ResultadosAtletaDAO = new ResultadosModalidadeDAOImp(conexao);
        // Limpar cartões anteriores
        ResultadosContainer.getChildren().clear();
        TopDezContainer.getChildren().clear();

        if(modalidadeCombo.getValue().equals("--------")){
            return;
        }

        // Recuperar filtros
        int modalidadeID = ModalidadeHashMap.get(modalidadeCombo.getValue());

        ResultadosModalidadeDAOImp resultadosAtletaDAO = new ResultadosModalidadeDAOImp(ConnectionBD.getInstance().getConexao());

        // Buscar resultados filtrados
        List<ResultadosModalidade> filteredResults = resultadosAtletaDAO.getFilteredResults(modalidadeID);
        List<ResultadosModalidade> TopTenResultados = ResultadosAtletaDAO.getAllOrderedTopTen(modalidadeID);


        // Criar novos cartões com os resultados filtrados
        for (ResultadosModalidade resultado : filteredResults) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/VerResultados/Cards/VerResultadosCard.fxml"));
                Pane Resultados = loader.load();
                VerResultadosCardController cardsController = loader.getController();
                cardsController.PreencherDados(resultado);

                Resultados.setUserData(resultado);

                ResultadosContainer.getChildren().add(Resultados);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(TopTenResultados != null){
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

        RedirecionarHelper.GotoSubMenuListagens().switchScene(s);
    }
}
