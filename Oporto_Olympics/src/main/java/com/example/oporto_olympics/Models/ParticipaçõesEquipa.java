package com.example.oporto_olympics.Models;

public class ParticipaçõesEquipa {

    private int AnoParticipacao;

    private String Resultado;

    public ParticipaçõesEquipa(int anoParticipacao, String resultado) {
        AnoParticipacao = anoParticipacao;
        Resultado = resultado;
    }

    public int getAnoParticipacao() {
        return AnoParticipacao;
    }

    public void setAnoParticipacao(int anoParticipacao) {
        AnoParticipacao = anoParticipacao;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }
}
