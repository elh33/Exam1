package com.example.exam01.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> get(Long id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(T t);
}