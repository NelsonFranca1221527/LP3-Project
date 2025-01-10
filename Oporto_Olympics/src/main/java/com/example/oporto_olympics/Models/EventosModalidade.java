package com.example.oporto_olympics.Models;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class EventosModalidade {

    private int evento_id;
    private int modalidade_id;
    private byte modalidade_status;
    private Timestamp data_modalidade;
    private Time duracao;
    private int local_id;
    /**
     * Construtor para inicializar todos os atributos de {@code EventosModalidade}.
     *
     * @param evento_id ID do evento associado.
     * @param modalidade_id ID da modalidade associada.
     * @param modalidade_status Estado atual da modalidade (byte).
     * @param data_modalidade Data e hora do evento da modalidade.
     * @param duracao Duração do evento.
     * @param local_id ID do local onde o evento será realizado.
     */
    public EventosModalidade(int evento_id, int modalidade_id,byte modalidade_status, Timestamp data_modalidade, Time duracao, int local_id) {
        this.evento_id = evento_id;
        this.modalidade_id = modalidade_id;
        this.modalidade_status = modalidade_status;
        this.data_modalidade = data_modalidade;
        this.duracao = duracao;
        this.local_id = local_id;
    }
    /**
     * Obtém o ID do evento associado.
     *
     * @return O ID do evento.
     */
    public int getEvento_id() {
        return evento_id;
    }
    /**
     * Define o ID do evento associado.
     *
     * @param evento_id O ID do evento.
     */
    public void setEvento_id(int evento_id) {
        this.evento_id = evento_id;
    }
    /**
     * Obtém o ID da modalidade associada.
     *
     * @return O ID da modalidade.
     */
    public int getModalidade_id() {
        return modalidade_id;
    }
    /**
     * Define o ID da modalidade associada.
     *
     * @param modalidade_id O ID da modalidade.
     */
    public void setModalidade_id(int modalidade_id) {
        this.modalidade_id = modalidade_id;
    }
    /**
     * Obtém o estado atual da modalidade.
     *
     * @return O estado atual da modalidade (byte).
     */
    public byte getModalidade_status() {
        return modalidade_status;
    }
    /**
     * Define o estado atual da modalidade.
     *
     * @param modalidade_status O estado da modalidade (byte).
     */
    public void setModalidade_status(byte modalidade_status) {
        this.modalidade_status = modalidade_status;
    }
    /**
     * Obtém a data e hora do evento da modalidade.
     *
     * @return A data e hora do evento da modalidade como {@link Timestamp}.
     */
    public Timestamp getData_modalidade() {
        return data_modalidade;
    }
    /**
     * Define a data e hora do evento da modalidade.
     *
     * @param data_modalidade A data e hora do evento da modalidade como {@link Timestamp}.
     */
    public void setData_modalidade(Timestamp data_modalidade) {
        this.data_modalidade = data_modalidade;
    }
    /**
     * Obtém a duração do evento.
     *
     * @return A duração do evento como {@link Time}.
     */
    public Time getDuracao() {
        return duracao;
    }
    /**
     * Define a duração do evento.
     *
     * @param duracao A duração do evento como {@link Time}.
     */
    public void setDuracao(Time duracao) {
        this.duracao = duracao;
    }
    /**
     * Obtém o ID do local onde o evento será realizado.
     *
     * @return O ID do local.
     */
    public int getLocal_id() {
        return local_id;
    }
    /**
     * Define o ID do local onde o evento será realizado.
     *
     * @param local_id O ID do local.
     */
    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }
}
