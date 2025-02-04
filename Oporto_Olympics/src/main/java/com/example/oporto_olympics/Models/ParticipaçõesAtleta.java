package com.example.oporto_olympics.Models;
/**
 * A classe {@link ParticipaçõesAtleta} representa as participações de um atleta nas competições olímpicas,
 * incluindo o número de medalhas (ouro, prata e bronze) conquistadas em um determinado ano.
 */
public class ParticipaçõesAtleta {
    /**
     * Ano em que o atleta participou na competição.
     */
    private int ano;
    /**
     * Número de medalhas de ouro conquistadas pelo atleta.
     */
    private int ouro;
    /**
     * Número de medalhas de prata conquistadas pelo atleta.
     */
    private int prata;
    /**
     * Número de medalhas de bronze conquistadas pelo atleta.
     */
    private int bronze;
    /**
     * Construtor da classe {@link ParticipaçõesAtleta} que inicializa os atributos de ano e medalhas.
     *
     * @param ano    O ano da competição.
     * @param ouro   O número de medalhas de ouro conquistadas.
     * @param prata  O número de medalhas de prata conquistadas.
     * @param bronze O número de medalhas de bronze conquistadas.
     */
    public ParticipaçõesAtleta(int ano, int ouro, int prata, int bronze) {
        this.ano = ano;
        this.ouro = ouro;
        this.prata = prata;
        this.bronze = bronze;
    }
    /**
     * Obtém o ano da competição.
     *
     * @return O ano da competição.
     */
    public int getAno() {
        return ano;
    }
    /**
     * Define o ano da competição.
     *
     * @param ano O ano da competição.
     */
    public void setAno(int ano) {
        this.ano = ano;
    }
    /**
     * Obtém o número de medalhas de ouro conquistadas.
     *
     * @return O número de medalhas de ouro.
     */
    public int getOuro() {
        return ouro;
    }
    /**
     * Define o número de medalhas de ouro conquistadas.
     *
     * @param ouro O número de medalhas de ouro.
     */
    public void setOuro(int ouro) {
        this.ouro = ouro;
    }
    /**
     * Obtém o número de medalhas de prata conquistadas.
     *
     * @return O número de medalhas de prata.
     */
    public int getPrata() {
        return prata;
    }
    /**
     * Define o número de medalhas de prata conquistadas.
     *
     * @param prata O número de medalhas de prata.
     */
    public void setPrata(int prata) {
        this.prata = prata;
    }
    /**
     * Obtém o número de medalhas de bronze conquistadas.
     *
     * @return O número de medalhas de bronze.
     */
    public int getBronze() {
        return bronze;
    }
    /**
     * Define o número de medalhas de bronze conquistadas.
     *
     * @param bronze O número de medalhas de bronze.
     */
    public void setBronze(int bronze) {
        this.bronze = bronze;
    }
}
