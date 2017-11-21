package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.AnswerExtendedResponseDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

public interface AnswerExtendedRepository extends BaseJpaRepository<AnswerExtended, Integer> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(value = "select new com.shutafin.model.web.initialization.AnswerExtendedResponseDTO " +
            "(ansl.answer.id, ansl.description) " +
            "from AnswerExtendedLocale ansl, AnswerExtended ans " +
            "where ansl.language = :language and ansl.answer.id = ans.id and ans.question.id = :questionId")
    List<AnswerExtendedResponseDTO> getLocaleAnswersExtended(@Param("language") Language language,
                                                             @Param("questionId") Integer questionId);
}
