package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Country;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.CountryResponseDTO;
import com.shutafin.repository.base.Dao;

import java.util.List;


public interface CountryRepository extends Dao<Country> {

    List<CountryResponseDTO> getLocaleCountries(Language language);
}
