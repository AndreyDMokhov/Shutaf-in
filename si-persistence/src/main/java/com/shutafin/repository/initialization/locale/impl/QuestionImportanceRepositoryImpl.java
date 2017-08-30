package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.QuestionImportance;
import com.shutafin.model.web.initialization.QuestionImportanceDTO;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.initialization.locale.QuestionImportanceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionImportanceRepositoryImpl extends AbstractConstEntityDao<QuestionImportance> implements QuestionImportanceRepository {

    @Override
    public List<QuestionImportance> getAllQuestionImportance() {
        StringBuilder hql = new StringBuilder();
        hql.append("select * from QuestionImportance; ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .getResultList();
    }

    @Override
    public List<QuestionImportanceDTO> getAllQuestionImportanceLocale(Language language) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new com.shutafin.model.web.initialization.QuestionImportanceDTO ")
                .append(" ( ")
                .append(" qi.questionImportance.id, ")
                .append(" qi.description ")
                .append(" )")
                .append(" from QuestionImportanceLocale qi where qi.language = :language ");

        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();
    }
}
