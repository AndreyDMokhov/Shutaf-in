package com.shutafin.service.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.CityResponseDTO;
import com.shutafin.model.web.initialization.CountryResponseDTO;
import com.shutafin.model.web.initialization.GenderResponseDTO;
import com.shutafin.repository.initialization.LanguageRepository;
import com.shutafin.service.InitializationService;
import com.shutafin.repository.initialization.locale.CityRepository;
import com.shutafin.repository.initialization.locale.CountryRepository;
import com.shutafin.repository.initialization.locale.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InitializationServiceImpl implements InitializationService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Language> findAllLanguages() {

        return languageRepository.findAll();
    }

    @Override
    public List<GenderResponseDTO> findAllGendersByLanguage(Language language) {
        return genderRepository.getLocaleGenders(language);
    }

    @Override
    public List<CountryResponseDTO> findAllCountriesByLanguage(Language language) {
        return countryRepository.getLocaleCountries(language);
    }

    @Override
    public List<CityResponseDTO> findAllCitiesByLanguage(Language language) {
        return cityRepository.getLocaleCities(language);
    }

}
