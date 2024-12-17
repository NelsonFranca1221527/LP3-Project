package com.example.oporto_olympics.Models.RegistoModalidades;

import java.time.LocalTime;
/**
 * A classe {@link RegistoTempo} representa um registo olímpico baseado em tempo,
 * estendendo a funcionalidade da classe abstrata {@link RegistoOlimpico}.
 * É usada para registar eventos onde o tempo de conclusão é o fator determinante,
 * ao invés de pontuações ou medalhas.
 */
public class RegistoTempo extends RegistoOlimpico {
    /**
     * Construtor que cria um registo olímpico com o vencedor, o ano, o tempo e as medalhas conquistadas.
     *
     * @param vencedor O nome do vencedor do evento olímpico.
     * @param ano      O ano em que ocorreu o evento.
     * @param tempo    O tempo que o vencedor levou para concluir o evento.
     * @param medalhas As medalhas conquistadas pelo vencedor (ex.: "Ouro", "Prata", "Bronze").
     */
    public RegistoTempo(String vencedor, int ano, LocalTime tempo, String medalhas) {
        super(vencedor, ano, tempo, medalhas);
    }
}
