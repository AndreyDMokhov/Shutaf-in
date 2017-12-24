package com.shutafin.core.service;

import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.account.AccountCityResponseDTO;
import com.shutafin.model.web.account.AccountCountryResponseDTO;
import com.shutafin.model.web.account.AccountGenderResponseDTO;
import com.shutafin.model.web.common.LanguageWeb;

import java.util.List;

public interface InitializationService {
    List<LanguageWeb> findAllLanguages();
    List<AccountGenderResponseDTO> findAllGendersByLanguage(Language language);
    List<AccountCountryResponseDTO> findAllCountriesByLanguage(Language language);
    List<AccountCityResponseDTO> findAllCitiesByLanguage(Language language);
}
