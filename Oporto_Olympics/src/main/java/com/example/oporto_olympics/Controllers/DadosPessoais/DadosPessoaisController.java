package com.example.oporto_olympics.Controllers.DadosPessoais;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Models.Atleta;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DadosPessoaisController {

    @FXML
    private Button VoltarBtn;

    @FXML
    private Label alturaLabel;

    @FXML
    private Label paisLabel;

    @FXML
    private Label generoLabel;

    @FXML
    private Label DataNascLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label pesoLabel;

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

    @FXML
    protected void OnVoltarButtonClick() {
        Stage s = (Stage) VoltarBtn.getScene().getWindow();

        RedirecionarHelper.GotoMenuPrincipalAtleta().switchScene(s);
    }

}
