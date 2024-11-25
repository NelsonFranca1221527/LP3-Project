package com.example.oporto_olympics.Controllers.ListagemEventos.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.DAO.XML.EquipaDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.Equipa;
import com.example.oporto_olympics.Models.Evento;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

        List<Modalidade> lstModalidades = modalidadeDAOImp.getAll();

        HashMap<String, Integer> ModalidadesHashMap = new HashMap<>();

        //Lista todas as Modalidades
        for (Modalidade modalidade : lstModalidades) {
            //Verifica se a modalidade já foi iniciada
            if(!modalidadeDAOImp.getStatusModalidade(evento.getId(),modalidade.getId())){
                ModalidadesHashMap.put(modalidade.getNome() + " - " + modalidade.getTipo() + " - " + modalidade.getGenero(), modalidade.getId());
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

            int modalidadeID = ModalidadesHashMap.get(choiceBox.getValue());

            for (Equipa equipa : lstEquipas) {

                Map<Integer, String> EquipasExistentesHashMap;

                boolean skipEquipaAtual = false;

                if (equipa.getModalidadeID() != modalidadeID) {
                    continue;
                }

                try {
                    EquipasExistentesHashMap = modalidadeDAOImp.getEquipasPorEvento(evento.getId(), modalidadeID);
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

                EquipasHashMap.put(equipa, equipa.getId());
            }

            //Verifica se todas as equipas já foram associadas a um evento ou não existem equipas que participem do mesmo desporto da modalidade selecionada
            if(EquipasHashMap.isEmpty()){
                alertHandler2 = new AlertHandler(Alert.AlertType.WARNING,"Equipas Inexistentes!!", "Todas as equipas desta modalidade já estão associadas a este evento, ou não há equipas que pratiquem o mesmo desporto!!");
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

            alertHandler2 = new AlertHandler(Alert.AlertType.CONFIRMATION, "Inscrever Equipas!", "Deseja inscrever " + EquipasHashMap.size()  + " equipa/s na modalidade " + choiceBox.getValue()  + "?");
            Optional<ButtonType> rs = alertHandler2.getAlert().showAndWait();

            if (rs.isPresent() && rs.get() != ButtonType.OK) {
                return;
            }

            int modalidadeID = ModalidadesHashMap.get(choiceBox.getValue());

            //Associa todas as equipas selecionadas ao evento e modalidade escolhidas
            for(int equipaId : EquipasHashMap.values()){
                modalidadeDAOImp.saveParticipantesColetivo(equipaId,evento.getId(), modalidadeID);
            }

            alertHandler2 = new AlertHandler(Alert.AlertType.INFORMATION,"Inscrições Bem Sucedidas", "As Equipas foram inscritas com Sucesso!!");
            alertHandler2.getAlert().showAndWait();

            inscreverEquipasStage.close();
        });

        vBox.getChildren().addAll(choiceBox,tableView,btnInscrever);

        inscreverEquipasStage.setScene(new Scene(vBox, 600, 400));
        inscreverEquipasStage.setTitle("Inscrever Equipas");
        inscreverEquipasStage.show();

    }
}
