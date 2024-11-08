package com.example.oporto_olympics.Controllers.DAO.Atleta;

import com.example.oporto_olympics.Models.Atleta;

import java.time.LocalDate;
import java.util.Optional;

public interface InserirAtletaDAO {

    /**
     * Salva e atualiza os dados de um atleta na base de dados.
     *
     * @param nome           Nome do atleta.
     * @param pais           Sigla do país de origem do atleta (deve ser validada previamente).
     * @param altura         Altura do atleta em centímetros.
     * @param peso           Peso do atleta em quilogramas.
     * @param dataNascimento Data de nascimento do atleta.
     * @param genero         Gênero do atleta (ex.: "M" para masculino, "F" para feminino).
     *
     */
    void saveAtleta(String nome, String pais, double altura, double peso, LocalDate dataNascimento, String genero);

    /**
     * Verifica se um país existe na tabela com a sigla fornecida.
     *
     * @param sigla A sigla do país para verificar.
     * @return true se o país existe, false caso contrário.
     */
    boolean getPais(String sigla);
}
