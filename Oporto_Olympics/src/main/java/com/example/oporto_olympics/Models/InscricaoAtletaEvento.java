package com.example.oporto_olympics.Models;

public class InscricaoAtletaEvento {

    private int id;
    private String estado;
    private int evento_id;
    private int atleta_id;
    private int modalidade_id;
    /**
     * Construtor da classe {@code InscricaoAtletaEvento}.
     *
     * Inicializa uma nova instância de uma inscrição de atleta em um evento, com os valores
     * fornecidos para o identificador da inscrição, estado, e os IDs relacionados ao evento,
     * atleta e modalidade.
     *
     * @param id o identificador único da inscrição
     * @param estado o estado atual da inscrição (ex.: "pendente", "aprovado", "rejeitado")
     * @param evento_id o identificador do evento associado
     * @param atleta_id o identificador do atleta associado
     * @param modalidade_id o identificador da modalidade associada
     */
    public InscricaoAtletaEvento(int id, String estado, int evento_id, int atleta_id, int modalidade_id) {

        this.id = id;
        this.estado = estado;
        this.evento_id = evento_id;
        this.atleta_id = atleta_id;
        this.modalidade_id = modalidade_id;
    }
    /**
     * Obtém o identificador único da inscrição.
     *
     * @return o identificador da inscrição
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador único da inscrição.
     *
     * @param id o identificador da inscrição a ser definido
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém o estado atual da inscrição.
     *
     * @return o estado da inscrição (ex.: "pendente", "aprovado", "rejeitado")
     */
    public String getEstado() {
        return estado;
    }
    /**
     * Define o estado atual da inscrição.
     *
     * @param estado o estado da inscrição a ser definido
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    /**
     * Obtém o identificador do evento associado à inscrição.
     *
     * @return o identificador do evento
     */
    public int getEvento_id() {
        return evento_id;
    }
    /**
     * Define o identificador do evento associado à inscrição.
     *
     * @param evento_id o identificador do evento a ser definido
     */
    public void setEvento_id(int evento_id) {
        this.evento_id = evento_id;
    }
    /**
     * Obtém o identificador do atleta associado à inscrição.
     *
     * @return o identificador do atleta
     */
    public int getAtleta_id() {
        return atleta_id;
    }
    /**
     * Define o identificador do atleta associado à inscrição.
     *
     * @param atleta_id o identificador do atleta a ser definido
     */
    public void setAtleta_id(int atleta_id) {
        this.atleta_id = atleta_id;
    }
    /**
     * Obtém o identificador da modalidade associada à inscrição.
     *
     * @return o identificador da modalidade
     */
    public int getModalidade_id() {
        return modalidade_id;
    }

    /**
     * Define o identificador da modalidade associada à inscrição.
     *
     * @param modalidade_id o identificador da modalidade a ser definido
     */
    public void setModalidade_id(int modalidade_id) {
        this.modalidade_id = modalidade_id;
    }


}
