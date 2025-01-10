package com.example.oporto_olympics.Models;

import java.util.Date;
/**
 * A classe {@link ResultadosModalidade} representa os resultados de um atleta numa modalidade
 * com informações sobre a sua data, resultado, tipo de resultado, medalha ganha,
 * modalidade associada e atleta associado.
 */
public class ResultadosModalidade {

    private int id;

    private Date data;

    private String resultado;

    private String tipo;

    private String medalha;

    private int modalidadeID;

    private int atletaID;

    private int equipaID;

    /**
     * Construtor para a classe {@link ResultadosModalidade}, que inicializa todos os atributos dos resultados.
     *
     * @param id                  Identificador único do resultado.
     * @param data                Data do resultado.
     * @param resultado           Resultado.
     * @param tipo                Tipo do resultado.
     * @param medalha             Medalha ganha com o resultado.
     * @param modalidadeID        Identificador da modalidade associada ao resultado.
     * @param atletaID            Identificador do atleta associada ao resultado.
     * @param equipaID            Identificador da equipa associada ao resultado.
     */
    public ResultadosModalidade(int id, Date data, String resultado, String tipo, String medalha, int modalidadeID, int atletaID, int equipaID) {
        this.id = id;
        this.data = data;
        this.resultado = resultado;
        this.tipo = tipo;
        this.medalha = medalha;
        this.modalidadeID = modalidadeID;
        this.atletaID = atletaID;
        this.equipaID = equipaID;
    }
    /**
     * Obtém o identificador único do resultado.
     *
     * @return O identificador do resultado.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador único do resultado.
     *
     * @param id O novo identificador do resultado.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém a data do resultado.
     *
     * @return A data do resultado.
     */
    public Date getData() {
        return data;
    }
    /**
     * Define a data do resultado.
     *
     * @param data A data do resultado.
     */
    public void setData(Date data) {
        this.data = data;
    }
    /**
     * Obtém o resultado.
     *
     * @return O resultado.
     */
    public String getResultado() {
        return resultado;
    }
    /**
     * Define o resultado.
     *
     * @param resultado O resultado.
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    /**
     * Obtém o tipo do resultado.
     *
     * @return O tipo do resultado.
     */
    public String getTipo() {
        return tipo;
    }
    /**
     * Define o tipo do resultado.
     *
     * @param tipo O tipo do resultado.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    /**
     * Obtém a medalha ganha com o resultado.
     *
     * @return A medalha ganha.
     */
    public String getMedalha() {
        return medalha;
    }
    /**
     * Define a medalha ganha com o resultado.
     *
     * @param medalha A medalha ganha.
     */
    public void setMedalha(String medalha) {
        this.medalha = medalha;
    }
    /**
     * Obtém o identificador da modalidade associada ao resultado.
     *
     * @return O identificador da modalidade.
     */
    public int getModalidadeID() {
        return modalidadeID;
    }
    /**
     * Define o identificador da modalidade associada ao resultado.
     *
     * @param modalidadeID O novo identificador da modalidade.
     */
    public void setModalidadeID(int modalidadeID) {
        this.modalidadeID = modalidadeID;
    }
    /**
     * Obtém o identificador do atleta associada ao resultado.
     *
     * @return O identificador do atleta.
     */
    public int getAtletaID() {
        return atletaID;
    }
    /**
     * Define o identificador do atleta associada ao resultado.
     *
     * @param atletaID O novo identificador do atleta.
     */
    public void setAtletaID(int atletaID) {
        this.atletaID = atletaID;
    }
    /**
     * Obtém o identificador da equipa associada ao resultado.
     *
     * @return O identificador da equipa.
     */
    public int getEquipaID() {
        return equipaID;
    }
    /**
     * Define o identificador da equipa associada ao resultado.
     *
     * @param equipaID O novo identificador da equipa.
     */
    public void setEquipaID(int equipaID) {
        this.equipaID = equipaID;
    }
}
