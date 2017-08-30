package com.shutafin.repository.matching.impl;

import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.matching.AnswerSimilarity;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.matching.AnswerSimilarityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerSimilarityRepositoryImpl extends AbstractConstEntityDao<AnswerSimilarity> implements AnswerSimilarityRepository {

    @Override
    public AnswerSimilarity getAnswerSimilarity(AnswerExtended answer, AnswerExtended answerToCompare) {
        return (AnswerSimilarity) getSession()
                .createQuery("from AnswerSimilarity anssim where anssim.answer.id = :answerId and " +
                        "anssim.answerToCompare.id = :answerToCompareId")
                .setParameter("answerId", answer.getId())
                .setParameter("answerToCompareId", answerToCompare.getId())
                .uniqueResult();
    }
}
