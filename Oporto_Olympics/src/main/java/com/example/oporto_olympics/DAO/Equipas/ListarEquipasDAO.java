package com.example.oporto_olympics.DAO.Equipas;
import com.example.oporto_olympics.Models.InscricaoEquipas;

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
}
