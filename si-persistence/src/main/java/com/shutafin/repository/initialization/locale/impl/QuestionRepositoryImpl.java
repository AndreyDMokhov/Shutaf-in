package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.web.initialization.QuestionResponseDTO;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.initialization.locale.QuestionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 8/22/2017.
 */
@Repository
public class QuestionRepositoryImpl extends AbstractConstEntityDao<Question> implements QuestionRepository {
    @Override
    public List<QuestionResponseDTO> getLocaleQuestions(Language language) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new com.shutafin.model.web.initialization.QuestionResponseDTO ")
                .append(" ( ")
                .append(" cl.question.id, ")
                .append(" cl.description, ")
                .append(" cl.question.isActive ")
                .append(" )")
                .append(" from QuestionLocale cl where cl.language = :language ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();
    }
}
