package com.example.oporto_olympics;

import com.example.oporto_olympics.Controllers.Atleta.InserirAtletaController;
import com.example.oporto_olympics.DAO.Atleta.InserirAtletaDAOImp;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDate;

class InserirAtletaTest {

    @Test
    void onClickCriarAtletaButton() {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();

        String nome = "Roberto Rodriguez";
        String pais = "PRT";
        Double altura = 1.75;
        Double peso = 70.5;
        LocalDate DataNasc = LocalDate.of(1998, 11, 25);
        String genero = "Men";

        InserirAtletaDAOImp dao = new InserirAtletaDAOImp(conexao);

        dao.saveAtleta(nome,pais,altura,peso,DataNasc,genero);

    }
}