package com.gestionnaire_de_stage.service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

public interface ICrudService <T, K> {

    Optional<T> create(T t) throws Exception;

    Optional<T> getOneByID(K k);

    List<T> getAll();

    Optional<T> update(T t, K k) throws Exception;;

    boolean deleteByID(K k);
}
