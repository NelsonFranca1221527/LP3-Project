package com.example.oporto_olympics.Controllers.DadosPessoais;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Models.Atleta;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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

}
