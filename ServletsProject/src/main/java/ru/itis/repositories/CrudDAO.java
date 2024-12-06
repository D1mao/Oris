package ru.itis.repositories;

import java.util.List;

public interface CrudDAO <T>{
    void save(T t);
    void update(T t);
    void delete(T t);
    List<T> getAll();
}
