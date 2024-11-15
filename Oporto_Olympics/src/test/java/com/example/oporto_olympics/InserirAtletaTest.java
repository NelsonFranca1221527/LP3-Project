package com.example.oporto_olympics;

import com.example.oporto_olympics.Controllers.Atleta.InserirAtletaController;
import com.example.oporto_olympics.DAO.Atleta.InserirAtletaDAOImp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static javafx.beans.binding.Bindings.when;
import static org.mockito.Mockito.clearInvocations;

class InserirAtletaTest {

    private InserirAtletaController controller;

    @Mock
    private InserirAtletaDAOImp dao;

    @Mock
    private TextField Nome;

    @Mock
    private TextField Pais;

    @Mock
    private TextField Altura;

    @Mock
    private TextField Peso;

    @Mock
    private TextField Data_nasc;

    @Mock
    private ChoiceBox<String> GeneroChoice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        // Limpar mocks após cada teste (opcional se for configurado automaticamente)
        clearInvocations(dao, Nome, Pais, Altura, Peso, Data_nasc, GeneroChoice);
    }

    @Test
    void onClickCriarAtletaButton() {

        controller.OnClickCriarAtletaButton();
    }
}