package com.shutafin.service.extended.impl;


import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.model.entities.extended.UserMatchingScore;
import com.shutafin.model.entities.extended.UserQuestionExtendedAnswer;
import com.shutafin.repository.extended.MaxUserMatchingScoreRepository;
import com.shutafin.repository.extended.UserMatchingScoreRepository;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.extended.AnswerSimilarityService;
import com.shutafin.service.extended.CoreMatchingService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CoreMatchingServiceImpl implements CoreMatchingService {

    private static final Double BASIC_MATCHING_SCORE = 30.;

    @Autowired
    private AnswerSimilarityService answerSimilarityService;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserMatchingScoreRepository userMatchingScoreRepository;

    @Autowired
    private MaxUserMatchingScoreRepository maxUserMatchingScoreRepository;

    @Override
    public UserMatchingScore evaluateUserMatchingScore(Long userOriginId, Long userToMatchId) {

        Double resultScore = calculateMatchingScore(userOriginId, userToMatchId);
        UserMatchingScore matchingScore = userMatchingScoreRepository.findByUserOriginIdAndUserToMatchId(userOriginId, userToMatchId);
        if (matchingScore == null) {
            matchingScore = new UserMatchingScore(userOriginId, userToMatchId, resultScore.intValue());
            userMatchingScoreRepository.save(matchingScore);
        } else {
            matchingScore.setScore(resultScore.intValue());
        }
        return matchingScore;

    }

    private Double calculateMatchingScore(Long userOriginId, Long userToMatchId) {

        if (!isUserHasAnswers(userOriginId) || !isUserHasAnswers(userToMatchId)) {
            return BASIC_MATCHING_SCORE;
        }

        Double maxPossibleScoreOrigin = maxUserMatchingScoreRepository.findByUserId(userOriginId)
                .getScore().doubleValue();
        Double maxPossibleScoreToMatch = maxUserMatchingScoreRepository.findByUserId(userToMatchId)
                .getScore().doubleValue();

        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers =
                userQuestionExtendedAnswerService.getAllUserQuestionExtendedAnswers(userOriginId);
        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers =
                userQuestionExtendedAnswerService.getAllUserQuestionExtendedAnswers(userToMatchId);

        Double totalScore = 0.;
        Double crossScore = 0.;

        for (QuestionExtended question : userOriginAnswers.keySet()) {
            Integer answerSimilarityScore = getAnswerSimilarityScore(userOriginAnswers, userToMatchAnswers, question);

            totalScore += userOriginAnswers.get(question).get(0).getImportance().getWeight() *
                    answerSimilarityScore;

            if (userToMatchAnswers.get(question) != null) {
                crossScore += userToMatchAnswers.get(question).get(0).getImportance().getWeight() *
                        answerSimilarityScore;
            }
        }

        if (maxPossibleScoreToMatch == 0 || maxPossibleScoreOrigin == 0) {
            return BASIC_MATCHING_SCORE;
        }

        return Math.sqrt(totalScore / maxPossibleScoreOrigin * crossScore / maxPossibleScoreToMatch)
                * (100 - BASIC_MATCHING_SCORE) + BASIC_MATCHING_SCORE;
    }

    private Integer getAnswerSimilarityScore(Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers,
                                             Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers,
                                             QuestionExtended question) {

        if (userToMatchAnswers.get(question) == null) {
            return 0;
        }

        Integer maxAnswerSimilarityScore = 0;
        Integer currentSimilarityScore;
        for (UserQuestionExtendedAnswer answer: userOriginAnswers.get(question)) {
            for (UserQuestionExtendedAnswer answerToMatch : userToMatchAnswers.get(question)) {
                currentSimilarityScore = answerSimilarityService.getAnswerSimilarity(answer.getAnswer(),
                        answerToMatch.getAnswer()).getSimilarityScore();
                if (currentSimilarityScore > maxAnswerSimilarityScore) {
                    maxAnswerSimilarityScore = currentSimilarityScore;
                }
            }
        }
        return maxAnswerSimilarityScore;

    }
    //TODO establish connection with account service to get all active users
    @Override
    public void evaluateAllUserMatchingScores() {
//        for (User userOrigin : userRepository.findAll()) {
//            evaluateUserMatchingScores(userOrigin);
//        }
    }

    private void evaluateUserMatchingScores(Long userOriginId) {
        for (Long userToMatch : userMatchService.findMatchingUsers(userOriginId)) {
            evaluateUserMatchingScore(userOriginId, userToMatch);
        }
    }

    private Boolean isUserHasAnswers(Long userId) {
        return maxUserMatchingScoreRepository.findByUserId(userId) != null;
    }
}
