package com.example.oporto_olympics.Models.RegistoModalidades;

import java.time.LocalTime;

public abstract class RegistoOlimpico {

    private String vencedor;
    private int ano;
    private LocalTime tempo;
    private String medalhas;

    public RegistoOlimpico(String vencedor, int ano, LocalTime tempo) {
        this.vencedor = vencedor;
        this.ano = ano;
        this.tempo = tempo;
        this.medalhas = null;
    }

    public RegistoOlimpico(String vencedor, int ano, String medalhas) {
        this.vencedor = vencedor;
        this.ano = ano;
        this.tempo = null;
        this.medalhas = medalhas;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public LocalTime getTempo() {
        return tempo;
    }

    public void setTempo(LocalTime tempo) {
        this.tempo = tempo;
    }

    public String getMedalhas() {
        return medalhas;
    }

    public void setMedalhas(String medalhas) {
        this.medalhas = medalhas;
    }
}
