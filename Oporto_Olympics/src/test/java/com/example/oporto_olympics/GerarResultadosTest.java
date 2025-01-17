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

/**
 * Classe de teste para os métodos de geração de resultados em diferentes modalidades.
 */
class GerarResultadosTest {

    private Connection connection;
    private ModalidadeDAOImp modalidadeDAOImp;
    private ListagemModalidadesCardController listagemModalidadeController;

    /**
     * Configuração inicial antes de cada teste.
     * Inicializa a conexão com a base de dados, os DAO's e o controlador necessário.
     *
     * @throws Exception caso ocorra algum erro durante a configuração.
     */
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

    /**
     * Teste para verificar a geração de resultados no formato de distância (000,00).
     *
     * @throws Exception caso ocorra algum erro durante a execução do teste.
     */
    @Test
    void testGerarResultadosDistancia() throws Exception {
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

        // Verificar se os resultados estão no formato "000,00"
        for (Map.Entry<Participante, String> entry : resultados.entrySet()) {
            String resultado = entry.getValue();
            assertTrue(resultado.matches("\\d{3},\\d{2}"), "O resultado para " + entry.getKey().getNome() + " não está no formato correto (000,00).");
        }
    }

    /**
     * Teste para verificar a geração de resultados no formato de tempo (00:00:00.000).
     *
     * @throws Exception caso ocorra algum erro durante a execução do teste.
     */
    @Test
    void testGerarResultadosTempo() throws Exception {
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

        // Verificar se os resultados estão no formato "00:00:00.000"
        for (Map.Entry<Participante, String> entry : resultados.entrySet()) {
            String resultado = entry.getValue();
            assertTrue(resultado.matches("\\d{2}:\\d{2}:\\d{2}\\.\\d{3}"), "O resultado para " + entry.getKey().getNome() + " não está no formato correto (00:00:00.000).");
        }
    }

    /**
     * Teste para verificar a geração de resultados no formato de pontos (inteiros positivos).
     *
     * @throws Exception caso ocorra algum erro durante a execução do teste.
     */
    @Test
    void testGerarResultadosPontos() throws Exception {
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

        // Verificar se os resultados são inteiros e positivos
        for (Map.Entry<Participante, Integer> entry : resultados.entrySet()) {
            Integer resultado = entry.getValue();
            assertTrue(resultado >= 0, "O resultado para " + entry.getKey().getNome() + " deve ser um valor inteiro positivo.");
        }
    }
}
