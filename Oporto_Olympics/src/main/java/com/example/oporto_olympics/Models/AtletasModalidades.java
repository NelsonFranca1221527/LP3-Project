package com.example.oporto_olympics.Models;

public class AtletasModalidades {

    private int atleta_id;
    private int modalidade_id;
    private int evento_id;

    public AtletasModalidades(int atleta_id, int modalidade_id, int evento_id) {
        this.atleta_id = atleta_id;
        this.modalidade_id = modalidade_id;
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
    public int getEvento_id() {
        return evento_id;
    }
}
