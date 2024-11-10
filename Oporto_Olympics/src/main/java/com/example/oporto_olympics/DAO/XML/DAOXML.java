package com.example.oporto_olympics.DAO.XML;


import java.util.List;
import java.util.Optional;

public interface DAOXML<T> {
    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);

    Optional<T> get(String i);

}
