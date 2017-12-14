package com.shutafin.repository.extended;


import com.shutafin.model.dto.QuestionImportanceDTO;
import com.shutafin.model.entities.extended.QuestionImportance;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;


public interface QuestionImportanceRepository extends BaseJpaRepository<QuestionImportance, Integer> {
    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(value = "select new com.shutafin.model.dto.QuestionImportanceDTO " +
            "(qi.questionImportance.id, qi.description) " +
            "from QuestionImportanceLocale qi where qi.languageId = :languageId")
    List<QuestionImportanceDTO> getAllQuestionImportanceLocale(@Param("languageId") Integer languageId);
}
