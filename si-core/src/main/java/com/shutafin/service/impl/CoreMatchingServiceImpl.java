package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.entities.matching.AnswerSimilarity;
import com.shutafin.model.entities.matching.UserMatchingScore;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.repository.matching.UserMatchingScoreRepository;
import com.shutafin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class CoreMatchingServiceImpl implements CoreMatchingService {

    @Autowired
    private AnswerSimilarityService answerSimilarityService;

    @Autowired
    private UserQuestionAnswerService userQuestionAnswerService;

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserMatchingScoreRepository userMatchingScoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserMatchingScore evaluateUserMatchingScore(User userOrigin, User userToMatch) {
        UserMatchingScore matchingScore = userMatchingScoreRepository.getUserMatchingScore(userOrigin, userToMatch);
        if (matchingScore != null) {
            return matchingScore;
        }

        Double totalScore = 0.;
        Double crossScore = 0.;
        Double maxPossibleScoreOrigin = 0.;
        Double maxPossibleScoreToMatch = 0.;

        Map<Question, UserQuestionAnswer> userOriginAnswers = userQuestionAnswerService.getAllUserQuestionAnswers(userOrigin);
        Map<Question, UserQuestionAnswer> userToMatchAnswers = userQuestionAnswerService.getAllUserQuestionAnswers(userToMatch);

        Integer maxAnswerSimilarityScore = answerSimilarityService.getAnswerSimilarity(
                userOriginAnswers.get(0).getAnswer(),
                userOriginAnswers.get(0).getAnswer())
                .getSimilarityScore();

        for (Question question : userOriginAnswers.keySet()) {
            AnswerSimilarity answerSimilarity = answerSimilarityService.getAnswerSimilarity(
                    userOriginAnswers.get(question).getAnswer(),
                    userToMatchAnswers.get(question).getAnswer());

            totalScore += userOriginAnswers.get(question).getQuestionImportance().getWeight() *
                    answerSimilarity.getSimilarityScore();

            crossScore += userToMatchAnswers.get(question).getQuestionImportance().getWeight() *
                    answerSimilarity.getSimilarityScore();

            maxPossibleScoreOrigin += userOriginAnswers.get(question).getQuestionImportance().getWeight() *
                    maxAnswerSimilarityScore;

            maxPossibleScoreToMatch += userToMatchAnswers.get(question).getQuestionImportance().getWeight() *
                    maxAnswerSimilarityScore;

        }

        Double resultScore = Math.sqrt(totalScore/maxPossibleScoreOrigin * crossScore/maxPossibleScoreToMatch) * 100;
        matchingScore = new UserMatchingScore(userOrigin, userToMatch, resultScore.intValue());
        userMatchingScoreRepository.save(matchingScore);
        return matchingScore;

    }

    @Override
    public void evaluateAllUserMatchingScores() {
        for (User userOrigin : userRepository.findAllUsers()) {
            evaluateUserMatchingScores(userOrigin);
        }
    }

    private void evaluateUserMatchingScores(User userOrigin) {
        for (User userToMatch : userMatchService.findPartners(userOrigin)) {
            evaluateUserMatchingScore(userOrigin, userToMatch);
        }
    }
}
