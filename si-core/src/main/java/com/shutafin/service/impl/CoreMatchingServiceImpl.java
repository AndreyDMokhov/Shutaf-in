package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.matching.UserMatchingScore;
import com.shutafin.model.entities.matching.UserQuestionExtendedAnswer;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.repository.matching.MaxUserMatchingScoreRepository;
import com.shutafin.repository.matching.UserMatchingScoreRepository;
import com.shutafin.service.AnswerSimilarityService;
import com.shutafin.service.CoreMatchingService;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.UserQuestionExtendedAnswerService;
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
    private UserRepository userRepository;

    @Autowired
    private MaxUserMatchingScoreRepository maxUserMatchingScoreRepository;

    @Override
    public UserMatchingScore evaluateUserMatchingScore(User userOrigin, User userToMatch) {

        Double resultScore = calculateMatchingScore(userOrigin, userToMatch);
        UserMatchingScore matchingScore = userMatchingScoreRepository.getUserMatchingScore(userOrigin, userToMatch);
        if (matchingScore == null) {
            matchingScore = new UserMatchingScore(userOrigin, userToMatch, resultScore.intValue());
            userMatchingScoreRepository.save(matchingScore);
        } else {
            matchingScore.setScore(resultScore.intValue());
            userMatchingScoreRepository.update(matchingScore);
        }
        return matchingScore;

    }

    private Double calculateMatchingScore(User userOrigin, User userToMatch) {
        Double totalScore = 0.;
        Double crossScore = 0.;
        Double maxPossibleScoreOrigin = maxUserMatchingScoreRepository.getUserMaxMatchingScore(userOrigin)
                .getScore().doubleValue();
        Double maxPossibleScoreToMatch = maxUserMatchingScoreRepository.getUserMaxMatchingScore(userToMatch)
                .getScore().doubleValue();

        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers =
                userQuestionExtendedAnswerService.getAllUserQuestionAnswers(userOrigin);
        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers =
                userQuestionExtendedAnswerService.getAllUserQuestionAnswers(userToMatch);

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

    @Override
    public void evaluateAllUserMatchingScores() {
        for (User userOrigin : userRepository.findAllUsers()) {
            evaluateUserMatchingScores(userOrigin);
        }
    }

    private void evaluateUserMatchingScores(User userOrigin) {
        for (User userToMatch : userMatchService.findMatchingUsers(userOrigin)) {
            evaluateUserMatchingScore(userOrigin, userToMatch);
        }
    }
}
