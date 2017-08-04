package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.types.LanguageEnum;
import com.shutafin.model.web.initialization.CityResponseDTO;
import com.shutafin.repository.base.Dao;

import java.util.List;

public interface CityRepository extends Dao<City> {

    List<CityResponseDTO> getLocaleCities(LanguageEnum language);
}