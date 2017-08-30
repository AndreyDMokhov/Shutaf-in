package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.web.initialization.AnswerResponseDTO;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.initialization.locale.AnswerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 8/22/2017.
 */
@Repository
public class AnswerRepositoryImpl extends AbstractConstEntityDao<Answer> implements AnswerRepository {
    @Override
    public List<AnswerResponseDTO> getLocaleAnswers(Language language) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new com.shutafin.model.web.initialization.AnswerResponseDTO ")
                .append(" ( ")
                .append(" cl.answer.id, ")
                .append(" cl.description, ")
                .append(" cl.answer.isUniversal as isUniversal ")
                .append(" )")
                .append(" from AnswerLocale cl where cl.language = :language ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();
    }

    @Override
    public List<AnswerResponseDTO> getQuestionLocaleAnswers(Language language, Question question) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new com.shutafin.model.web.initialization.AnswerResponseDTO ")
                .append(" ( ")
                .append(" cl.answer.id, ")
                .append(" cl.description, ")
                .append(" cl.answer.isUniversal as isUniversal ")
                .append(" )")
                .append(" from AnswerLocale cl where cl.language = :language ")
                .append(" and cl.answer.question = :question ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .setParameter("question", question)
                .getResultList();
    }
}
