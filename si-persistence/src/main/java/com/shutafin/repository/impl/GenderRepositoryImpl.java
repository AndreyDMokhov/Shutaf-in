package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.locale.GenderLocalized;
import com.shutafin.model.web.initialization.GenderWeb;
import com.shutafin.repository.base.AbstractLocalizedConstEntityDao;
import com.shutafin.repository.GenderRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GenderRepositoryImpl extends AbstractLocalizedConstEntityDao<GenderLocalized> implements GenderRepository {
    @Override
    public List<GenderWeb> getWebData(Language language) {
        List<GenderWeb> webData = new ArrayList<>();
        for (GenderLocalized gen: findAllByLanguage(language)) {
            webData.add(new GenderWeb(gen.getGender().getId(), gen.getDescription(), language.getId()));
        }
        return webData;
    }
}
