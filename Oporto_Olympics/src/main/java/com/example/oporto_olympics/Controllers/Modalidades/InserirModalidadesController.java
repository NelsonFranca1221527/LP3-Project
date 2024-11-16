package com.example.oporto_olympics.Controllers.Modalidades;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Evento;
import com.example.oporto_olympics.Models.Local;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
/**
 * Classe de controlador para a interface de inserção de modalidades.
 * Controla a criação de novas modalidades no sistema, gerindo a interface e a comunicação com a base de dados.
 */
public class InserirModalidadesController {

    /**
     * Botão para criar uma nova modalidade.
     */
    @FXML
    private Button CriarModalidadeButton;

    /**
     * Campo de texto para a descrição da modalidade.
     */
    @FXML
    private TextArea Descricao;

    /**
     * Campo de escolha para selecionar o género da modalidade.
     */
    @FXML
    private ChoiceBox<String> GeneroChoice;

    /**
     * Campo de entrada para definir o número mínimo de participantes.
     */
    @FXML
    private TextField MinParticipantes;

    /**
     * Campo de entrada para o nome da modalidade.
     */
    @FXML
    private TextField Nome;

    /**
     * Campo de escolha para selecionar o evento associado à modalidade.
     */
    @FXML
    private ChoiceBox<String> EventoChoice;

    /**
     * Campo de escolha para selecionar a quantidade de jogos.
     */
    @FXML
    private ChoiceBox<String> QuantJogosChoice;

    /**
     * Campo de texto para inserir as regras da modalidade.
     */
    @FXML
    private TextArea Regras;

    /**
     * Campo de escolha para selecionar o tipo da modalidade (Individual ou Coletivo).
     */
    @FXML
    private ChoiceBox<String> TipoChoice;

    /**
     * Campo de escolha para selecionar a unidade de medida para pontuação da modalidade.
     */
    @FXML
    private ChoiceBox<String> UniMedidaChoice;

    /**
     * Botão para voltar para o ecrã anterior.
     */
    @FXML
    private Button VoltarButton;

    /**
     * Mapa para armazenar as opções de género.
     */
    private HashMap<String, String> generoMap = new HashMap<>();

    /**
     * Mapa para armazenar os tipos de modalidade.
     */
    private HashMap<String, String> tipoMap = new HashMap<>();

    /**
     * Mapa para armazenar as quantidades de jogos.
     */
    private HashMap<String, String> QuantJogosMap = new HashMap<>();

    /**
     * Mapa para armazenar as unidades de medida.
     */
    private HashMap<String, String> UniMedidaMap = new HashMap<>();

    /**
     * Mapa para armazenar os eventos e os seus respetivos IDs.
     */
    private HashMap<String, Integer> EventoMap = new HashMap<>();

