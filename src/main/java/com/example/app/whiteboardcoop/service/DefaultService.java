package com.example.app.whiteboardcoop.service;

import java.util.List;
import java.util.Optional;

public interface DefaultService<T> {
    void save(T t);
    Optional<T> findOne(Long id);
    List<T> findAll();
    void delete(Long id);
    void update(T t);

}
