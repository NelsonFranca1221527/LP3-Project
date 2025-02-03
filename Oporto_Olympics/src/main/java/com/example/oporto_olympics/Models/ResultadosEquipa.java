package com.example.oporto_olympics.Models;
/**
 * A classe <code>ResultadosEquipa</code> representa o histórico de resultados de uma equipa em uma determinada competição,
 * contendo informações sobre o identificador único do resultado, o identificador da equipa, o ano da participação e o
 * resultado da equipa.
 *
 */
public class ResultadosEquipa {
    /**
     * Identificador único da entrada de resultados.
     */
    private int id;
    /**
     * Identificador único da equipa associada ao resultado.
     */
    private int equipa_id;
    /**
     * Ano em que o resultado foi alcançado.
     */
    private int ano;
    /**
     * Resultado obtido pela equipa (por exemplo, posição final ou conquista específica).
     */
    private String resultado;
    /**
     * Constrói um novo objeto <code>ResultadosEquipa</code> com os valores fornecidos.
     *
     * @param id O identificador único do resultado.
     * @param equipa_id O identificador da equipa.
     * @param ano O ano da participação na competição.
     * @param resultado O resultado obtido pela equipa.
     */
    public ResultadosEquipa(int id, int equipa_id, int ano, String resultado) {
        this.id = id;
        this.equipa_id = equipa_id;
        this.ano = ano;
        this.resultado = resultado;
    }
    /**
     * Obtém o identificador único do resultado.
     *
     * @return O identificador único do resultado.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador único do resultado.
     *
     * @param id O identificador único do resultado.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o identificador da equipa associada a este resultado.
     *
     * @return O identificador da equipa.
     */
    public int getEquipa_id() {
        return equipa_id;
    }

    /**
     * Define o identificador da equipa associada a este resultado.
     *
     * @param equipa_id O identificador da equipa.
     */
    public void setEquipa_id(int equipa_id) {
        this.equipa_id = equipa_id;
    }

    /**
     * Obtém o ano da participação da equipa.
     *
     * @return O ano da participação.
     */
    public int getAno() {
        return ano;
    }

    /**
     * Define o ano da participação da equipa.
     *
     * @param ano O ano da participação.
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * Obtém o resultado obtido pela equipa.
     *
     * @return O resultado obtido pela equipa.
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * Define o resultado obtido pela equipa.
     *
     * @param resultado O resultado obtido pela equipa.
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }


}
