package com.example.oporto_olympics.Models;

import java.util.Date;
import java.util.List;
/**
 * A classe {@link AtletaInfo} representa as informações pessoais básicas de um atleta,
 * incluindo identificador, nome, país de origem, género, altura, peso e data de nascimento.
 */
public class AtletaInfo {
    /**
     * Identificador único.
     */
    private int id;
    /**
     * Nome do Atleta.
     */
    private String nome;
    /**
     * País de origem.
     */
    private String pais;
    /**
     * Género do Atleta.
     */
    private String genero;
    /**
     * Altura em centímetros.
     */
    private int altura;
    /**
     * Peso em quilogramas.
     */
    private int peso;
    /**
     * Data de nascimento.
     */
    private Date dataNascimento;

    /**
     * Construtor para a classe {@link AtletaInfo}, que inicializa todos os atributos do atleta.
     *
     * @param id              Identificador único do atleta.
     * @param nome            Nome completo do atleta.
     * @param pais            País de origem do atleta.
     * @param genero          Género do atleta.
     * @param altura          Altura do atleta em centímetros.
     * @param peso            Peso do atleta em quilogramas.
     * @param dataNascimento  Data de nascimento do atleta.
     */
    public AtletaInfo(int id, String nome, String pais, String genero, int altura, int peso, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.genero = genero;
        this.altura = altura;
        this.peso = peso;
        this.dataNascimento = dataNascimento;
    }
    /**
     * Obtém o identificador único do atleta.
     *
     * @return O identificador do atleta.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador único do atleta.
     *
     * @param id O novo identificador do atleta.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém o nome do atleta.
     *
     * @return O nome do atleta.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Define o nome do atleta.
     *
     * @param nome O novo nome do atleta.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Obtém o país de origem do atleta.
     *
     * @return O país de origem do atleta.
     */
    public String getPais() {
        return pais;
    }
    /**
     * Define o país de origem do atleta.
     *
     * @param pais O novo país de origem do atleta.
     */
    public void setPais(String pais) {
        this.pais = pais;
    }
    /**
     * Obtém o género do atleta.
     *
     * @return O género do atleta.
     */
    public String getGenero() {
        return genero;
    }
    /**
     * Define o género do atleta.
     *
     * @param genero O novo género do atleta.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }
    /**
     * Obtém a altura do atleta em centímetros.
     *
     * @return A altura do atleta.
     */
    public int getAltura() {
        return altura;
    }
    /**
     * Define a altura do atleta.
     *
     * @param altura A nova altura do atleta em centímetros.
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }
    /**
     * Obtém o peso do atleta em quilogramas.
     *
     * @return O peso do atleta.
     */
    public int getPeso() {
        return peso;
    }
    /**
     * Define o peso do atleta.
     *
     * @param peso O novo peso do atleta em quilogramas.
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }
    /**
     * Obtém a data de nascimento do atleta.
     *
     * @return A data de nascimento do atleta.
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }
    /**
     * Define a data de nascimento do atleta.
     *
     * @param dataNascimento A nova data de nascimento do atleta.
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


}
