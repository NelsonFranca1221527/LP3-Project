package com.example.oporto_olympics.Models.RegistoModalidades;

import java.time.LocalTime;

/**
 * A classe abstrata {@link RegistoOlimpico} representa o registo de um vencedor ou recorde olímpico,
 * incluindo informações sobre o vencedor, o ano, o tempo (quando aplicável), a distância (quando aplicável) e as medalhas conquistadas.
 * Esta classe serve de base para registos específicos de eventos olímpicos que podem envolver tempos, distâncias e medalhas.
 */
public abstract class RegistoOlimpico {

    /** O nome do vencedor do evento olímpico. */
    private String vencedor;

    /** O ano em que o evento olímpico ocorreu. */
    private int ano;

    /** O tempo que o vencedor levou para concluir a modalidade, quando aplicável. */
    private LocalTime tempo;

    /** A distância obtida pelo vencedor na modalidade, quando aplicável. */
    private double distancia;

    /** As medalhas conquistadas pelo vencedor (ex.: "Ouro", "Prata", "Bronze"). */
    private String medalhas;

    /**
     * Construtor que cria um registo olímpico com o vencedor, o ano, o tempo e as medalhas conquistadas.
     *
     * @param vencedor O nome do vencedor do evento olímpico.
     * @param ano      O ano em que ocorreu o evento.
     * @param tempo    O tempo que o vencedor levou para concluir o evento.
     * @param medalhas As medalhas conquistadas pelo vencedor (ex.: "Ouro", "Prata", "Bronze").
     */
    public RegistoOlimpico(String vencedor, int ano, LocalTime tempo, String medalhas) {
        this.vencedor = vencedor;
        this.ano = ano;
        this.tempo = tempo;
        this.distancia = 0;
        this.medalhas = medalhas;
    }

    /**
     * Construtor que cria um registo olímpico com o vencedor, o ano, a distância e as medalhas conquistadas.
     *
     * @param vencedor  O nome do vencedor do evento olímpico.
     * @param ano       O ano em que ocorreu o evento.
     * @param distancia A distância obtida pelo vencedor.
     * @param medalhas  As medalhas conquistadas pelo vencedor (ex.: "Ouro", "Prata", "Bronze").
     */
    public RegistoOlimpico(String vencedor, int ano, double distancia, String medalhas) {
        this.vencedor = vencedor;
        this.ano = ano;
        this.tempo = null;
        this.distancia = distancia;
        this.medalhas = medalhas;
    }

    /**
     * Construtor que cria um registo olímpico com o vencedor, o ano e as medalhas conquistadas.
     * Este construtor é útil para eventos onde apenas as medalhas são relevantes.
     *
     * @param vencedor O nome do vencedor do evento olímpico.
     * @param ano      O ano em que ocorreu o evento.
     * @param medalhas As medalhas conquistadas pelo vencedor (ex.: "Ouro", "Prata", "Bronze").
     */
    public RegistoOlimpico(String vencedor, int ano, String medalhas) {
        this.vencedor = vencedor;
        this.ano = ano;
        this.tempo = null;
        this.distancia = 0.0;
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
     * @return O tempo de conclusão do evento, ou {@code null} se não aplicável.
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
     * Retorna a distância obtida pelo vencedor, quando aplicável.
     *
     * @return A distância obtida, ou 0.0 se não aplicável.
     */
    public double getDistancia() {
        return distancia;
    }

    /**
     * Define a distância obtida pelo vencedor, quando aplicável.
     *
     * @param distancia A distância obtida no evento.
     */
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    /**
     * Retorna as medalhas conquistadas pelo vencedor, quando aplicável.
     *
     * @return As medalhas conquistadas, ou {@code null} se não aplicável.
     */
    public String getMedalhas() {
        return medalhas;
    }

    /**
     * Define as medalhas conquistadas pelo vencedor.
     *
     * @param medalhas As medalhas conquistadas pelo vencedor (ex.: "Ouro", "Prata", "Bronze").
     */
    public void setMedalhas(String medalhas) {
        this.medalhas = medalhas;
    }
}
