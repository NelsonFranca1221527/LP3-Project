package com.example.oporto_olympics.DAO.Gestor;

import java.time.LocalDate;

/**
 * Implementação da interface {@link InserirGestorDAO} para inserir e atualizar dados de gestores na base de dados.
 * Esta classe usa uma conexão com a base de dados para inserir.
 */
public interface InserirGestorDAO {

    /**
     * Salva e atualiza os dados de um gestor na base de dados.
     *
     * @param nome           Nome do gestor.
     *
     */
    void saveGestor(String nome);
}
