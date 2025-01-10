package com.example.oporto_olympics.Models;

public class AtletasModalidades {

    private int atleta_id;
    private int modalidade_id;
    private int evento_id;

    /**
     * Construtor que inicializa os atributos de {@code AtletasModalidades}.
     *
     * @param atleta_id ID do atleta inscrito.
     * @param modalidade_id ID da modalidade na qual o atleta está inscrito.
     * @param evento_id ID do evento associado à inscrição.
     */
    public AtletasModalidades(int atleta_id, int modalidade_id, int evento_id) {
        this.atleta_id = atleta_id;
        this.modalidade_id = modalidade_id;
        this.evento_id = evento_id;
    }
    /**
     * Obtém o ID do atleta inscrito.
     *
     * @return O ID do atleta.
     */
    public int getAtleta_id() {
        return atleta_id;
    }
    /**
     * Define o ID do atleta inscrito.
     *
     * @param atleta_id O ID do atleta.
     */
    public void setAtleta_id(int atleta_id) {
        this.atleta_id = atleta_id;
    }
    /**
     * Obtém o ID da modalidade na qual o atleta está inscrito.
     *
     * @return O ID da modalidade.
     */
    public int getModalidade_id() {
        return modalidade_id;
    }
    /**
     * Define o ID da modalidade na qual o atleta está inscrito.
     *
     * @param modalidade_id O ID da modalidade.
     */
    public void setModalidade_id(int modalidade_id) {
        this.modalidade_id = modalidade_id;
    }
    /**
     * Obtém o ID do evento associado à inscrição do atleta.
     *
     * @return O ID do evento.
     */
    public int getEvento_id() {
        return evento_id;
    }
}
