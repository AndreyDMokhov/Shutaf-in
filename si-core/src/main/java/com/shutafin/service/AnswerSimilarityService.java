package com.shutafin.service;


import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.matching.AnswerSimilarity;

public interface AnswerSimilarityService {

    AnswerSimilarity getAnswerSimilarity(Answer answer, Answer answerToCompare);
}
