/**
 * Classe de teste para verificar o funcionamento do sistema de inscrições em equipas.
 * Contém testes unitários para verificar diferentes estados das inscrições.
 */
package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Equipas.InscricaoEquipaDAO;
import com.example.oporto_olympics.DAO.Equipas.InscricaonaEquipaDAOImp;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe de teste para validar as funcionalidades relacionadas à inscrição de equipes em eventos.
 */
class InscricaoEquipaTest {

    /**
     * Testa se existe um pedido de inscrição pendente para um determinado atleta numa equipa.
     * Verifica se o método `existePedidoPendente` da classe `InscricaoEquipaDAO` retorna true
     * quando um pedido pendente está registado no sistema.
     *
     * @throws SQLException se ocorrer um erro de conexão com a base de dados.
     */
    @Test
    void testInscricaoPendente() throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        InscricaoEquipaDAO dao = new InscricaonaEquipaDAOImp(conexao);

        int atleta_id = 1354; // ID do atleta
        int equipaId = 1092;   // ID da equipa

        boolean existePedidoPendente = dao.existePedidoPendente(atleta_id, equipaId);

        // Verifica se a inscrição pendente existe
        assertTrue(existePedidoPendente, "Inscrição deveria Existir");
    }

    /**
     * Testa se existe um pedido de inscrição aprovado para um determinado atleta numa equipa.
     * Verifica se o método `existePedidoAprovado` da classe `InscricaoEquipaDAO` retorna true
     * quando um pedido aprovado está registado no sistema.
     *
     * @throws SQLException se ocorrer um erro de conexão com a base de dados.
     */
    @Test
    void testInscricaoAceite() throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        InscricaoEquipaDAO dao = new InscricaonaEquipaDAOImp(conexao);

        int atleta_id = 1354; // ID do atleta
        int equipaId = 1091;   // ID da equipa

        boolean existePedidoAprovado = dao.existePedidoAprovado(atleta_id, equipaId);

        // Verifica se a inscrição aprovada existe
        assertTrue(existePedidoAprovado, "Inscrição deveria Existir");
    }
}
