package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.web.initialization.QuestionExtendedResponseDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;
@Deprecated
public interface QuestionExtendedRepository extends BaseJpaRepository<QuestionExtended, Integer> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(value = "select new com.shutafin.model.web.initialization.QuestionExtendedResponseDTO " +
            "(q.question.id, q.description) " +
            "from QuestionExtendedLocale q " +
            "where q.language = :language")
    List<QuestionExtendedResponseDTO> getLocaleQuestionsExtended(@Param("language") Language language);
}
