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
     * Constrói um novo registo de tempo para um evento olímpico, definindo o vencedor,
     * o ano e o tempo de conclusão do evento. Esta classe foca-se em competições
     * onde o tempo é crucial para determinar a vitória.
     *
     * @param vencedor O nome do vencedor do evento olímpico baseado em tempo.
     * @param ano      O ano em que o evento ocorreu.
     * @param tempo    O tempo de conclusão do vencedor no evento, representado como {@link LocalTime}.
     */
    public RegistoTempo(String vencedor, int ano, LocalTime tempo) {
        super(vencedor, ano, tempo);
    }
}
