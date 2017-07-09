package com.shutafin.repository.infrastructure;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.locale.CountryLocalized;
import com.shutafin.model.web.initialization.CountryWeb;
import com.shutafin.repository.base.LocalizedDao;

import java.util.List;


public interface CountryRepository extends LocalizedDao<CountryLocalized> {

    public List<CountryWeb> getWebData(Language language);
}
