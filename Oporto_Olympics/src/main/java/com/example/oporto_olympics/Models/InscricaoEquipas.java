package com.example.oporto_olympics.Models;
/**
 * A classe {@link InscricaoEquipas} representa os detalhes de inscrição de uma equipa
 * numa competição, incluindo identificador, país, ano de fundação, modalidade, número de participações,
 * medalhas conquistadas, nome, género e desporto associado.
 */
public class InscricaoEquipas {
    /**
     * Identificador único da equipa.
     */
    private int id;
    /**
     * Sigla do país de origem da equipa.
     */
    private String pais_sigla;
    /**
     * Ano de fundação da equipa.
     */
    private Integer ano_fundacao;
    /**
     * Identificador único da modalidade associada à equipa.
     */
    private Integer modalidade_id;
    /**
     * Nome da equipa.
     */
    private String nome;
    /**
     * Género predominante dos membros da equipa.
     */
    private String genero;
    /**
     * Desporto praticado pela equipa.
     */
    private String desporto;

    /**
     * Construtor da classe {@link InscricaoEquipas} que inicializa todos os campos.
     *
     * @param id            Identificador único da equipa.
     * @param pais_sigla    Sigla do país da equipa.
     * @param ano_fundacao  Ano de fundação da equipa (pode ser nulo).
     * @param modalidade_id Identificador da modalidade desportiva.
     * @param nome          Nome da equipa.
     * @param genero        Género (ex.: masculino, feminino) associado à equipa.
     * @param desporto      Desporto ou modalidade em que a equipa compete.
     */
    public InscricaoEquipas(int id, String pais_sigla, Integer ano_fundacao, Integer modalidade_id, String nome, String genero, String desporto) {
        this.id = id;
        this.pais_sigla = pais_sigla;
        this.ano_fundacao = ano_fundacao;
        this.modalidade_id = modalidade_id;
        this.nome = nome;
        this.genero = genero;
        this.desporto = desporto;
    }
    /**
     * Obtém o identificador da equipa.
     *
     * @return O identificador da equipa.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador da equipa.
     *
     * @param id O novo identificador da equipa.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém a sigla do país da equipa.
     *
     * @return A sigla do país.
     */
    public String getPais_sigla() {
        return pais_sigla;
    }
    /**
     * Define a sigla do país da equipa.
     *
     * @param pais_sigla A nova sigla do país.
     */
    public void setPais_sigla(String pais_sigla) {
        this.pais_sigla = pais_sigla;
    }
    /**
     * Obtém o ano de fundação da equipa.
     *
     * @return O ano de fundação da equipa.
     */
    public Integer getAno_fundacao() {
        return ano_fundacao;
    }
    /**
     * Define o ano de fundação da equipa.
     *
     * @param ano_fundacao O novo ano de fundação da equipa.
     */
    public void setAno_fundacao(Integer ano_fundacao) {
        this.ano_fundacao = ano_fundacao;
    }
    /**
     * Obtém o identificador da modalidade da equipa.
     *
     * @return O identificador da modalidade.
     */
    public Integer getModalidade_id() {
        return modalidade_id;
    }
    /**
     * Define o identificador da modalidade da equipa.
     *
     * @param modalidade_id O novo identificador da modalidade.
     */
    public void setModalidade_id(Integer modalidade_id) {
        this.modalidade_id = modalidade_id;
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
     * Obtém o género da equipa (ex.: masculino, feminino).
     *
     * @return O género da equipa.
     */
    public String getGenero() {
        return genero;
    }
    /**
     * Define o género da equipa.
     *
     * @param genero O novo género da equipa.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }
    /**
     * Obtém o desporto associado à equipa.
     *
     * @return O desporto associado.
     */
    public String getDesporto() {
        return desporto;
    }
    /**
     * Define o desporto associado à equipa.
     *
     * @param desporto O novo desporto associado.
     */
    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }

}
