package com.example.oporto_olympics.Models;
/**
 * A classe {@link ParticipaocesEquipa} representa as participações de uma equipa em competições olímpicas,
 * incluindo o ano da participação e o resultado obtido pela equipa.
 */
public class ParticipaocesEquipa {

    private int AnoParticipacao;

    private String Resultado;
    /**
     * Construtor da classe {@link ParticipaocesEquipa} que inicializa os atributos de ano de participação
     * e o resultado da competição.
     *
     * @param anoParticipacao O ano da participação da equipa na competição.
     * @param resultado       O resultado obtido pela equipa (ex: "Ouro", "Prata", "Bronze", "Eliminada").
     */
    public ParticipaocesEquipa(int anoParticipacao, String resultado) {
        AnoParticipacao = anoParticipacao;
        Resultado = resultado;
    }
    /**
     * Obtém o ano da participação da equipa na competição.
     *
     * @return O ano da participação.
     */
    public int getAnoParticipacao() {
        return AnoParticipacao;
    }
    /**
     * Define o ano da participação da equipa na competição.
     *
     * @param anoParticipacao O ano da participação.
     */
    public void setAnoParticipacao(int anoParticipacao) {
        AnoParticipacao = anoParticipacao;
    }
    /**
     * Obtém o resultado da participação da equipa na competição.
     *
     * @return O resultado da participação (ex: "Ouro", "Prata", "Bronze", "Eliminada").
     */
    public String getResultado() {
        return Resultado;
    }
    /**
     * Define o resultado da participação da equipa na competição.
     *
     * @param resultado O resultado da participação (ex: "Ouro", "Prata", "Bronze", "Eliminada").
     */
    public void setResultado(String resultado) {
        Resultado = resultado;
    }
}
