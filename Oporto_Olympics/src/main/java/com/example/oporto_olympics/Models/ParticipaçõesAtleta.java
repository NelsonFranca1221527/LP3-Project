package com.example.oporto_olympics.Models;

public class ParticipaçõesAtleta {

    private int ano;

    private int ouro;

    private int prata;

    private int bronze;

    public ParticipaçõesAtleta(int ano, int ouro, int prata, int bronze) {
        this.ano = ano;
        this.ouro = ouro;
        this.prata = prata;
        this.bronze = bronze;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getOuro() {
        return ouro;
    }

    public void setOuro(int ouro) {
        this.ouro = ouro;
    }

    public int getPrata() {
        return prata;
    }

    public void setPrata(int prata) {
        this.prata = prata;
    }

    public int getBronze() {
        return bronze;
    }

    public void setBronze(int bronze) {
        this.bronze = bronze;
    }
}
