package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.GenderResponseDTO;
import com.shutafin.repository.base.Dao;

import java.util.List;


public interface GenderRepository extends Dao<Gender> {

    List<GenderResponseDTO> getLocaleGenders(Language language);
}
