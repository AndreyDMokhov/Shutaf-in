package com.shutafin.service;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.CityResponseDTO;
import com.shutafin.model.web.initialization.CountryResponseDTO;
import com.shutafin.model.web.initialization.GenderResponseDTO;

import java.util.List;

public interface InitializationService {
    List<Language> findAllLanguages();
    List<GenderResponseDTO> findAllGendersByLanguage(Language language);
    List<CountryResponseDTO> findAllCountriesByLanguage(Language language);
    List<CityResponseDTO> findAllCitiesByLanguage(Language language);
}
