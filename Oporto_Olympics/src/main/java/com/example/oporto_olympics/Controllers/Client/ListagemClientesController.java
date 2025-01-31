package com.example.oporto_olympics.Controllers.Client;

import com.example.oporto_olympics.API.DAO.Client.ClienteDAO;
import com.example.oporto_olympics.API.DAO.Client.ClienteDAOImp;
import com.example.oporto_olympics.API.Models.Client;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
/**
 * Controlador da interface de listagem de clientes.
 *
 * Esta classe é responsável por gerenciar a interface de listagem de clientes,
 * incluindo a visualização dos clientes e a navegação entre as telas. Utiliza
 * o DAO {@link ClienteDAO} para obter os dados dos clientes e exibi-los.
 */
public class ListagemClientesController {
    /**
     * Botão de navegação para voltar à tela anterior.
     *
     * Este botão é usado para permitir ao utilizador voltar à tela anterior.
     */
    @FXML
    private Button VoltarBtn;
    /**
     * VBox que contém os itens de clientes na interface gráfica.
     *
     * Esta VBox é usada para armazenar e organizar os elementos de cliente
     * que serão exibidos na interface do utilizador.
     */
    @FXML
    private VBox clientesVBox;

    @FXML
    private ScrollPane clientesScrollPane;

    /**
     * Instância do DAO de clientes.
     *
     * O {@link ClienteDAO} é usado para interagir com a base de dados ou
     * com o serviço de clientes, obtendo, inserindo ou removendo dados de
     * clientes conforme necessário.
     */
    private ClienteDAO clienteDAO = new ClienteDAOImp();

    /**
     * Evento para o botão "Voltar". Este método redireciona o utilizador para o menu de inserções.
     */
    @FXML
    void OnVoltarButtonClick(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();
        RedirecionarHelper.GotoSubMenuCliente().switchScene(s);
    }

    /**
     * Método para carregar todos os clientes na VBox.
     * Este método é chamado durante a inicialização para popular a lista de clientes.
     */
    @FXML
    public void initialize() {
        clientesScrollPane.setContent(clientesVBox);
        clientesScrollPane.setFitToWidth(true);
        loadClients();
    }

    private void loadClients() {
        try {
            List<Client> clients = clienteDAO.getAllClients();
            clientesVBox.getChildren().clear();

            for (Client client : clients) {
                HBox clientPane = new HBox(10);
                clientPane.setMinHeight(Region.USE_PREF_SIZE);
                clientPane.setSpacing(20);

                Label nameLabel = new Label("Nome: " + client.getName());
                Label emailLabel = new Label("Email: " + client.getEmail());
                Label statusLabel = new Label("Status: " + (client.getActive() ? "Ativo" : "Inativo"));

                Button disableButton = new Button(client.getActive() ? "Desativar" : "Ativar");
                Button removeButton = new Button("Remover");

                removeButton.setOnAction(e -> removeClient(client.getId()));
                disableButton.setOnAction(e -> deactivateClient(client.getId(), client.getActive()));

                BorderPane clientContainer = new BorderPane();
                clientContainer.setLeft(nameLabel);
                clientContainer.setCenter(emailLabel);
                clientContainer.setRight(statusLabel);

                HBox footer = new HBox(10, removeButton, disableButton);
                clientContainer.setBottom(footer);
                clientContainer.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #f5f5f5;");

                clientesVBox.getChildren().add(clientContainer);
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar clientes: " + e.getMessage());
        }
    }


    /**
     * Método para remover o cliente.
     * Este método pode ser chamado quando o botão "Remover" for pressionado.
     *
     * @param clientId O ID do cliente a ser removido.
     */
    private void removeClient(String clientId) {
        try {
            clienteDAO.removeClient(clientId);
            loadClients();
        } catch (IOException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
        }
    }

    /**
     * Método para desativar o cliente.
     * Este método pode ser chamado quando o botão "Desativar" for pressionado.
     *
     * @param clientid O cliente a ser desativado.
     */
    private void deactivateClient(String clientid, Boolean status) {

        try {

            if (status == true) {
                clienteDAO.BanClient(clientid);
                loadClients();
            }else {
                clienteDAO.UnBanClient(clientid);
                loadClients();
            }
        } catch (IOException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
        }

    }
}