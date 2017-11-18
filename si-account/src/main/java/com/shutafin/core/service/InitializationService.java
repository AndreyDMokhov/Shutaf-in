package com.shutafin.core.service;

import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.locale.CityResponseDTO;
import com.shutafin.model.web.locale.CountryResponseDTO;
import com.shutafin.model.web.locale.GenderResponseDTO;

import java.util.List;

public interface InitializationService {
    List<Language> findAllLanguages();
    List<GenderResponseDTO> findAllGendersByLanguage(Language language);
    List<CountryResponseDTO> findAllCountriesByLanguage(Language language);
    List<CityResponseDTO> findAllCitiesByLanguage(Language language);
}
