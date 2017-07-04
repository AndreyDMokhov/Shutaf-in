package com.shutafin.service.initialization;

import java.util.List;

public interface InitializationService {
    List findAllLanguages();
    List findAllGendersByLanguage(Integer languageId);
    List findAllCountriesByLanguage(Integer languageId);
    List findAllCitiesByLanguage(Integer languageId);
}
