package com.example.oporto_olympics.Controllers.ListagemJogos;

import com.example.oporto_olympics.API.ConnectAPI.ConnectionAPI;
import com.example.oporto_olympics.API.DAO.Jogos.JogosDAOImp;
import com.example.oporto_olympics.API.Models.Jogo;
import com.example.oporto_olympics.Controllers.ListagemJogos.CardController.ListagemJogosCardController;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.ClientSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe responsável pelo controlo da interface de listagem de jogos.
 * Esta classe lida com a inicialização da interface, carregamento de dados dos jogos e redirecionamento para outras páginas.
 */
public class ListagemJogosController {

    /**
     * Container que armazena os cartões de jogos na interface.
     */
    @FXML
    private VBox JogosContainer;

    /**
     * Botão para voltar à página anterior.
     */
    @FXML
    private Button VoltarBtn;

    /**
     * Método de inicialização da interface. É responsável por estabelecer a conexão à API,
     * carregar a lista de jogos e preencher os dados na interface.
     *
     * @throws SQLException Caso ocorra um erro ao processar os dados da base de dados.
     * @throws IOException Caso ocorra um erro ao carregar os ficheiros FXML ou ao estabelecer a conexão com a API.
     */
    public void initialize() throws SQLException, IOException {
        ConnectionAPI connectionAPI = new ConnectionAPI();
        HttpURLConnection httpURLConnection = connectionAPI.getConexao();

        JogosDAOImp jogosDAOImp = new JogosDAOImp(httpURLConnection);

        List<Jogo> jogosList = jogosDAOImp.getAll();

        for (Jogo jogo : jogosList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/ListagemJogos/Cards/ListagemJogosCard.fxml"));
                Pane Jogos = loader.load();
                ListagemJogosCardController cardsController = loader.getController();
                cardsController.PreencherDados(jogo);

                Jogos.setUserData(jogo);

                JogosContainer.getChildren().add(Jogos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Evento acionado ao clicar no botão "Voltar".
     * Este método redireciona o utilizador para uma página diferente com base no tipo de utilizador (Gestor ou Cliente).
     *
     * @param event Evento associado ao clique no botão.
     */
    @FXML
    void onActionBack(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        ClientSingleton ClienteSingle = ClientSingleton.getInstance();

        if (GestorSingle.getGestor() != null) {
            RedirecionarHelper.GotoSubMenuListagens().switchScene(s);
        }

        if (ClienteSingle.getClient() != null) {
            RedirecionarHelper.GotoHomeClient().switchScene(s);
        }
    }
}
