package com.shutafin.service.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.repository.CityRepository;
import com.shutafin.repository.CountryRepository;
import com.shutafin.repository.GenderRepository;
import com.shutafin.repository.LanguageRepository;
import com.shutafin.service.InitializationService;
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
        Language language = languageRepository.findById(languageId);
        return genderRepository.getWebData(language);
    }

    @Override
    public List findAllCountriesByLanguage(Integer languageId) {
        Language language = languageRepository.findById(languageId);
        return countryRepository.getWebData(language);
    }

    @Override
    public List findAllCitiesByLanguage(Integer languageId) {
        Language language = languageRepository.findById(languageId);
        return cityRepository.getWebData(language);
    }


}
