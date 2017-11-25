package com.shutafin.repository.extended;


import com.shutafin.model.dto.QuestionExtendedResponseDTO;
import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;


public interface QuestionExtendedRepository extends BaseJpaRepository<QuestionExtended, Integer> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(value = "select new com.shutafin.model.dto.QuestionExtendedResponseDTO " +
            "(q.question.id, q.description) " +
            "from QuestionExtendedLocale q " +
            "where q.languageId = :languageId")
    List<QuestionExtendedResponseDTO> getLocaleQuestionsExtended(@Param("languageId") Integer languageId);
}
