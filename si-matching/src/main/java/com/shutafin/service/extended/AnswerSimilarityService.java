package com.shutafin.service.extended;


import com.shutafin.model.entities.extended.AnswerExtended;
import com.shutafin.model.entities.extended.AnswerSimilarity;

public interface AnswerSimilarityService {

    AnswerSimilarity getAnswerSimilarity(AnswerExtended answer, AnswerExtended answerToCompare);
}
