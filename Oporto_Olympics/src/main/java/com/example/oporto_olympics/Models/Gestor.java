package com.example.oporto_olympics.Models;

/**
 * A classe {@link Gestor} representa um gestor com detalhes pessoais,
 * como o nome.
 */
public class Gestor {

    private int id;

    private String nome;

    /**
     * Construtor para a classe {@link Gestor}, inicializando todos os atributos do Gestor.
     *
     * @param id                     Identificador único do Gestor.
     * @param nome                   Nome completo do Gestor.
     */
    public Gestor(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    /**
     * Obtém o identificador do gestor.
     *
     * @return O identificador único do gestor.
     */
    public int getId() { return id; }

    /**
     * Define o identificador do atleta.
     *
     * @param id O novo identificador do atleta.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Obtém o nome do gestor.
     *
     * @return O nome do gestor.
     */
    public String getNome() { return nome; }

    /**
     * Define o nome do gestor.
     *
     * @param nome O novo nome do gestor.
     */
    public void setNome(String nome) { this.nome = nome; }
}
