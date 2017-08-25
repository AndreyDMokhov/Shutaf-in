package com.shutafin.service.impl;

import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.matching.AnswerSimilarity;
import com.shutafin.repository.matching.AnswerSimilarityRepository;
import com.shutafin.service.AnswerSimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerSimilarityServiceImpl implements AnswerSimilarityService {

    @Autowired
    private AnswerSimilarityRepository answerSimilarityRepository;

    @Override
    public AnswerSimilarity getAnswerSimilarity(Answer answer, Answer answerToCompare) {
        return answerSimilarityRepository.getAnswerSimilarity(answer, answerToCompare);
    }
}
