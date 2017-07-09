package com.shutafin.repository;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.locale.GenderLocalized;
import com.shutafin.model.web.initialization.GenderWeb;
import com.shutafin.repository.base.LocalizedDao;

import java.util.List;


public interface GenderRepository extends LocalizedDao<GenderLocalized> {

    public List<GenderWeb> getWebData(Language language);
}
