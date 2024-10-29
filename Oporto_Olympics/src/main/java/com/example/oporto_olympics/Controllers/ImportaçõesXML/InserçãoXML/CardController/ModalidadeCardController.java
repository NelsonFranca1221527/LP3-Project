package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.CardController;

import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ModalidadeCardController {

    @FXML
    private Label Descricao;

    @FXML
    private Label Genero;

    @FXML
    private Label Medida;

    @FXML
    private Label MinParticipantes;

    @FXML
    private Label Nome;

    @FXML
    private Label OneGame;

    @FXML
    private Label RecordeAno;

    @FXML
    private Label RecordeResultado;

    @FXML
    private Label RecordeTitular;

    @FXML
    private Label Regras;

    @FXML
    private Label Tipo;

    @FXML
    private Label VencedorAno;

    @FXML
    private Label VencedorResultado;

    @FXML
    private Label VencedorTitular;

    /**
     * Preenche os dados da modalidade nos rótulos correspondentes.
     *
     * @param modalidade O objeto Modalidade cujos dados serão preenchidos nos rótulos.
     */
    public void preencherDados(Modalidade modalidade) {
        Nome.setText(modalidade.getNome());
        Descricao.setText(modalidade.getDescricao());
        Genero.setText(modalidade.getGenero());
        Medida.setText(modalidade.getMedida());
        MinParticipantes.setText(String.valueOf(modalidade.getMinParticipantes()));
        Tipo.setText(modalidade.getTipo());
        OneGame.setText(modalidade.getOneGame());
        Regras.setText(modalidade.getRegras());

        switch (modalidade.getMedida()) {
            case "Tempo":
                VencedorTitular.setText(modalidade.getVencedorOlimpico().getVencedor());
                VencedorAno.setText(String.valueOf(modalidade.getVencedorOlimpico().getAno()));
                VencedorResultado.setText(String.valueOf(modalidade.getVencedorOlimpico().getTempo()));

                RecordeTitular.setText(modalidade.getRecordeOlimpico().getVencedor());
                RecordeAno.setText(String.valueOf(modalidade.getRecordeOlimpico().getAno()));
                RecordeResultado.setText(String.valueOf(modalidade.getRecordeOlimpico().getTempo()));
                break;

            case "Pontos":
                VencedorTitular.setText(modalidade.getVencedorOlimpico().getVencedor());
                VencedorAno.setText(String.valueOf(modalidade.getVencedorOlimpico().getAno()));
                VencedorResultado.setText(String.valueOf(modalidade.getVencedorOlimpico().getMedalhas()));

                RecordeTitular.setText(modalidade.getRecordeOlimpico().getVencedor());
                RecordeAno.setText(String.valueOf(modalidade.getRecordeOlimpico().getAno()));
                RecordeResultado.setText(String.valueOf(modalidade.getRecordeOlimpico().getMedalhas()));
                break;
        }
    }
}
