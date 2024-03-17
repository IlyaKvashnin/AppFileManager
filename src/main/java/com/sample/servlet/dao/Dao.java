package com.sample.servlet.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(String login);

    List<T> getAll();

    boolean save(T t);

    boolean update(T t);

    boolean delete(T t);
}