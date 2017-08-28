package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.matching.AnswerSimilarity;
import com.shutafin.model.entities.matching.UserMatchingScore;
import com.shutafin.model.entities.matching.UserQuestionExtendedAnswer;
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
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserMatchingScoreRepository userMatchingScoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserMatchingScore evaluateUserMatchingScore(User userOrigin, User userToMatch) {

        Double totalScore = 0.;
        Double crossScore = 0.;
        Double maxPossibleScoreOrigin = 0.;
        Double maxPossibleScoreToMatch = 0.;

        Map<QuestionExtended, UserQuestionExtendedAnswer> userOriginAnswers =
                userQuestionExtendedAnswerService.getAllUserQuestionAnswers(userOrigin);
        Map<QuestionExtended, UserQuestionExtendedAnswer> userToMatchAnswers =
                userQuestionExtendedAnswerService.getAllUserQuestionAnswers(userToMatch);



        for (QuestionExtended question : userOriginAnswers.keySet()) {
            AnswerSimilarity answerSimilarity = answerSimilarityService.getAnswerSimilarity(
                    userOriginAnswers.get(question).getAnswer(),
                    userToMatchAnswers.get(question).getAnswer());

            Integer maxAnswerSimilarityScore = answerSimilarityService.getAnswerSimilarity(
                    userOriginAnswers.get(question).getAnswer(),
                    userOriginAnswers.get(question).getAnswer())
                    .getSimilarityScore();

            totalScore += userOriginAnswers.get(question).getImportance().getWeight() *
                    answerSimilarity.getSimilarityScore();

            crossScore += userToMatchAnswers.get(question).getImportance().getWeight() *
                    answerSimilarity.getSimilarityScore();

            maxPossibleScoreOrigin += userOriginAnswers.get(question).getImportance().getWeight() *
                    maxAnswerSimilarityScore;

            maxPossibleScoreToMatch += userToMatchAnswers.get(question).getImportance().getWeight() *
                    maxAnswerSimilarityScore;

        }

        Double resultScore = Math.sqrt(totalScore/maxPossibleScoreOrigin * crossScore/maxPossibleScoreToMatch) * 100;
        UserMatchingScore matchingScore = new UserMatchingScore(userOrigin, userToMatch, resultScore.intValue());
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
