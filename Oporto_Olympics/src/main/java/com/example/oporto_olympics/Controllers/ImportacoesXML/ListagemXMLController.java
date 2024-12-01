package com.example.oporto_olympics.Controllers.ImportacoesXML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.XML.ListagemXMLDAO;
import com.example.oporto_olympics.DAO.XML.ListagemXMLDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;

/**
 * Controlador da interface responsável por gerir a visualização e interação com os ficheiros XML.
 *
 * É responsável por estabelecer a conexão com a base de dados, carregar os ficheiros XML
 * e disponibilizar funcionalidades como visualização dos detalhes e download dos ficheiros.
 */
public class ListagemXMLController {

    @FXML
    private Button VoltarBtn;

    @FXML
    private VBox ModalidadesContainer;

    private ListagemXMLDAO dao;
    /**
     * Método chamado para inicar a interface. Estabelece a conexão com a base de dados e
     * inicializa o DAO para manipulação dos ficheiros XML. Se a conexão falhar, mostra uma mensagem de erro.
     * Caso contrário, chama o método para carregar os ficheiros XML na interface.
     */
    @FXML
    private void initialize() {
        try {

            Connection conexao = ConnectionBD.getInstance().getConexao();

            if (conexao == null) {
                System.out.println("Conexão com a base de dados falhou!");
                return;
            } else {
                System.out.println("Conexão com a base de dados bem-sucedida!");
            }

            dao = new ListagemXMLDAOImp(conexao);

            carregarXMLs();

        } catch (SQLException exception) {
            System.out.println("Erro ao conectar à base de dados: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar à base de dados: " + exception.getMessage());
            alert.show();
        }
    }
    /**
     * Carrega e mostra uma lista de ficheiros XML na interface. Para cada ficheiro, cria um cartão (card) com
     * informações como o nome do ficheiro, tipo e data, juntamente com botões para realizar o download ou
     * ver os detalhes.
     */
    private void carregarXMLs() {

        List<ListagemXML> historicoXMLList = dao.getAllXMLFile();

        if (historicoXMLList == null || historicoXMLList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nenhum ficheiro XML encontrado.");
            alert.show();
            return;
        }

        ModalidadesContainer.getChildren().clear();

        for (ListagemXML historicoXML : historicoXMLList) {

            HBox card = new HBox(20);
            card.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10; -fx-padding: 10; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.1), 10, 0.2, 0, 0);");
            card.setPrefWidth(760);
            card.setPrefHeight(80);

            String nomeGestor = dao.getGestorNomeByUserId(historicoXML.getUserId());

            VBox vbox = new VBox(10);
            Text fileNameText = new Text("Ficheiro: " + historicoXML.getTipo() + ".xml");
            Text fileTypeText = new Text("Tipo: " + historicoXML.getTipo());
            Text gestorNameText = new Text("Gestor: " + nomeGestor);

            LocalDateTime data = historicoXML.getData();
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
            String dataFormatada = data.format(formatador);

            Text fileDateText = new Text("Data: " + dataFormatada);

            vbox.getChildren().addAll(fileNameText, fileTypeText,gestorNameText, fileDateText);

            Button downloadBtn = new Button("Download");
            downloadBtn.setOnAction(e -> fazerDownload(historicoXML));

            Button detalhesBtn = new Button("Ver Detalhes");
            detalhesBtn.setOnAction(e -> mostrarDetalhes(historicoXML));

