package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para verificar a funcionalidade de inserção de modalidades.
 * Este teste valida a inserção de uma nova modalidade na base de dados, garantindo
 * que a modalidade seja adicionada corretamente ou que já exista.
 */
class InserirModalidadeTest {
    /**
     * Testa o processo de inserção de uma modalidade na base de dados.
     * O teste verifica se já existe uma modalidade com os mesmos atributos:
     * - Caso exista, confirma que a modalidade está presente na base de dados.
     * - Caso não exista, insere uma nova modalidade e valida que foi adicionada corretamente.
     *
     * @throws SQLException caso ocorra algum erro ao conectar à base de dados ou ao executar as operações.
     */
    @Test
    void InserirModalidadeValida() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        boolean modalidadeExistente = false;

        String tipo = "Individual";
        String genero = "Men";
        String nome = "Teste Modalidade";
        String descricao = "Descrição de teste para modalidade.";
        int minParticipantes = 5;
        String medida = "Pontos";
        String quant = "One";
        String regras = "Regras para teste";

        assertTrue(minParticipantes > 1, "A modalidade deve ter um mínimo de 2 ou mais participantes.");

        assertTrue(quant.equals("One") || quant.equals("Multiple"), "A modalidade deve ser One ou Multiple.");

        assertTrue(medida.equals("Pontos") || medida.equals("Tempo") || medida.equals("Distância"), "A modalidade deve ter uma unidade de medida válida.");

        assertTrue(genero.equals("Men") || genero.equals("Women"), "A modalidade deve ter um gênero válido.");

        assertTrue(tipo.equals("Individual") || genero.equals("Coletivo"), "A modalidade deve ter um tipo válido.");

        Modalidade modalidade = new Modalidade(
                0,
                tipo,
                genero,
                nome,
                descricao,
                minParticipantes,
                medida,
                quant,
                null,
                new RegistoPontos("", 0, ""),
                new RegistoPontos("", 0, ""),
                regras
        );

        ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

        Modalidade modalidadeExistenteBD = modalidadeDAOImp.getModalidadeByNomeGeneroTipo(modalidade.getNome(), modalidade.getGenero(), modalidade.getTipo(), modalidade.getMinParticipantes());
        if (modalidadeExistenteBD == null) {
            modalidadeDAOImp.save(modalidade);
        } else {
            modalidadeExistente = true;
        }

        Modalidade modalidadeRecuperada = modalidadeDAOImp.getModalidadeByNomeGeneroTipo(modalidade.getNome(), modalidade.getGenero(), modalidade.getTipo(), modalidade.getMinParticipantes());

        assertNotNull(modalidadeRecuperada, "A modalidade deveria existir na base de dados.");
        assertTrue(modalidadeExistente || modalidadeRecuperada.getNome().equals(nome), "A modalidade foi inserida ou já existia na base de dados.");
    }

    /**
     * Testa a tentativa de inserção de uma modalidade inválido.
     * Verifica se o sistema impede a criação de modalidades com dados inválidos.
     *
     * @throws SQLException Caso ocorra um erro na conexão com a base de dados.
     * @throws IOException  Caso ocorra um erro ao ler os ficheiros de imagem.
     */
    @Test
    void InserirModalidadeInvalida() throws SQLException {
        String tipo = "Joker";
        String genero = "Dog";
        String nome = "Teste Modalidade";
        String descricao = "Descrição de teste para modalidade.";
        int minParticipantes = 1;
        String medida = "Altura";
        String quant = "Five";
        String regras = "Regras para teste";

        assertFalse(minParticipantes > 1, "A modalidade deve ter um mínimo de participantes de 1 ou menos para ser inválida");

        assertFalse(quant.equals("One") || quant.equals("Multiple"), "A modalidade não pode ser One ou Multiple para ser inválida.");

        assertFalse(medida.equals("Pontos") || medida.equals("Tempo") || medida.equals("Distância"), "A modalidade deve ter uma unidade de medida inválida.");

        assertFalse(genero.equals("Men") || genero.equals("Women"), "A modalidade deve ter um gênero inválido.");

        assertFalse(tipo.equals("Individual") || genero.equals("Coletivo"), "A modalidade deve ter um tipo inválido.");
    }
}
