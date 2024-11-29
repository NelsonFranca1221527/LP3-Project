/**
 * Representa um participante nas Modalidades, que pode ser um atleta ou uma equipa.
 */
package com.example.oporto_olympics.Models;

public class Participante {

    /**
     * Identificador único do participante.
     */
    private int ID;

    /**
     * Nome do participante.
     */
    private String nome;

    /**
     * Tipo do participante (por exemplo, "Equipa" ou "Atleta").
     */
    private String tipo;

    /**
     * Construtor da classe Participante.
     *
     * @param ID Identificador único do participante.
     * @param nome Nome do participante.
     * @param tipo Tipo do participante ("Equipa" ou "Atleta").
     */
    public Participante(int ID, String nome, String tipo) {
        this.ID = ID;
        this.nome = nome;
        this.tipo = tipo;
    }

    /**
     * Obtém o identificador único do participante.
     *
     * @return O ID do participante.
     */
    public int getID() {
        return ID;
    }

    /**
     * Define o identificador único do participante.
     *
     * @param ID O novo ID do participante.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Obtém o nome do participante.
     *
     * @return O nome do participante.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do participante.
     *
     * @param nome O novo nome do participante.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o tipo do participante.
     *
     * @return O tipo do participante ("Equipa" ou "Atleta").
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do participante.
     *
     * @param tipo O novo tipo do participante ("Equipa" ou "Atleta").
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
