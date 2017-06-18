package com.shutafin.repository.base;


import java.io.Serializable;
import java.util.List;

public interface Dao<T> {

    List<T> findAll();
    T findById(Serializable id);


}
