package com.shutafin.repository.locale;

import com.shutafin.model.infrastructure.Country;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.locale.CountryResponseDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface CountryRepository extends BaseJpaRepository<Country, Integer> {

    @Query("select new com.shutafin.model.web.locale.CountryResponseDTO  (  cl.country.id,  cl.description  ) from CountryLocale cl where cl.language = :language")
    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    }, forCounting = false)
    List<CountryResponseDTO> getLocaleCountries(@Param("language") Language language);
}
