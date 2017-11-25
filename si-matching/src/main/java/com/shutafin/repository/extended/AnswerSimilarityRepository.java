package com.shutafin.repository.extended;


import com.shutafin.model.entities.extended.AnswerExtended;
import com.shutafin.model.entities.extended.AnswerSimilarity;
import com.shutafin.repository.base.BaseJpaRepository;

public interface AnswerSimilarityRepository extends BaseJpaRepository<AnswerSimilarity, Integer> {

    AnswerSimilarity findByAnswerAndAndAnswerToCompare(AnswerExtended answer, AnswerExtended answerToCompare);
}
