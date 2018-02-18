package com.shutafin.service.extended.impl;


import com.shutafin.model.entities.extended.AnswerSimilarity;
import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.model.entities.extended.UserMatchingScore;
import com.shutafin.model.entities.extended.UserQuestionExtendedAnswer;
import com.shutafin.repository.UserExamKeyRepository;
import com.shutafin.repository.extended.MaxUserMatchingScoreRepository;
import com.shutafin.repository.extended.UserMatchingScoreRepository;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.extended.AnswerSimilarityService;
import com.shutafin.service.extended.CoreMatchingService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CoreMatchingServiceImpl implements CoreMatchingService {

    private static final Double BASIC_MATCHING_SCORE = 30.;

    private AnswerSimilarityService answerSimilarityService;

    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    private UserMatchService userMatchService;

    private UserMatchingScoreRepository userMatchingScoreRepository;

    private MaxUserMatchingScoreRepository maxUserMatchingScoreRepository;

    private UserExamKeyRepository userExamKeyRepository;

    private static Map<Integer, Map<Integer,Integer>> answerSimilarityCache;

    @Autowired
    public CoreMatchingServiceImpl(AnswerSimilarityService answerSimilarityService, UserQuestionExtendedAnswerService userQuestionExtendedAnswerService, UserMatchService userMatchService, UserMatchingScoreRepository userMatchingScoreRepository, MaxUserMatchingScoreRepository maxUserMatchingScoreRepository, UserExamKeyRepository userExamKeyRepository) {
        this.answerSimilarityService = answerSimilarityService;
        this.userQuestionExtendedAnswerService = userQuestionExtendedAnswerService;
        this.userMatchService = userMatchService;
        this.userMatchingScoreRepository = userMatchingScoreRepository;
        this.maxUserMatchingScoreRepository = maxUserMatchingScoreRepository;
        this.userExamKeyRepository = userExamKeyRepository;
        loadAnswerSimilarityCache();
    }

    private void loadAnswerSimilarityCache() {
        answerSimilarityCache = new HashMap<Integer, Map<Integer, Integer>>();
        List<AnswerSimilarity> answerSimilarityList = answerSimilarityService.getAllAnswerSimilarity();
        for (AnswerSimilarity answerSimilarity : answerSimilarityList){
            Map<Integer, Integer> answersToCompare = answerSimilarityCache.get(answerSimilarity.getAnswer().getId());
            if (answersToCompare == null){
                answerSimilarityCache.put(answerSimilarity.getAnswer().getId(), new HashMap<Integer, Integer>());
            }
            answersToCompare = answerSimilarityCache.get(answerSimilarity.getAnswer().getId());
            answersToCompare.put(answerSimilarity.getAnswerToCompare().getId(), answerSimilarity.getSimilarityScore());
        }
    }

    @Override
    public UserMatchingScore evaluateUserMatchingScore(Long userOriginId,
                                                       Long userToMatchId,
                                                       Double maxPossibleScoreOrigin,
                                                       Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers,
                                                       Double maxPossibleScoreToMatch,
                                                       Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers) {

        Double resultScore = calculateMatchingScore(maxPossibleScoreOrigin, userOriginAnswers, maxPossibleScoreToMatch, userToMatchAnswers);
        UserMatchingScore matchingScore = userMatchingScoreRepository.findByUserOriginIdAndUserToMatchId(userOriginId, userToMatchId);
        if (matchingScore == null) {
            matchingScore = new UserMatchingScore(userOriginId, userToMatchId, resultScore.intValue());
        } else {
            matchingScore.setScore(resultScore.intValue());
        }
        return matchingScore;

    }

    private Double calculateMatchingScore(Double maxPossibleScoreOrigin,
                                          Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers,
                                          Double maxPossibleScoreToMatch,
                                          Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers) {

        Boolean isUserOriginHasAnswers = userOriginAnswers!=null && !userOriginAnswers.isEmpty();
        Boolean isUserToMatchHasAnswers = userToMatchAnswers!=null && !userToMatchAnswers.isEmpty();
        if (!isUserOriginHasAnswers || !isUserToMatchHasAnswers) {
            return BASIC_MATCHING_SCORE;
        }

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
                currentSimilarityScore = answerSimilarityCache.get(answer.getAnswer().getId()).get(answerToMatch.getAnswer().getId());

                if (currentSimilarityScore > maxAnswerSimilarityScore) {
                    maxAnswerSimilarityScore = currentSimilarityScore;
                }
            }
        }
        return maxAnswerSimilarityScore;

    }

    @Override
    public void evaluateAllUserMatchingScores() {
        for (Long userOrigin : userExamKeyRepository.findAllUserIds()) {
            Double maxPossibleScoreOrigin = maxUserMatchingScoreRepository.findByUserId(userOrigin).getScore().doubleValue();
            Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers = userQuestionExtendedAnswerService.getAllUserQuestionExtendedAnswers(userOrigin);
            evaluateUserMatchingScores(userOrigin, maxPossibleScoreOrigin, userOriginAnswers);
        }
    }

    private void evaluateUserMatchingScores(Long userOriginId, Double maxPossibleScoreOrigin, Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers) {
        for (Long userToMatch : userMatchService.findMatchingUsers(userOriginId)) {
            Double maxPossibleScoreToMatch = maxUserMatchingScoreRepository.findByUserId(userToMatch).getScore().doubleValue();
            Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers = userQuestionExtendedAnswerService.getAllUserQuestionExtendedAnswers(userToMatch);
            evaluateUserMatchingScore(userOriginId, userToMatch, maxPossibleScoreOrigin, userOriginAnswers, maxPossibleScoreToMatch, userToMatchAnswers);
        }
    }
}
