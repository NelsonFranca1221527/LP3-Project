package com.example.oporto_olympics.API.Models;

import java.time.LocalDateTime;

/**
 * Classe que representa um jogo no contexto dos Jogos Olímpicos do Porto.
 * Contém informações sobre o jogo, como o identificador, horário, local, desporto,
 * capacidade de público e o evento associado.
 */
public class Jogo {

    /**
     * Identificador único do jogo.
     */
    private String id;

    /**
     * Data e hora de início do jogo.
     */
    private LocalDateTime dataInicio;

    /**
     * Data e hora de fim do jogo.
     */
    private LocalDateTime dataFim;

    /**
     * Nome do local onde o jogo será realizado.
     */
    private String local;

    /**
     * Nome do desporto associado ao jogo.
     */
    private String desporto;

    /**
     * Capacidade máxima de público do local onde o jogo será realizado.
     */
    private int capacidade;

    /**
     * Identificador do evento ao qual o jogo está associado.
     */
    private int eventoID;

    /**
     * Construtor da classe Jogo.
     *
     * @param id o identificador único do jogo
     * @param dataInicio a data e hora de início do jogo
     * @param dataFim a data e hora de fim do jogo
     * @param local o nome do local onde o jogo será realizado
     * @param desporto o nome do desporto associado ao jogo
     * @param capacidade a capacidade máxima de público do local
     * @param eventoID o identificador do evento ao qual o jogo está associado
     */
    public Jogo(String id, LocalDateTime dataInicio, LocalDateTime dataFim, String local, String desporto, int capacidade, int eventoID) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.desporto = desporto;
        this.capacidade = capacidade;
        this.eventoID = eventoID;
    }

    /**
     * Obtém o identificador único do jogo.
     *
     * @return o identificador do jogo
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador único do jogo.
     *
     * @param id o novo identificador do jogo
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtém a data e hora de início do jogo.
     *
     * @return a data e hora de início
     */
    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data e hora de início do jogo.
     *
     * @param dataInicio a nova data e hora de início
     */
    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Obtém a data e hora de fim do jogo.
     *
     * @return a data e hora de fim
     */
    public LocalDateTime getDataFim() {
        return dataFim;
    }

    /**
     * Define a data e hora de fim do jogo.
     *
     * @param dataFim a nova data e hora de fim
     */
    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Obtém o nome do local onde o jogo será realizado.
     *
     * @return o nome do local
     */
    public String getLocal() {
        return local;
    }

    /**
     * Define o nome do local onde o jogo será realizado.
     *
     * @param local o novo nome do local
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * Obtém o nome do desporto associado ao jogo.
     *
     * @return o nome do desporto
     */
    public String getDesporto() {
        return desporto;
    }

    /**
     * Define o nome do desporto associado ao jogo.
     *
     * @param desporto o novo nome do desporto
     */
    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }

    /**
     * Obtém a capacidade máxima de público do local.
     *
     * @return a capacidade máxima
     */
    public int getCapacidade() {
        return capacidade;
    }

    /**
     * Define a capacidade máxima de público do local.
     *
     * @param capacidade a nova capacidade máxima
     */
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    /**
     * Obtém o identificador do evento ao qual o jogo está associado.
     *
     * @return o identificador do evento
     */
    public int getEventoID() {
        return eventoID;
    }

    /**
     * Define o identificador do evento ao qual o jogo está associado.
     *
     * @param eventoID o novo identificador do evento
     */
    public void setEventoID(int eventoID) {
        this.eventoID = eventoID;
    }
}