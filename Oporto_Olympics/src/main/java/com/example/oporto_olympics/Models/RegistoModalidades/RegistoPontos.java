package com.example.oporto_olympics.Models.RegistoModalidades;

import java.sql.Time;
/**
 * A classe {@link RegistoPontos} representa um registo olímpico específico baseado em pontos,
 * estendendo a funcionalidade da classe abstrata {@link RegistoOlimpico}.
 * É utilizada para registar eventos onde a pontuação e as medalhas são relevantes,
 * mas o tempo de conclusão não se aplica.
 */
public class RegistoPontos extends RegistoOlimpico{
    /**
     * Construtor que cria um registo olímpico com o vencedor, o ano e as medalhas conquistadas.
     * Este construtor é útil para eventos onde apenas as medalhas são relevantes.
     *
     * @param vencedor O nome do vencedor do evento olímpico.
     * @param ano      O ano em que ocorreu o evento.
     * @param medalhas As medalhas conquistadas pelo vencedor (ex.: "Ouro", "Prata", "Bronze").
     */
    public RegistoPontos(String vencedor, int ano, String medalhas) {
        super(vencedor, ano, medalhas);
    }
}
