package com.example.oporto_olympics.Controllers.ListagemEventos.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Equipas.AprovarInscricaoEquipaDAOImp;
import com.example.oporto_olympics.DAO.Equipas.ListarEquipasDAOImp;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.DAO.Eventos.InscricaonoEventoDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.DAO.XML.EquipaDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.*;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Controlador responsável por gerir a exibição dos dados de um evento em um card.
 * Esta classe preenche os rótulos e imagens do card com as informações do evento,
 * como o ano de edição, o local, o país, o logótipo e a mascote.
 */
public class ListagemEventosCardController {
    /**
     * Rótulo para mostrar o ano de edição do evento.
     */
    @FXML
    private Label AnoLabel;
    /**
     * Rótulo para mostrar o local do evento.
     */
    @FXML
    private Label LocalLabel;
    /**
     * Rótulo para mostrar o país do evento.
     */
    @FXML
    private Label PaisLabel;
    /**
     * Imagem para mostrar o logótipo do evento.
     */
    @FXML
    private ImageView img_logo;
    /**
     * Imagem para mostrar a mascote do evento.
     */
    @FXML
    private ImageView img_mascote;

    /**
     * Botão da interface gráfica associado à funcionalidade de inscrever equipas.
     */
    @FXML
    private Button InscreverEquipasButton;

    /**
     * Botão para alterar foto do Logotipo.
     */
    @FXML
    private Button AlterarLogoBtn;

    /**
     * Botão para alterar foto da Mascote.
     */
    @FXML
    private Button AlterarMascoteBtn;

    /**
     * Representa um evento específico de um card.
     */
    private Evento eventoEspecifico;

    /**
     * Obtém o evento específico associado.
     *
     * @return o evento específico atualmente associado a esta classe.
     */
    public Evento getEventoEspecifico() {
        return eventoEspecifico;
    }

    /**
     * Define o evento específico associado.
     *
     * @param eventoEspecifico o novo evento específico a associar a esta classe.
     */
    public void setEventoEspecifico(Evento eventoEspecifico) {
        this.eventoEspecifico = eventoEspecifico;
    }

    /**
     * Botão da interface gráfica associado à funcionalidade de inscrever o atleta.
     */
    @FXML
    private Button InscreverButton;

    /**
     * Preenche os dados do evento nos rótulos e imagens correspondentes.
     *
     * @param evento O objeto {@link Evento} contendo os dados do evento a serem preenchidos.
     * @throws SQLException Se ocorrer um erro na consulta da base de dados.
     */
    public void PreencherDados (Evento evento) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();
        LocaisDAOImp locaisDAO = new LocaisDAOImp(conexao);

        AnoLabel.setText(String.valueOf(evento.getAno_edicao()));
        PaisLabel.setText(evento.getPais());

        // Define o LocalLabel com o nome do local usando o ID
        String nomeLocal = locaisDAO.getNomeById(evento.getLocal_id());
        if (nomeLocal != null) {
            LocalLabel.setText(nomeLocal);
        } else {
            LocalLabel.setText("Local não encontrado");
        }

        byte[] logoBytes = evento.getLogo();

        if (logoBytes != null) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(logoBytes);
                Image image = new Image(inputStream);
                img_logo.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        byte[] mascoteBytes = evento.getMascote();

        if (mascoteBytes != null) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(mascoteBytes);
                Image image = new Image(inputStream);
                img_mascote.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();

        if(GestorSingle.getGestor() == null && AtletaSingle.getAtleta() != null){
            InscreverEquipasButton.setDisable(true);
            InscreverEquipasButton.setVisible(false);
            AlterarLogoBtn.setDisable(true);
            AlterarLogoBtn.setVisible(false);
            AlterarMascoteBtn.setDisable(true);
            AlterarMascoteBtn.setVisible(false);
        }

        if(GestorSingle.getGestor() != null && AtletaSingle.getAtleta() == null){
            InscreverButton.setDisable(true);
            InscreverButton.setVisible(false);
        }

