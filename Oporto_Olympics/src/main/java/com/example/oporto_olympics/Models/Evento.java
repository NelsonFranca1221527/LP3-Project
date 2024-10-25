package com.example.oporto_olympics.Models;

public class Evento {
    private int Id;
    private int Ano_edicao;
    private String Pais;
    private byte[] Logo;
    private byte[] Mascote;
    private int Local_id;

    public Evento (int id, int ano_edicao, String pais, byte[] logo, byte[] mascote, int local_id) {
        this.Id = id;
        this.Ano_edicao = ano_edicao;
        this.Pais = pais;
        this.Logo = logo;
        this.Mascote = mascote;
        this.Local_id = local_id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getAno_edicao() {
        return Ano_edicao;
    }

    public void setAno_edicao(int ano_edicao) {
        this.Ano_edicao = ano_edicao;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        this.Pais = pais;
    }

    public byte[] getLogo() {
        return Logo;
    }

    public void setLogo(byte[] logo) {
        this.Logo = logo;
    }

    public byte[] getMascote() {
        return Mascote;
    }

    public void setMascote(byte[] mascote) {
        this.Mascote = mascote;
    }

    public int getLocal_id() {
        return Local_id;
    }

    public void setLocal_id(int local_id) {
        this.Local_id = local_id;
    }
}
