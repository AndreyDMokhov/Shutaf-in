package com.shutafin.repository.extended;


import com.shutafin.model.dto.AnswerExtendedResponseDTO;
import com.shutafin.model.entities.extended.AnswerExtended;
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
    @Query(value = "select new com.shutafin.model.dto.AnswerExtendedResponseDTO " +
            "(ansl.answer.id, ansl.description) " +
            "from AnswerExtendedLocale ansl, AnswerExtended ans " +
            "where ansl.languageId = :languageId and ansl.answer.id = ans.id and ans.question.id = :questionId")
    List<AnswerExtendedResponseDTO> getLocaleAnswersExtended(@Param("languageId") Integer languageId,
                                                             @Param("questionId") Integer questionId);
}
