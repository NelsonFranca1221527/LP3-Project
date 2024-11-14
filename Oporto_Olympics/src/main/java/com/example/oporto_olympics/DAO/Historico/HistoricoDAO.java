package com.example.oporto_olympics.DAO.Historico;

import com.example.oporto_olympics.Models.Resultados;

import java.util.List;
/**
 * Interface responsável pela interação com os dados históricos dos atletas.
 * Define o contrato para a implementação de métodos que permitem o acesso aos resultados históricos dos atletas.
 */
public interface HistoricoDAO {
    /**
     * Recupera o histórico de competições de um atleta com base no seu ID.
     *
     * @param id O identificador único do atleta.
     * @return Uma lista de resultados de competições associadas ao atleta.
     *         Se o atleta não tiver histórico, a lista estará vazia.
     */
    List<Resultados> getResultAtleta(int id);
}
