package com.example.oporto_olympics.API.Models;

import java.time.LocalDateTime;

public class Jogo {

    private String id;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    private String local;

    private String desporto;

    private int capacidade;

    private int eventoID;

    public Jogo(String id, LocalDateTime dataInicio, LocalDateTime dataFim, String local, String desporto, int capacidade, int eventoID) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.desporto = desporto;
        this.capacidade = capacidade;
        this.eventoID = eventoID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDesporto() {
        return desporto;
    }

    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getEventoID() {
        return eventoID;
    }

    public void setEventoID(int eventoID) {
        this.eventoID = eventoID;
    }
}
