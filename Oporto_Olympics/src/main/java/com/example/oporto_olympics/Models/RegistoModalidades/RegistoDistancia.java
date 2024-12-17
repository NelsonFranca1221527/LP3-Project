package com.example.oporto_olympics.Models.RegistoModalidades;

/**
 * A classe {@link RegistoDistancia} representa um registo olímpico específico para eventos
 * cuja principal métrica é a distância obtida pelo vencedor.
 * Esta classe herda de {@link RegistoOlimpico}.
 */
public class RegistoDistancia extends RegistoOlimpico{
    /**
     * Construtor que cria um registo olímpico com o vencedor, o ano, a distância e as medalhas conquistadas.
     *
     * @param vencedor  O nome do vencedor do evento olímpico.
     * @param ano       O ano em que ocorreu o evento.
     * @param distancia A distância obtida pelo vencedor.
     * @param medalhas  As medalhas conquistadas pelo vencedor (ex.: "Ouro", "Prata", "Bronze").
     */
    public RegistoDistancia(String vencedor, int ano, double distancia, String medalhas) {
        super(vencedor, ano, distancia, medalhas);
    }
}
