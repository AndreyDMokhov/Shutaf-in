package com.shutafin.core.service.impl;

import com.shutafin.core.service.InitializationService;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.locale.CityResponseDTO;
import com.shutafin.model.web.locale.CountryResponseDTO;
import com.shutafin.model.web.locale.GenderResponseDTO;
import com.shutafin.repository.LanguageRepository;
import com.shutafin.repository.locale.CityRepository;
import com.shutafin.repository.locale.CountryRepository;
import com.shutafin.repository.locale.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InitializationServiceImpl implements InitializationService {

    private LanguageRepository languageRepository;
    private GenderRepository genderRepository;
    private CountryRepository countryRepository;
    private CityRepository cityRepository;

    @Autowired
    public InitializationServiceImpl(
            LanguageRepository languageRepository,
            GenderRepository genderRepository,
            CountryRepository countryRepository,
            CityRepository cityRepository) {
        this.languageRepository = languageRepository;
        this.genderRepository = genderRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

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
