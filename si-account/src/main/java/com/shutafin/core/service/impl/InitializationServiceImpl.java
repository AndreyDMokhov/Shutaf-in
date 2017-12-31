package com.shutafin.core.service.impl;

import com.shutafin.core.service.InitializationService;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.account.AccountCityResponseDTO;
import com.shutafin.model.web.account.AccountCountryResponseDTO;
import com.shutafin.model.web.account.AccountGenderResponseDTO;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.repository.LanguageRepository;
import com.shutafin.repository.locale.CityRepository;
import com.shutafin.repository.locale.CountryRepository;
import com.shutafin.repository.locale.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<LanguageWeb> findAllLanguages() {

        return languageRepository
                .findAll()
                .stream()
                .map(x -> new LanguageWeb(
                        x.getId(),
                        x.getDescription(),
                        x.getLanguageNativeName(),
                        x.getIsActive()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountGenderResponseDTO> findAllGendersByLanguage(Language language) {
        return genderRepository.getLocaleGenders(language);
    }

    @Override
    public List<AccountCountryResponseDTO> findAllCountriesByLanguage(Language language) {
        return countryRepository.getLocaleCountries(language);
    }

    @Override
    public List<AccountCityResponseDTO> findAllCitiesByLanguage(Language language) {
        return cityRepository.getLocaleCities(language);
    }

}
