package com.example.oporto_olympics.DAO.Equipas;

import com.example.oporto_olympics.Models.AprovarInscricaoEquipa;
import com.example.oporto_olympics.Models.AtletaInfo;
import com.example.oporto_olympics.Models.InscricaoEquipas;

import java.util.List;
/**
 * A interface {@link AprovarInscricaoEquipaDAO} define os métodos necessários para a gestão das inscrições de equipas
 * em um sistema de aprovações, permitindo a consulta e aprovação ou reprovação de inscrições de equipas.
 */
public interface AprovarInscricaoEquipaDAO {
    /**
     * Obtém uma lista de todas as inscrições de equipas pendentes de aprovação.
     *
     * @return uma lista de objetos AprovarInscricaoEquipa representando as inscrições de equipa.
     */
    List<AprovarInscricaoEquipa> getAll();
    /**
     * Obtém uma lista de todas as inscrições de equipas aprovadas.
     *
     * @return uma lista de objetos AprovarInscricaoEquipa representando as inscrições de equipa.
     */
    List<AprovarInscricaoEquipa> getAllAprovado();
    /**
     * Obtém a equipa com o identificador especificado.
     *
     * @param id o identificador da equipa que se deseja obter.
     * @return o objeto InscricaoEquipas que representa a equipa com o ID especificado.
     */
    InscricaoEquipas getEquipa(int id);
    /**
     * Obtém informações detalhadas sobre o atleta com o identificador especificado.
     *
     * @param id o identificador do atleta.
     * @return o objeto AtletaInfo contendo as informações do atleta.
     */
    AtletaInfo getAtletaInfo(int id);
    /**
     * Aprova a inscrição da equipa especificada.
     *
     * @param id o identificador da inscrição da equipa a ser aprovada.
     */
    void aprovarInscricao(int id);
    /**
     * Reprova a inscrição da equipa especificada.
     *
     * @param id o identificador da inscrição da equipa a ser reprovada.
     */
    void reprovarInscricao(int id);
}
