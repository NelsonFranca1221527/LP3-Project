package com.example.oporto_olympics.Models.RegistoModalidades;

import java.time.LocalTime;
/**
 * A classe abstrata {@link RegistoOlimpico} representa o registo de uma vitória ou participação olímpica,
 * incluindo informações sobre o vencedor, o ano, o tempo (quando aplicável) e as medalhas conquistadas.
 * Esta classe serve de base para registos específicos de eventos olímpicos que podem envolver tempos e medalhas.
 */
public abstract class RegistoOlimpico {

    private String vencedor;
    private int ano;
    private LocalTime tempo;
    private String medalhas;
    /**
     * Construtor que cria um registo olímpico com o vencedor, o ano e o tempo da participação.
     * Este construtor é útil para eventos em que o tempo é relevante, como competições de corrida.
     *
     * @param vencedor O nome do vencedor do evento olímpico.
     * @param ano      O ano em que ocorreu o evento.
     * @param tempo    O tempo que o vencedor levou para concluir o evento.
     */
    public RegistoOlimpico(String vencedor, int ano, LocalTime tempo) {
        this.vencedor = vencedor;
        this.ano = ano;
        this.tempo = tempo;
        this.medalhas = null;
    }
    /**
     * Construtor que cria um registo olímpico com o vencedor, o ano e as medalhas conquistadas.
     * Este construtor é útil para eventos em que as medalhas são relevantes mas o tempo não se aplica.
     *
     * @param vencedor O nome do vencedor do evento olímpico.
     * @param ano      O ano em que ocorreu o evento.
     * @param medalhas As medalhas conquistadas pelo vencedor (ex.: "Ouro", "Prata", "Bronze").
     */
    public RegistoOlimpico(String vencedor, int ano, String medalhas) {
        this.vencedor = vencedor;
        this.ano = ano;
        this.tempo = null;
        this.medalhas = medalhas;
    }
    /**
     * Retorna o nome do vencedor do evento olímpico.
     *
     * @return O nome do vencedor.
     */
    public String getVencedor() {
        return vencedor;
    }
    /**
     * Define o nome do vencedor do evento olímpico.
     *
     * @param vencedor O nome do vencedor.
     */
    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }
    /**
     * Retorna o ano em que o evento olímpico ocorreu.
     *
     * @return O ano do evento.
     */
    public int getAno() {
        return ano;
    }
    /**
     * Define o ano em que o evento olímpico ocorreu.
     *
     * @param ano O ano do evento.
     */
    public void setAno(int ano) {
        this.ano = ano;
    }
    /**
     * Retorna o tempo que o vencedor levou para concluir o evento, quando aplicável.
     *
     * @return O tempo de conclusão do evento, ou null se não aplicável.
     */
    public LocalTime getTempo() {
        return tempo;
    }
    /**
     * Define o tempo que o vencedor levou para concluir o evento, quando aplicável.
     *
     * @param tempo O tempo de conclusão do evento.
     */
    public void setTempo(LocalTime tempo) {
        this.tempo = tempo;
    }
    /**
     * Retorna as medalhas conquistadas pelo vencedor, quando aplicável.
     *
     * @return As medalhas conquistadas, ou null se não aplicável.
     */
    public String getMedalhas() {
        return medalhas;
    }
    /**
     * Define as medalhas conquistadas pelo vencedor.
     *
     * @param medalhas As medalhas conquistadas.
     */
    public void setMedalhas(String medalhas) {
        this.medalhas = medalhas;
    }
}
