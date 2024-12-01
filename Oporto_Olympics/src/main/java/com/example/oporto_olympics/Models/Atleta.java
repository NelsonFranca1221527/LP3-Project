package com.example.oporto_olympics.Models;

import java.util.Date;
import java.util.List;
/**
 * A classe {@link Atleta} representa um atleta com detalhes pessoais,
 * como nome, país, género, altura, peso e data de nascimento, bem como
 * uma lista de participações em eventos olímpicos.
 */
public class Atleta {

    private int id;

    private String nome;

    private String pais;

    private String genero;

    private int altura;

    private int peso;

    private Date dataNascimento;

    private List<ParticipaçõesAtleta> participaçõesAtletas;

    private byte[] fotoPerfil;
    /**
     * Construtor para a classe {@link Atleta}, inicializando todos os atributos do atleta.
     *
     * @param id                     Identificador único do atleta.
     * @param nome                   Nome completo do atleta.
     * @param pais                   País de origem do atleta.
     * @param genero                 Género do atleta.
     * @param altura                 Altura do atleta em centímetros.
     * @param peso                   Peso do atleta em quilogramas.
     * @param dataNascimento         Data de nascimento do atleta.
     * @param participaçõesAtletas   Lista de participações do atleta em eventos.
     */
    public Atleta(int id, String nome, String pais, String genero, int altura, int peso, Date dataNascimento, List<ParticipaçõesAtleta> participaçõesAtletas, byte[] fotoPerfil) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.genero = genero;
        this.altura = altura;
        this.peso = peso;
        this.dataNascimento = dataNascimento;
        this.participaçõesAtletas = participaçõesAtletas;
        this.fotoPerfil = fotoPerfil;
    }
    /**
     * Obtém o identificador do atleta.
     *
     * @return O identificador único do atleta.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador do atleta.
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
     * Obtém o país do atleta.
     *
     * @return O país de origem do atleta.
     */
    public String getPais() {
        return pais;
    }
    /**
     * Define o país do atleta.
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
    /**
     * Obtém a lista de participações do atleta em eventos.
     *
     * @return A lista de participações do atleta.
     */
    public List<ParticipaçõesAtleta> getParticipaçõesAtletas() {
        return participaçõesAtletas;
    }
    /**
     * Define a lista de participações do atleta em eventos.
     *
     * @param participaçõesAtletas A nova lista de participações do atleta.
     */
    public void setParticipaçõesAtletas(List<ParticipaçõesAtleta> participaçõesAtletas) {
        this.participaçõesAtletas = participaçõesAtletas;
    }
    /**
     * Obtém a foto de perfil do atleta.
     *
     * @return A foto de perfil do atleta como um array de bytes.
     */
    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }
    /**
     * Define a foto de perfil do atleta.
     *
     * @param fotoPerfil A nova foto de perfil do atleta como um array de bytes.
     */
    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
