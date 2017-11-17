package com.shutafin.repository.locale;

import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.infrastructure.locale.CountryLocale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<CountryLocale, Integer> {
    List<CountryLocale> findAllByLanguage(Language language);
}
