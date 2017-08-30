package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.CityResponseDTO;
import com.shutafin.repository.initialization.locale.CityRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepositoryImpl extends AbstractConstEntityDao<City> implements CityRepository {

    @Override
    public List<CityResponseDTO> getLocaleCities(Language language) {
        StringBuilder hql = new StringBuilder()
        .append("select new com.shutafin.model.web.initialization.CityResponseDTO ")
        .append(" ( ")
        .append(" cl.city.id, ")
        .append(" cl.description, ")
        .append(" cl.city.country.id as countryId ")
        .append(" )")
        .append(" from CityLocale cl where cl.language = :language ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();
    }
}
