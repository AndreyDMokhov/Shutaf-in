package com.shutafin.repository.impl;

import com.shutafin.model.entities.ILanguage;
import com.shutafin.repository.ILanguageRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class ILanguageRepositoryImpl extends AbstractConstEntityDao<ILanguage> implements ILanguageRepository {

    @Override
    public Serializable save(ILanguage entity) {
        return getSession().save(entity);
    }

    @Override
    public void update(ILanguage entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(ILanguage entity) {
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
