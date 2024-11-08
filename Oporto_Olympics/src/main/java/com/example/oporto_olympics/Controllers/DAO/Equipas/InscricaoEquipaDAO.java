package com.example.oporto_olympics.Controllers.DAO.Equipas;

import java.util.ArrayList;
import java.util.List;

import com.example.oporto_olympics.Models.InscricaoEquipas;

public interface InscricaoEquipaDAO {

    List<InscricaoEquipas> getEquipas(String pais, String genero);

    void inserirInscricao(String status, int modalidadeId, int atletaId, int equipaId);

    boolean existePedidoPendente(int atletaId, int equipaId);

    boolean existePedidoAprovado(int atletaId, int equipaId);
}
