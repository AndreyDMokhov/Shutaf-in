package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Country;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.CountryResponseDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;


public interface CountryRepository extends BaseJpaRepository<Country, Long> {

    @Query("select new com.shutafin.model.web.initialization.CountryResponseDTO  (  cl.country.id,  cl.description  ) from CountryLocale cl where cl.language = :language")
    @QueryHints(value = {@QueryHint(name = "org.springframework.data.jpa.repository", value = "true")})
    List<CountryResponseDTO> getLocaleCountries(@Param("language") Language language);
}
