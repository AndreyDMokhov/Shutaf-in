package com.shutafin.repository.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository
public abstract class AbstractDao<T> implements Dao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> entityClass;

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    public AbstractDao() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {

            entityClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            entityClass = (Class<T>) Object.class;
        }
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<T> findAll() {
        return getSession().createQuery("from " + getEntityClass().getName()).list();
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
