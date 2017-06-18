package com.shutafin.repository.base;


import com.shutafin.model.AbstractBaseEntity;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public abstract class AbstractEntityDao<T extends AbstractBaseEntity> extends AbstractDao<T> implements PersistentDao<T> {



    @Override
    public Serializable save(T entity) {
        return getSession().save(entity);
    }

    @Override
    public void update(T entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    public void deleteById(Serializable id) {
        getSession()
            .createQuery("delete from " + getEntityClass().getName() + " as e where e.id=:id")
            .setParameter("id", id)
            .executeUpdate();
    }
}
