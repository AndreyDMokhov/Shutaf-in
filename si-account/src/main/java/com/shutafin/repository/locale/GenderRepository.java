package com.shutafin.repository.locale;

import com.shutafin.model.infrastructure.Gender;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.account.AccountGenderResponseDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface GenderRepository extends BaseJpaRepository<Gender, Integer> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query("select new com.shutafin.model.web.account.AccountGenderResponseDTO (  gl.gender.id,  gl.description  ) from GenderLocale gl where gl.language = :language")
    List<AccountGenderResponseDTO> getLocaleGenders(@Param("language") Language language);
}
