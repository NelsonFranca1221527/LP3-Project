package com.example.oporto_olympics.Controllers.DadosPessoais;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Gestor;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controlador responsável pela exibição e manipulação dos dados pessoais do gestor.
 *
 */
public class DadosPessoaisGestorController {

    /**
     * Botão para voltar.
     */
    @FXML
    private Button VoltarBtn;

    /**
     * Rótulo para mostrar o nome.
     */
    @FXML
    private Label nomeLabel;

    /**
     * Inicializa os elementos de interface com as informações do gestor atualmente
     * armazenado na instância única de {@code GestorSingleton}.
     *
     * Este método obtém o gestor da instância singleton {@code GestorSingleton} e atualiza
     * os rótulos da interface com os detalhes do gestor.
     *
     */
    public void initialize() {

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        Gestor gestor = GestorSingle.getGestor();


        nomeLabel.setText(gestor.getNome());
    }

    /**
     * Manipulador de eventos para o botão "Voltar".
     * <p>
     * Este método é invocado quando o utilizador clica no botão "Voltar".
     * Redireciona o utilizador para o menu principal do gestor utilizando
     * a classe {@code RedirecionarHelper}.
     */
    @FXML
    protected void OnVoltarButtonClick() {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalGestor().switchScene(s);
    }
}
