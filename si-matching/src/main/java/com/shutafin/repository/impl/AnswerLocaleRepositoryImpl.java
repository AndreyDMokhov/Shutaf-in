package com.shutafin.repository.impl;


import com.shutafin.model.infrastructure.AnswerElement;
import com.shutafin.repository.CustomAnswerLocaleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class AnswerLocaleRepositoryImpl implements CustomAnswerLocaleRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<AnswerElement> findAllByLanguageId(Integer languageId) {
        StringBuilder hql = new StringBuilder()
                .append("SELECT NEW com.shutafin.model.infrastructure.AnswerElement" +
            "(cl.answer.question.id,  cl.answer.id, cl.description, cl.answer.isUniversal ) " +
            "from AnswerLocale cl where cl.languageId = :languageId ");

        Query ql = em.createQuery(hql.toString());
        ql.setParameter("languageId",languageId);
        return ql.getResultList();

    }
}
