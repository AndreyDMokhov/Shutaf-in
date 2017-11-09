package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.web.initialization.AnswerResponseDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Created by evgeny on 8/22/2017.
 */
public interface AnswerRepository extends BaseJpaRepository<Answer, Integer> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(value = "select new com.shutafin.model.web.initialization.AnswerResponseDTO  (  cl.answer.id,  cl.description,  cl.answer.isUniversal as isUniversal  ) from AnswerLocale cl where cl.language = :language")
    List<AnswerResponseDTO> getLocaleAnswers(@Param("language") Language language);

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(value = "select new com.shutafin.model.web.initialization.AnswerResponseDTO (cl.answer.id, cl.description, cl.answer.isUniversal as isUniversal) from AnswerLocale cl where cl.language = :language and cl.answer.question = :question")
    List<AnswerResponseDTO> getQuestionLocaleAnswers(@Param("language") Language language, @Param("question") Question question);
}
