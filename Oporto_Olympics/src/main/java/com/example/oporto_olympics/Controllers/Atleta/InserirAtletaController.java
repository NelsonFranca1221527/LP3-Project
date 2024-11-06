package com.example.oporto_olympics.Controllers.Atleta;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.Atleta.InserirAtletaDAO;
import com.example.oporto_olympics.Controllers.DAO.Atleta.InserirAtletaDAOImp;
import com.example.oporto_olympics.Controllers.DAO.Equipas.AprovarInscricaoEquipaDAOImp;
import com.example.oporto_olympics.Controllers.DAO.Equipas.ListarEquipasDAO;
import com.example.oporto_olympics.Models.Atleta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InserirAtletaController {
    @FXML
    private TextField Altura;

    @FXML
    private Button CriarAtletaButton;

    @FXML
    private TextField Data_nasc;

    @FXML
    private ChoiceBox<String> GeneroChoice;

    @FXML
    private TextField Nome;

    @FXML
    private TextField Pais;

    @FXML
    private TextField Peso;

    @FXML
    private Button VoltarButton;

    private InserirAtletaDAOImp dao;

    /**
     * Método de inicialização do controlador, executado automaticamente após o carregamento da interface FXML.
     * Este método configura a conexão com a base de dados, inicializa a instância de DAO e configura o ChoiceBox
     * para a seleção do gênero do atleta.
     *
     */
    @FXML
    void initialize() {

        try {
            ConnectionBD conexaoBD = ConnectionBD.getInstance();
            Connection conexao = conexaoBD.getConexao();

            if (conexao == null) {
                System.out.println("Conexão com a base de dados falhou!");
                return;
            } else {
                System.out.println("Conexão com a base de dados bem-sucedida!");
                dao = new InserirAtletaDAOImp(conexao);
            }
        }catch (SQLException exception) {
            System.out.println("Ligação falhou: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar à base de dados: " + exception.getMessage());
            alert.show();
        }

        // Preencher o ChoiceBox de Gênero
        GeneroChoice.getItems().addAll("Men", "Women");
        GeneroChoice.setValue("Men"); // Valor default, pode mudar se necessário
    }

    /**
     * Método associado ao botão de criação de atleta. Este método é acionado ao clicar no botão de "Criar Atleta"
     * na interface gráfica, obtendo os valores inseridos pelo utilizador nos campos de texto e de seleção.
     * Realiza a validação dos dados, conversões de tipo necessárias, e em seguida invoca o método do DAO
     * para armazenar os dados do atleta na base de dados.
     *
     * Fluxo do método:
     * 1. Obtém os valores inseridos nos campos da interface.
     * 2. Valida se todos os campos obrigatórios estão preenchidos.
     * 3. Converte os campos de altura e peso para `double` e verifica se são válidos.
     * 4. Valida e converte a data de nascimento para `LocalDate`, garantindo que está no formato correto e que não é uma data futura.
     * 5. Se todos os dados forem válidos, invoca o método `saveAtleta` do DAO para guardar o atleta.
     * 6. Exibe uma mensagem de sucesso ou erro consoante o resultado da operação.
     *
     * @param event O evento acionado pelo clique no botão, do tipo `ActionEvent`.
     */
    @FXML
    void OnClickCriarAtletaButton(ActionEvent event) {
        String nome = Nome.getText();
        String pais = Pais.getText();
        String altura = Altura.getText();
        String peso = Peso.getText();
        String dataNasc = Data_nasc.getText();
        String genero = GeneroChoice.getValue().toString();

        if (nome.isEmpty() || pais.isEmpty() || altura.isEmpty() || peso.isEmpty() || dataNasc.isEmpty() || genero.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos obrigatórios", "Todos os campos devem ser preenchidos.");
            return;
        }
        if (!dao.getPais(pais)) {
            showAlert(Alert.AlertType.WARNING, "País inválido", "A sigla do país inserido não é válida.");
            return;
        }

        double alturaDouble;
        double pesoDouble;
        try {
            alturaDouble = Double.parseDouble(altura);
            pesoDouble = Double.parseDouble(peso);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Dados inválidos", "Altura e Peso devem ser números válidos.");
            return;
        }

        LocalDate dataNascimento = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dataNascimento = LocalDate.parse(dataNasc, formatter);

            if (dataNascimento.isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.WARNING, "Data inválida", "A data de nascimento não pode ser maior que a data de hoje.");
                return;
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.WARNING, "Data inválida", "A data de nascimento deve estar no formato: yyyy-MM-dd");
            return;
        }

        try {

            dao.saveAtleta(nome, pais, alturaDouble, pesoDouble, dataNascimento, genero);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Atleta criado com sucesso!");

            refreshPage();


        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao criar atleta", "Erro ao salvar o atleta na base de dados.");
        }
    }
    /**
     * Reseta os campos do formulário de criação do atleta para os valores padrão.
     *
     */
    private void refreshPage() {
        Nome.clear();
        Pais.clear();
        Altura.clear();
        Peso.clear();
        Data_nasc.clear();
        GeneroChoice.setValue("Men");
    }

    @FXML
    void OnClickVoltarButton(ActionEvent event) {

    }
    /**
     * Mostra um alerta com o tipo, título e mensagem especificados.
     * Este método é utilizado para informar o utilizador sobre o resultado de uma operação,
     * seja uma mensagem de sucesso, aviso ou erro.
     *
     * @param alertType O tipo de alerta a ser exibido, que define o ícone e o estilo da janela.
     *                  Pode ser `Alert.AlertType.INFORMATION`, `Alert.AlertType.WARNING` ou `Alert.AlertType.ERROR`.
     * @param title     O título da janela de alerta.
     * @param message   A mensagem de conteúdo do alerta que será exibida ao utilizador.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
