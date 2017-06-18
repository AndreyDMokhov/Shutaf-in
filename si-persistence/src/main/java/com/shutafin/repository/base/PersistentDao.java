package com.shutafin.repository.base;

import com.shutafin.model.AbstractBaseEntity;

import java.io.Serializable;


public interface PersistentDao<T extends AbstractBaseEntity> extends Dao<T> {

    Serializable save(T entity);
    void update(T entity);
    void delete(T entity);
    void deleteById(Serializable id);

}
