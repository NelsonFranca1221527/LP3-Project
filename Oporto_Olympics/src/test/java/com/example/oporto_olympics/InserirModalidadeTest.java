package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.Modalidades.InserirModalidadesController;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Models.RegistoModalidades.RegistoPontos;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Classe de teste para verificar a funcionalidade de inserção de modalidades.
 * Este teste valida a inserção de uma nova modalidade na base de dados, garantindo
 * que a modalidade seja adicionada corretamente ou que já exista.
 */
class InserirModalidadesTest {
    /**
     * Testa o processo de inserção de uma modalidade na base de dados.
     * O teste verifica se já existe uma modalidade com os mesmos atributos:
     * - Caso exista, confirma que a modalidade está presente na base de dados.
     * - Caso não exista, insere uma nova modalidade e valida que foi adicionada corretamente.
     *
     * @throws SQLException caso ocorra algum erro ao conectar à base de dados ou ao executar as operações.
     */
    @Test
    void InserirModalidade() throws SQLException {
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
}
