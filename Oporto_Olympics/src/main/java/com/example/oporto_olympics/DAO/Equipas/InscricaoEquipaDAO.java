package com.example.oporto_olympics.DAO.Equipas;

import java.util.List;

import com.example.oporto_olympics.Models.InscricaoEquipas;
/**
 * Interface {@link InscricaoEquipaDAO} que define os métodos de acesso aos dados de inscrições de equipas.
 * Essa interface fornece métodos para manipulação de inscrições, como inserção, verificação de status e filtragem
 * de equipas com base em critérios específicos como país e gênero.
 */
public interface InscricaoEquipaDAO {
    /**
     * Obtém uma lista de inscrições de equipas filtradas por país e gênero.
     *
     * @param pais o nome do país para filtrar as equipas.
     * @param genero o gênero para filtrar as equipas.
     * @return uma lista de objetos InscricaoEquipas que correspondem aos critérios fornecidos.
     */
    List<InscricaoEquipas> getEquipas(String pais, String genero);
    /**
     * Insere uma nova inscrição de atleta em uma equipa com um status especificado.
     *
     * @param status o status da inscrição (ex.: "pendente", "aprovado", "reprovado").
     * @param modalidadeId o identificador da modalidade associada à inscrição.
     * @param atletaId o identificador do atleta que está a realizar a inscrição.
     * @param equipaId o identificador da equipa à qual o atleta está a inscrever-se.
     */
    void inserirInscricao(String status, int modalidadeId, int atletaId, int equipaId);
    /**
     * Verifica se já existe um pedido de inscrição pendente para o atleta e a equipa especificados.
     *
     * @param atletaId o identificador do atleta a ser verificado.
     * @param equipaId o identificador da equipa a ser verificado.
     * @return true se existir um pedido pendente de inscrição, false caso contrário.
     */
    boolean existePedidoPendente(int atletaId, int equipaId);
    /**
     * Verifica se já existe um pedido de inscrição aprovado para o atleta e a equipa especificados.
     *
     * @param atletaId o identificador do atleta a ser verificado.
     * @param equipaId o identificador da equipa a ser verificado.
     * @return true se existir um pedido aprovado de inscrição, false caso contrário.
     */
    boolean existePedidoAprovado(int atletaId, int equipaId);
}
