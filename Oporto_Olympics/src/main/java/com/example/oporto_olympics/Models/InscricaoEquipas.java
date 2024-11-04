package com.example.oporto_olympics.Models;

public class InscricaoEquipas {

    private int id;
    private String pais_sigla;
    private Integer ano_fundacao;
    private Integer modalidade_id;
    private Integer participacoes;
    private Integer medalhas;
    private String nome;
    private String genero;
    private String desporto;


    public InscricaoEquipas(int id, String pais_sigla, Integer ano_fundacao, Integer modalidade_id, Integer participacoes, Integer medalhas, String nome, String genero, String desporto) {
        this.id = id;
        this.pais_sigla = pais_sigla;
        this.ano_fundacao = ano_fundacao;
        this.modalidade_id = modalidade_id;
        this.participacoes = participacoes;
        this.medalhas = medalhas;
        this.nome = nome;
        this.genero = genero;
        this.desporto = desporto;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPais_sigla() {
        return pais_sigla;
    }
    public void setPais_sigla(String pais_sigla) {
        this.pais_sigla = pais_sigla;
    }
    public Integer getAno_fundacao() {
        return ano_fundacao;
    }
    public void setAno_fundacao(Integer ano_fundacao) {
        this.ano_fundacao = ano_fundacao;
    }
    public Integer getModalidade_id() {
        return modalidade_id;
    }
    public void setModalidade_id(Integer modalidade_id) {
        this.modalidade_id = modalidade_id;
    }
    public Integer getParticipacoes() {
        return participacoes;
    }
    public void setParticipacoes(Integer participacoes) {
        this.participacoes = participacoes;
    }
    public Integer getMedalhas() {
        return medalhas;
    }
    public void setMedalhas(Integer medalhas) {
        this.medalhas = medalhas;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getDesporto() {
        return desporto;
    }
    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }
}
