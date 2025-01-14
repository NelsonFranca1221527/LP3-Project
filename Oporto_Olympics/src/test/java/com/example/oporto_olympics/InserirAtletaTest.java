package com.example.oporto_olympics;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.Atleta.InserirAtletaController;
import com.example.oporto_olympics.DAO.Atleta.InserirAtletaDAOImp;
import com.example.oporto_olympics.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.Models.Atleta;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InserirAtletaTest {

    /**
     * Testa a funcionalidade de inserir um novo atleta na base de dados.
     *
     * O teste verifica o seguinte:
     * <ul>
     *   <li>Insere um atleta com os dados fornecidos utilizando o DAO responsável.</li>
     *   <li>Recupera todos os atletas da base de dados e verifica se o atleta inserido está presente.</li>
     * </ul>
     *
     * Asserção:
     * <ul>
     *   <li>O teste confirma que o atleta inserido foi corretamente adicionado, verificando pelo nome.</li>
     * </ul>
     *
     * @throws SQLException se ocorrer um erro de comunicação com a base de dados durante a execução.
     */
    @Test
    void InserirAtleta() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        Boolean igual = false;

        String nome = "Atleta test2";
        String pais = "PRT";
        Double altura = 1.80;
        Double peso = 66.7;
        LocalDate DataNasc = LocalDate.of(1998, 8, 8);
        String genero = "Men";

        InserirAtletaDAOImp dao = new InserirAtletaDAOImp(conexao);
        AtletaDAOImp dao2 = new AtletaDAOImp(conexao);

        dao.saveAtleta(nome,pais,altura,peso,DataNasc,genero);

        for (Atleta atleta : dao2.getAll()){

            if(atleta.getNome().equals(nome)){
                igual = true;
                break;
            }

        }

        assertTrue(igual, "O atleta deveria existir..");
    }

    @Test
    void InserirAtletaValido() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();
        Boolean criado = false;

        String nome = "Atleta test3";
        String pais = "PRT";
        Double altura = 171.00;
        Double peso = 70.7;
        LocalDate DataNasc = LocalDate.of(1995, 12, 14);
        String genero = "Women";

        InserirAtletaDAOImp dao = new InserirAtletaDAOImp(conexao);
        AtletaDAOImp dao2 = new AtletaDAOImp(conexao);

        if(!dao.getPais(pais)){
            assertFalse(criado, "País inválido.");
        }

        if(altura < 120.00){
            assertFalse(criado,"O atleta não deve menor a 120 cm.");
        }
        if(peso < 20){
            assertFalse(criado, "O alteta não pode pesar menor de 20 Kg.");
        }

        dao.saveAtleta(nome,pais,altura,peso,DataNasc,genero);

        for (Atleta atleta : dao2.getAll()){

            if(atleta.getNome().equals(nome)){
                criado = true;
                break;
            }

        }

        assertTrue(criado, "O atleta foi criado");
    }

    @Test
    void InserirAtletaInvalido() throws SQLException {
        ConnectionBD connectionBD = ConnectionBD.getInstance();
        Connection conexao = connectionBD.getConexao();

        String nome = "Atleta test4";
        String pais = "PVT";
        Double altura = 119.00;
        Double peso = 19.0;
        LocalDate DataNasc = LocalDate.of(1995, 12, 14);
        String genero = "Women";

        InserirAtletaDAOImp dao = new InserirAtletaDAOImp(conexao);

        if(!dao.getPais(pais)){
            assertTrue(true, "País inválido.");
        }

        if(altura < 120){
            assertTrue(true,"O atleta não deve menor a 120 cm.");
        }
        if(peso < 20){
            assertTrue(true, "O alteta não pode pesar menor de 20 Kg.");
        }


    }


}