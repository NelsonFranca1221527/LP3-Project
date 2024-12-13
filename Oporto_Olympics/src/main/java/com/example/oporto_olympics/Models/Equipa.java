package com.example.oporto_olympics.Models;

import java.util.List;
/**
 * A classe {@link Equipa} representa uma equipa desportiva com informações sobre
 * o seu nome, país de origem, género, desporto praticado, modalidade associada,
 * ano de fundação e lista de participações em eventos desportivos.
 */
public class Equipa {

    private int id;

    private String nome;

    private String pais;

    private String genero;

    private String desporto;

    private int modalidadeID;

    private int anoFundacao;

    private List<ParticipaocesEquipa> participaocesEquipa;
    /**
     * Construtor para a classe {@link Equipa}, que inicializa todos os atributos da equipa.
     *
     * @param id                  Identificador único da equipa.
     * @param nome                Nome da equipa.
     * @param pais                País de origem da equipa.
     * @param genero              Género da equipa (masculino, feminino, misto).
     * @param desporto            Tipo de desporto praticado pela equipa.
     * @param modalidadeID        Identificador da modalidade associada à equipa.
     * @param anoFundacao         Ano de fundação da equipa.
     * @param participaocesEquipa Lista das participações da equipa em competições.
     */
    public Equipa(int id, String nome, String pais, String genero, String desporto, int modalidadeID, int anoFundacao, List<ParticipaocesEquipa> participaocesEquipa) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.genero = genero;
        this.desporto = desporto;
        this.modalidadeID = modalidadeID;
        this.anoFundacao = anoFundacao;
        this.participaocesEquipa = participaocesEquipa;
    }
    /**
     * Obtém o identificador único da equipa.
     *
     * @return O identificador da equipa.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador único da equipa.
     *
     * @param id O novo identificador da equipa.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém o nome da equipa.
     *
     * @return O nome da equipa.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Define o nome da equipa.
     *
     * @param nome O novo nome da equipa.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Obtém o país de origem da equipa.
     *
     * @return O país de origem da equipa.
     */
    public String getPais() {
        return pais;
    }
    /**
     * Define o país de origem da equipa.
     *
     * @param pais O novo país de origem da equipa.
     */
    public void setPais(String pais) {
        this.pais = pais;
    }
    /**
     * Obtém o género da equipa.
     *
     * @return O género da equipa (masculino, feminino, misto).
     */
    public String getGenero() {
        return genero;
    }
    /**
     * Define o género da equipa.
     *
     * @param genero O novo género da equipa (masculino, feminino, misto).
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }
    /**
     * Obtém o desporto praticado pela equipa.
     *
     * @return O desporto da equipa.
     */
    public String getDesporto() {
        return desporto;
    }
    /**
     * Define o desporto praticado pela equipa.
     *
     * @param desporto O novo desporto da equipa.
     */
    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }
    /**
     * Obtém o identificador da modalidade associada à equipa.
     *
     * @return O identificador da modalidade.
     */
    public int getModalidadeID() {
        return modalidadeID;
    }
    /**
     * Define o identificador da modalidade associada à equipa.
     *
     * @param modalidadeID O novo identificador da modalidade.
     */
    public void setModalidadeID(int modalidadeID) {
        this.modalidadeID = modalidadeID;
    }
    /**
     * Obtém o ano de fundação da equipa.
     *
     * @return O ano de fundação da equipa.
     */
    public int getAnoFundacao() {
        return anoFundacao;
    }
    /**
     * Define o ano de fundação da equipa.
     *
     * @param anoFundacao O novo ano de fundação da equipa.
     */
    public void setAnoFundacao(int anoFundacao) {
        this.anoFundacao = anoFundacao;
    }
    /**
     * Obtém a lista de participações da equipa em competições.
     *
     * @return A lista de participações da equipa.
     */
    public List<ParticipaocesEquipa> getParticipaçõesEquipa() {
        return participaocesEquipa;
    }
    /**
     * Define a lista de participações da equipa em competições.
     *
     * @param participaocesEquipa A nova lista de participações da equipa.
     */
    public void setParticipaçõesEquipa(List<ParticipaocesEquipa> participaocesEquipa) {
        this.participaocesEquipa = participaocesEquipa;
    }
}
