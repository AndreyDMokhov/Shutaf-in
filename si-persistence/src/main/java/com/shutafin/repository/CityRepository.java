package com.shutafin.repository;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.locale.CityLocalized;
import com.shutafin.model.web.initialization.CityWeb;
import com.shutafin.repository.base.LocalizedDao;

import java.util.List;

public interface CityRepository extends LocalizedDao<CityLocalized> {

    public List<CityWeb> getWebData(Language language);
}
