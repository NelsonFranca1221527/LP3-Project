package com.example.oporto_olympics.Controllers.ImportacoesXML.InsercaoXML.CardController;

import com.example.oporto_olympics.Models.Atleta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
/**
 * Controlador responsável pela gestão dos dados do atleta na interface do utilizador.
 * Esta classe preenche os rótulos com as informações do atleta, como nome, país, peso, altura,
 * género e data de nascimento.
 */
public class AtletaCardController {
    /**
     * Rótulo para mostrar a altura.
     */
    @FXML
    private Label Altura;
    /**
     * Rótulo para mostrar a data de aniversário.
     */
    @FXML
    private Label DataAniversario;

    /**
     * Rótulo para mostrar o género.
     */
    @FXML
    private Label Genero;
    /**
     * Rótulo para mostrar o nome.
     */
    @FXML
    private Label Nome;
    /**
     * Rótulo para mostrar o país.
     */
    @FXML
    private Label Pais;
    /**
     * Rótulo para mostrar o peso.
     */
    @FXML
    private Label Peso;

    /**
     * Preenche os dados do atleta nos rótulos correspondentes.
     *
     * @param atleta O objeto Atleta cujos dados serão preenchidos nos rótulos.
     */
    public void preencherDados(Atleta atleta) {
        Nome.setText(atleta.getNome());
        Pais.setText(atleta.getPais());
        Peso.setText(String.valueOf(atleta.getPeso()) + " kg.");
        Altura.setText(String.valueOf(atleta.getAltura()) + " cm.");
        Genero.setText(atleta.getGenero());
        DataAniversario.setText(new SimpleDateFormat("dd-MM-yyyy").format(atleta.getDataNascimento()));
    }
}
