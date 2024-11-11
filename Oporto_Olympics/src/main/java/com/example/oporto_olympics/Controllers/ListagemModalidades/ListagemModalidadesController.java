package com.example.oporto_olympics.Controllers.ListagemModalidades;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.ListagemModalidades.CardController.ListagemModalidadesCardController;
import com.example.oporto_olympics.Models.Modalidade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
/**
 * Controlador responsável por exibir a lista de modalidades em uma interface gráfica.
 * Esta classe é responsável por carregar e exibir dinamicamente os cards de modalidades
 * dentro de um contêiner VBox, a partir dos dados obtidos na base de dados.
 */
public class ListagemModalidadesController {
    /**
     * Contêiner de layout para a exibição das modalidades.
     */
    @FXML
    private VBox ModalidadesContainer;

    /**
     * Botão para voltar à tela anterior.
     */
    @FXML
    private Button VoltarBtn;


    /**
     *
     * Esta função estabelece uma conexão com a base de dados, busca todos os eventos
     *  e carrega e popula dinamicamente nos cards de eventos num contêiner VBox.
     *
     * @throws SQLException se ocorrer um erro de acesso a base de dados
     */
    public void initialize() throws SQLException{

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        ModalidadeDAOImp ModalidadeDAO = new ModalidadeDAOImp(conexao);

        List<Modalidade> AllModalidades = ModalidadeDAO.getAll();

        for (Modalidade modalidade : AllModalidades) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/ListagemModalidades/Cards/ListagemModalidadesCard.fxml"));
                Pane Locais = loader.load();
                ListagemModalidadesCardController cardsController = loader.getController();
                cardsController.PreencherDados(modalidade);

                Locais.setUserData(modalidade);

                ModalidadesContainer.getChildren().add(Locais);

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

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
