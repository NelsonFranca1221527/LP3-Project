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
 * Esta classe gere a interface gráfica responsável por exibir a lista de clientes.
 * Permite a visualização dos clientes, bem como a navegação entre as telas da aplicação.
 * Utiliza o DAO {@link ClienteDAO} para obter e manipular os dados dos clientes.
 */
public class ListagemClientesController {

    /**
     * Botão utilizado para voltar à tela anterior.
     */
    @FXML
    private Button VoltarBtn;

    /**
     * VBox que contém os itens de clientes na interface gráfica.
     *
     * Esta VBox organiza e apresenta os clientes na interface do utilizador.
     */
    @FXML
    private VBox clientesVBox;

    /**
     * ScrollPane utilizado para permitir a rolagem da lista de clientes.
     */
    @FXML
    private ScrollPane clientesScrollPane;

    /**
     * Instância do DAO responsável pela gestão dos clientes.
     *
     * O {@link ClienteDAO} permite interagir com a base de dados ou serviço de clientes,
     * possibilitando a obtenção, inserção e remoção de dados.
     */
    private ClienteDAO clienteDAO = new ClienteDAOImp();

    /**
     * Evento acionado ao clicar no botão "Voltar".
     *
     * Este método redireciona o utilizador para o menu de inserções.
     *
     * @param event Evento do clique no botão.
     */
    @FXML
    void OnVoltarButtonClick(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();
        RedirecionarHelper.GotoSubMenuCliente().switchScene(s);
    }

    /**
     * Método chamado durante a inicialização do controlador.
     *
     * Configura o ScrollPane e carrega a lista de clientes.
     */
    @FXML
    public void initialize() {
        clientesScrollPane.setContent(clientesVBox);
        clientesScrollPane.setFitToWidth(true);
        loadClients();
    }

    /**
     * Carrega e exibe a lista de clientes na interface gráfica.
     *
     * Obtém os clientes do DAO e adiciona-os dinamicamente à VBox.
     */
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
     * Remove um cliente da base de dados e atualiza a lista na interface.
     *
     * @param clientId ID do cliente a ser removido.
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
     * Ativa ou desativa um cliente na base de dados e atualiza a lista na interface.
     *
     * Se o cliente estiver ativo, será desativado. Se estiver inativo, será reativado.
     *
     * @param clientId ID do cliente a ser alterado.
     * @param status Estado atual do cliente (ativo ou inativo).
     */
    private void deactivateClient(String clientId, Boolean status) {
        try {
            if (status) {
                clienteDAO.BanClient(clientId);
            } else {
                clienteDAO.UnBanClient(clientId);
            }
            loadClients();
        } catch (IOException e) {
            System.out.println("Erro ao alterar estado do cliente: " + e.getMessage());
        }
    }
}
