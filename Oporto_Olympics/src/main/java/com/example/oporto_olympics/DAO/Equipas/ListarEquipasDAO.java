package com.example.oporto_olympics.DAO.Equipas;
import com.example.oporto_olympics.Models.AtletaInfo;
import com.example.oporto_olympics.Models.InscricaoEquipas;
import com.example.oporto_olympics.Models.ResultadosEquipa;

import java.util.List;
/**
 * Interface que define os métodos para listar inscrições de equipas.
 * A interface permite obter uma lista de todas as equipas registadas no sistema.
 */
public interface ListarEquipasDAO {
    /**
     * Obtém uma lista de todas as inscrições de equipas.
     *
     * @return uma lista de objetos {@link InscricaoEquipas} representando todas as inscrições de equipas.
     */
    List<InscricaoEquipas> getEquipas();
    /**
     * Recupera o histórico de resultados de uma equipa, dado o seu identificador.
     *
     * Este método consulta a base de dados e retorna uma lista de resultados da equipa especificada
     * pelo identificador <code>equipaId</code>. Cada resultado contém o ano da competição e o
     * desempenho da equipa na mesma.
     *
     * @param equipaId O identificador único da equipa cujos resultados históricos serão recuperados.
     */
    List<ResultadosEquipa> getHistorico(int equipaId);
    /**
     * Recupera os atletas associados a uma equipa, dado o seu identificador.
     *
     * Este método consulta a base de dados e retorna uma lista de atletas que fazem parte da equipa
     * especificada pelo identificador <code>equipaId</code>.
     *
     * @param equipaId O identificador único da equipa cujos atletas serão recuperados.
     */
    List<AtletaInfo> getAtletasByEquipaId(int equipaId);
}
