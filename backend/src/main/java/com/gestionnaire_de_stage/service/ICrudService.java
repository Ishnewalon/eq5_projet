package com.gestionnaire_de_stage.service;

import java.util.List;

public interface ICrudService <T, K> {

    T create(T t);

    T getOneByID(K k);

    List<T> getAll();

    T update(T t, K k);

    boolean deleteByID(K k);
}
