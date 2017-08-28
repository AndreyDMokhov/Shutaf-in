package com.shutafin.repository.matching;

import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.matching.AnswerSimilarity;
import com.shutafin.repository.base.Dao;

public interface AnswerSimilarityRepository extends Dao<AnswerSimilarity> {

    AnswerSimilarity getAnswerSimilarity(AnswerExtended answer, AnswerExtended answerToCompare);
}
