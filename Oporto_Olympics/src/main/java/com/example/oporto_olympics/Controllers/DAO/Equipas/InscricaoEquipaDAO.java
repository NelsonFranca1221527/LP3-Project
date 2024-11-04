package com.example.oporto_olympics.Controllers.DAO.Equipas;

import java.util.ArrayList;
import java.util.List;

import com.example.oporto_olympics.Controllers.DAO.Equipas.Model.InscricaoEquipas;

public interface InscricaoEquipaDAO {

    List<InscricaoEquipas> getEquipas(String pais);
}
