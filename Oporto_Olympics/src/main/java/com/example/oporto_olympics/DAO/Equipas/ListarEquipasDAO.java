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
     * Interface para obter uma lista de equipas da base de dados, com possibilidade de aplicar um filtro pelo nome.
     *
     * Este método permite recuperar todas as equipas registadas ou, se fornecido um filtro,
     * apenas as equipas cujo nome contenha o valor especificado.
     *
     * @param filtroNome O nome (ou parte do nome) a ser utilizado como filtro na pesquisa das equipas.
     *                   Se for nulo ou vazio, todas as equipas serão retornadas.
     * @return Uma lista de objetos {@link InscricaoEquipas}, representando as equipas recuperadas.
     */

    List<InscricaoEquipas> getEquipas(String filtroNome);
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
