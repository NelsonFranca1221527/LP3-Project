package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.Models.Local;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes para validação da inserção de locais na base de dados.
 * Inclui testes para cenários válidos e inválidos.
 */
class InserirLocalTest {

    /**
     * Testa a inserção de um local válido.
     * Verifica se os dados do local estão corretos e se o local é criado com sucesso na base de dados.
     *
     * @throws SQLException Caso ocorra um erro na conexão com a base de dados.
     */
    @Test
    void InserirLocalValido() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        Boolean criado = false;

        String nome = "TesteEstadio";
        String tipo = "interior";
        String pais = "PRT";
        String morada = "Rua do teste";
        String cidade = "teste";
        int capacidade = 10;
        String data = "2025-01-07";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate AnoPicker;
        Date anoConstrucao = null;
        AnoPicker = LocalDate.parse(data,formatter);
        anoConstrucao = java.sql.Date.valueOf(AnoPicker);

        Local novolocal = new Local(0, nome, tipo, morada, cidade, pais, capacidade, anoConstrucao);

        LocaisDAOImp locaisDAO = new LocaisDAOImp(conexao);

        assertTrue(locaisDAO.getSigla(pais), "País inválido.");

        assertFalse(locaisDAO.existsByLocal(nome, tipo, morada, cidade, pais), "Este local já existe.");

        assertTrue(capacidade > 0, "Um local interior deve possuir uma capacidade superior a 0!");

        assertTrue(AnoPicker.isBefore(LocalDate.now()), "O ano de construção deve ser inferior ao ano atual.");

        // Insere o novo local
        locaisDAO.save(novolocal);

        // Confirma se o local foi criado
        for (Local local : locaisDAO.getAll()) {
            if (Objects.equals(local.getNome(), nome)) {
                criado = true;
                break;
            }
        }

        assertTrue(criado, "O local foi criado");

        // Remove o local para manter a base de dados consistente para futuros testes
        locaisDAO.deleteByLocal(nome, tipo, morada, cidade, pais);
    }

    /**
     * Testa a tentativa de inserção de um local inválido.
     * Verifica se o sistema impede a criação de locais com dados inválidos.
     *
     * @throws SQLException Caso ocorra um erro na conexão com a base de dados.
     */
    @Test
    void InserirLocalInvalido() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        Boolean criado = false;

        String nome = "Estádio do Dragão";
        String tipo = "interior";
        String pais = "POR";
        String morada = "Via Futebol Clube do Porto, 4350-415";
        String cidade = "Porto";
        int capacidade = 0;
        String data = "2026-01-07";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate AnoPicker;
        Date anoConstrucao = null;
        AnoPicker = LocalDate.parse(data,formatter);
        anoConstrucao = java.sql.Date.valueOf(AnoPicker);

        Local novolocal = new Local(0, nome, tipo, morada, cidade, pais, capacidade, anoConstrucao);

        LocaisDAOImp locaisDAO = new LocaisDAOImp(conexao);

        assertFalse(locaisDAO.getSigla(pais), "País válido.");

        assertTrue(locaisDAO.existsByLocal(nome, tipo, morada, cidade, pais), "Este local não existe.");

        assertTrue(capacidade <= 0, "Um local interior deve possuir uma capacidade inferior ou igual a 0!");

        assertTrue(AnoPicker.isAfter(LocalDate.now()), "O ano de construção deve ser superior ao ano atual.");
    }
}
