package com.shutafin.service.impl;

import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.matching.AnswerSimilarity;
import com.shutafin.repository.matching.AnswerSimilarityRepository;
import com.shutafin.service.AnswerSimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Deprecated
@Service
@Transactional
public class AnswerSimilarityServiceImpl implements AnswerSimilarityService {

    @Autowired
    private AnswerSimilarityRepository answerSimilarityRepository;

    @Override
    public AnswerSimilarity getAnswerSimilarity(AnswerExtended answer, AnswerExtended answerToCompare) {
        if (answer == null || answerToCompare == null) {
            return new AnswerSimilarity(answer, answerToCompare, 0);
        }
        return answerSimilarityRepository.findByAnswerAndAndAnswerToCompare(answer, answerToCompare);
    }
}
