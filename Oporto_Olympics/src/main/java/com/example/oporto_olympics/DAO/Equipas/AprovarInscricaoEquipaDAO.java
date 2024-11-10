package com.example.oporto_olympics.DAO.Equipas;

import com.example.oporto_olympics.Models.AprovarInscricaoEquipa;
import com.example.oporto_olympics.Models.AtletaInfo;
import com.example.oporto_olympics.Models.InscricaoEquipas;

import java.util.List;

public interface AprovarInscricaoEquipaDAO {

    List<AprovarInscricaoEquipa> getAll();

    InscricaoEquipas getEquipa(int id);

    AtletaInfo getAtletaInfo(int id);

    void aprovarInscricao(int id);

    void reprovarInscricao(int id);
}
