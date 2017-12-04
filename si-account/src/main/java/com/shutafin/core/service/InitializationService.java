package com.shutafin.core.service;

import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.account.AccountCityResponseDTO;
import com.shutafin.model.web.account.AccountCountryResponseDTO;
import com.shutafin.model.web.account.AccountGenderResponseDTO;

import java.util.List;

public interface InitializationService {
    List<Language> findAllLanguages();
    List<AccountGenderResponseDTO> findAllGendersByLanguage(Language language);
    List<AccountCountryResponseDTO> findAllCountriesByLanguage(Language language);
    List<AccountCityResponseDTO> findAllCitiesByLanguage(Language language);
}
