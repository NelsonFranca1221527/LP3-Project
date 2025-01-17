package com.example.oporto_olympics;

import static org.junit.jupiter.api.Assertions.*;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.ListagemModalidades.CardController.ListagemModalidadesCardController;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.Participante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Map;

class GerarResultadosTest {

    private Connection connection;
    private ModalidadeDAOImp modalidadeDAOImp;
    private ListagemModalidadesCardController listagemModalidadeController;

    @BeforeEach
    void setUp() throws Exception {
        // Inicializar a conexão com a base de dados
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        connection = connectionBD.getConexao();

        // Inicializar os DAO's
        modalidadeDAOImp = new ModalidadeDAOImp(connection);

        // Inicializar o controlador
        listagemModalidadeController = new ListagemModalidadesCardController();
    }

    @Test
    void testGerarResultadosDistancia() throws Exception {
        // Configurar o teste para a modalidade de distância
        String nomeModalidade = "ModalidadeDistanciaTeste";
        String genero = "Men";
        String tipo = "Individual";
        int min_part = 2;
        int eventoID = 9;

        // Obter a modalidade da base de dados
        Modalidade modalidade = modalidadeDAOImp.getModalidadeByNomeGeneroTipo(nomeModalidade, genero, tipo, min_part);
        assertNotNull(modalidade, "A modalidade deve existir na base de dados.");

        // Invocar o metodo gerarResultadosOneGameLogic para obter os resultados
        Map<Participante, String> resultados = listagemModalidadeController.gerarResultadosOneGameLogic(modalidade, eventoID);

        // Verificar se os resultados não são null
        assertNotNull(resultados, "Os resultados não podem ser null.");

        // Verificar se os resultados são no formato "000,00"
        for (Map.Entry<Participante, String> entry : resultados.entrySet()) {
            String resultado = entry.getValue();
            assertTrue(resultado.matches("\\d{3},\\d{2}"), "O resultado para " + entry.getKey().getNome() + " não está no formato correto (000,00).");
        }
    }

    @Test
    void testGerarResultadosTempo() throws Exception {
        // Configurar o teste para a modalidade de tempo
        String nomeModalidade = "ModalidadeTempoTeste";
        String genero = "Men";
        String tipo = "Individual";
        int min_part = 2;
        int eventoID = 9;

        // Obter a modalidade da base de dados
        Modalidade modalidade = modalidadeDAOImp.getModalidadeByNomeGeneroTipo(nomeModalidade, genero, tipo, min_part);
        assertNotNull(modalidade, "A modalidade deve existir na base de dados.");

        // Invocar o metodo gerarResultadosOneGameLogic para obter os resultados
        Map<Participante, String> resultados = listagemModalidadeController.gerarResultadosOneGameLogic(modalidade, eventoID);

        // Verificar se os resultados não são null
        assertNotNull(resultados, "Os resultados não podem ser null.");

        // Verificar se os resultados são no formato "00:00:00.000"
        for (Map.Entry<Participante, String> entry : resultados.entrySet()) {
            String resultado = entry.getValue();
            assertTrue(resultado.matches("\\d{2}:\\d{2}:\\d{2}\\.\\d{3}"), "O resultado para " + entry.getKey().getNome() + " não está no formato correto (00:00:00.000).");
        }
    }

    @Test
    void testGerarResultadosPontos() throws Exception {
        // Configurar o teste para a modalidade de pontos
        String nomeModalidade = "ModalidadePontosTeste";
        String genero = "Men";
        String tipo = "Coletivo";
        int min_part = 2;
        int eventoID = 9;

        // Obter a modalidade da base de dados
        Modalidade modalidade = modalidadeDAOImp.getModalidadeByNomeGeneroTipo(nomeModalidade, genero, tipo, min_part);
        assertNotNull(modalidade, "A modalidade deve existir na base de dados.");

        // Invocar o metodo gerarResultadosMultipleLogic para obter os resultados
        Map<Participante, Integer> resultados = listagemModalidadeController.gerarResultadosMultipleLogic(modalidade, eventoID);

        // Verificar se os resultados não são null
        assertNotNull(resultados, "Os resultados não podem ser null.");

        // Verificar se os resultados são inteiros
        for (Map.Entry<Participante, Integer> entry : resultados.entrySet()) {
            Integer resultado = entry.getValue();
            assertTrue(resultado >= 0, "O resultado para " + entry.getKey().getNome() + " deve ser um valor inteiro positivo.");
        }
    }
}
