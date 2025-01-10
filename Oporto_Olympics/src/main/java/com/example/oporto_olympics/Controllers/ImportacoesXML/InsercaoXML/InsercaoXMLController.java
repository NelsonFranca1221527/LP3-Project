package com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Atleta.InserirAtletaDAOImp;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.DAO.XML.EquipaDAOImp;
import com.example.oporto_olympics.DAO.XML.HistoricoXMLDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML.CardController.AtletaCardController;
import com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML.CardController.EquipaCardController;
import com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML.CardController.ModalidadeCardController;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import com.example.oporto_olympics.Singleton.InserçãoXMLSingleton;
import com.example.oporto_olympics.HelloApplication;
import com.example.oporto_olympics.Models.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Controlador responsável pela inserção de dados de Atletas, Equipas e Modalidades a partir de um arquivo XML.
 * A classe interage com a interface gráfica para carregar e validar arquivos XML,
 * e insere os dados na base de dados com base no tipo de XML selecionado.
 */
public class InsercaoXMLController {
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
     * Mapa para armazenar os eventos.
     */
    private Map<String, Evento> EventoMap = new HashMap<>();
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
     * Ficheiro selecionado.
     *
     * Este campo armazena o ficheiro que foi selecionado pelo utilizador. O tipo do campo é {@code File}.
     */
    private File selectedFile;

    /**
     * Obtém o ficheiro selecionado.
     *
     * Este método retorna o ficheiro atualmente armazenado na instância de {@code selectedFile}.
     *
     * @return o ficheiro selecionado ou {@code null} se nenhum ficheiro foi selecionado
     */
    public File getSelectedFile() {
        return selectedFile;
    }

