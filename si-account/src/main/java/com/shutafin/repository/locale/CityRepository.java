package com.shutafin.repository.locale;

import com.shutafin.model.infrastructure.City;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.account.AccountCityResponseDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface CityRepository extends BaseJpaRepository<City, Integer> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(value = "select new com.shutafin.model.web.account.AccountCityResponseDTO (  cl.city.id,  cl.description,  cl.city.country.id as countryId  ) from CityLocale cl where cl.language = :language")
    List<AccountCityResponseDTO> getLocaleCities(@Param("language") Language language);
}
