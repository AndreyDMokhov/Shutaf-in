package com.shutafin.repository.base;


import com.shutafin.model.AbstractKeyConstEntity;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;


@Repository
public abstract class AbstractConstEntityDao<T extends AbstractKeyConstEntity> extends AbstractDao<T> implements Dao<T> {

    @Override
    public List<T> findAll() {
        return getSession()
                .createQuery("from " + getEntityClass().getName())
                .setCacheable(true)
                .list();
    }

    @Override
    public T findById(Serializable id) {
        return getSession().get(getEntityClass(), id);
    }

    @Override
    public void evict(T object) {
        getSession().evict(object);
    }
}
