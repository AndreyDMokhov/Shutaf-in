package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.locale.CityLocalized;
import com.shutafin.model.web.initialization.CityWeb;
import com.shutafin.repository.base.AbstractLocalizedConstEntityDao;
import com.shutafin.repository.CityRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CityRepositoryImpl extends AbstractLocalizedConstEntityDao<CityLocalized> implements CityRepository {
    @Override
    public List<CityWeb> getWebData(Language language) {
        List<CityWeb> webData = new ArrayList<>();
        for (CityLocalized city : findAllByLanguage(language)) {
            webData.add(new CityWeb(city.getCity().getId(), city.getDescription(), language.getId(),
                    city.getCity().getCountry().getId()));
        }
        return webData;
    }
}
