package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.Country;
import com.shutafin.model.entities.types.LanguageEnum;
import com.shutafin.model.web.initialization.CountryResponseDTO;
import com.shutafin.repository.initialization.locale.CountryRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepositoryImpl extends AbstractConstEntityDao<Country> implements CountryRepository {

    @Override
    public List<CountryResponseDTO> getLocaleCountries(LanguageEnum language) {

        StringBuilder hql = new StringBuilder();
        hql.append("select new com.shutafin.model.web.initialization.CountryResponseDTO ");
        hql.append(" ( ");
        hql.append(" cl.country.id, ");
        hql.append(" cl.description ");
        hql.append(" )");
        hql.append(" from CountryLocale cl where cl.language = :language ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();
    }
}
