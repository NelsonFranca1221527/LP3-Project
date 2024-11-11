package com.example.oporto_olympics.Controllers.Equipas;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.DAO.XML.EquipaDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Equipa;
import com.example.oporto_olympics.Models.Evento;
import com.example.oporto_olympics.Models.Local;
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

public class InserirEquipasController {

    @FXML
    private TextField AnoFundacao;

    @FXML
    private Button CriarEquipaButton;

    @FXML
    private ChoiceBox<String> EventoChoice;

    @FXML
    private ChoiceBox<String> GeneroChoice;

    @FXML
    private ChoiceBox<String> ModalidadeChoice;

    @FXML
    private TextField Nome;

    @FXML
    private TextField Pais;

    @FXML
    private Button VoltarButton;

    /**
     * Mapa para armazenar as opções de género.
     */
    private HashMap<String, String> generoMap = new HashMap<>();

    /**
     * Mapa para armazenar os eventos e os respetivos IDs.
     */
    private HashMap<String, Integer> eventoMap = new HashMap<>();

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

        EventosDAOImp eventosDAOImp = new EventosDAOImp(conexao);
        List<Evento> eventoList = eventosDAOImp.getAll();

        LocaisDAOImp locaisDAOImp = new LocaisDAOImp(conexao);
        List<Local> localList = locaisDAOImp.getAll();

        EventoChoice.getItems().add("-------");

        for (Local local : localList) {
            for (Evento evento : eventoList) {
                if (local.getId() == evento.getLocal_id()) {
                    eventoMap.put(local.getNome() + " - " + evento.getAno_edicao(), evento.getId());
                }
            }
        }

        Tooltip tooltip = new Tooltip("Para selecionar uma modalidade, deve primeiro escolher um género e um evento.");
        Tooltip.install(ModalidadeChoice, tooltip);

        EventoChoice.setItems(FXCollections.observableArrayList(eventoMap.keySet()));
        EventoChoice.setValue("-------");

        ModalidadeChoice.getItems().add("-------");
        ModalidadeChoice.setValue("-------");

        EventoChoice.getSelectionModel().selectedItemProperty().addListener((observableEvento, oldValueEvento, newValueEvento) -> {
            try {
                AtualizarModalidadeChoice(newValueEvento, GeneroChoice.getSelectionModel().getSelectedItem(), conexao);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        GeneroChoice.getSelectionModel().selectedItemProperty().addListener((observableGenero, oldValueGenero, newValueGenero) -> {
            try {
                AtualizarModalidadeChoice(EventoChoice.getSelectionModel().getSelectedItem(), newValueGenero, conexao);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Atualiza as opções de modalidades com base no evento e género selecionados.
     *
     * @param Evento o evento selecionado
     * @param Genero o género selecionado
     * @param conexao a conexão com a base de dados
     * @throws SQLException se ocorrer um erro ao acessar a base de dados
     */
    private void AtualizarModalidadeChoice(String Evento, String Genero, Connection conexao) throws SQLException {

        ModalidadeChoice.getItems().removeIf(item -> !item.equals("-------"));
        ModalidadeChoice.setValue("-------");
        modalidadeMap.clear();

        if (!Evento.equals("-------") && !Genero.equals("-------")) {

            int EventoID = eventoMap.get(Evento);

            ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

            List<Modalidade> lst = modalidadeDAOImp.getAll();

            for (Modalidade modalidade : lst) {

                if (modalidade.getListEventosID().contains(EventoID) && !EventoChoice.getValue().equals("-------") && modalidade.getGenero().equals(generoMap.get(Genero))) {
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
                eventoMap.get(EventoChoice.getValue()) == null ||
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

        if (EquipaExiste.isPresent() && equipa.getPais().equals(EquipaExiste.get().getPais()) && equipa.getModalidadeID() == EquipaExiste.get().getModalidadeID() ) {
            alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Equipa Existente", "A Equipa " + equipa.getNome() + " já encontra-se registada no sistema!");
            alertHandler.getAlert().showAndWait();
            return;
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

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }

    /**
     * Limpa os dados dos campos após a criação da equipa.
     */
    private void LimparDados(){
        EventoChoice.setValue("-------");
        GeneroChoice.setValue("-------");
        ModalidadeChoice.setValue("-------");
        Nome.clear();
        Pais.clear();
        AnoFundacao.clear();
    }
}
