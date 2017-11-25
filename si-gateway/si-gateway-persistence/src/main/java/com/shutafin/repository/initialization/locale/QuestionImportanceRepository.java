package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.QuestionImportance;
import com.shutafin.model.web.initialization.QuestionImportanceDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

@Deprecated
public interface QuestionImportanceRepository extends BaseJpaRepository<QuestionImportance, Integer> {
    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(value = "select new com.shutafin.model.web.initialization.QuestionImportanceDTO " +
            "(qi.questionImportance.id, qi.description) " +
            "from QuestionImportanceLocale qi where qi.language = :language")
    List<QuestionImportanceDTO> getAllQuestionImportanceLocale(@Param("language") Language language);
}
