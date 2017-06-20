package com.shutafin.repository.base;

import com.shutafin.model.AbstractConstEntity;

import java.io.Serializable;

/**
 * Created by evgeny on 6/20/2017.
 */
public interface PersistentConstDao<T extends AbstractConstEntity> extends Dao<T> {
    Serializable save(T entity);
    void update(T entity);
    void delete(T entity);
    void deleteById(Serializable id);
}
