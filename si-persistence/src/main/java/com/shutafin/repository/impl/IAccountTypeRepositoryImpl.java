package com.shutafin.repository.impl;

import com.shutafin.model.entities.IAccountType;
import com.shutafin.repository.IAccountTypeRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class IAccountTypeRepositoryImpl extends AbstractConstEntityDao<IAccountType> implements IAccountTypeRepository {
    @Override
    public Serializable save(IAccountType entity) {
        return getSession().save(entity);
    }

    @Override
    public void update(IAccountType entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(IAccountType entity) {
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
