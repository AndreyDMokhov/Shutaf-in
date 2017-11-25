package com.shutafin.service;


import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.matching.AnswerSimilarity;
@Deprecated
public interface AnswerSimilarityService {

    AnswerSimilarity getAnswerSimilarity(AnswerExtended answer, AnswerExtended answerToCompare);
}
