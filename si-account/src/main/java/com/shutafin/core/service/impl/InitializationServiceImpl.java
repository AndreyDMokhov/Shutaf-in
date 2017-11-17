package com.shutafin.core.service.impl;

import com.shutafin.core.service.InitializationService;
import com.shutafin.repository.LanguageRepository;
import com.shutafin.repository.locale.CityRepository;
import com.shutafin.repository.locale.CountryRepository;
import com.shutafin.repository.locale.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

        return StreamSupport.stream(languageRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List findAllGendersByLanguage(Integer languageId) {
        return genderRepository.findAllByLanguage(languageRepository.findOne(languageId));
    }

    @Override
    public List findAllCountriesByLanguage(Integer languageId) {
        return countryRepository.findAllByLanguage(languageRepository.findOne(languageId));
    }

    @Override
    public List findAllCitiesByLanguage(Integer languageId) {
        return cityRepository.findAllByLanguage(languageRepository.findOne(languageId));
    }

}
