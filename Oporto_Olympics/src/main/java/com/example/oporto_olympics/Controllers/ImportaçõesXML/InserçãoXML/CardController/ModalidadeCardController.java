package com.example.oporto_olympics.Controllers.ImportaçõesXML.InserçãoXML.CardController;

import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoTempo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * Controlador responsável pela gestão dos dados de uma modalidade na interface do utilizador.
 * Esta classe preenche os rótulos com as informações de uma modalidade, como nome, descrição,
 * género, medida, número mínimo de participantes, regras, e os dados do recorde e vencedor.
 */
public class ModalidadeCardController {
    /**
     * Rótulo para mostrar a descrição.
     */
    @FXML
    private Label Descricao;
    /**
     * Rótulo para mostrar o género.
     */
    @FXML
    private Label Genero;
    /**
     * Rótulo para mostrar a medida (ex: unidades, tipo de medida).
     */
    @FXML
    private Label Medida;
    /**
     * Rótulo para mostrar o número mínimo de participantes.
     */
    @FXML
    private Label MinParticipantes;
    /**
     * Rótulo para mostrar o número mínimo de participantes.
     */
    @FXML
    private Label Nome;
    /**
     * Rótulo para mostrar se é um evento de "One Game".
     */
    @FXML
    private Label OneGame;
    /**
     * Rótulo para mostrar o ano do recorde.
     */
    @FXML
    private Label RecordeAno;
    /**
     * Rótulo para mostrar o resultado do recorde.
     */
    @FXML
    private Label RecordeResultado;
    /**
     * Rótulo para mostrar o titular do recorde.
     */
    @FXML
    private Label RecordeTitular;
    /**
     * Rótulo para mostrar as regras.
     */
    @FXML
    private Label Regras;
    /**
     * Rótulo para mostrar o tipo de evento ou competição.
     */
    @FXML
    private Label Tipo;
    /**
     * Rótulo para mostrar o ano do vencedor.
     */
    @FXML
    private Label VencedorAno;
    /**
     * Rótulo para mostrar o resultado do vencedor.
     */
    @FXML
    private Label VencedorResultado;
    /**
     * Rótulo para mostrar o titular do vencedor.
     */
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
