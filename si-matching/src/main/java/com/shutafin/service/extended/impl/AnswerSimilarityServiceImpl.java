package com.shutafin.service.extended.impl;


import com.shutafin.model.entities.extended.AnswerExtended;
import com.shutafin.model.entities.extended.AnswerSimilarity;
import com.shutafin.repository.extended.AnswerSimilarityRepository;
import com.shutafin.service.extended.AnswerSimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<AnswerSimilarity> getAllAnswerSimilarity() {
        return answerSimilarityRepository.findAll();
    }
}
