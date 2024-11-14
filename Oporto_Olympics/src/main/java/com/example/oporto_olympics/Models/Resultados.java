package com.example.oporto_olympics.Models;
/**
 * Representa os resultados de um atleta em uma competição.
 * Esta classe armazena informações sobre o atleta, o evento, o ano e o número de medalhas conquistadas
 * (ouro, prata e bronze) em um evento específico.
 */
public class Resultados {

    private int id;

    private int atleta_id;

    private int evento_id;

    private int ano;

    private int medalhas_ouro;

    private int medalhas_prata;

    private int medalhas_bronze;
    /**
     * Construtor para a classe {@link Resultados}.
     * Inicializa os dados de resultados de um atleta em uma competição.
     *
     * @param id O identificador único do resultado.
     * @param atleta_id O identificador do atleta relacionado com o resultado.
     * @param evento_id O identificador do evento onde o atleta competiu.
     * @param ano O ano da competição.
     * @param medalhas_ouro O número de medalhas de ouro conquistadas pelo atleta.
     * @param medalhas_prata O número de medalhas de prata conquistadas pelo atleta.
     * @param medalhas_bronze O número de medalhas de bronze conquistadas pelo atleta.
     */
    public Resultados(int id, int atleta_id, int evento_id, int ano, int medalhas_ouro, int medalhas_prata,int medalhas_bronze) {
        this.id = id;
        this.atleta_id = atleta_id;
        this.evento_id = evento_id;
        this.ano = ano;
        this.medalhas_ouro = medalhas_ouro;
        this.medalhas_prata = medalhas_prata;
        this.medalhas_bronze = medalhas_bronze;
    }
    /**
     * Obtém o identificador do resultado.
     *
     * @return O identificador único do resultado.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador do resultado.
     *
     * @param id O identificador único do resultado.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém o identificador do atleta.
     *
     * @return O identificador do atleta associado ao resultado.
     */
    public int getAtleta_id() {
        return atleta_id;
    }
    /**
     * Define o identificador do atleta.
     *
     * @param atleta_id O identificador do atleta.
     */
    public void setAtleta_id(int atleta_id) {
        this.atleta_id = atleta_id;
    }
    /**
     * Obtém o identificador do evento.
     *
     * @return O identificador do evento onde o atleta participou.
     */
    public int getEvento_id() {
        return evento_id;
    }
    /**
     * Define o identificador do evento.
     *
     * @param evento_id O identificador do evento.
     */
    public void setEvento_id(int evento_id) {
        this.evento_id = evento_id;
    }
    /**
     * Obtém o ano da competição.
     *
     * @return O ano da competição onde o atleta obteve os resultados.
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
     * Obtém o número de medalhas de ouro conquistadas pelo atleta.
     *
     * @return O número de medalhas de ouro.
     */
    public int getMedalhas_ouro() {
        return medalhas_ouro;
    }
    /**
     * Define o número de medalhas de ouro conquistadas pelo atleta.
     *
     * @param medalhas_ouro O número de medalhas de ouro.
     */
    public void setMedalhas_ouro(int medalhas_ouro) {
        this.medalhas_ouro = medalhas_ouro;
    }
    /**
     * Obtém o número de medalhas de prata conquistadas pelo atleta.
     *
     * @return O número de medalhas de prata.
     */
    public int getMedalhas_prata() {
        return medalhas_prata;
    }
    /**
     * Define o número de medalhas de prata conquistadas pelo atleta.
     *
     * @param medalhas_prata O número de medalhas de prata.
     */
    public void setMedalhas_prata(int medalhas_prata) {
        this.medalhas_prata = medalhas_prata;
    }
    /**
     * Obtém o número de medalhas de bronze conquistadas pelo atleta.
     *
     * @return O número de medalhas de bronze.
     */
    public int getMedalhas_bronze() {
        return medalhas_bronze;
    }
    /**
     * Define o número de medalhas de bronze conquistadas pelo atleta.
     *
     * @param medalhas_bronze O número de medalhas de bronze.
     */
    public void setMedalhas_bronze(int medalhas_bronze) {
        this.medalhas_bronze = medalhas_bronze;
    }


}
