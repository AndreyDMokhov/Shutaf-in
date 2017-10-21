package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.web.initialization.QuestionExtendedResponseDTO;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.initialization.locale.QuestionExtendedRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionExtendedRepositoryImpl extends AbstractConstEntityDao<QuestionExtended> implements QuestionExtendedRepository  {
    @Override
    public List<QuestionExtended> getAllQuestionsExtended() {
        StringBuilder hql = new StringBuilder();
        hql.append("from QuestionExtended ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .getResultList();
    }

    @Override
    public List<QuestionExtendedResponseDTO> getLocaleQuestionsExtended(Language language) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new com.shutafin.model.web.initialization.QuestionExtendedResponseDTO ")
                .append(" ( ")
                .append(" q.question.id, ")
                .append(" q.description ")
                .append(" )")
                .append(" from QuestionExtendedLocale q where q.language = :language ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();
    }
}
