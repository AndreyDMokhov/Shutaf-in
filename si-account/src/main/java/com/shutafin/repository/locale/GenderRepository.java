package com.shutafin.repository.locale;

import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.infrastructure.locale.GenderLocale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenderRepository extends CrudRepository<GenderLocale, Integer> {
    List<GenderLocale> findAllByLanguage(Language language);
}
