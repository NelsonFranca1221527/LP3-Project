package com.example.oporto_olympics.DAO.Atleta;

import com.example.oporto_olympics.Models.InscricaoAtletaEvento;

import java.util.List;

/**
 * Interface para o DAO de aprovação de inscrições de atletas.
 * Define os métodos que são implementados para manipulação de inscrições de atletas em eventos.
 */
public interface AprovarInscricaoAtletaDAO {

    /**
     * Aprova a inscrição de um atleta com base no ID da inscrição.
     *
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL.
     */
    void aprovarInscricao(int atletaId, int modalidadeId, int eventoId);

    /**
     * Rejeita a inscrição de um atleta com base no ID da inscrição.
     *
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL.
     */
    void rejeitarInscricao(int atletaId, int modalidadeId, int eventoId);
    /**
     * Aprova uma inscrição de um atleta e associa o atleta à modalidade e ao evento.
     *
     *
     * @param atletaId O ID do atleta que será associado ao evento e à modalidade.
     * @param modalidadeId O ID da modalidade com a qual o atleta será associado.
     * @param eventoId O ID do evento ao qual o atleta será associado.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL ou ao processar os dados.
     */
    void associarAtletaAoEvento(int atletaId, int modalidadeId, int eventoId);
    /**
     * Lista todas as inscrições pendentes.
     *
     * @return Uma lista de objetos `InscricaoAtletaEvento` representando as inscrições pendentes.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta SQL.
     */
    List<InscricaoAtletaEvento> listarInscricoesPendentes();
    // Método para buscar o nome do atleta
    String getAtletaNome(int atletaId);

    // Método para buscar o nome da modalidade
    String getModalidadeNome(int modalidadeId);

    String getLocalNome(int eventoId);
}
