package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.DAO.EventosDAOImp;
import com.example.oporto_olympics.Controllers.DAO.LocaisDAOImp;
import com.example.oporto_olympics.Controllers.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.Controllers.DAO.XML.EquipaDAOImp;
import com.example.oporto_olympics.Controllers.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Controllers.Helper.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.CardController.AtletaCardController;
import com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.CardController.EquipaCardController;
import com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.CardController.ModalidadeCardController;
import com.example.oporto_olympics.Controllers.Misc.AlertHandler;
import com.example.oporto_olympics.Controllers.Singleton.InserçãoXMLSingleton;
import com.example.oporto_olympics.HelloApplication;
import com.example.oporto_olympics.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InserçãoXMLController {

    @FXML
    private VBox Container;

    @FXML
    private ChoiceBox<String> EventoChoice;

    @FXML
    private Group EventoGroup;

    @FXML
    private Button InserirButton;

    @FXML
    private Button SelecionarXMLButton;

    @FXML
    private Button VoltarButton;

    @FXML
    private Label tituloTipoXML;

    private Map<String, Integer> itemMap = new HashMap<>();

    private String AtletaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/athletes_xsd.xml";
    private String EquipaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/teams_xsd.xml";
    private String ModalidadeXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/sports_xsd.xml";

    private List<Atleta> ListaAtletas;
    private List<Equipa> ListaEquipas;
    private List<Modalidade> ListaModalidades;

    public List<Atleta> getListaAtletas() {
        return ListaAtletas;
    }

    public void setListaAtletas(List<Atleta> listaAtletas) {
        ListaAtletas = listaAtletas;
    }

    public List<Equipa> getListaEquipas() {
        return ListaEquipas;
    }

    public void setListaEquipas(List<Equipa> listaEquipas) {
        ListaEquipas = listaEquipas;
    }

    public List<Modalidade> getListaModalidades() {
        return ListaModalidades;
    }

    public void setListaModalidades(List<Modalidade> listaModalidades) {
        ListaModalidades = listaModalidades;
    }

    /**
     * Inicializa o controlador, ajustando a interface com base no tipo de XML
     * a ser inserido (Atleta, Equipa ou Modalidade).
     */
    public void initialize() throws SQLException {

        itemMap.clear();

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        EventosDAOImp eventosDAOImp = new EventosDAOImp(conexao);
        List<Evento> eventoList = eventosDAOImp.getAll();

        LocaisDAOImp locaisDAOImp = new LocaisDAOImp(conexao);
        List<Local> localList = locaisDAOImp.getAll();

        switch (inserçãoXMLSingleton.getTipoXML()){
            case "Atleta":
                EventoGroup.setVisible(false);
                tituloTipoXML.setText(tituloTipoXML.getText() + " um Atleta");
                return;
            case "Equipa":
                tituloTipoXML.setText(tituloTipoXML.getText() + " uma Equipa");
                break;
            case "Modalidade":
                tituloTipoXML.setText(tituloTipoXML.getText() + " uma Modalidade");
                break;
        }

        EventoChoice.getItems().add("-------");

        for (int i = 0; i < localList.size(); i++) {
            Local local = localList.get(i);
            for (int j = 0; j < eventoList.size(); j++) {
                Evento evento = eventoList.get(j);

                if(local.getId() == evento.getLocal_id()){
                    EventoChoice.getItems().add(local.getNome() + " - " + evento.getAno_edicao());
                    itemMap.put(local.getNome() + " - " + evento.getAno_edicao(), evento.getId());
                }

            }
        }

        EventoChoice.setValue("-------");
    }

    /**
     *
     * Abre um seletor de ficheiros para escolher um ficheiro XML e valida
     * o ficheiro com base no tipo de XSD correspondente.
     *
     * @param event O evento de ação gerado ao clicar no botão.
     * @throws ParserConfigurationException Se houver um erro na configuração do parser.
     * @throws IOException Se ocorrer um erro ao ler o ficheiro.
     * @throws SAXException Se ocorrer um erro durante a validação do XML.
     */
    @FXML
    void OnClickSelecionarXMLButton(ActionEvent event) throws ParserConfigurationException, IOException, SAXException, SQLException {

        AlertHandler alertHandler;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o seu Ficheiro XML!");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.xml")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) {
            return;
        }

        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        LerXMLController lerXMLController = new LerXMLController();

        boolean valido = false;

        switch (inserçãoXMLSingleton.getTipoXML()){
            case "Atleta":
                valido = VerificarXML(selectedFile, AtletaXSDPath);
                if (valido) {

                    setListaAtletas(lerXMLController.LerXMLAtleta(selectedFile));

                    VisualizarAtletas(getListaAtletas());

                }else {
                    alertHandler = new AlertHandler(Alert.AlertType.ERROR,"Ficheiro XML Inválido!!!", "Insira um ficheiro XML de Atletas Válido!!");
                    alertHandler.getAlert().showAndWait();
                }
                break;
            case "Equipa":
                valido = VerificarXML(selectedFile, EquipaXSDPath);
                if (valido) {
                    if(EventoChoice.getValue().equals("-------")){
                        alertHandler = new AlertHandler(Alert.AlertType.WARNING,"Selecione um Evento!!!", "Para inserir uma ou mais modalidades deve inserir um evento");
                        alertHandler.getAlert().showAndWait();
                        return;
                    }

                    int IDEvento = itemMap.get(EventoChoice.getValue());

                    setListaEquipas(lerXMLController.LerXMLEquipa(selectedFile, IDEvento));

                    VisualizarEquipas(getListaEquipas());

                }else {
                    alertHandler = new AlertHandler(Alert.AlertType.ERROR,"Ficheiro XML Inválido!!!", "Insira um ficheiro XML de Equipas Válido!!");
                    alertHandler.getAlert().showAndWait();
                }
                break;
            case "Modalidade":
                valido = VerificarXML(selectedFile, ModalidadeXSDPath);
                if (valido) {

                    if(EventoChoice.getValue().equals("-------")){
                        alertHandler = new AlertHandler(Alert.AlertType.WARNING,"Selecione um Evento!!!", "Para inserir uma ou mais modalidades deve inserir um evento");
                        alertHandler.getAlert().showAndWait();
                        return;
                    }

                    int IDEvento = itemMap.get(EventoChoice.getValue());

                    setListaModalidades(lerXMLController.LerXMLModalidade(selectedFile, IDEvento));

                    VisualizarModalidades(getListaModalidades());

                }else {

                    alertHandler = new AlertHandler(Alert.AlertType.ERROR,"Ficheiro XML Inválido!!!", "Insira um ficheiro XML de Modalidades Válido!!");
                    alertHandler.getAlert().showAndWait();
                }
        }
    }

    /**
     * Método chamado quando o botão de inserção é clicado.
     * Apresenta uma confirmação para inserir atletas, equipas ou modalidades,
     * dependendo do tipo de XML selecionado.
     *
     * @param event O evento de ação que disparou este método.
     * @throws SQLException Se ocorrer um erro ao acessar a base de dados.
     */
    @FXML
    void OnClickInserirButton(ActionEvent event) throws SQLException {
        AlertHandler alertHandler;

        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        switch (inserçãoXMLSingleton.getTipoXML()) {
            case "Atleta":
                alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Atletas por Inserir!!!", "Deseja inserir os atletas?");
                Optional<ButtonType> rs1 = alertHandler.getAlert().showAndWait();

                if (rs1.isPresent() && rs1.get() == ButtonType.OK) {
                    InserirAtletas(getListaAtletas());
                }
                break;
            case "Equipa":
                alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Equipas por Inserir!!!", "Deseja inserir as equipas?");
                Optional<ButtonType> rs2 = alertHandler.getAlert().showAndWait();

                if (rs2.isPresent() && rs2.get() == ButtonType.OK) {
                    InserirEquipas(getListaEquipas());
                }
                break;
            case "Modalidade":
                alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Modalidades por Inserir!!!", "Deseja inserir as modalidades?");
                Optional<ButtonType> rs3 = alertHandler.getAlert().showAndWait();

                if (rs3.isPresent() && rs3.get() == ButtonType.OK) {
                    InserirModalidades(getListaModalidades());
                }
                break;
        }

        Stage s = (Stage) InserirButton.getScene().getWindow();
        RedirecionarHelper.GotoSeleçãoXML().switchScene(s);
    }

    /**
     * Visualiza a lista de atletas, criando e adicionando cards
     * para cada atleta na interface do utilizador.
     *
     * @param lst A lista de atletas a ser visualizada.
     */
    private void VisualizarAtletas(List<Atleta> lst) {
        for (Atleta atleta : lst) {
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/ImportaçõesXML/InserçãoXML/Cards/AtletasCard.fxml"));
                Pane PaneAtleta = loader.load();
                AtletaCardController cardsController = loader.getController();
                cardsController.preencherDados(atleta);

                PaneAtleta.setUserData(atleta);

                if (atleta.getGenero().equals("Men")) {
                    PaneAtleta.setStyle("-fx-background-color: #87ceeb; -fx-border-color: #eee; -fx-border-width: 5px;");
                } else {
                    PaneAtleta.setStyle("-fx-background-color: #f2a2ee; -fx-border-color: #eee; -fx-border-width: 5px;");
                }

                Container.getChildren().add(PaneAtleta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Visualiza a lista de equipas, criando e adicionando cards
     * para cada equipa na interface do utilizador.
     *
     * @param lst A lista de equipas a ser visualizada.
     */
    private void VisualizarEquipas(List<Equipa> lst) {
        for (Equipa equipa : lst) {
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/ImportaçõesXML/InserçãoXML/Cards/EquipasCard.fxml"));
                Pane PaneAtleta = loader.load();
                EquipaCardController cardsController = loader.getController();
                cardsController.preencherDados(equipa);

                PaneAtleta.setUserData(equipa);

                if (equipa.getGenero().equals("Men")) {
                    PaneAtleta.setStyle("-fx-background-color: #87ceeb; -fx-border-color: #eee; -fx-border-width: 5px;");
                } else {
                    PaneAtleta.setStyle("-fx-background-color: #f2a2ee; -fx-border-color: #eee; -fx-border-width: 5px;");
                }

                Container.getChildren().add(PaneAtleta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Visualiza a lista de modalidades, criando e adicionando cards
     * para cada modalidade na interface do utilizador.
     *
     * @param lst A lista de modalidades a ser visualizada.
     */
    private void VisualizarModalidades(List<Modalidade> lst) {
        for (Modalidade modalidade : lst) {
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/ImportaçõesXML/InserçãoXML/Cards/ModalidadesCard.fxml"));
                Pane PaneAtleta = loader.load();
                ModalidadeCardController cardsController = loader.getController();
                cardsController.preencherDados(modalidade);

                PaneAtleta.setUserData(modalidade);

                if (modalidade.getGenero().equals("Men")) {
                    PaneAtleta.setStyle("-fx-background-color: #87ceeb; -fx-border-color: #eee; -fx-border-width: 5px;");
                } else {
                    PaneAtleta.setStyle("-fx-background-color: #f2a2ee; -fx-border-color: #eee; -fx-border-width: 5px;");
                }

                Container.getChildren().add(PaneAtleta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Insere a lista de atletas na base de dados.
     *
     * @param lst A lista de atletas a ser inserida.
     * @throws SQLException Se ocorrer um erro ao acessar a base de dados.
     */
    private void InserirAtletas(List<Atleta> lst) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        AtletaDAOImp atletaDAOImp = new AtletaDAOImp(conexao);

        for (Atleta atleta : lst) {
            atletaDAOImp.save(atleta);
        }

        AlertHandler alertHandler;
        alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso", "Atleta/s insirado/s com Sucesso!");
        alertHandler.getAlert().showAndWait();
    }

    /**
     * Insere a lista de equipas na base de dados.
     *
     * @param lst A lista de equipas a ser inserida.
     * @throws SQLException Se ocorrer um erro ao acessar a base de dados.
     */
    private void InserirEquipas(List<Equipa> lst) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

        for (Equipa equipa : lst) {
            equipaDAOImp.save(equipa);
        }

        AlertHandler alertHandler;
        alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso", "Equipa/s insirada/s com Sucesso!");
        alertHandler.getAlert().showAndWait();
    }

    /**
     * Insere a lista de modalidades na base de dados.
     *
     * @param lst A lista de modalidades a ser inserida.
     * @throws SQLException Se ocorrer um erro ao acessar a base de dados.
     */
    private void InserirModalidades(List<Modalidade> lst) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

        for (Modalidade modalidade : lst) {
            modalidadeDAOImp.save(modalidade);
        }

        AlertHandler alertHandler;
        alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso", "Modalidade/s insirada/s com Sucesso!");
        alertHandler.getAlert().showAndWait();
    }


    /**
     * Verifica se o ficheiro XML fornecido é válido em relação ao ficheiro XSD.
     *
     * @param xmlFile O ficheiro XML a ser validado.
     * @param xsdFilePath O caminho do ficheiro XSD para validação.
     * @return true se o ficheiro XML for válido, false caso contrário.
     */
    private boolean VerificarXML(File xmlFile, String xsdFilePath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new File(xsdFilePath)));
            schema.newValidator().validate(new StreamSource(xmlFile));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * Redireciona o utilizador de volta para a cena de seleção XML.
     *
     * @param event O evento de ação gerado ao clicar no botão.
     */
    @FXML
    void OnClickVoltarButton(ActionEvent event) {
        Stage s = (Stage) VoltarButton.getScene().getWindow();
        RedirecionarHelper.GotoSeleçãoXML().switchScene(s);
    }
}
