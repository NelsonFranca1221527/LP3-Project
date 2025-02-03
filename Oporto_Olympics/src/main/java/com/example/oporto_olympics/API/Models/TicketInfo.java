package com.example.oporto_olympics.API.Models;

/**
 * A classe {@link TicketInfo} representa as informações de um bilhete para um evento.
 * Contém detalhes como data de início e fim do evento, local, número do lugar e código QR associado ao bilhete.
 */
public class TicketInfo {

    private String id;
    private String dataInicio;
    private String dataFim;
    private String local;
    private int lugar;
    private String codigoQR;

    /**
     * Construtor da classe {@link TicketInfo}.
     *
     * @param id          Identificador único do bilhete.
     * @param dataInicio  Data de início do evento associado ao bilhete.
     * @param dataFim     Data de fim do evento associado ao bilhete.
     * @param local       Local onde o evento será realizado.
     * @param lugar       Número do lugar atribuído ao bilhete.
     * @param codigoQR    Código QR associado ao bilhete.
     */
    public TicketInfo(String id, String dataInicio, String dataFim, String local, int lugar, String codigoQR) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.lugar = lugar;
        this.codigoQR = codigoQR;
    }

    /**
     * Obtém o identificador do bilhete.
     *
     * @return O ID do bilhete.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador do bilhete.
     *
     * @param id O novo ID do bilhete.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtém a data de início do evento associado ao bilhete.
     *
     * @return A data de início.
     */
    public String getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data de início do evento associado ao bilhete.
     *
     * @param dataInicio A nova data de início.
     */
    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Obtém a data de fim do evento associado ao bilhete.
     *
     * @return A data de fim.
     */
    public String getDataFim() {
        return dataFim;
    }

    /**
     * Define a data de fim do evento associado ao bilhete.
     *
     * @param dataFim A nova data de fim.
     */
    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Obtém o local onde o evento será realizado.
     *
     * @return O local do evento.
     */
    public String getLocal() {
        return local;
    }

    /**
     * Define o local onde o evento será realizado.
     *
     * @param local O novo local do evento.
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * Obtém o número do lugar atribuído ao bilhete.
     *
     * @return O número do lugar.
     */
    public int getLugar() {
        return lugar;
    }

    /**
     * Define o número do lugar atribuído ao bilhete.
     *
     * @param lugar O novo número do lugar.
     */
    public void setLugar(int lugar) {
        this.lugar = lugar;
    }

    /**
     * Obtém o código QR associado ao bilhete.
     *
     * @return O código QR.
     */
    public String getCodigoQR() {
        return codigoQR;
    }

    /**
     * Define o código QR associado ao bilhete.
     *
     * @param codigoQR O novo código QR.
     */
    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }
}
