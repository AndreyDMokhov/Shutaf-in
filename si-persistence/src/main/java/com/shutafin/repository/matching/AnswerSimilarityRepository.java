package com.shutafin.repository.matching;

import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.matching.AnswerSimilarity;
import com.shutafin.repository.base.BaseJpaRepository;

public interface AnswerSimilarityRepository extends BaseJpaRepository<AnswerSimilarity, Integer> {

    AnswerSimilarity findByAnswerAndAndAnswerToCompare(AnswerExtended answer, AnswerExtended answerToCompare);
}
