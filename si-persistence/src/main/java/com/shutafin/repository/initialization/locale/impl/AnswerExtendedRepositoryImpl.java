package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.AnswerExtendedResponseDTO;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.initialization.locale.AnswerExtendedRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerExtendedRepositoryImpl extends AbstractConstEntityDao<AnswerExtended> implements AnswerExtendedRepository{
    @Override
    public List<AnswerExtended> getAllAnswersExtended() {
        StringBuilder hql = new StringBuilder();
        hql.append("select * from AnswerExtended; ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .getResultList();
    }

    @Override
    public List<AnswerExtendedResponseDTO> getLocaleAnswerExtended(Language language, Integer questionId) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new com.shutafin.model.web.initialization.AnswerExtendedResponseDTO ");
        hql.append(" ( ");
        hql.append(" ansl.answer.id, ");
        hql.append(" ansl.description ");
        hql.append(" )");
        hql.append(" from AnswerExtendedLocale ansl, AnswerExtended ans ");
        hql.append(" where ansl.language = :language ");
        hql.append(" and ansl.answer.id = ans.id ");
        hql.append(" and ans.question.id = :questionId ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .setParameter("questionId", questionId)
                .getResultList();
    }
}
