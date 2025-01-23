package com.example.oporto_olympics.Models;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Representa o horário de uma modalidade.
 * Contém informações sobre a data e hora, duração e o identificador do local onde decorre a modalidade.
 */
public class HorarioModalidade {

    /**
     * Data e hora do início da modalidade.
     */
    private LocalDateTime dataHora;

    /**
     * Duração da modalidade.
     */
    private LocalTime duracao;

    /**
     * Identificador do local onde a modalidade será realizada.
     */
    private int localID;

    /**
     * Identificador do jogo.
     */
    private String gameID;

    /**
     * Construtor para inicializar um horário de modalidade com os detalhes fornecidos.
     *
     * @param dataHora A data e hora do início da modalidade.
     * @param duracao  A duração da modalidade.
     * @param localID  O identificador do local onde a modalidade será realizada.
     * @param gameID  O identificador do jogo.
     */
    public HorarioModalidade(LocalDateTime dataHora, LocalTime duracao, int localID, String gameID) {
        this.dataHora = dataHora;
        this.duracao = duracao;
        this.localID = localID;
        this.gameID = gameID;
    }

    /**
     * Obtém a data e hora do início da modalidade.
     *
     * @return A data e hora do início da modalidade.
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Define a data e hora do início da modalidade.
     *
     * @param dataHora A nova data e hora do início da modalidade.
     */
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * Obtém a duração da modalidade.
     *
     * @return A duração da modalidade.
     */
    public LocalTime getDuracao() {
        return duracao;
    }

    /**
     * Define a duração da modalidade.
     *
     * @param duracao A nova duração da modalidade.
     */
    public void setDuracao(LocalTime duracao) {
        this.duracao = duracao;
    }

    /**
     * Obtém o identificador do local onde a modalidade será realizada.
     *
     * @return O identificador do local.
     */
    public int getLocalID() {
        return localID;
    }

    /**
     * Define o identificador do local onde a modalidade será realizada.
     *
     * @param localID O novo identificador do local.
     */
    public void setLocalID(int localID) {
        this.localID = localID;
    }

    /**
     * Obtém o ID do jogo associado.
     *
     * @return o ID do jogo como uma String.
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Define o ID do jogo associado.
     *
     * @param gameID o ID do jogo a ser definido como uma String.
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
