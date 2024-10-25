package com.example.oporto_olympics.Models;

import java.util.Date;
import java.util.List;

public class Atleta {

    private int id;

    private String nome;

    private String pais;

    private String genero;

    private int altura;

    private int peso;

    private Date dataNascimento;

    private List<ParticipaçõesAtleta> participaçõesAtletas;

    public Atleta(int id, String nome, String pais, String genero, int altura, int peso, Date dataNascimento, List<ParticipaçõesAtleta> participaçõesAtletas) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.genero = genero;
        this.altura = altura;
        this.peso = peso;
        this.dataNascimento = dataNascimento;
        this.participaçõesAtletas = participaçõesAtletas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<ParticipaçõesAtleta> getParticipaçõesAtletas() {
        return participaçõesAtletas;
    }

    public void setParticipaçõesAtletas(List<ParticipaçõesAtleta> participaçõesAtletas) {
        this.participaçõesAtletas = participaçõesAtletas;
    }
}
