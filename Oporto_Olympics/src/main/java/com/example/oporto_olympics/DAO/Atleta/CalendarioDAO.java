package com.example.oporto_olympics.DAO.Atleta;

import com.example.oporto_olympics.Models.AtletasModalidades;
import com.example.oporto_olympics.Models.EventosModalidade;
import com.example.oporto_olympics.Models.Resultados;

import java.util.List;

public interface CalendarioDAO {

    /**
     * Recupera o histórico de competições de um atleta com base no seu ID.
     *
     * @param id O identificador único do atleta.
     * @return Uma lista de resultados de competições associadas ao atleta.
     *         Se o atleta não tiver histórico, a lista estará vazia.
     */
    List<AtletasModalidades> getAtletaModalidade(int id);

    List<EventosModalidade> getEventosAtleta(int evento_id, int modalidade_id);

}
