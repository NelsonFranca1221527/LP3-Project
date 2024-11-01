package com.example.oporto_olympics.Models;

import com.example.oporto_olympics.Models.RegistoModalidades.RegistoOlimpico;

public class Modalidade {

    private int id;

    private String tipo;

    private String genero;

    private String nome;

    private String descricao;

    private int minParticipantes;

    private String medida;

    private String OneGame;

    private int EventoID;

    private RegistoOlimpico recordeOlimpico;

    private RegistoOlimpico vencedorOlimpico;

    private String regras;

    public Modalidade(int id, String tipo, String genero, String nome, String descricao, int minParticipantes, String medida, String oneGame, int eventoID, RegistoOlimpico recordeOlimpico, RegistoOlimpico vencedorOlimpico, String regras) {
        this.id = id;
        this.tipo = tipo;
        this.genero = genero;
        this.nome = nome;
        this.descricao = descricao;
        this.minParticipantes = minParticipantes;
        this.medida = medida;
        OneGame = oneGame;
        EventoID = eventoID;
        this.recordeOlimpico = recordeOlimpico;
        this.vencedorOlimpico = vencedorOlimpico;
        this.regras = regras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getMinParticipantes() {
        return minParticipantes;
    }

    public void setMinParticipantes(int minParticipantes) {
        this.minParticipantes = minParticipantes;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getOneGame() {
        return OneGame;
    }

    public void setOneGame(String oneGame) {
        OneGame = oneGame;
    }

    public int getEventoID() {
        return EventoID;
    }

    public void setEventoID(int eventoID) {
        EventoID = eventoID;
    }

    public RegistoOlimpico getRecordeOlimpico() {
        return recordeOlimpico;
    }

    public void setRecordeOlimpico(RegistoOlimpico recordeOlimpico) {
        this.recordeOlimpico = recordeOlimpico;
    }

    public RegistoOlimpico getVencedorOlimpico() {
        return vencedorOlimpico;
    }

    public void setVencedorOlimpico(RegistoOlimpico vencedorOlimpico) {
        this.vencedorOlimpico = vencedorOlimpico;
    }

    public String getRegras() {
        return regras;
    }

    public void setRegras(String regras) {
        this.regras = regras;
    }
}
