package com.example.oporto_olympics.Models;

public class AprovarInscricaoEquipa {

    private int id;
    private String status;
    private int modalidade_id;
    private int atleta_id;
    private int equipa_id;

    public AprovarInscricaoEquipa(int id, String status, int modalidade_id, int atleta_id, int equipa_id) {
        this.id = id;
        this.status = status;
        this.modalidade_id = modalidade_id;
        this.atleta_id = atleta_id;
        this.equipa_id = equipa_id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getModalidade_id() {
        return modalidade_id;
    }
    public void setModalidade_id(int modalidade_id) {
        this.modalidade_id = modalidade_id;
    }
    public int getAtleta_id() {
        return atleta_id;
    }
    public void setAtleta_id(int atleta_id) {
        this.atleta_id = atleta_id;
    }
    public int getEquipa_id() {
        return equipa_id;
    }
    public void setEquipa_id(int equipa_id) {this.equipa_id = equipa_id;}
}
