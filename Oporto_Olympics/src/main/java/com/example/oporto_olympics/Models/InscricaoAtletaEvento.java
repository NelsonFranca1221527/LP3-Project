package com.example.oporto_olympics.Models;

public class InscricaoAtletaEvento {

    private int id;
    private String estado;
    private int evento_id;
    private int atleta_id;
    private int modalidade_id;

    public InscricaoAtletaEvento(int id, String estado, int evento_id, int atleta_id, int modalidade_id) {

        this.id = id;
        this.estado = estado;
        this.evento_id = evento_id;
        this.atleta_id = atleta_id;
        this.modalidade_id = modalidade_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getEvento_id() {
        return evento_id;
    }

    public void setEvento_id(int evento_id) {
        this.evento_id = evento_id;
    }

    public int getAtleta_id() {
        return atleta_id;
    }

    public void setAtleta_id(int atleta_id) {
        this.atleta_id = atleta_id;
    }

    public int getModalidade_id() {
        return modalidade_id;
    }

    public void setModalidade_id(int modalidade_id) {
        this.modalidade_id = modalidade_id;
    }


}
