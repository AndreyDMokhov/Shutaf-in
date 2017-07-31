package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.model.entities.types.LanguageEnum;
import com.shutafin.model.web.initialization.GenderResponseDTO;
import com.shutafin.repository.initialization.locale.GenderRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenderRepositoryImpl extends AbstractConstEntityDao<Gender> implements GenderRepository {

    @Override
    public List<GenderResponseDTO> getLocaleGenders(LanguageEnum language) {

        StringBuilder hql = new StringBuilder();
        hql.append("select new com.shutafin.model.web.initialization.GenderResponseDTO ");
        hql.append(" ( ");
        hql.append(" gl.gender.id, ");
        hql.append(" gl.description ");
        hql.append(" )");
        hql.append(" from GenderLocale gl where gl.language = :languageId");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("languageId", language)
                .getResultList();
    }


}
