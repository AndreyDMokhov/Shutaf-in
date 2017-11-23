package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.CityResponseDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

@Deprecated
public interface CityRepository extends BaseJpaRepository<City, Integer> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(value = "select new com.shutafin.model.web.initialization.CityResponseDTO  (  cl.city.id,  cl.description,  cl.city.country.id as countryId  ) from CityLocale cl where cl.language = :language")
    List<CityResponseDTO> getLocaleCities(@Param("language") Language language);
}
