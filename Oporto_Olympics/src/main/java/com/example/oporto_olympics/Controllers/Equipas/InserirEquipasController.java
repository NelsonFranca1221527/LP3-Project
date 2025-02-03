package com.example.oporto_olympics.Controllers.Equipas;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.XML.EquipaDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Equipa;
import com.example.oporto_olympics.Models.Modalidade;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
/**
 * Controlador para a inserção de novas equipas.
 * Permite ao utilizador preencher dados da equipa, como evento, género, modalidade, país e ano de fundação.
 * Valida os dados inseridos e insere a equipa na base de dados.
 */
public class InserirEquipasController {

    /**
     * Campo para introdução do ano de fundação.
     */
    @FXML
    private TextField AnoFundacao;
    /**
     * Botão para criação de uma nova equipa.
     */
    @FXML
    private Button CriarEquipaButton;
    /**
     * Caixa de seleção para escolher o género.
     */
    @FXML
    private ChoiceBox<String> GeneroChoice;
    /**
     * Caixa de seleção para escolher a modalidade.
     */
    @FXML
    private ChoiceBox<String> ModalidadeChoice;
    /**
     * Caixa de seleção para escolher a modalidade.
     */
    @FXML
    private TextField Nome;
    /**
     * Campo de texto para introdução do país.
     */
    @FXML
    private TextField Pais;
    /**
     * Botão para voltar.
     */
    @FXML
    private Button VoltarButton;

    /**
     * Mapa para armazenar as opções de género.
     */
    private HashMap<String, String> generoMap = new HashMap<>();

    /**
     * Mapa para armazenar as modalidades e os respetivos IDs.
     */
    private HashMap<String, Integer> modalidadeMap = new HashMap<>();

    /**
     * Mapa para armazenar o desporto associado a cada modalidade.
     */
    private HashMap<Integer, String> desportoMap = new HashMap<>();

    /**
     * Inicializa os componentes da interface, preenchendo os campos e mapeando os eventos, locais e modalidades.
     *
     * @throws SQLException se ocorrer um erro ao acessar a base de dados
     */
    public void initialize() throws SQLException {

        AnoFundacao.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d*")) ? change : null
        ));

        Pais.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().length() <= 3) ? change : null
        ));

        GeneroChoice.getItems().add("-------");

        generoMap.put("Homem", "Men");
        generoMap.put("Mulher", "Women");

        GeneroChoice.setItems(FXCollections.observableArrayList(generoMap.keySet()));

        GeneroChoice.setValue("-------");

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        ModalidadeChoice.getItems().add("-------");
        ModalidadeChoice.setValue("-------");

        GeneroChoice.getSelectionModel().selectedItemProperty().addListener((observableGenero, oldValueGenero, newValueGenero) -> {
            try {
                AtualizarModalidadeChoice(newValueGenero, conexao);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Atualiza as opções de modalidades com base no evento e género selecionados.
     *
     * @param Genero o género selecionado
     * @param conexao a conexão com a base de dados
     * @throws SQLException se ocorrer um erro ao acessar a base de dados
     */
    private void AtualizarModalidadeChoice(String Genero, Connection conexao) throws SQLException {

        ModalidadeChoice.getItems().removeIf(item -> !item.equals("-------"));
        ModalidadeChoice.setValue("-------");
        modalidadeMap.clear();

        if (!Genero.equals("-------")) {

            ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

            List<Modalidade> lst = modalidadeDAOImp.getAll();

            for (Modalidade modalidade : lst) {

                if (modalidade.getGenero().equals(generoMap.get(Genero))) {
                    modalidadeMap.put(modalidade.getNome() + " - " + modalidade.getGenero(), modalidade.getId());
                    desportoMap.put(modalidade.getId(), modalidade.getNome());
                }
            }

            ModalidadeChoice.getItems().addAll(modalidadeMap.keySet());
        }
    }

    /**
     * Ação ao clicar no botão "Criar Equipa". Cria uma Equipa apartir dos dados inseridos
     *
     * @param event o evento de ação
     * @throws SQLException se ocorrer um erro ao inserir a equipa na base de dados
     */
    @FXML
    void OnClickCriarEquipaButton(ActionEvent event) throws SQLException {
        AlertHandler alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Equipa a Inserir", "Deseja inserir a equipa preenchida?");
        Optional<ButtonType> rs = alertHandler.getAlert().showAndWait();

        if (rs.isPresent() && rs.get() != ButtonType.OK) {
            return;
        }

        if (generoMap.get(GeneroChoice.getValue()) == null ||
                Nome.getText().trim().isEmpty() ||
                desportoMap.get(modalidadeMap.get(ModalidadeChoice.getValue())) == null ||
                Pais.getText().trim().isEmpty() ||
                AnoFundacao.getText().trim().isEmpty() ||
                modalidadeMap.get(ModalidadeChoice.getValue()) == null) {

            alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Dados Vazios", "Preencha todos os dados para inserir a equipa");
            alertHandler.getAlert().showAndWait();
            return;
        }

        String genero = generoMap.get(GeneroChoice.getValue());
        String nome = Nome.getText();
        String pais = Pais.getText().toUpperCase();
        int anoFundacao = Integer.parseInt(AnoFundacao.getText());
        int modalidadeID = modalidadeMap.get(ModalidadeChoice.getValue());
        String desporto = desportoMap.get(modalidadeMap.get(ModalidadeChoice.getValue()));

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        Equipa equipa = new Equipa(0, nome, pais,genero,desporto, modalidadeID, anoFundacao, null);

        EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

        Optional<Equipa> EquipaExiste = equipaDAOImp.get(equipa.getNome());

        if(EquipaExiste.isPresent()) {

            if (equipa.getPais().equals(EquipaExiste.get().getPais()) && equipa.getModalidadeID() == EquipaExiste.get().getModalidadeID()) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Equipa Existente", "A Equipa " + equipa.getNome() + " já encontra-se registada no sistema!");
                alertHandler.getAlert().showAndWait();
                return;
            }
        }

        int anoMin = 1000;

        if(equipa.getAnoFundacao() < anoMin || equipa.getAnoFundacao() > LocalDate.now().getYear()) {
            alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Ano de Fundação Inválido", "O ano de fundação não deve ser inferior a " + anoMin + " e superior a " + LocalDate.now().getYear()+"!");
            alertHandler.getAlert().showAndWait();
            return;
        }

        if (!equipaDAOImp.getSigla(equipa.getPais())){
            alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Pais Inválido", "Insira a sigla de um País válido!");
            alertHandler.getAlert().showAndWait();
            return;
        }

            equipaDAOImp.save(equipa);

            alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso!", "Equipa criada com sucesso!");
            alertHandler.getAlert().show();
    }

    /**
     * Ação ao clicar no botão "Voltar".
     *
     * @param event o evento de ação
     */
    @FXML
    void OnClickVoltarButton(ActionEvent event) {
        Stage s = (Stage) VoltarButton.getScene().getWindow();

        RedirecionarHelper.GotoSubMenuInsercoes().switchScene(s);
    }

    /**
     * Limpa os dados dos campos após a criação da equipa.
     */
    private void LimparDados(){
        GeneroChoice.setValue("-------");
        ModalidadeChoice.setValue("-------");
        Nome.clear();
        Pais.clear();
        AnoFundacao.clear();
    }
}
