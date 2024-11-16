package com.example.oporto_olympics.Models;
/**
 * A classe {@link AprovarInscricaoEquipa} representa um registo de inscrição de um atleta
 * numa equipa para uma modalidade, com o objetivo de gerir o estado de aprovação da inscrição.
 * Inclui informações como identificadores de inscrição, modalidade, atleta, e equipa, bem como o estado atual da inscrição.
 */
public class AprovarInscricaoEquipa {

    private int id;
    private String status;
    private int modalidade_id;
    private int atleta_id;
    private int equipa_id;
    /**
     * Construtor para a classe {@link AprovarInscricaoEquipa}, inicializando todos os atributos.
     *
     * @param id            Identificador único da inscrição.
     * @param status        Estado atual da inscrição (e.g., "Aprovado", "Pendente").
     * @param modalidade_id Identificador da modalidade associada.
     * @param atleta_id     Identificador do atleta associado.
     * @param equipa_id     Identificador da equipa associada.
     */
    public AprovarInscricaoEquipa(int id, String status, int modalidade_id, int atleta_id, int equipa_id) {
        this.id = id;
        this.status = status;
        this.modalidade_id = modalidade_id;
        this.atleta_id = atleta_id;
        this.equipa_id = equipa_id;
    }
    /**
     * Obtém o identificador da inscrição.
     *
     * @return O identificador da inscrição.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador da inscrição.
     *
     * @param id O novo identificador da inscrição.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém o estado atual da inscrição.
     *
     * @return O estado atual da inscrição.
     */
    public String getStatus() {
        return status;
    }
    /**
     * Define o estado atual da inscrição.
     *
     * @param status O novo estado da inscrição (e.g., "Aprovado", "Pendente").
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Obtém o identificador da modalidade associada à inscrição.
     *
     * @return O identificador da modalidade.
     */
    public int getModalidade_id() {
        return modalidade_id;
    }
    /**
     * Define o identificador da modalidade associada à inscrição.
     *
     * @param modalidade_id O novo identificador da modalidade.
     */
    public void setModalidade_id(int modalidade_id) {
        this.modalidade_id = modalidade_id;
    }
    /**
     * Obtém o identificador do atleta associado à inscrição.
     *
     * @return O identificador do atleta.
     */
    public int getAtleta_id() {
        return atleta_id;
    }
    /**
     * Define o identificador do atleta associado à inscrição.
     *
     * @param atleta_id O novo identificador do atleta.
     */
    public void setAtleta_id(int atleta_id) {
        this.atleta_id = atleta_id;
    }
    /**
     * Obtém o identificador da equipa associada à inscrição.
     *
     * @return O identificador da equipa.
     */
    public int getEquipa_id() {
        return equipa_id;
    }
    /**
     * Define o identificador da equipa associada à inscrição.
     *
     * @param equipa_id O novo identificador da equipa.
     */
    public void setEquipa_id(int equipa_id) {this.equipa_id = equipa_id;}
}

