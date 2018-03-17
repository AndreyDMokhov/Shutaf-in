package com.shutafin.service;

import com.shutafin.model.entities.extended.AnswerSimilarity;
import com.shutafin.service.extended.AnswerSimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by evgeny on 3/9/2018.
 */
@Component
public class AnswerSimilarityCacheService implements ApplicationListener<ContextRefreshedEvent> {

    private AnswerSimilarityService answerSimilarityService;
    private static Map<Integer, Map<Integer, Integer>> answerSimilarityCache  = new HashMap<>();

    @Autowired
    public AnswerSimilarityCacheService(AnswerSimilarityService answerSimilarityService){
        this.answerSimilarityService = answerSimilarityService;
    }

    private void loadAnswerSimilarityCache() {
        List<AnswerSimilarity> answerSimilarityList = answerSimilarityService.getAllAnswerSimilarity();
        for (AnswerSimilarity answerSimilarity : answerSimilarityList) {
            Map<Integer, Integer> answersToCompare = answerSimilarityCache.get(answerSimilarity.getAnswer().getId());
            if (answersToCompare == null) {
                answerSimilarityCache.put(answerSimilarity.getAnswer().getId(), new HashMap<>());
                answersToCompare = answerSimilarityCache.get(answerSimilarity.getAnswer().getId());
            }
            answersToCompare.put(answerSimilarity.getAnswerToCompare().getId(), answerSimilarity.getSimilarityScore());
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadAnswerSimilarityCache();
    }

    public Map<Integer, Map<Integer, Integer>> getAnswerSimilarityCache(){
        return answerSimilarityCache;
    }
}
