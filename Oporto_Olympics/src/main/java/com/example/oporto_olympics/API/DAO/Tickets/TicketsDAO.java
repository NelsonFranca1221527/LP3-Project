package com.example.oporto_olympics.API.DAO.Tickets;


import com.example.oporto_olympics.API.Models.TicketInfo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Interface genérica que define as operações CRUD (Criar, Ler, Atualizar, Eliminar)
 * para manipulação de dados persistidos, podendo ser implementada para diferentes tipos de armazenamento.
 *
 * @param <T> O tipo de objeto que a implementação irá manipular (por exemplo, {@code User}, {@code Evento}, etc.).
 */

public interface TicketsDAO<T> {
    /**
     * Obtém todos os objetos do tipo {@code T} armazenados.
     *
     * @return Uma lista de objetos {@code T} que representam todos os dados armazenados.
     */
    List<T> getAll();

    /**
     * Guarda um objeto do tipo {@code T} no armazenamento.
     *
     * @param t O objeto a ser guardado.
     */
    void save(T t) throws IOException;
    /**
     * Atualiza os dados de um objeto do tipo {@code T} no armazenamento.
     *
     * @param t O objeto com os novos dados a serem atualizados.
     */
    void update(T t);
    /**
     * Elimina um objeto do tipo {@code T} do armazenamento.
     *
     * @param t O objeto a ser removido.
     */
    void delete(T t) throws IOException;
    /**
     * Obtém um objeto do tipo {@code T} através de um identificador único de um Cliente.
     *
     * @param i O identificador único do objeto (geralmente o ID).
     * @return Um {@link Optional} contendo o objeto {@code T} se encontrado, ou vazio caso não exista.
     */
    Optional<List<TicketInfo>> getbyClient(String i) throws IOException;

    /**
     * Obtém um objeto do tipo {@code T} através de um identificador único de um Jogo.
     *
     * @param i O identificador único do objeto (geralmente o ID).
     * @return Um {@link Optional} contendo o objeto {@code T} se encontrado, ou vazio caso não exista.
     */
    Optional<List<TicketInfo>> getbyGame(String i) throws IOException;
}