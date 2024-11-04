package com.example.oporto_olympics.Controllers.DAO;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);

    Optional<T> get(int i);

}