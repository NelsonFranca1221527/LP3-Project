package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Eventos.InscricaonoEventoDAOImp;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe de teste para validar as funcionalidades relacionadas à Inscrição de Atleta em Evento.
 */
public class InscricaoAtletaEventoTest {

    /**
     * Teste unitário para verificar a inscrição válida de um atleta em um evento.
     *
     * Este teste valida se um atleta pode ser inscrito em um evento e se a inscrição
     * é corretamente adicionada e marcada como pendente na base de dados.
     *
     * @throws SQLException se ocorrer um erro de conexão ou consulta na base de dados.
     */
    @Test
    void InscricaoValida() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        InscricaonoEventoDAOImp dao = new InscricaonoEventoDAOImp(conexao);

        int atletaId = 1329;
        int eventoId = 42;
        String estado = "Pendente";

        // Inscrever atleta
        dao.inserirInscricao(estado, eventoId, atletaId, 1160);

        // Verificar se a inscrição foi adicionada
        assertTrue(dao.existeInscricaoPendente(atletaId, eventoId, 1160),
                "A inscrição deveria estar pendente na base de dados.");
    }

    /**
     * Teste unitário para verificar o comportamento ao tentar inscrever um atleta em um evento inexistente.
     *
     * Este teste garante que o sistema lança uma exceção apropriada ao tentar realizar uma inscrição
     * para um evento que não existe na base de dados.
     *
     * @throws SQLException se ocorrer um erro de conexão ou consulta na base de dados.
     */
    @Test
    void InscricaoInvalidaEventoInexistente() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        InscricaonoEventoDAOImp dao = new InscricaonoEventoDAOImp(conexao);

        int atletaId = 1329; // ID válido de atleta
        int eventoId = 1; // ID inexistente de evento

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.inserirInscricao("Pendente", eventoId, atletaId, 1160);
        });

        assertFalse(exception.getMessage().contains("Evento inexistente"),
                "Deveria retornar erro indicando que o evento não foi encontrado.");
    }

    /**
     * Teste unitário para verificar o comportamento ao tentar inscrever um atleta inexistente em um evento.
     *
     * Este teste garante que o sistema lança uma exceção apropriada ao tentar realizar uma inscrição
     * para um atleta que não existe na base de dados.
     *
     * @throws SQLException se ocorrer um erro de conexão ou consulta na base de dados.
     */
    @Test
    void InscricaoInvalidaAtletaInexistente() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        InscricaonoEventoDAOImp dao = new InscricaonoEventoDAOImp(conexao);

        int atletaId = 12; // ID inexistente de atleta
        int eventoId = 42; // ID válido de evento

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.inserirInscricao("Pendente", eventoId, atletaId, 1160);
        });

        assertFalse(exception.getMessage().contains("Atleta inexistente"),
                "Deveria retornar erro indicando que o atleta não foi encontrado.");
    }

    /**
     * Teste unitário para verificar o comportamento ao tentar inscrever um atleta com campos obrigatórios ausentes.
     *
     * Este teste garante que o sistema lança uma exceção apropriada ao tentar realizar uma inscrição
     * com dados obrigatórios faltando (por exemplo, ID de atleta inválido ou zero).
     *
     * @throws SQLException se ocorrer um erro de conexão ou consulta na base de dados.
     */
    @Test
    void InscricaoInvalidaCamposObrigatorios() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        InscricaonoEventoDAOImp dao = new InscricaonoEventoDAOImp(conexao);

        int atletaId = 0;
        int eventoId = 11;

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                dao.inserirInscricao("Pendente", eventoId, atletaId, 1160)
        );

        assertFalse(exception.getMessage().contains("Campo obrigatório ausente"),
                "Deveria retornar erro indicando que um campo obrigatório está ausente.");
    }
}
