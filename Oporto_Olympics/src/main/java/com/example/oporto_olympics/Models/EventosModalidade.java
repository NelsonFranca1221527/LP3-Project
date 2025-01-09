package com.example.oporto_olympics.Models;

import java.sql.Time;
import java.util.Date;

public class EventosModalidade {

    private int evento_id;
    private int modalidade_id;
    private byte modalidade_status;
    private Date data_modalidade;
    private Time duracao;
    private int local_id;

    public EventosModalidade(int evento_id, int modalidade_id,byte modalidade_status, Date data_modalidade, Time duracao, int local_id) {
        this.evento_id = evento_id;
        this.modalidade_id = modalidade_id;
        this.modalidade_status = modalidade_status;
        this.data_modalidade = data_modalidade;
        this.duracao = duracao;
        this.local_id = local_id;
    }
    public int getEvento_id() {
        return evento_id;
    }
    public void setEvento_id(int evento_id) {
        this.evento_id = evento_id;
    }
    public int getModalidade_id() {
        return modalidade_id;
    }
    public void setModalidade_id(int modalidade_id) {
        this.modalidade_id = modalidade_id;
    }
    public byte getModalidade_status() {
        return modalidade_status;
    }
    public void setModalidade_status(byte modalidade_status) {
        this.modalidade_status = modalidade_status;
    }
    public Date getData_modalidade() {
        return data_modalidade;
    }
    public void setData_modalidade(Date data_modalidade) {
        this.data_modalidade = data_modalidade;
    }
    public Time getDuracao() {
        return duracao;
    }
    public void setDuracao(Time duracao) {
        this.duracao = duracao;
    }
    public int getLocal_id() {
        return local_id;
    }
    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }
}
