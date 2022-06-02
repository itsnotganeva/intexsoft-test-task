package by.ganevich.service;

import java.util.List;

public interface BaseService<T> {
    List<T> readAll();

    T save(T entity);

}
