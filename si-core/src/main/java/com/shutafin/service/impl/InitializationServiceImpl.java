package com.shutafin.service.impl;

import com.shutafin.model.entities.types.LanguageEnum;
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
    public List findAllLanguages() {

        return languageRepository.findAll();
    }

    @Override
    public List findAllGendersByLanguage(Integer languageId) {
        return genderRepository.getLocaleGenders(LanguageEnum.getById(languageId));
    }

    @Override
    public List findAllCountriesByLanguage(Integer languageId) {
        return countryRepository.getLocaleCountries(LanguageEnum.getById(languageId));
    }

    @Override
    public List findAllCitiesByLanguage(Integer languageId) {
        return cityRepository.getLocaleCities(LanguageEnum.getById(languageId));
    }

}
