package com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.DAO.XML.EquipaDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML.CardController.AtletaCardController;
import com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML.CardController.EquipaCardController;
import com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML.CardController.ModalidadeCardController;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Singleton.InserçãoXMLSingleton;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Controlador responsável pela inserção de dados de Atletas, Equipas e Modalidades a partir de um arquivo XML.
 * A classe interage com a interface gráfica para carregar e validar arquivos XML,
 * e insere os dados na base de dados com base no tipo de XML selecionado.
 */
public class InserçãoXMLController {
    /**
     * Contêiner de layout para agrupar os componentes de evento.
     */
    @FXML
    private VBox Container;
    /**
     * Caixa de seleção para escolher o evento.
     */
    @FXML
    private ChoiceBox<String> EventoChoice;
    /**
     * Grupo que contém os elementos do evento.
     */
    @FXML
    private Group EventoGroup;
    /**
     * Botão para inserir um novo evento.
     */
    @FXML
    private Button InserirButton;
    /**
     * Botão para selecionar o arquivo XML.
     */
    @FXML
    private Button SelecionarXMLButton;
    /**
     * Botão para voltar à tela anterior.
     */
    @FXML
    private Button VoltarButton;
    /**
     * Rótulo para mostrar o título do tipo de XML.
     */
    @FXML
    private Label tituloTipoXML;
    /**
     * Mapa que relaciona um item (evento, equipa, etc.) com um valor inteiro.
     */
    private Map<String, Integer> itemMap = new HashMap<>();
    /**
     * Caminho do arquivo XSD para a validação dos dados dos atletas.
     */
    private String AtletaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/athletes_xsd.xml";
    /**
     * Caminho do arquivo XSD para a validação dos dados das equipas.
     */
    private String EquipaXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/teams_xsd.xml";
    /**
     * Caminho do arquivo XSD para a validação dos dados das modalidades.
     */
    private String ModalidadeXSDPath = System.getProperty("user.dir") + "/src/main/resources/com/example/oporto_olympics/Assets/XSD/sports_xsd.xml";
    /**
     * Lista de atletas.
     */
    private List<Atleta> ListaAtletas;
    /**
     * Lista de equipas.
     */
    private List<Equipa> ListaEquipas;
    /**
     * Lista de modalidades.
     */
    private List<Modalidade> ListaModalidades;
    /**
     * Obtém a lista de atletas.
     *
     * @return Lista de atletas.
     */
    public List<Atleta> getListaAtletas() {
        return ListaAtletas;
    }
    /**
     * Define a lista de atletas.
     *
     * @param listaAtletas Lista de atletas a ser definida.
     */
    public void setListaAtletas(List<Atleta> listaAtletas) {
        ListaAtletas = listaAtletas;
    }
    /**
     * Obtém a lista de equipas.
     *
     * @return Lista de equipas.
     */
    public List<Equipa> getListaEquipas() {
        return ListaEquipas;
    }
    /**
     * Define a lista de equipas.
     *
     * @param listaEquipas Lista de equipas a ser definida.
     */
    public void setListaEquipas(List<Equipa> listaEquipas) {
        ListaEquipas = listaEquipas;
    }
    /**
     * Obtém a lista de modalidades.
     *
     * @return Lista de modalidades.
     */
    public List<Modalidade> getListaModalidades() {
        return ListaModalidades;
    }
    /**
     * Define a lista de modalidades.
     *
     * @param listaModalidades Lista de modalidades a ser definida.
     */
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
                new FileChooser.ExtensionFilter("Ficheiros XML", "*.xml")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) {
            return;
        }

        InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

        LerXMLController lerXMLController = new LerXMLController();

        boolean valido = false;

        Container.getChildren().clear();

        switch (inserçãoXMLSingleton.getTipoXML()){
            case "Atleta":
                valido = VerificarXML(selectedFile, AtletaXSDPath);
                if (!valido) {
                    alertHandler = new AlertHandler(Alert.AlertType.ERROR,"Ficheiro XML Inválido!!!", "Insira um ficheiro XML de Atletas Válido!!");
                    alertHandler.getAlert().showAndWait();
                    return;
                }

                    setListaAtletas(lerXMLController.LerXMLAtleta(selectedFile));

                    VisualizarAtletas(getListaAtletas());

                break;
            case "Equipa":
                valido = VerificarXML(selectedFile, EquipaXSDPath);
                if (!valido) {
                    alertHandler = new AlertHandler(Alert.AlertType.ERROR,"Ficheiro XML Inválido!!!", "Insira um ficheiro XML de Equipas Válido!!");
                    alertHandler.getAlert().showAndWait();
                    return;
                }

                    setListaEquipas(lerXMLController.LerXMLEquipa(selectedFile));

                    VisualizarEquipas(getListaEquipas());

                break;
            case "Modalidade":
                valido = VerificarXML(selectedFile, ModalidadeXSDPath);
                if (!valido) {
                    alertHandler = new AlertHandler(Alert.AlertType.ERROR,"Ficheiro XML Inválido!!!", "Insira um ficheiro XML de Modalidades Válido!!");
                    alertHandler.getAlert().showAndWait();
                    return;
                }

                    setListaModalidades(lerXMLController.LerXMLModalidade(selectedFile));

                    VisualizarModalidades(getListaModalidades());

                break;
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

                if (rs1.isPresent() && rs1.get() != ButtonType.OK) {
                    return;
                }
                    InserirAtletas(getListaAtletas());

                break;
            case "Equipa":
                alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Equipas por Inserir!!!", "Deseja inserir as equipas?");
                Optional<ButtonType> rs2 = alertHandler.getAlert().showAndWait();

                if (rs2.isPresent() && rs2.get() != ButtonType.OK) {
                    return;
                }

                if(EventoChoice.getValue().equals("-------")){
                    alertHandler = new AlertHandler(Alert.AlertType.WARNING,"Selecione um Evento!!!", "Para inserir uma ou mais equipas deve inserir um evento");
                    alertHandler.getAlert().showAndWait();
                    return;
                }

                InserirEquipas(getListaEquipas(), itemMap.get(EventoChoice.getValue()));

                break;
            case "Modalidade":
                alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Modalidades por Inserir!!!", "Deseja inserir as modalidades?");
                Optional<ButtonType> rs3 = alertHandler.getAlert().showAndWait();

                if (rs3.isPresent() && rs3.get() != ButtonType.OK) {
                    return;
                }

                if(EventoChoice.getValue().equals("-------")){
                    alertHandler = new AlertHandler(Alert.AlertType.WARNING,"Selecione um Evento!!!", "Para inserir uma ou mais modalidades deve inserir um evento");
                    alertHandler.getAlert().showAndWait();
                    return;
                }

                InserirModalidades(getListaModalidades(), itemMap.get(EventoChoice.getValue()));

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
                Pane PaneEquipa = loader.load();
                EquipaCardController cardsController = loader.getController();
                cardsController.preencherDados(equipa);

                PaneEquipa.setUserData(equipa);

                if (equipa.getGenero().equals("Men")) {
                    PaneEquipa.setStyle("-fx-background-color: #87ceeb; -fx-border-color: #eee; -fx-border-width: 5px;");
                } else {
                    PaneEquipa.setStyle("-fx-background-color: #f2a2ee; -fx-border-color: #eee; -fx-border-width: 5px;");
                }

                Container.getChildren().add(PaneEquipa);
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
                Pane PaneModalidade = loader.load();
                ModalidadeCardController cardsController = loader.getController();
                cardsController.preencherDados(modalidade);

                PaneModalidade.setUserData(modalidade);

                if (modalidade.getGenero().equals("Men")) {
                    PaneModalidade.setStyle("-fx-background-color: #87ceeb; -fx-border-color: #eee; -fx-border-width: 5px;");
                } else {
                    PaneModalidade.setStyle("-fx-background-color: #f2a2ee; -fx-border-color: #eee; -fx-border-width: 5px;");
                }

                Container.getChildren().add(PaneModalidade);
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
     * @param lst      A lista de equipas a ser inserida.
     * @param IDEvento
     * @throws SQLException Se ocorrer um erro ao acessar a base de dados.
     */
    private void InserirEquipas(List<Equipa> lst, int IDEvento) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        AlertHandler alertHandler;

        EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

        for (Equipa equipa : lst) {

            Optional<Equipa> EquipaExiste = equipaDAOImp.get(equipa.getNome());

            if (EquipaExiste.isPresent() && equipa.getPais().equals(EquipaExiste.get().getPais()) && equipa.getModalidadeID() == EquipaExiste.get().getModalidadeID() ) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Equipa Existente", "A Equipa " + equipa.getNome() + " já encontra-se registada no sistema!");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            int anoMin = 1000;

            if(equipa.getAnoFundacao() < anoMin || equipa.getAnoFundacao() > LocalDate.now().getYear()) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Ano de Fundação Inválido", "O ano de fundação da equipa " + equipa.getNome() + " - " + equipa.getGenero()  +  " não deve ser inferior a " + anoMin + " e superior a " + LocalDate.now().getYear()+"!");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            if (!equipaDAOImp.getSigla(equipa.getPais())){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Pais Inválido", "Insira a sigla de um País válido para a equipa " + equipa.getNome() + " - " + equipa.getGenero()  + "!");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);
            List<Modalidade> modalidadeList = modalidadeDAOImp.getAll();

            for (Modalidade modalidade : modalidadeList) {
                if (modalidade.getNome().equals(equipa.getDesporto()) && modalidade.getGenero().equals(equipa.getGenero()) && modalidade.getListEventosID().contains(IDEvento)) {
                    equipa.setModalidadeID(modalidade.getId());
                    break;
                }

            }

            if (equipa.getModalidadeID() == 0) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Modalidade Não Encontrada", "A equipa " + equipa.getNome() + ", não possui uma modalidade em que possa participar no evento selecionado!!");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            equipaDAOImp.save(equipa);
        }

        alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso", "Equipa/s insirada/s com Sucesso!");
        alertHandler.getAlert().showAndWait();
    }

    /**
     * Insere a lista de modalidades na base de dados.
     *
     * @param lst      A lista de modalidades a ser inserida.
     * @param IDEvento
     * @throws SQLException Se ocorrer um erro ao acessar a base de dados.
     */
    private void InserirModalidades(List<Modalidade> lst, int IDEvento) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        AlertHandler alertHandler;

        ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

        for (Modalidade modalidade : lst) {

            Modalidade ModalidadeExistente = modalidadeDAOImp.getModalidadeByNomeGeneroTipo(modalidade.getNome(), modalidade.getGenero(), modalidade.getTipo());

            if (ModalidadeExistente != null) {

                if (ModalidadeExistente.getListEventosID().contains(IDEvento)) {
                    alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Modalidade Existente", "A Modalidade " + modalidade.getNome() + ", Género: " + modalidade.getGenero() + " já encontra-se registada no evento selecionado!");
                    alertHandler.getAlert().showAndWait();
                    continue;
                }

                modalidadeDAOImp.saveEventos_Modalidades(IDEvento, ModalidadeExistente.getId());
                continue;
            }

            modalidadeDAOImp.save(modalidade);
            modalidadeDAOImp.saveEventos_Modalidades(IDEvento, ModalidadeExistente.getId());
        }

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