        setEventoEspecifico(evento);
    }

    /**
     * Método responsável por gerir o processo de inscrição de equipas em modalidades de um evento.
     *
     * Este método é acionado ao clicar no botão "Inscrever Equipas". Ele permite selecionar uma modalidade,
     * listar as equipas disponíveis para essa modalidade, e inscrever as equipas escolhidas.
     *
     * @param event o evento associado à ação do botão.
     * @throws SQLException se ocorrer um erro ao aceder à base de dados.
     */
    @FXML
    void OnClickInscreverEquipasButton(ActionEvent event) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        AlertHandler alertHandler;

        Evento evento = getEventoEspecifico();

        ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

        AprovarInscricaoEquipaDAOImp aprovarInscricaoEquipaDAOImp = new AprovarInscricaoEquipaDAOImp(conexao);

        EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

        List<Modalidade> lstModalidades = modalidadeDAOImp.getAll();

        HashMap<String, Modalidade> ModalidadesHashMap = new HashMap<>();

        //Lista todas as Modalidades
        for (Modalidade modalidade : lstModalidades) {
            //Verifica se a modalidade já foi iniciada
            if(!modalidadeDAOImp.getStatusModalidade(evento.getId(),modalidade.getId()) && modalidade.getTipo().equals("Coletivo")){
                ModalidadesHashMap.put(modalidade.getNome() + " - " + modalidade.getTipo() + " - " + modalidade.getGenero(), modalidade);
            }
        }

        //Verifica se todas as modalidades já foram iniciadas
        if(ModalidadesHashMap.isEmpty()){
            alertHandler = new AlertHandler(Alert.AlertType.WARNING,"Modalidades Iniciadas", "Todas as modalidades deste evento já foram iniciadas!!");
            alertHandler.getAlert().showAndWait();
            return;
        }

        VBox vBox = new VBox(10);
        vBox.setStyle("-fx-padding: 20; -fx-background-color: #ffffff; -fx-border-color: black; -fx-border-width: 1;");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setMaxWidth(100);

        //ChoiceBox com todas as Modalidades Não Iniciadas
        choiceBox.setItems(FXCollections.observableArrayList(ModalidadesHashMap.keySet()));
        choiceBox.getItems().addAll("------");
        choiceBox.setValue("------");

        TableView<Equipa> tableView = new TableView<>();

        //Coluna "Nome" da Tabela
        TableColumn<Equipa, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setMinWidth(400);
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        // Coluna "Ação" para adicionar um botão de remover
        TableColumn<Equipa, Void> buttonColumn = new TableColumn<>("Ação");
        buttonColumn.setMinWidth(175);

        HashMap<Equipa, Integer> EquipasHashMap = new HashMap<>();

        // Define a célula da tabela com um botão
        buttonColumn.setCellFactory(column -> {
            return new TableCell<Equipa, Void>() {
                private final Button btn = new Button("Remover");

                {
                    // Configuração do botão para cada linha
                    btn.setOnAction(event -> {
                        Equipa equipa = getTableView().getItems().get(getIndex()); // Obtendo o item da linha atual
                        if (equipa != null) {

                            EquipasHashMap.remove(equipa);

                            getTableView().getItems().remove(equipa);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
        });

        // Adiciona as colunas à tabela
        tableView.getColumns().add(nomeColumn);
        tableView.getColumns().add(buttonColumn);

        List<Equipa> lstEquipas = equipaDAOImp.getAll();

        //Ao alterar a modalidade, lista novamente as equipas associadas a essa modalidade
        choiceBox.setOnAction(e -> {

            AlertHandler alertHandler2;

            EquipasHashMap.clear();

            tableView.getItems().clear();

            if(choiceBox.getValue().equals("------")){
                alertHandler2 = new AlertHandler(Alert.AlertType.WARNING,"Modalidade Inválida!!", "Selecione uma modalidade válida!!");
                alertHandler2.getAlert().showAndWait();
                return;
            }

            Modalidade modalidade = ModalidadesHashMap.get(choiceBox.getValue());

            String participantesEquipas = "\n ";

            for (Equipa equipa : lstEquipas) {

                ListarEquipasDAOImp listarEquipasDAOImp = new ListarEquipasDAOImp(conexao);

                Map<Integer, String> EquipasExistentesHashMap;

                boolean skipEquipaAtual = false;

                if (equipa.getModalidadeID() != modalidade.getId() || equipaDAOImp.getStatus(equipa.getId())) {
                    continue;
                }

                try {
                    EquipasExistentesHashMap = modalidadeDAOImp.getEquipasPorEvento(evento.getId(), modalidade.getId());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int equipaID : EquipasExistentesHashMap.keySet()) {
                    if (equipa.getId() == equipaID) {
                        skipEquipaAtual = true;
                        break;
                    }
                }

                if (skipEquipaAtual) {
                    continue;
                }

                List<AtletaInfo> Membroslst = listarEquipasDAOImp.getAtletasByEquipaId(equipa.getId());

                int membros = 0;

                if(Membroslst != null && !Membroslst.isEmpty()){
                    membros = Membroslst.size();
                }

                if(membros < modalidade.getMinParticipantes()){
                    participantesEquipas = participantesEquipas + equipa.getNome() + " - " + membros  + " de " + modalidade.getMinParticipantes() + " membros. \n";
                    continue;
                }

                EquipasHashMap.put(equipa, equipa.getId());
            }

            //Verifica se todas as equipas já foram associadas a um evento ou não existem equipas que participem do mesmo desporto da modalidade selecionada
            if(EquipasHashMap.isEmpty()){
                alertHandler2 = new AlertHandler(
                        Alert.AlertType.WARNING,
                        "Equipas Inexistentes!!",
                        "Todas as equipas já estão inscritas no evento,\n" +
                                "não há equipas que pratiquem este desporto\n" +
                                "ou nenhuma equipa possui o mínimo de membros!\n" +
                                participantesEquipas
                );

                alertHandler2.getAlert().getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alertHandler2.getAlert().getDialogPane().setMaxHeight(Region.USE_PREF_SIZE);
                alertHandler2.getAlert().showAndWait();
                return;
            }

            tableView.setItems(FXCollections.observableArrayList(EquipasHashMap.keySet()));
        });

        Stage inscreverEquipasStage = new Stage();

        Button btnInscrever = new Button("Inscrever Equipas");
        btnInscrever.setMaxWidth(200);
        btnInscrever.setMinWidth(200);
        btnInscrever.setOnAction(e -> {

            AlertHandler alertHandler2;

            if(choiceBox.getValue().equals("------")){
                alertHandler2 = new AlertHandler(Alert.AlertType.WARNING,"Modalidade Inválida!!", "Selecione uma modalidade válida!!");
                alertHandler2.getAlert().showAndWait();
                return;
            }

            if(EquipasHashMap.isEmpty()){
                alertHandler2 = new AlertHandler(
                        Alert.AlertType.WARNING,
                        "Equipas Inexistentes!!",
                        "Todas as equipas já estão inscritas no evento,\n" +
                                "não há equipas que pratiquem este desporto\n" +
                                "ou nenhuma equipa possui o mínimo de membros!\n"
                );

                alertHandler2.getAlert().getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alertHandler2.getAlert().getDialogPane().setMaxHeight(Region.USE_PREF_SIZE);
                alertHandler2.getAlert().showAndWait();
                return;
            }

            alertHandler2 = new AlertHandler(Alert.AlertType.CONFIRMATION, "Inscrever Equipas!", "Deseja inscrever " + EquipasHashMap.size()  + " equipa/s na modalidade " + choiceBox.getValue()  + "?");
            Optional<ButtonType> rs = alertHandler2.getAlert().showAndWait();

            if (rs.isPresent() && rs.get() != ButtonType.OK) {
                return;
            }

            Modalidade modalidade = ModalidadesHashMap.get(choiceBox.getValue());

            // Lista para armazenar equipas com conflitos de horário
            List<String> equipasComConflitos = new ArrayList<>();

            // Iterar sobre as equipas selecionadas
            for (int equipaId : EquipasHashMap.values()) {
                HorarioModalidade horariomodalidade;
                InscricaoEquipas equipa;

                try {
                    horariomodalidade = modalidadeDAOImp.getHorarioModalidadeById(modalidade.getId(), evento.getId());
                    equipa = aprovarInscricaoEquipaDAOImp.getEquipa(equipaId);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                // Verificar se existe conflito de horário
                if (VerificarConflito(
                        horariomodalidade.getDataHora(),
                        horariomodalidade.getDuracao(),
                        modalidadeDAOImp.getAllHorarioModalidadeByEquipa(equipaId))) {
                    // Adicionar o nome da equipa à lista de conflitos
                    equipasComConflitos.add(equipa.getNome());
                } else {
                    // Inscrever a equipa se não houver conflito
                    modalidadeDAOImp.saveParticipantesColetivo(equipaId, evento.getId(), modalidade.getId());
                }
            }

            // Verificar se houve conflitos
            if (!equipasComConflitos.isEmpty()) {
                // Construir uma mensagem com todas as equipas com conflitos
                StringBuilder mensagem = new StringBuilder("As seguintes equipas têm conflitos de horário:\n");
                for (String nomeEquipa : equipasComConflitos) {
                    mensagem.append("- ").append(nomeEquipa).append("\n");
                }
                alertHandler2 = new AlertHandler(Alert.AlertType.WARNING, "Horário Indisponível", mensagem.toString());
                alertHandler2.getAlert().showAndWait();

                // Retornar aqui para evitar a mensagem de sucesso
                return;
            }

            // Se não houve conflitos, exibir a mensagem de sucesso
            alertHandler2 = new AlertHandler(Alert.AlertType.INFORMATION, "Inscrições Bem Sucedidas", "As Equipas foram inscritas com Sucesso!!");
            alertHandler2.getAlert().showAndWait();


            inscreverEquipasStage.close();
        });

        vBox.getChildren().addAll(choiceBox,tableView,btnInscrever);

        inscreverEquipasStage.setScene(new Scene(vBox, 600, 400));
        inscreverEquipasStage.setTitle("Inscrever Equipas");
        inscreverEquipasStage.show();


        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();

        if(GestorSingle.getGestor() != null && AtletaSingle.getAtleta() == null){
            InscreverButton.setDisable(true);
            InscreverButton.setVisible(false);
        }

        setEventoEspecifico(evento);
    }
    /**
     * Método acionado ao clicar no botão "Inscrever", permitindo que um atleta se inscreva em modalidades disponíveis.
     *
     * Funcionalidades:
     * <ul>
     *   <li>Obtém o evento específico e as modalidades disponíveis (não iniciadas).</li>
     *   <li>Exibe uma tabela com as modalidades disponíveis e um botão para inscrição.</li>
     *   <li>Valida se já existe uma inscrição pendente ou aprovada antes de criar uma nova.</li>
     *   <li>Cria uma inscrição com estado "Pendente" e exibe mensagens de sucesso ou erro.</li>
     * </ul>
     *
     * @throws SQLException se ocorrer um erro ao acessar a base de dados.
     */
    @FXML
    public void OnClickInscreverButton() throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        InscricaonoEventoDAOImp inscreverEvento = new InscricaonoEventoDAOImp(conexao);
        ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);
        AtletaSingleton atletaSingle = AtletaSingleton.getInstance();
        Atleta atleta = atletaSingle.getAtleta();

        // Obter o evento específico
        Evento evento = getEventoEspecifico();

        // Obter modalidades não iniciadas do evento
        List<Modalidade> lstModalidades = modalidadeDAOImp.getAll();
        List<Modalidade> modalidadesDisponiveis = new ArrayList<>();

        for (Modalidade modalidade : lstModalidades) {
            if (!modalidadeDAOImp.getStatusModalidade(evento.getId(), modalidade.getId()) && atleta.getGenero().equals(modalidade.getGenero())) {
                modalidadesDisponiveis.add(modalidade);
            }
        }

        // Verificar se há modalidades disponíveis
        if (modalidadesDisponiveis.isEmpty()) {
            Alert noModalidadesAlert = new Alert(Alert.AlertType.WARNING, "Não há modalidades disponíveis para inscrição neste evento.");
            noModalidadesAlert.show();
            return;
        }

        // Construir a interface com uma tabela
        VBox vBox = new VBox(10);
        vBox.setStyle("-fx-padding: 20; -fx-background-color: #ffffff; -fx-border-color: black; -fx-border-width: 1;");

        TableView<Modalidade> tableView = new TableView<>();
        tableView.setItems(FXCollections.observableArrayList(modalidadesDisponiveis));

        // Coluna Nome da Modalidade
        TableColumn<Modalidade, String> nomeColumn = new TableColumn<>("Nome da Modalidade");
        nomeColumn.setMinWidth(250);
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        // Coluna Gênero
        TableColumn<Modalidade, String> generoColumn = new TableColumn<>("Gênero");
        generoColumn.setMinWidth(150);
        generoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));

        // Coluna Ação (Botão Inscrever)
        TableColumn<Modalidade, Void> actionColumn = new TableColumn<>("Ação");
        actionColumn.setMinWidth(150);
        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Inscrever");

            {
                btn.setOnAction(event -> {
                    Modalidade modalidade = getTableView().getItems().get(getIndex());

                    int modalidadeId = modalidade.getId();
                    int atletaId = atleta.getId();
                    int eventoId = evento.getId();

                    try {
                        if (inscreverEvento.existeInscricaoPendente(atletaId, eventoId, modalidadeId)) {
                            Alert pendenteAlert = new Alert(Alert.AlertType.WARNING, "Já existe um pedido pendente para esta modalidade.");
                            pendenteAlert.show();
                        } else if (inscreverEvento.existeInscricaoAprovada(atletaId, eventoId, modalidadeId)) {
                            Alert aprovadoAlert = new Alert(Alert.AlertType.WARNING, "Você já está inscrito nesta modalidade.");
                            aprovadoAlert.show();
                        } else {
                            String estado = "Pendente";

                            if(!inscreverEvento.verificarInscricao(eventoId,atletaId,modalidadeId)) {
                                inscreverEvento.inserirInscricao(estado, eventoId, atletaId, modalidadeId);

                                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Inscrição pendente criada com sucesso. Aguarde aprovação.");
                                successAlert.showAndWait();

                                // Opcional: remover a modalidade da tabela após inscrição
                                getTableView().getItems().remove(modalidade);
                            } else {
                                Alert erroAlert = new Alert(Alert.AlertType.INFORMATION, "Inscrição não foi criada...");
                                erroAlert.showAndWait();
                            }

                        }
                    } catch (RuntimeException ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Erro ao realizar inscrição: " + ex.getMessage());
                        errorAlert.show();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        // Adicionar colunas à tabela
        tableView.getColumns().addAll(nomeColumn, generoColumn, actionColumn);

        // Adicionar tabela ao layout
        vBox.getChildren().add(tableView);

        // Mostrar a janela
        Stage inscreverStage = new Stage();
        inscreverStage.setScene(new Scene(vBox, 600, 400));
        inscreverStage.setTitle("Modalidades Disponíveis para Inscrição");
        inscreverStage.show();
    }

    /**
     * Manipulador de eventos para o botão "Alterar Logo".
     *
     * Este método é chamado quando o utilizador clica no botão para alterar o Logotipo de um evento.
     * Ele permite que o utilizador selecione uma nova imagem através de um explorador de ficheiros,
     * converte a imagem selecionada em um array de bytes e atualiza a base de dados com o novo Logotipo.
     * Além disso, a interface gráfica é atualizada para exibir o novo Logotipo.
     *
     */
    @FXML
    void OnAlterarLogoButton() {
        Evento evento = getEventoEspecifico();

        // Abrir explorador de ficheiros
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione uma imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(AlterarLogoBtn.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Converter imagem para byte[]
                byte[] fotoLogo = Files.readAllBytes(selectedFile.toPath());

                // Atualizar no modelo Atleta
                evento.setLogo(fotoLogo);

                // Atualizar na base de dados
                ConnectionBD conexaoBD = ConnectionBD.getInstance();
                Connection conexao = conexaoBD.getConexao();
                EventosDAOImp EventosDAO = new EventosDAOImp(conexao);
                EventosDAO.updateLogotipo(evento.getId(), fotoLogo);

                // Atualizar na interface
                ByteArrayInputStream inputStream = new ByteArrayInputStream(fotoLogo);
                Image novaImagem = new Image(inputStream);
                img_logo.setImage(novaImagem);

                // Notificar sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Logotipo atualizado com sucesso!");
                alert.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar a imagem: " + e.getMessage());
                alert.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao atualizar a imagem na base de dados: " + e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nenhuma imagem foi selecionada.");
            alert.show();
        }
    }

    /**
     * Manipulador de eventos para o botão "Alterar Mascote".
     *
     * Este método é chamado quando o utilizador clica no botão para alterar a Mascote de um evento.
     * Ele permite que o utilizador selecione uma nova imagem através de um explorador de ficheiros,
     * converte a imagem selecionada em um array de bytes e atualiza a base de dados com a nova foto/mascote.
     * Além disso, a interface gráfica é atualizada para exibir a nova mascote.
     *
     */
    @FXML
    void OnAlterarMascoteButton() {
        Evento evento = getEventoEspecifico();

        // Abrir explorador de ficheiros
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione uma imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(AlterarMascoteBtn.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Converter imagem para byte[]
                byte[] mascote = Files.readAllBytes(selectedFile.toPath());

                // Atualizar no modelo Atleta
                evento.setMascote(mascote);

                // Atualizar na base de dados
                ConnectionBD conexaoBD = ConnectionBD.getInstance();
                Connection conexao = conexaoBD.getConexao();
                EventosDAOImp EventosDAO = new EventosDAOImp(conexao);
                EventosDAO.updateMascote(evento.getId(), mascote);

                // Atualizar na interface
                ByteArrayInputStream inputStream = new ByteArrayInputStream(mascote);
                Image novaMascote = new Image(inputStream);
                img_mascote.setImage(novaMascote);

                // Notificar sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Mascote atualizado com sucesso!");
                alert.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar a imagem: " + e.getMessage());
                alert.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao atualizar a imagem na base de dados: " + e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nenhuma imagem foi selecionada.");
            alert.show();
        }
    }

    /**
     * Verifica se o horário da modalidade que a equipa está a ser inscrita entra em conflito com modalidades que ela já esteja inscrita.
     *
     * @param inicioModalidade A data e hora de início da modalidade a ser verificada.
     * @param duracaoModalidade A duração da modalidade a ser verificada.
     * @param listaHorarios A lista de horários de outras modalidades para verificar possíveis conflitos.
     * @return {@code true} se for encontrado um conflito de horários; {@code false} caso contrário.
     */
    public boolean VerificarConflito(LocalDateTime inicioModalidade, LocalTime duracaoModalidade, List<HorarioModalidade> listaHorarios) {
        LocalDateTime dataHoraFim = inicioModalidade.plus(Duration.ofHours(duracaoModalidade.getHour())
                .plusMinutes(duracaoModalidade.getMinute())
                .plusSeconds(duracaoModalidade.getSecond()));

        for (HorarioModalidade horario : listaHorarios) {
            LocalDateTime horarioInicio = horario.getDataHora();
            LocalDateTime horarioFim = horarioInicio.plus(Duration.ofHours(horario.getDuracao().getHour())
                    .plusMinutes(horario.getDuracao().getMinute())
                    .plusSeconds(horario.getDuracao().getSecond()));

            // Ajustes nos horários
            LocalDateTime horarioInicioAjustado = horarioInicio.minusHours(2);
            LocalDateTime horarioFimAjustado = horarioFim.plusHours(2);

            // Verificação de conflito
            if ((inicioModalidade.isBefore(horarioFimAjustado) && dataHoraFim.isAfter(horarioInicioAjustado))) {
                return true;
            }
        }
        return false;
    }
}
