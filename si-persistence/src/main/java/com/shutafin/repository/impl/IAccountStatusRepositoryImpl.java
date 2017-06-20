package com.shutafin.repository.impl;

import com.shutafin.model.entities.IAccountStatus;
import com.shutafin.repository.IAccountStatusRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.base.PersistentConstDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class IAccountStatusRepositoryImpl extends AbstractConstEntityDao<IAccountStatus> implements IAccountStatusRepository {
    @Override
    public Serializable save(IAccountStatus entity) {
        return getSession().save(entity);
    }

    @Override
    public void update(IAccountStatus entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(IAccountStatus entity) {
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
