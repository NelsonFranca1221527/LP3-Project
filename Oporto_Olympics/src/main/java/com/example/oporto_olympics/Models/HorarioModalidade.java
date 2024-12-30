package com.example.oporto_olympics.Models;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class HorarioModalidade {

    private LocalDateTime dataHora;

    private LocalTime duracao;

    private int localID;

    public HorarioModalidade(LocalDateTime dataHora, LocalTime duracao, int localID) {
        this.dataHora = dataHora;
        this.duracao = duracao;
        this.localID = localID;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public LocalTime getDuracao() {
        return duracao;
    }

    public void setDuracao(LocalTime duracao) {
        this.duracao = duracao;
    }

    public int getLocalID() {
        return localID;
    }

    public void setLocalID(int localID) {
        this.localID = localID;
    }
}