    /**
     * Método de inicialização para configurar os campos e carregar dados da base de dados.
     * @throws SQLException se ocorrer um erro de SQL ao tentar ligar à base de dados.
     */
    public void initialize() throws SQLException {

        MinParticipantes.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d*")) ? change : null
        ));

        GeneroChoice.getItems().add("-------");
        TipoChoice.getItems().add("-------");
        QuantJogosChoice.getItems().add("-------");
        UniMedidaChoice.getItems().add("-------");

        generoMap.put("Homem", "Men");
        generoMap.put("Mulher", "Women");

        tipoMap.put("Individual", "Individual");
        tipoMap.put("Coletivo", "Coletivo");

        QuantJogosMap.put("Um", "One");
        QuantJogosMap.put("Vários", "Multiple");

        UniMedidaMap.put("Tempo", "Tempo");
        UniMedidaMap.put("Pontos", "Pontos");

        GeneroChoice.setItems(FXCollections.observableArrayList(generoMap.keySet()));
        TipoChoice.setItems(FXCollections.observableArrayList(tipoMap.keySet()));
        QuantJogosChoice.setItems(FXCollections.observableArrayList(QuantJogosMap.keySet()));
        UniMedidaChoice.setItems(FXCollections.observableArrayList(UniMedidaMap.keySet()));

        GeneroChoice.setValue("-------");
        TipoChoice.setValue("-------");
        QuantJogosChoice.setValue("-------");
        UniMedidaChoice.setValue("-------");

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
                    EventoMap.put(local.getNome() + " - " + evento.getAno_edicao(), evento.getId());
                }
            }
        }

        EventoChoice.setItems(FXCollections.observableArrayList(EventoMap.keySet()));
        EventoChoice.setValue("-------");
    }

    /**
     * Ação ao clicar no botão de criar modalidade. Verifica os dados e guarda a nova modalidade na base de dados.
     * @param event evento de clique.
     * @throws SQLException se ocorrer um erro de SQL durante a inserção.
     */
    @FXML
    void OnClickCriarModalidadeButton(ActionEvent event) throws SQLException {
        AlertHandler alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Modalidade a Inserir", "Deseja inserir a modalidade preenchida?");
        Optional<ButtonType> rs = alertHandler.getAlert().showAndWait();

        if (rs.isPresent() && rs.get() != ButtonType.OK) {
            return;
        }

        if (tipoMap.get(TipoChoice.getValue()) == null ||
                generoMap.get(GeneroChoice.getValue()) == null ||
                Nome.getText().trim().isEmpty() ||
                Descricao.getText().trim().isEmpty() ||
                MinParticipantes.getText().trim().isEmpty() ||
                Regras.getText().trim().isEmpty() ||
                UniMedidaMap.get(UniMedidaChoice.getValue()) == null ||
                QuantJogosMap.get(QuantJogosChoice.getValue()) == null ||
                EventoChoice.getValue() == null) {

            alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Dados Vazios", "Preencha todos os dados para inserir a modalidade");
            alertHandler.getAlert().showAndWait();
            return;
        }

        String tipo = tipoMap.get(TipoChoice.getValue());
        String genero = generoMap.get(GeneroChoice.getValue());
        String nome = Nome.getText();
        String descricao = Descricao.getText();
        String medida = UniMedidaMap.get(UniMedidaChoice.getValue());
        String quant = QuantJogosMap.get(QuantJogosChoice.getValue());
        String regras = Regras.getText();
        int minParticipantes = Integer.parseInt(MinParticipantes.getText());
        int eventoID = EventoMap.get(EventoChoice.getValue());

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        Modalidade modalidade = new Modalidade(0, tipo, genero, nome, descricao, minParticipantes, medida, quant, null, new RegistoPontos("", 0, ""), new RegistoPontos("", 0, ""), regras);

        ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

        Modalidade ModalidadeExistente = modalidadeDAOImp.getModalidadeByNomeGeneroTipo(modalidade.getNome(), modalidade.getGenero(), modalidade.getTipo());

        if (ModalidadeExistente != null) {

            if (ModalidadeExistente.getListEventosID().contains(eventoID)) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Modalidade Existente", "A Modalidade " + modalidade.getNome() + ", Género: " + modalidade.getGenero() + " já encontra-se registada no evento selecionado!");
                alertHandler.getAlert().showAndWait();
                return;
            }

            modalidadeDAOImp.saveEventos_Modalidades(eventoID, ModalidadeExistente.getId());
            return;
        }

        modalidadeDAOImp.save(modalidade);
        modalidadeDAOImp.saveEventos_Modalidades(eventoID, ModalidadeExistente.getId());

        alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso", "Modalidade inserida com sucesso!");
        alertHandler.getAlert().showAndWait();

        LimparDados();
    }

    /**
     * Ação ao clicar no botão de voltar. Retorna ao ecrã anterior.
     * @param event evento de clique.
     */
    @FXML
    void OnClickVoltarButton(ActionEvent event) {
        Stage s = (Stage) VoltarButton.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }

    /**
     * Limpa todos os campos de entrada e redefine as seleções padrão.
     */
    private void LimparDados() {
        TipoChoice.setValue("-------");
        GeneroChoice.setValue("-------");
        Nome.clear();
        Descricao.clear();
        UniMedidaChoice.setValue("-------");
        QuantJogosChoice.setValue("-------");
        Regras.clear();
        MinParticipantes.clear();
        EventoChoice.setValue("-------");
    }
}
