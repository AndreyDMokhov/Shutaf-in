package com.shutafin.repository.impl;


import com.shutafin.model.DTO.QuestionsListWithAnswersDTO;
import com.shutafin.repository.QuestionLocaleRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class QuestionLocaleRepositoryImpl implements QuestionLocaleRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<QuestionsListWithAnswersDTO> findByLanguageId(Integer languageId) {
        StringBuilder hql = new StringBuilder()
                .append("SELECT NEW com.shutafin.model.DTO.QuestionsListWithAnswersDTO " +
                        " (cl.question.id, cl.description,cl.question.isActive ) " +
                        " from QuestionLocale cl where cl.languageId = :languageId  " +
                        " AND cl.question.isActive = 1  ");

        Query query = em.createQuery(hql.toString());
        query.setParameter("languageId", languageId);

        return query.getResultList();

    }
}
