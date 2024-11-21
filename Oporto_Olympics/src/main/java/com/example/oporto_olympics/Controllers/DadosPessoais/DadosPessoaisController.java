package com.example.oporto_olympics.Controllers.DadosPessoais;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Models.Atleta;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;

/**
 * Controlador responsável pela exibição e manipulação dos dados pessoais do atleta.
 * Esta classe é encarregada de mostrar as informações pessoais do atleta, como nome, altura, peso,
 * país, género e data de nascimento, na interface gráfica.
 */
public class DadosPessoaisController {
    /**
     * Botão para voltar.
     */
    @FXML
    private Button VoltarBtn;
    /**
     * Rótulo para mostrar a altura.
     */
    @FXML
    private Label alturaLabel;
    /**
     * Rótulo para mostrar o país.
     */
    @FXML
    private Label paisLabel;
    /**
     * Rótulo para mostrar o género.
     */
    @FXML
    private Label generoLabel;
    /**
     * Rótulo para mostrar a data de nascimento.
     */
    @FXML
    private Label DataNascLabel;
    /**
     * Rótulo para mostrar o nome.
     */
    @FXML
    private Label nomeLabel;
    /**
     * Rótulo para mostrar o peso.
     */
    @FXML
    private Label pesoLabel;
    /**
     * Botão para alterar password.
     */
    @FXML
    private Button ChangePassBtn;
    /**
     * Botão para alterar foto de perfil.
     */
    @FXML
    private Button ChangeFotoBtn;
    /**
     * ImageView para a foto de perfil.
     */
    @FXML
    private ImageView img_perfil;

    /**
     * Inicializa os elementos de interface com as informações do atleta atualmente
     * armazenado na instância única de {@code AtletaSingleton}.
     *
     * Este método obtém o atleta da instância singleton {@code AtletaSingleton} e atualiza
     * os rótulos da interface com os detalhes do atleta, incluindo nome, género, altura,
     * país, peso e data de nascimento.
     */
    public void initialize() {

        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();
        Atleta atleta = AtletaSingle.getAtleta();


        nomeLabel.setText(atleta.getNome());
        generoLabel.setText(atleta.getGenero());
        alturaLabel.setText(String.valueOf(atleta.getAltura()) + " cm");
        paisLabel.setText(atleta.getPais());
        pesoLabel.setText(String.valueOf(atleta.getPeso()) + " kg");
        DataNascLabel.setText(String.valueOf(atleta.getDataNascimento()));

        byte[] fotoBytes = atleta.getFotoPerfil();

        if (fotoBytes != null) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(fotoBytes);
                Image image = new Image(inputStream);
                img_perfil.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * Manipulador de eventos para o botão "Alterar Password".
     * <p>
     * Este método é invocado quando o utilizador clica no botão "Alterar Password".
     * Redireciona o utilizador para um menu onde o utilizador altera a sua password utilizando
     * a classe {@code RedirecionarHelper}.
     */
    @FXML
    public void onAlterarPasswordCLick(){
        Stage s = (Stage) ChangePassBtn.getScene().getWindow();

        RedirecionarHelper.GotoAtlerarPassword().switchScene(s);
    }

    /**
     * Manipulador de eventos para o botão "Voltar".
     * <p>
     * Este método é invocado quando o utilizador clica no botão "Voltar".
     * Redireciona o utilizador para o menu principal do atleta utilizando
     * a classe {@code RedirecionarHelper}.
     */
    @FXML
    protected void OnVoltarButtonClick() {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalAtleta().switchScene(s);
    }

    /**
     * Manipulador de eventos para o botão "Alterar Foto".
     *
     * Este método é chamado quando o utilizador clica no botão para alterar a foto de perfil.
     * Ele permite que o utilizador selecione uma nova imagem através de um explorador de ficheiros,
     * converte a imagem selecionada em um array de bytes e atualiza a base de dados com a nova foto.
     * Além disso, a interface gráfica é atualizada para exibir a nova foto.
     *
     * @param event o evento associado ao clique no botão.
     */
    @FXML
    void onAlterarFotoCLick(ActionEvent event) {
        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();
        Atleta atleta = AtletaSingle.getAtleta();

        // Abrir explorador de ficheiros
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione uma imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(ChangeFotoBtn.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Converter imagem para byte[]
                byte[] fotoPerfil = Files.readAllBytes(selectedFile.toPath());

                // Atualizar no modelo Atleta
                atleta.setFotoPerfil(fotoPerfil);

                // Atualizar na base de dados
                ConnectionBD conexaoBD = ConnectionBD.getInstance();
                Connection conexao = conexaoBD.getConexao();
                AtletaDAOImp atletaDAO = new AtletaDAOImp(conexao);
                atletaDAO.updateFotoPerfil(atleta.getId(), fotoPerfil);

                // Atualizar na interface
                ByteArrayInputStream inputStream = new ByteArrayInputStream(fotoPerfil);
                Image novaImagem = new Image(inputStream);
                img_perfil.setImage(novaImagem);

                // Notificar sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Foto de perfil atualizada com sucesso!");
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

}
