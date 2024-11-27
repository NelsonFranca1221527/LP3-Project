package com.example.oporto_olympics.Controllers.ImportacoesXML;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.XML.ListagemXMLDAO;
import com.example.oporto_olympics.DAO.XML.ListagemXMLDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.HistoricoXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListagemXMLController {

    @FXML
    private Button VoltarBtn;

    @FXML
    private VBox ModalidadesContainer; // Referência ao VBox correto no FXML

    private ListagemXMLDAO dao;

    @FXML
    private void initialize() {
        try {
            // Conectar ao banco de dados
            Connection conexao = ConnectionBD.getInstance().getConexao();

            if (conexao == null) {
                System.out.println("Conexão com a base de dados falhou!");
                return;
            } else {
                System.out.println("Conexão com a base de dados bem-sucedida!");
            }

            // Inicializar o DAO com a conexão do banco de dados
            dao = new ListagemXMLDAOImp(conexao);

            // Chamar o método para carregar e exibir os arquivos XML
            carregarXMLs();

        } catch (SQLException exception) {
            System.out.println("Erro ao conectar à base de dados: " + exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao conectar à base de dados: " + exception.getMessage());
            alert.show();
        }
    }

    private void carregarXMLs() {
        // Buscar todos os arquivos XML da base de dados
        List<HistoricoXML> historicoXMLList = dao.getAllXMLFile();

        // Verifique se há dados a mostrar
        if (historicoXMLList == null || historicoXMLList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nenhum arquivo XML encontrado.");
            alert.show();
            return;
        }

        // Limpar o VBox antes de adicionar os novos cards
        ModalidadesContainer.getChildren().clear();

        // Iterar sobre os dados e adicionar dinamicamente no VBox
        for (HistoricoXML historicoXML : historicoXMLList) {
            // Criar um card para cada item de histórico XML
            HBox card = new HBox(20); // Espaçamento entre os itens
            card.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10; -fx-padding: 10; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.1), 10, 0.2, 0, 0);");

            // Criar um VBox para as informações do arquivo
            VBox vbox = new VBox(10);
            Text fileNameText = new Text("Arquivo: " + historicoXML.getTipo() + ".xml");
            Text fileTypeText = new Text("Tipo: " + historicoXML.getTipo());
            Text fileDateText = new Text("Data: " + historicoXML.getData().toString());

            // Adicionar as informações ao VBox
            vbox.getChildren().addAll(fileNameText, fileTypeText, fileDateText);

            // Criar um botão de download
            Button downloadBtn = new Button("Download");
            downloadBtn.setOnAction(e -> fazerDownload(historicoXML));

            // Adicionar o VBox e o botão ao card
            card.getChildren().addAll(vbox, downloadBtn);

            // Adicionar o card ao VBox principal
            ModalidadesContainer.getChildren().add(card);
        }
    }

    private void fazerDownload(HistoricoXML historicoXML) {
        // Abrir a janela para o usuário escolher onde salvar o arquivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos XML", "*.xml"));
        fileChooser.setInitialFileName("Arquivo_" + historicoXML.getTipo() + ".xml");

        File destino = fileChooser.showSaveDialog(null); // Local de destino escolhido pelo usuário

        if (destino != null) {
            try {
                // Ler o conteúdo do arquivo XML
                File origem = historicoXML.getFicheiroXML();
               // String conteudoXML = Files.readString(origem.toPath(), StandardCharsets.UTF_8);

                // Escrever o conteúdo no arquivo de destino
                //Files.writeString(destino.toPath(), conteudoXML, StandardCharsets.UTF_8);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Arquivo XML salvo com sucesso em: " + destino.getAbsolutePath());
                alert.show();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao salvar o arquivo XML: " + ex.getMessage());
                alert.show();
            }
        }
    }


    @FXML
    void onActionBack(ActionEvent event) {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();
        RedirecionarHelper.GotoMenuPrincipalAtleta().switchScene(s);
    }
}
