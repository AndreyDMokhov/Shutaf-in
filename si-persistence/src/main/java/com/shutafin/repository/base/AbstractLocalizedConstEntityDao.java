package com.shutafin.repository.base;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.infrastructure.Language;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class AbstractLocalizedConstEntityDao<T extends AbstractLocalizedConstEntity>
        extends AbstractConstEntityDao<T> implements LocalizedDao<T>{

    @Override
    public List<T> findAllByLanguage(Language language) {
        return getSession()
                .createQuery("from " + getEntityClass().getName() +
                        " where language_id=" + language.getId())
                .setCacheable(true)
                .list();
    }
}
