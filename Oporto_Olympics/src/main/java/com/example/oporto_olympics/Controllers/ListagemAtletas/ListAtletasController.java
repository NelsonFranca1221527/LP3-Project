package com.example.oporto_olympics.Controllers.ListagemAtletas;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.ListagemAtletas.CardController.ListAtletaCardController;
import com.example.oporto_olympics.Models.Atleta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
/**
 * Controlador responsável por gerir a exibição dos atletas numa lista.
 * Esta classe estabelece uma conexão com a base de dados, obtém todos os atletas
 * e popula dinamicamente os cards de atletas num contêiner `VBox`.
 */
public class ListAtletasController {
    /**
     * Contêiner de layout para os atletas.
     */
    @FXML
    private VBox AtletasContainer;
    /**
     * Botão para voltar à tela anterior.
     */
    @FXML
    private Button VoltarBtn;

    @FXML
    private TextField searchfield;

    private AtletaDAOImp AtletaDAO;


    /**
     *
     * Esta função estabelece uma conexão com a base de dados, busca todos os atletas
     *  e carrega e popula dinamicamente nos cards de atletas num contêiner VBox.
     *
     * @throws SQLException se ocorrer um erro de acesso a base de dados
     */
    public void initialize() throws SQLException{

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        if (conexao == null) {
            System.out.println("Conexão com a base de dados falhou!");
            return;
        } else {
            System.out.println("Conexão com a base de dados bem-sucedida!");
        }

        AtletaDAO = new AtletaDAOImp(conexao);

        configurarSearchField();

        ListarAtletas(null);
    }

    /**
     * Configura o listener para o campo de texto de pesquisa.
     */
    private void configurarSearchField() {
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            ListarAtletas(newValue.trim()); // Atualizar a lista conforme o texto digitado
        });
    }

    public void ListarAtletas(String filtroNome){
        try{
            List<Atleta> atletas = AtletaDAO.getAtletas(filtroNome);

            AtletasContainer.getChildren().clear();

            if (atletas.isEmpty()) {
                Label noResultsLabel = new Label("Nenhuma equipa encontrada.");
                noResultsLabel.setStyle("-fx-font-size: 16px; -fx-font-style: italic; -fx-text-fill: grey; -fx-padding: 10 0 0 10;");
                AtletasContainer.getChildren().add(noResultsLabel);
                return;
            }

            for (Atleta atleta : atletas) {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/ListagemAtletas/Cards/ListAtletaCard.fxml"));
                    Pane Atletas = loader.load();
                    ListAtletaCardController cardsController = loader.getController();
                    cardsController.PreencherDados(atleta);

                    Atletas.setUserData(atleta);

                    AtletasContainer.getChildren().add(Atletas);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar os atletas: " + e.getMessage());
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
        Stage s = (Stage) VoltarBtn.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