            card.getChildren().addAll(vbox, downloadBtn, detalhesBtn);
            ModalidadesContainer.getChildren().add(card);
        }
    }

    /**
     * Mostra os detalhes de um ficheiro XML em uma nova janela, mostrando informações sobre modalidades, equipas e atletas.
     * O conteúdo XML é organizado em cards dentro de um container.
     *
     * @param historicoXML O objeto contém o histórico XML a ser mostrado.
     */
    private void mostrarDetalhes(ListagemXML historicoXML) {
        Stage detalhesStage = new Stage();
        detalhesStage.setTitle("Detalhes do XML - " + historicoXML.getTipo());

        VBox detalhesContainer = new VBox(10);
        detalhesContainer.setStyle("-fx-padding: 15; -fx-spacing: 10;");

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(historicoXML.getConteudoXML().getBytes(StandardCharsets.UTF_8)));

            NodeList nodes = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i) instanceof Element) {
                    Element element = (Element) nodes.item(i);

                    if (element.getNodeName().equals("sport")) {
                        HBox card = criarCardDetalheModalidade(element);
                        detalhesContainer.getChildren().add(card);
                    } else if (element.getNodeName().equals("team")) {
                        HBox card = criarCardDetalheEquipe(element);
                        detalhesContainer.getChildren().add(card);
                    } else if (element.getNodeName().equals("athlete")) {
                        HBox card = criarCardDetalheAtleta(element);
                        detalhesContainer.getChildren().add(card);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao processar o XML: " + e.getMessage());
            alert.show();
        }


        ScrollPane scrollPane = new ScrollPane(detalhesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 600, 400);
        detalhesStage.setScene(scene);
        detalhesStage.show();
    }

    /**
     * Cria um card para mostrar os detalhes do xml do tipo Modalidade.
     *
     * @param element O elemento XML representa a Modalidade.
     * @return Um HBox com os cards das Modalidades.
     */
    private HBox criarCardDetalheModalidade(Element element) {
        HBox card = new HBox(10);
        card.setStyle("-fx-padding: 10; -fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-spacing: 10;");
        card.setPrefWidth(780);

        VBox vbox = new VBox(5);

        NodeList nameNodeList = element.getElementsByTagName("name");
        String name = (nameNodeList.getLength() > 0) ? nameNodeList.item(0).getTextContent() : "Não disponível";
        Text title = new Text("Nome da Modalidade: " + name);
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        vbox.getChildren().add(title);

        NodeList typeNodeList = element.getElementsByTagName("type");
        String type = (typeNodeList.getLength() > 0) ? typeNodeList.item(0).getTextContent() : "Não disponível";
        NodeList genreNodeList = element.getElementsByTagName("genre");
        String genre = (genreNodeList.getLength() > 0) ? genreNodeList.item(0).getTextContent() : "Não disponível";
        vbox.getChildren().addAll(
                new Text("Tipo: " + type),
                new Text("Gênero: " + genre)
        );

        NodeList descriptionNodeList = element.getElementsByTagName("description");
        String description = (descriptionNodeList.getLength() > 0) ? descriptionNodeList.item(0).getTextContent() : "Não disponível";
        vbox.getChildren().add(new Text("Descrição: " + description));

        NodeList scoringMeasureNodeList = element.getElementsByTagName("scoringMeasure");
        String scoringMeasure = (scoringMeasureNodeList.getLength() > 0) ? scoringMeasureNodeList.item(0).getTextContent() : "Não disponível";
        vbox.getChildren().add(new Text("Medida de Pontuação: " + scoringMeasure));

        NodeList olympicRecordNodeList = element.getElementsByTagName("olympicRecord");
        if (olympicRecordNodeList.getLength() > 0) {
            Element recordElement = (Element) olympicRecordNodeList.item(0);
            String time = (recordElement.getElementsByTagName("time").getLength() > 0) ? recordElement.getElementsByTagName("time").item(0).getTextContent() : "Não disponível";
            String year = (recordElement.getElementsByTagName("year").getLength() > 0) ? recordElement.getElementsByTagName("year").item(0).getTextContent() : "Não disponível";
            String holder = (recordElement.getElementsByTagName("holder").getLength() > 0) ? recordElement.getElementsByTagName("holder").item(0).getTextContent() : "Não disponível";
            Text olympicRecordText = new Text("Recorde Olímpico: " + time + " (" + year + ", " + holder + ")");
            vbox.getChildren().add(olympicRecordText);
        }

        NodeList winnerOlympicNodeList = element.getElementsByTagName("winnerOlympic");
        if (winnerOlympicNodeList.getLength() > 0) {
            Element winnerElement = (Element) winnerOlympicNodeList.item(0);
            String winnerTime = (winnerElement.getElementsByTagName("time").getLength() > 0) ? winnerElement.getElementsByTagName("time").item(0).getTextContent() : "Não disponível";
            String winnerYear = (winnerElement.getElementsByTagName("year").getLength() > 0) ? winnerElement.getElementsByTagName("year").item(0).getTextContent() : "Não disponível";
            String winnerHolder = (winnerElement.getElementsByTagName("holder").getLength() > 0) ? winnerElement.getElementsByTagName("holder").item(0).getTextContent() : "Não disponível";
            Text winnerText = new Text("Vencedor Olímpico: " + winnerTime + " (" + winnerYear + ", " + winnerHolder + ")");
            vbox.getChildren().add(winnerText);
        }

        NodeList rulesNodeList = element.getElementsByTagName("rules");
        if (rulesNodeList.getLength() > 0) {
            Element rulesElement = (Element) rulesNodeList.item(0);
            NodeList ruleList = rulesElement.getElementsByTagName("rule");

            StringBuilder rulesTextBuilder = new StringBuilder();
            for (int i = 0; i < ruleList.getLength(); i++) {
                rulesTextBuilder.append(ruleList.item(i).getTextContent()).append("\n");
            }

            Text rulesText = new Text("Regras: \n" + (rulesTextBuilder.length() > 0 ? rulesTextBuilder.toString() : "Não disponível"));
            vbox.getChildren().add(rulesText);
        }

        card.getChildren().add(vbox);
        return card;
    }



    /**
     * Cria um card para mostrar os detalhes do XML do tipo Atleta.
     *
     * @param element O elemento XML representa um Atleta.
     * @return Um HBox com os cards dos Atleta.
     */
    private HBox criarCardDetalheAtleta(Element element) {
        HBox card = new HBox(10);
        card.setStyle("-fx-padding: 10; -fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-spacing: 10;");
        card.setPrefWidth(580);

        VBox vbox = new VBox(5);

        String name = element.getElementsByTagName("name").item(0) != null ? element.getElementsByTagName("name").item(0).getTextContent() : "Não disponível";
        Text title = new Text("Nome do Atleta: " + name);
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        vbox.getChildren().add(title);

        String country = element.getElementsByTagName("country").item(0) != null ? element.getElementsByTagName("country").item(0).getTextContent() : "Não disponível";

        String genre = element.getElementsByTagName("genre").item(0) != null ? element.getElementsByTagName("genre").item(0).getTextContent() : "Não disponível";

        String height = element.getElementsByTagName("height").item(0) != null ? element.getElementsByTagName("height").item(0).getTextContent() + " cm" : "Não disponível";

        String weight = element.getElementsByTagName("weight").item(0) != null ? element.getElementsByTagName("weight").item(0).getTextContent() + " kg" : "Não disponível";

        String birthDate = element.getElementsByTagName("dateOfBirth").item(0) != null ? element.getElementsByTagName("dateOfBirth").item(0).getTextContent() : "Não disponível";

        vbox.getChildren().addAll(
                new Text("País: " + country),
                new Text("Gênero: " + genre),
                new Text("Altura: " + height),
                new Text("Peso: " + weight),
                new Text("Data de Nascimento: " + birthDate)
        );

        NodeList participations = element.getElementsByTagName("participation");
        if (participations.getLength() > 0) {
            Text participationsTitle = new Text("Participações Olímpicas:");
            participationsTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            vbox.getChildren().add(participationsTitle);

            for (int i = 0; i < participations.getLength(); i++) {
                Element participation = (Element) participations.item(i);

                String year = participation.getElementsByTagName("year").item(0) != null ? participation.getElementsByTagName("year").item(0).getTextContent() : "Não disponível";
                Text participationText = new Text("Ano: " + year);
                vbox.getChildren().add(participationText);
            }
        }

        card.getChildren().add(vbox);
        return card;
    }

    /**
     * Cria um card para mostrar os detalhes do XML do tipo Equipa.
     *
     * @param element O elemento XML representa uma Equipa.
     * @return Um HBox com os cards das Equipas.
     */
    private HBox criarCardDetalheEquipe(Element element) {
        HBox card = new HBox(10);
        card.setStyle("-fx-padding: 10; -fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-spacing: 10;");
        card.setPrefWidth(580);

        VBox vbox = new VBox(5);
        Text title = new Text("Nome da Equipa: " + element.getElementsByTagName("name").item(0).getTextContent());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        vbox.getChildren().add(title);

        Text country = new Text("País: " + element.getElementsByTagName("country").item(0).getTextContent());
        Text genre = new Text("Gênero: " + element.getElementsByTagName("genre").item(0).getTextContent());
        Text sport = new Text("Esporte: " + element.getElementsByTagName("sport").item(0).getTextContent());
        Text foundationYear = new Text("Ano de Fundação: " + element.getElementsByTagName("foundationYear").item(0).getTextContent());

        vbox.getChildren().addAll(country, genre, sport, foundationYear);

        NodeList olympicParticipations = element.getElementsByTagName("olympicParticipations");
        if (olympicParticipations.getLength() > 0) {
            Text olympicTitle = new Text("Participações Olímpicas:");
            olympicTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            vbox.getChildren().add(olympicTitle);

            NodeList participations = olympicParticipations.item(0).getChildNodes();
            for (int i = 0; i < participations.getLength(); i++) {
                if (participations.item(i) instanceof Element) {
                    Element participation = (Element) participations.item(i);
                    String year = participation.getElementsByTagName("year").item(0).getTextContent();
                    String result = participation.getElementsByTagName("result").item(0).getTextContent();
                    Text participationText = new Text("Ano: " + year + " - Resultado: " + result);
                    vbox.getChildren().add(participationText);
                }
            }
        }

        card.getChildren().add(vbox);
        return card;
    }

    /**
     * Este método permite que o gestor faça o download de um ficheiro XML com o conteúdo histórico.
     * Ele abre uma scene para o gestor escolher onde guardar o ficheiro, e o conteúdo XML é guardado
     * no local selecionado. O nome do ficheiro é gerado com base no tipo e na data do histórico.
     *
     * @param historicoXML O objeto que contém o histórico a ser exportado para XML, incluindo o tipo,
     *                     data e o conteúdo XML a ser transferido.
     */
    private void fazerDownload(ListagemXML historicoXML) {
        LocalDateTime data = historicoXML.getData();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
        String dataFormatada = data.format(formatador);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Ficheiro XML", "*.xml"));
        fileChooser.setInitialFileName("Ficheiro" + historicoXML.getTipo() +"_"+ dataFormatada + ".xml");

        File destino = fileChooser.showSaveDialog(null);

        if (destino != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(destino))) {
                String conteudoXML = formatarXML(historicoXML.getConteudoXML());
                writer.write(conteudoXML);
                writer.flush();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "O ficheiro foi transferido para: " + destino.getAbsolutePath());
                alert.show();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao transferir o XML: " + ex.getMessage());
                alert.show();
            }
        }
    }
    /**
     * Formata o conteúdo XML para um formato legível, com indentação.
     * Este método recebe uma string que contém o XML e a formata com uma indentação de 4 espaços,
     * tornando-a mais legível.
     *
     * @param xml O conteúdo XML a ser formatado.
     * @return Uma string contendo o XML formatado com indentação.
     * @throws Exception Caso ocorra um erro durante o processo de parsing ou transformação do XML.
     */
    private String formatarXML(String xml) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    /**
     * Este método é chamado quando o gestor clica no botão para voltar.
     * Ele fecha a janela atual e redireciona o gestor para o menu principal do atleta.
     *
     * @param event O evento gerado pelo clique no botão de voltar.
     */
    @FXML
    void onActionBack(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();
        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
