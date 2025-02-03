package com.example.oporto_olympics.Models;

import java.util.Date;

/**
 * A classe {@link Local} representa um local onde eventos desportivos podem ocorrer,
 * incluindo informações sobre o nome, tipo, morada, cidade, país, capacidade e ano de construção.
 */
public class Local {
    /**
     * Identificador único do local.
     */
    private int Id;
    /**
     * Nome do local.
     */
    private String Nome;
    /**
     * Tipo do local.
     */
    private String Tipo;
    /**
     * Morada do local.
     */
    private String Morada;
    /**
     * Cidade onde o local está situado.
     */
    private String Cidade;
    /**
     * País onde o local está situado.
     */
    private String Pais;
    /**
     * Capacidade máxima do local (número de pessoas que pode acomodar).
     */
    private int Capacidade;
    /**
     * Ano de construção do local.
     */
    private Date Ano_construcao;
    /**
     * Construtor da classe {@link Local} que inicializa todos os campos.
     *
     * @param id             Identificador único do local.
     * @param nome           Nome do local.
     * @param tipo           Tipo do local (ex.: estádio, ginásio).
     * @param morada         Morada do local.
     * @param cidade         Cidade onde o local está localizado.
     * @param pais           País onde o local está localizado.
     * @param capacidade     Capacidade máxima de público do local.
     * @param ano_construcao Ano de construção do local.
     */
    public Local (int id, String nome, String tipo, String morada, String cidade, String pais, int capacidade, Date ano_construcao) {
        this.Id = id;
        this.Nome = nome;
        this.Tipo = tipo;
        this.Morada = morada;
        this.Cidade = cidade;
        this.Pais = pais;
        this.Capacidade = capacidade;
        this.Ano_construcao = ano_construcao;
    }
    /**
     * Obtém o identificador do local.
     *
     * @return O identificador do local.
     */
    public int getId() {
        return Id;
    }
    /**
     * Define o identificador do local.
     *
     * @param id O novo identificador do local.
     */
    public void setId(int id) {
        this.Id = id;
    }
    /**
     * Obtém o nome do local.
     *
     * @return O nome do local.
     */
    public String getNome() {
        return Nome;
    }
    /**
     * Define o nome do local.
     *
     * @param nome O novo nome do local.
     */
    public void setNome(String nome) {
        this.Nome = nome;
    }
    /**
     * Obtém o tipo do local (ex.: estádio, ginásio).
     *
     * @return O tipo do local.
     */
    public String getTipo() {
        return Tipo;
    }
    /**
     * Define o tipo do local.
     *
     * @param tipo O novo tipo do local.
     */
    public void setTipo(String tipo) {
        this.Tipo = tipo;
    }
    /**
     * Obtém a morada do local.
     *
     * @return A morada do local.
     */
    public String getMorada() {
        return Morada;
    }
    /**
     * Define a morada do local.
     *
     * @param morada A nova morada do local.
     */
    public void setMorada(String morada) {
        this.Morada = morada;
    }
    /**
     * Obtém a cidade onde o local está localizado.
     *
     * @return A cidade do local.
     */
    public String getCidade() {
        return Cidade;
    }
    /**
     * Define a cidade onde o local está localizado.
     *
     * @param cidade A nova cidade do local.
     */
    public void setCidade(String cidade) {
        this.Cidade = cidade;
    }
    /**
     * Obtém o país onde o local está localizado.
     *
     * @return O país do local.
     */
    public String getPais() {
        return Pais;
    }
    /**
     * Define o país onde o local está localizado.
     *
     * @param pais O novo país do local.
     */
    public void setPais(String pais) {
        this.Pais = pais;
    }
    /**
     * Obtém a capacidade máxima de público do local.
     *
     * @return A capacidade do local.
     */
    public int getCapacidade() {
        return Capacidade;
    }
    /**
     * Define a capacidade máxima de público do local.
     *
     * @param capacidade A nova capacidade do local.
     */
    public void setCapacidade(int capacidade) {
        this.Capacidade = capacidade;
    }
    /**
     * Obtém o ano de construção do local.
     *
     * @return O ano de construção do local.
     */
    public Date getAno_construcao() {
        return Ano_construcao;
    }
    /**
     * Define o ano de construção do local.
     *
     * @param ano_construcao O novo ano de construção do local.
     */
    public void setAno_construcao(Date ano_construcao) {
        this.Ano_construcao = ano_construcao;
    }
}
