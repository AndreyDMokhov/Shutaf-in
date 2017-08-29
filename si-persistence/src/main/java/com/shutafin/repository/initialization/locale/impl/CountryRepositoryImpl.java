package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.Country;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.CountryResponseDTO;
import com.shutafin.repository.initialization.locale.CountryRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepositoryImpl extends AbstractConstEntityDao<Country> implements CountryRepository {

    @Override
    public List<CountryResponseDTO> getLocaleCountries(Language language) {

        StringBuilder hql = new StringBuilder()
        .append("select new com.shutafin.model.web.initialization.CountryResponseDTO ")
        .append(" ( ")
        .append(" cl.country.id, ")
        .append(" cl.description ")
        .append(" )")
        .append(" from CountryLocale cl where cl.language = :language ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();
    }
}
