package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.Models.Evento;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes para validação da inserção de eventos na base de dados.
 * Inclui testes para cenários válidos e inválidos.
 */
class InserirEventoTest {

    /**
     * Testa a inserção de um evento válido.
     * Verifica se os dados do evento estão corretos e se o evento é criado com sucesso na base de dados.
     *
     * @throws SQLException Caso ocorra um erro na conexão com a base de dados.
     * @throws IOException  Caso ocorra um erro ao ler os ficheiros de imagem.
     */
    @Test
    void InserirEventoValido() throws SQLException, IOException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        Boolean criado = false;
        Year thisYear = Year.now();

        int anoEdicao = 2060;
        String pais = "PRT";
        String logoURL = System.getProperty("user.dir") + "/src/test/resources/EventoIMG/logo.jpg";
        String mascoteURL = System.getProperty("user.dir") + "/src/test/resources/EventoIMG/mascote.jpg";
        File fileLogo = new File(logoURL);
        File fileMascote = new File(mascoteURL);
        byte[] logo = Files.readAllBytes(fileLogo.toPath());
        byte[] mascote = Files.readAllBytes(fileMascote.toPath());
        int localId = 10;

        Evento novoEvento = new Evento(0, anoEdicao, pais, logo, mascote, localId);

        EventosDAOImp eventosDAO = new EventosDAOImp(conexao);

        assertTrue(eventosDAO.getSigla(pais), "País inválido.");

        assertFalse(eventosDAO.existsByAnoEdicao(anoEdicao), "Já existe um evento com esse ano de edição.");

        assertTrue(anoEdicao > Integer.valueOf(String.valueOf(thisYear)), "O ano de edição deve ser superior ao ano atual.");

        // Insere o novo evento
        eventosDAO.save(novoEvento);

        // Confirma se o evento foi criado
        for (Evento evento : eventosDAO.getAll()) {
            if (evento.getAno_edicao() == anoEdicao) {
                criado = true;
                break;
            }
        }

        assertTrue(criado, "O evento foi criado");

        // Remove o evento para manter a base de dados consistente para futuros testes
        eventosDAO.deleteByAno(anoEdicao);
    }

    /**
     * Testa a tentativa de inserção de um evento inválido.
     * Verifica se o sistema impede a criação de eventos com dados inválidos.
     *
     * @throws SQLException Caso ocorra um erro na conexão com a base de dados.
     * @throws IOException  Caso ocorra um erro ao ler os ficheiros de imagem.
     */
    @Test
    void InserirEventoInvalido() throws SQLException, IOException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        Year thisYear = Year.now();

        int anoEdicao = 2019;
        String pais = "POR";
        String logoURL = System.getProperty("user.dir") + "/src/test/resources/EventoIMG/logo.jpg";
        String mascoteURL = System.getProperty("user.dir") + "/src/test/resources/EventoIMG/mascote.jpg";
        File fileLogo = new File(logoURL);
        File fileMascote = new File(mascoteURL);
        byte[] logo = Files.readAllBytes(fileLogo.toPath());
        byte[] mascote = Files.readAllBytes(fileMascote.toPath());
        int localId = 10;

        Evento novoEvento = new Evento(0, anoEdicao, pais, logo, mascote, localId);

        EventosDAOImp eventosDAO = new EventosDAOImp(conexao);

        assertFalse(eventosDAO.getSigla(pais), "País válido.");

        assertTrue(eventosDAO.existsByAnoEdicao(anoEdicao), "Não existe nenhum evento com esse ano de edição.");

        assertFalse(anoEdicao > Integer.valueOf(String.valueOf(thisYear)), "O ano de edição deve ser inferior ao ano atual.");
    }
}
