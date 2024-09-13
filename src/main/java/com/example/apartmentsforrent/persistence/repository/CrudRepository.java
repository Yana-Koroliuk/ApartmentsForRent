package com.example.apartmentsforrent.persistence.repository;

import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    Iterable<T> findAll();

    boolean existsById(ID id);

    void deleteById(ID id);
}
