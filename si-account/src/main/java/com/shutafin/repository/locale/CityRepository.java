package com.shutafin.repository.locale;

import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.infrastructure.locale.CityLocale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<CityLocale, Integer> {
    List<CityLocale> findAllByLanguage(Language language);
}
