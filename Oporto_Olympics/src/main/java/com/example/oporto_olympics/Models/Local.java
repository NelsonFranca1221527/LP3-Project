package com.example.oporto_olympics.Models;

public class Local {
    private int Id;
    private String Nome;
    private String Tipo;
    private String Morada;
    private String Cidade;
    private String Pais;
    private int Capacidade;
    private int Ano_construcao;

    public Local (int id, String nome, String tipo, String morada, String cidade, String pais, int capacidade, int ano_construcao) {
        this.Id = id;
        this.Nome = nome;
        this.Tipo = tipo;
        this.Morada = morada;
        this.Cidade = cidade;
        this.Pais = pais;
        this.Capacidade = capacidade;
        this.Ano_construcao = ano_construcao;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        this.Tipo = tipo;
    }

    public String getMorada() {
        return Morada;
    }

    public void setMorada(String morada) {
        this.Morada = morada;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        this.Cidade = cidade;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        this.Pais = pais;
    }

    public int getCapacidade() {
        return Capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.Capacidade = capacidade;
    }

    public int getAno_construcao() {
        return Ano_construcao;
    }

    public void setAno_construcao(int ano_construcao) {
        this.Ano_construcao = ano_construcao;
    }
}
