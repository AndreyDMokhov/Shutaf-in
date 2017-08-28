package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.GenderResponseDTO;
import com.shutafin.repository.initialization.locale.GenderRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenderRepositoryImpl extends AbstractConstEntityDao<Gender> implements GenderRepository {

    @Override
    public List<GenderResponseDTO> getLocaleGenders(Language language) {

        StringBuilder hql = new StringBuilder(200)
        .append("select new com.shutafin.model.web.initialization.GenderResponseDTO ")
        .append(" ( ")
        .append(" gl.gender.id, ")
        .append(" gl.description ")
        .append(" )")
        .append(" from GenderLocale gl where gl.language = :language");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();
    }


}
