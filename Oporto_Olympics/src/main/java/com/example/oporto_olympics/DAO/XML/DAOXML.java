package com.example.oporto_olympics.DAO.XML;


import java.util.List;
import java.util.Optional;
/**
 * Interface genérica para realizar operações CRUD (Criar, Ler, Atualizar, Eliminar) em dados
 * persistidos em formato XML. Esta interface pode ser implementada por qualquer classe que
 * deseje manipular objetos do tipo genérico {@code T} em formato XML.
 *
 * @param <T> O tipo de objeto que a implementação irá manipular.
 */
public interface DAOXML<T> {
    /**
     * Obtém todos os objetos do tipo {@code T} armazenados em formato XML.
     *
     * @return Uma lista de objetos {@code T} que representam todos os dados armazenados no XML.
     */
    List<T> getAll();
    /**
     * Guarda um objeto do tipo {@code T} no formato XML.
     *
     * @param t O objeto a ser guardado no ficheiro XML.
     */
    void save(T t);
    /**
     * Atualiza os dados de um objeto do tipo {@code T} no formato XML.
     *
     * @param t O objeto com os novos dados a serem atualizados no XML.
     */
    void update(T t);
    /**
     * Elimina um objeto do tipo {@code T} do armazenamento XML.
     *
     * @param t O objeto a ser removido do ficheiro XML.
     */
    void delete(T t);
    /**
     * Obtém um objeto do tipo {@code T} a partir de uma chave identificadora.
     *
     * @param i A chave identificadora do objeto, podendo ser um campo único como um ID.
     * @return Um {@link Optional} contendo o objeto {@code T} se encontrado, ou vazio caso não exista.
     */
    Optional<T> get(String i);

}
