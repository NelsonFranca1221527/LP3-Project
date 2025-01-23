package com.example.oporto_olympics.API.Models;

/**
 * Classe que representa um bilhete (Ticket) associado a um evento desportivo.
 * Contém informações sobre o cliente, o jogo e o lugar associado ao bilhete.
 */
public class Ticket {

    /**
     * Identificador único do cliente.
     */
    private String clientID;

    /**
     * Identificador único do jogo.
     */
    private String gameID;

    /**
     * Número do lugar associado ao bilhete.
     */
    private int lugar;

    /**
     * Construtor da classe Ticket.
     *
     * @param clientID o identificador único do cliente
     * @param gameID o identificador único do jogo
     * @param lugar o número do lugar associado ao bilhete
     */
    public Ticket(String clientID, String gameID, int lugar) {
        this.clientID = clientID;
        this.gameID = gameID;
        this.lugar = lugar;
    }

    /**
     * Obtém o identificador único do cliente.
     *
     * @return o identificador único do cliente
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * Define o identificador único do cliente.
     *
     * @param clientID o novo identificador único do cliente
     */
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    /**
     * Obtém o identificador único do jogo.
     *
     * @return o identificador único do jogo
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Define o identificador único do jogo.
     *
     * @param gameID o novo identificador único do jogo
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    /**
     * Obtém o número do lugar associado ao bilhete.
     *
     * @return o número do lugar
     */
    public int getLugar() {
        return lugar;
    }

    /**
     * Define o número do lugar associado ao bilhete.
     *
     * @param lugar o novo número do lugar
     */
    public void setLugar(int lugar) {
        this.lugar = lugar;
    }
}
