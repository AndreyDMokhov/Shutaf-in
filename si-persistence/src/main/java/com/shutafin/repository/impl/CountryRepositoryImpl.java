package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.locale.CountryLocalized;
import com.shutafin.model.web.initialization.CountryWeb;
import com.shutafin.repository.base.AbstractLocalizedConstEntityDao;
import com.shutafin.repository.CountryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CountryRepositoryImpl extends AbstractLocalizedConstEntityDao<CountryLocalized> implements CountryRepository {
    @Override
    public List<CountryWeb> getWebData(Language language) {
        List<CountryWeb> webData = new ArrayList<>();
        for (CountryLocalized country : findAllByLanguage(language)) {
            webData.add(new CountryWeb(country.getCountry().getId(), country.getDescription(), language.getId()));
        }
        return webData;
    }
}