    /**
     * Define o ficheiro selecionado.
     *
     * Este método permite que o ficheiro selecionado seja atribuído à instância. O ficheiro será
     * armazenado no campo {@code selectedFile} para utilização posterior.
     *
     * @param selectedFile o ficheiro a ser definido como o ficheiro selecionado
     *                     (não pode ser {@code null})
     */
    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    /**
     * Inicializa o controlador, ajustando a interface com base no tipo de XML
     * a ser inserido (Atleta, Equipa ou Modalidade).
     */
    public void initialize() throws SQLException {

        EventoMap.clear();

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
                EventoGroup.setVisible(false);
                tituloTipoXML.setText(tituloTipoXML.getText() + " uma Equipa");
                return;
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
                    EventoMap.put(local.getNome() + " - " + evento.getAno_edicao(), evento);
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

        setListaAtletas(new ArrayList<>());
        setListaEquipas(new ArrayList<>());
        setListaModalidades(new ArrayList<>());

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

        setSelectedFile(selectedFile);

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

                if(getListaAtletas() == null || getListaAtletas().isEmpty()){
                    alertHandler = new AlertHandler(Alert.AlertType.WARNING,"Sem Atletas!!!", "Não existem atletas para inserir, tente inserir um ficheiro XML de Atletas válido!");
                    alertHandler.getAlert().showAndWait();
                    return;
                }

                alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Atletas por Inserir!!!", "Deseja inserir os atletas?");
                Optional<ButtonType> rs1 = alertHandler.getAlert().showAndWait();

                if (rs1.isPresent() && rs1.get() != ButtonType.OK) {
                    return;
                }
                    InserirAtletas(getListaAtletas());

                break;
            case "Equipa":

                if(getListaEquipas() == null || getListaEquipas().isEmpty()){
                    alertHandler = new AlertHandler(Alert.AlertType.WARNING,"Sem Equipas!!!", "Não existem equipas para inserir, tente inserir um ficheiro XML de Equipas válido!");
                    alertHandler.getAlert().showAndWait();
                    return;
                }

                alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Equipas por Inserir!!!", "Deseja inserir as equipas?");
                Optional<ButtonType> rs2 = alertHandler.getAlert().showAndWait();

                if (rs2.isPresent() && rs2.get() != ButtonType.OK) {
                    return;
                }

                InserirEquipas(getListaEquipas());

                break;
            case "Modalidade":

                if(getListaModalidades() == null || getListaModalidades().isEmpty()){
                    alertHandler = new AlertHandler(Alert.AlertType.WARNING,"Sem Modalidades!!!", "Não existem modalidades para inserir, tente inserir um ficheiro XML de Modalidades válido!");
                    alertHandler.getAlert().showAndWait();
                    return;
                }

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

                InserirModalidades(getListaModalidades(), EventoMap.get(EventoChoice.getValue()));

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
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/ImportacoesXML/InsercaoXML/Cards/AtletasCard.fxml"));
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
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/ImportacoesXML/InsercaoXML/Cards/EquipasCard.fxml"));
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
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/ImportacoesXML/InsercaoXML/Cards/ModalidadesCard.fxml"));
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

        AlertHandler alertHandler;

        int quantAtletas = lst.size();

        AtletaDAOImp atletaDAOImp = new AtletaDAOImp(conexao);

        Iterator<Atleta> iterator = lst.iterator();
        while (iterator.hasNext()) {
            Atleta atleta = iterator.next();

            if (atleta.getNome() == null || atleta.getNome().isEmpty() ||
                    atleta.getGenero() == null || atleta.getGenero().isEmpty() ||
                    atleta.getDataNascimento() == null ||
                    atleta.getPais() == null || atleta.getPais().isEmpty()) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Atleta Inválida", "O atleta " + atleta.getNome() + ", possui campos vazios ou inválidos!! Verifique e insira o XML novamente.");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            if (!atletaDAOImp.getPais(atleta.getPais())) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "País inválido", "A sigla do país do atleta " + atleta.getNome() + " não é válida.");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            int alturaMinima = 120;

            if(atleta.getAltura() <= alturaMinima){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Altura inválida","A altura do atleta " + atleta.getNome() + " não é válida! O atleta deve possuir uma altura superior a " + alturaMinima + "cm.");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            int pesoMinimo = 20;

            if(atleta.getPeso() <= pesoMinimo){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Peso inválida","O peso do atleta " + atleta.getNome() + " não é válido! O atleta deve possuir um peso superior a " + pesoMinimo + "kg.");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            if (atleta.getDataNascimento().before(Date.from(Instant.from(LocalDate.now().minusYears(200))))) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Data inválida", "A data de nascimento do atleta " + atleta.getNome() + " não pode ser anterior a 200 anos atrás.");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            if (atleta.getDataNascimento().after(Date.from(Instant.now()))) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Data inválida", "A data de nascimento do atleta " + atleta.getNome() + " não pode ser maior que a data de hoje.");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            atletaDAOImp.save(atleta);
            iterator.remove();
        }

        if(lst.size() < quantAtletas){

            alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso", "Atleta/s insirado/s com Sucesso!");
            alertHandler.getAlert().showAndWait();

            HistoricoXMLDAOImp historicoXMLDAOImp = new HistoricoXMLDAOImp(conexao);

            InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

            GestorSingleton gestorSingleton = GestorSingleton.getInstance();

            historicoXMLDAOImp.save(new HistoricoXML(gestorSingleton.getGestor().getId(), LocalDateTime.now(), inserçãoXMLSingleton.getTipoXML(), getSelectedFile()));
        }
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

        int quantEquipas = lst.size();

        AlertHandler alertHandler;

        EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

        Iterator<Equipa> iterator = lst.iterator();
        while (iterator.hasNext()) {
            Equipa equipa = iterator.next();

            if(equipa.getNome() == null || equipa.getNome().trim().isEmpty() ||
                    equipa.getPais() == null || equipa.getPais().trim().isEmpty() ||
                    equipa.getGenero() == null || equipa.getGenero().trim().isEmpty() ||
                    equipa.getDesporto() == null || equipa.getDesporto().trim().isEmpty()){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Equipa Inválida", "A equipa " + equipa.getNome() + ", possui campos vazios ou inválidos!! Verifique e insira o XML novamente.");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);
            List<Modalidade> modalidadeList = modalidadeDAOImp.getAll();

            for (Modalidade modalidade : modalidadeList) {
                if (modalidade.getNome().equals(equipa.getDesporto()) && modalidade.getGenero().equals(equipa.getGenero())) {
                    equipa.setModalidadeID(modalidade.getId());
                    break;
                }
            }

            if (equipa.getModalidadeID() == 0) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Modalidade Não Encontrada", "A equipa " + equipa.getNome() + ", não possui uma modalidade em que possa participar no evento selecionado!!");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            Optional<Equipa> equipaExiste = equipaDAOImp.get(equipa.getNome());

            if (equipaExiste.isPresent()) {
                if (equipa.getPais().equals(equipaExiste.get().getPais()) && equipa.getModalidadeID() == equipaExiste.get().getModalidadeID()) {
                    alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Equipa Existente", "A Equipa " + equipa.getNome() + " já encontra-se registada no sistema!");
                    alertHandler.getAlert().showAndWait();
                    continue;
                }
            }

            int anoMin = 1000;

            if (equipa.getAnoFundacao() < anoMin || equipa.getAnoFundacao() > LocalDate.now().getYear()) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Ano de Fundação Inválido", "O ano de fundação da equipa " + equipa.getNome() + " - " + equipa.getGenero() + " não deve ser inferior a " + anoMin + " e superior a " + LocalDate.now().getYear() + "!");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            if (!equipaDAOImp.getSigla(equipa.getPais())) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Pais Inválido", "Insira a sigla de um País válido para a equipa " + equipa.getNome() + " - " + equipa.getGenero() + "!");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            equipaDAOImp.save(equipa);
            iterator.remove();
        }

        if(lst.size() < quantEquipas){

            alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso", "Equipa/s insirada/s com Sucesso!");
            alertHandler.getAlert().showAndWait();

            HistoricoXMLDAOImp historicoXMLDAOImp = new HistoricoXMLDAOImp(conexao);

            InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

            GestorSingleton gestorSingleton = GestorSingleton.getInstance();

            historicoXMLDAOImp.save(new HistoricoXML(gestorSingleton.getGestor().getId(), LocalDateTime.now(), inserçãoXMLSingleton.getTipoXML(), getSelectedFile()));
        }
    }

    /**
     * Insere a lista de equipas na base de dados.
     *
     * @param lst A lista de equipas a ser inserida.
     * @param evento Evento ao qual as modalidades estão associadas.
     * @throws SQLException Se ocorrer um erro ao acessar a base de dados.
     */
    private void InserirModalidades(List<Modalidade> lst, Evento evento) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        int quantModalidades = lst.size();

        AlertHandler alertHandler;

        ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

        Iterator<Modalidade> iterator = lst.iterator();

        while (iterator.hasNext()) {
            Modalidade modalidade = iterator.next();

            if(modalidade.getNome() == null || modalidade.getNome().trim().isEmpty() ||
                    modalidade.getGenero() == null || modalidade.getGenero().trim().isEmpty() ||
                    modalidade.getTipo() == null || modalidade.getTipo().trim().isEmpty() ||
                    modalidade.getOneGame() == null || modalidade.getOneGame().trim().isEmpty() ||
                    modalidade.getMedida() == null || modalidade.getMedida().trim().trim().isEmpty() ||
                    modalidade.getDescricao() == null || modalidade.getDescricao().trim().isEmpty() ||
                    modalidade.getRegras() == null || modalidade.getRegras().trim().isEmpty() ||
                    modalidade.getMinParticipantes() <= 1){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Modalidade Inválida", "A modalidade " + modalidade.getNome() + ", possui campos vazios ou inválidos!! Verifique e insira o XML novamente.");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            Modalidade ModalidadeExistente = modalidadeDAOImp.getModalidadeByNomeGeneroTipo(modalidade.getNome(), modalidade.getGenero(), modalidade.getTipo(), modalidade.getMinParticipantes());

            if(modalidade.getMinParticipantes() <= 1){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Participantes Insuficientes", "A Modalidade " + modalidade.getNome() + " deve ter um minimo de 2 ou mais participantes.");
                alertHandler.getAlert().showAndWait();
                continue;
            }

            if (ModalidadeExistente != null) {
                if (ModalidadeExistente.getListEventosID().contains(evento.getId())) {

                    alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Modalidade Existente", "A Modalidade " + modalidade.getNome() + ", Género: " + modalidade.getGenero() + " já encontra-se registada no evento selecionado!");
                    alertHandler.getAlert().showAndWait();
                    continue;
                }

                SaveEventosModalidades(conexao, evento, ModalidadeExistente);
                iterator.remove();
                continue;
            }

            modalidadeDAOImp.save(modalidade);
            SaveEventosModalidades(conexao, evento, modalidade);
            iterator.remove();
        }

        if(lst.size() < quantModalidades){

            alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Sucesso", "Modalidade/s insirada/s com Sucesso!");
            alertHandler.getAlert().showAndWait();

            HistoricoXMLDAOImp historicoXMLDAOImp = new HistoricoXMLDAOImp(conexao);

            InserçãoXMLSingleton inserçãoXMLSingleton = InserçãoXMLSingleton.getInstance();

            GestorSingleton gestorSingleton = GestorSingleton.getInstance();

            historicoXMLDAOImp.save(new HistoricoXML(gestorSingleton.getGestor().getId(), LocalDateTime.now(), inserçãoXMLSingleton.getTipoXML(), getSelectedFile()));
        }
    }

    /**
     * Verifica se o ficheiro XML fornecido é válido em relação ao ficheiro XSD.
     *
     * @param xmlFile O ficheiro XML a ser validado.
     * @param xsdFilePath O caminho do ficheiro XSD para validação.
     * @return true se o ficheiro XML for válido, false caso contrário.
     */
    public boolean VerificarXML(File xmlFile, String xsdFilePath) {
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
     * Este método permite registar uma modalidade num evento, solicitando ao utilizador que insira informações como o local, a data,
     * a hora de início e a duração da modalidade.
     *
     * @param conexao A conexão com a base de dados que será utilizada para realizar as operações de salvamento.
     * @param evento O evento ao qual a modalidade será associada.
     * @param modalidade A modalidade que será associada ao evento.
     */
    public void SaveEventosModalidades(Connection conexao, Evento evento,Modalidade modalidade){

        ChoiceBox<String> LocalChoice = new ChoiceBox<>();

        HashMap<String, Local> localMap = new HashMap<>();

        LocaisDAOImp locaisDAOImp = new LocaisDAOImp(conexao);
        List<Local> localList = locaisDAOImp.getAll();

        for (Local local : localList) {
            localMap.put(local.getNome(), local);
        }

        LocalChoice.setItems(FXCollections.observableArrayList(localMap.keySet()));
        LocalChoice.setValue("-------");

        DatePicker DataPicker = new DatePicker();

        TextField HoraInicio = new TextField();
        HoraInicio.setPromptText("HH:mm:ss");

        TextField Duracao = new TextField();
        Duracao.setPromptText("HH:mm:ss");

        DataPicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            String sanitized = newValue.replaceAll("[^0-9]", "");
            int length = sanitized.length();

            StringBuilder masked = new StringBuilder();
            if (length > 0) {
                masked.append(sanitized.substring(0, Math.min(length, 2))); // Dia
            }
            if (length > 2) {
                masked.append("/");
                masked.append(sanitized.substring(2, Math.min(length, 4))); // Mês
            }
            if (length > 4) {
                masked.append("/");
                masked.append(sanitized.substring(4, Math.min(length, 8))); // Ano
            }

            if (!masked.toString().equals(newValue)) {
                DataPicker.getEditor().setText(masked.toString());
                DataPicker.getEditor().positionCaret(masked.length());
            }
        });

        Duracao.textProperty().addListener((observable, oldValue, newValue) -> {

            String sanitized = newValue.replaceAll("[^0-9]", "");
            int length = sanitized.length();

            StringBuilder masked = new StringBuilder();
            if (length > 0) {
                masked.append(sanitized.substring(0, Math.min(length, 2))); // Horas
            }
            if (length > 2) {
                masked.append(":");
                masked.append(sanitized.substring(2, Math.min(length, 4))); // Minutos
            }
            if (length > 4) {
                masked.append(":");
                masked.append(sanitized.substring(4, Math.min(length, 6))); // Segundos
            }

            if (!masked.toString().equals(newValue)) {
                Duracao.setText(masked.toString());
                Duracao.positionCaret(masked.length());
            }
        });

        HoraInicio.textProperty().addListener((observable, oldValue, newValue) -> {

            String sanitized = newValue.replaceAll("[^0-9]", "");
            int length = sanitized.length();

            StringBuilder masked = new StringBuilder();
            if (length > 0) {
                masked.append(sanitized.substring(0, Math.min(length, 2))); // Horas
            }
            if (length > 2) {
                masked.append(":");
                masked.append(sanitized.substring(2, Math.min(length, 4))); // Minutos
            }
            if (length > 4) {
                masked.append(":");
                masked.append(sanitized.substring(4, Math.min(length, 6))); // Segundos
            }

            if (!masked.toString().equals(newValue)) {
                HoraInicio.setText(masked.toString());
                HoraInicio.positionCaret(masked.length());
            }
        });

        Button OKButton = new Button("OK");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Modalidade a Inserir Horário: "), 0, 0);
        grid.add(new Label(modalidade.getNome()), 1, 0);

        grid.add(new Label("Local:"), 0, 1);
        grid.add(LocalChoice, 1, 1);

        grid.add(new Label("Data:"), 0, 2);
        grid.add(DataPicker, 1, 2);

        grid.add(new Label("Hora de Início:"), 0, 3);
        grid.add(HoraInicio, 1, 3);

        grid.add(new Label("Duração:"), 0, 4);
        grid.add(Duracao, 1, 4);

        grid.add(OKButton, 0, 5);

        Stage HorarioStage = new Stage();
        HorarioStage.setScene(new Scene(grid, 300, 200));
        HorarioStage.setTitle("Inserir Horário");

        OKButton.setOnAction(e -> {
            AlertHandler alertHandler;

            if (DataPicker.getValue() == null) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Data Inválida", "A Data de início da modalidade deve ser uma Data válida!");
                alertHandler.getAlert().showAndWait();
                return;
            }

            if(DataPicker.getValue().getYear() != evento.getAno_edicao()){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Ano Inválido", "O ano selecionado na data de início não corresponde ao ano de edição do evento.");
                alertHandler.getAlert().showAndWait();
                return;
            }

            if(DataPicker.getValue().isBefore(LocalDate.now())){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Data Inválida", "A Data de Início da Modalidade não deve ser anterior ao dia de hoje!");
                alertHandler.getAlert().showAndWait();
                return;
            }

            if (!HoraInicio.getText().matches("^([01]?[0-9]|2[0-3])(:([0-5]?[0-9])){0,2}$")) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Hora de Início Inválida", "A Hora de Início inserida é inválida! As horas devem ser entre 00 e 23, e os minutos e segundos entre 00 e 59.");
                alertHandler.getAlert().showAndWait();
                return;
            }

            if (!Duracao.getText().matches("^([01]?[0-9]|2[0-3])(:([0-5]?[0-9])){0,2}$")) {
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Duração Inválida", "A Duração inserida é inválida! As horas devem ser entre 00 e 23, e os minutos e segundos entre 00 e 59.");
                alertHandler.getAlert().showAndWait();
                return;
            }

            LocalDateTime dataHora = LocalDateTime.of(DataPicker.getValue(), LocalTime.parse(HoraInicio.getText()));
            LocalTime duracao = LocalTime.parse(Duracao.getText());
            Local local = localMap.get(LocalChoice.getValue());

            if(!local.getPais().equals(evento.getPais())){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Local Não Correspondente", "O Local inserido não se encontra no mesmo país em que o evento será realizado!");
                alertHandler.getAlert().showAndWait();
                return;
            }

            ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

            if(VerificarConflito(dataHora,duracao,local.getId(), modalidadeDAOImp.getAllHorarioModalidade())){
                alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Horário Indisponivel", "O horário da modalidade entra em conflito com um horário já existente de outra modalidade no local selecionado!");
                alertHandler.getAlert().showAndWait();
                return;
            }

            modalidadeDAOImp.saveEventos_Modalidades(evento.getId(), modalidade.getId(), dataHora, duracao,local.getId());

            HorarioStage.close();
        });

        HorarioStage.showAndWait();
    }

    /**
     * Verifica se há conflitos de horários para um local específico com base em uma lista de horários existentes.
     *
     * @param dataHoraInicio A data e hora de início da modalidade a ser inserida.
     * @param duracao A duração da modalidade a ser inserida.
     * @param localID O identificador do local onde o horário será inserido.
     * @param listaHorarios A lista de horários existentes associados às modalidades.
     *
     * @return true se houver conflito de horários; false caso contrário.
     *
     * Este método verifica se o intervalo de tempo definido por dataHoraInicio e a duração da nova modalidade
     * entra em conflito com os intervalos de tempo das modalidades existentes associadas ao mesmo local.
     *
     * Um conflito ocorre quando:
     * - O início do novo horário está dentro do intervalo de um horário existente.
     * - O fim do novo horário está dentro do intervalo de um horário existente.
     * - O início de um horário existente está dentro do intervalo do novo horário.
     * - O fim de um horário existente está dentro do intervalo do novo horário.
     *
     * Se a lista de horários for nula ou estiver vazia, considera-se que não há conflitos.
     */
    public Boolean VerificarConflito(LocalDateTime dataHoraInicio, LocalTime duracao,int localID,List<HorarioModalidade> listaHorarios){

        if(listaHorarios == null || listaHorarios.isEmpty()){
            //Sem Conflitos
            return false;
        }

        LocalDateTime dataHoraFim = dataHoraInicio.plusSeconds(duracao.toSecondOfDay());

        for (HorarioModalidade horario : listaHorarios) {

            if(horario.getLocalID() != localID){
                continue;
            }

            LocalDateTime horarioInicio = horario.getDataHora();
            LocalDateTime horarioFim = horarioInicio.plusSeconds(horario.getDuracao().toSecondOfDay());

            /* Verifica se o horário da modalidade a ser inserida não se sobrepõe ao intervalo do horário da modalidade que está a ser verificada,
               assim como também verifica que o horário da modalidade existente não entra em conflito com o horário da modalidade a ser inserida. */
            if ((dataHoraInicio.isAfter(horarioInicio) && dataHoraInicio.isBefore(horarioFim) ) ||
                    ( dataHoraFim.isAfter(horarioInicio) && dataHoraFim.isBefore(horarioFim) ) ||
                    ( horarioInicio.isAfter(dataHoraInicio) && horarioInicio.isBefore(dataHoraFim) ) ||
                    ( horarioFim.isAfter(dataHoraInicio) && horarioFim.isBefore(dataHoraFim) )) {
                //Detetou Conflito de Horários
                return true;
            }
        }

        return false; // Sem conflitos
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
