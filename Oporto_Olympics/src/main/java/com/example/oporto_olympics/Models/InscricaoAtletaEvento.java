package com.example.oporto_olympics.Models;
/**
 * Representa a inscrição de um atleta em um evento específico, incluindo a modalidade e o estado da inscrição.
 */
public class InscricaoAtletaEvento {
    /**
     * Identificador único da inscrição.
     */
    private int id;
    /**
     * Estado atual da inscrição (por exemplo, "pendente", "aprovado").
     */
    private String estado;
    /**
     * Identificador único do evento em que o atleta está inscrito.
     */
    private int evento_id;
    /**
     * Identificador único do atleta.
     */
    private int atleta_id;
    /**
     * Identificador único da modalidade associada à inscrição.
     */
    private int modalidade_id;
    /**
     * Construtor que inicializa a inscrição com os valores fornecidos.
     *
     * @param id identificador da inscrição
     * @param estado estado da inscrição
     * @param evento_id identificador do evento
     * @param atleta_id identificador do atleta
     * @param modalidade_id identificador da modalidade
     */
    public InscricaoAtletaEvento(int id, String estado, int evento_id, int atleta_id, int modalidade_id) {

        this.id = id;
        this.estado = estado;
        this.evento_id = evento_id;
        this.atleta_id = atleta_id;
        this.modalidade_id = modalidade_id;
    }
    /**
     * Obtém o identificador da inscrição.
     *
     * @return o id da inscrição
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador da inscrição.
     *
     * @param id o id da inscrição
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém o estado da inscrição.
     *
     * @return o estado da inscrição
     */
    public String getEstado() {
        return estado;
    }
    /**
     * Define o estado da inscrição.
     *
     * @param estado o estado da inscrição
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    /**
     * Obtém o identificador do evento em que o atleta está inscrito.
     *
     * @return o id do evento
     */
    public int getEvento_id() {
        return evento_id;
    }
    /**
     * Define o identificador do evento.
     *
     * @param evento_id o id do evento
     */
    public void setEvento_id(int evento_id) {
        this.evento_id = evento_id;
    }
    /**
     * Obtém o identificador do atleta.
     *
     * @return o id do atleta
     */
    public int getAtleta_id() {
        return atleta_id;
    }
    /**
     * Define o identificador do atleta.
     *
     * @param atleta_id o id do atleta
     */
    public void setAtleta_id(int atleta_id) {
        this.atleta_id = atleta_id;
    }
    /**
     * Obtém o identificador da modalidade.
     *
     * @return o id da modalidade
     */
    public int getModalidade_id() {
        return modalidade_id;
    }
    /**
     * Define o identificador da modalidade.
     *
     * @param modalidade_id o id da modalidade
     */
    public void setModalidade_id(int modalidade_id) {
        this.modalidade_id = modalidade_id;
    }


}
