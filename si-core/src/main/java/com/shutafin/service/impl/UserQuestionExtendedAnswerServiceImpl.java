package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.infrastructure.QuestionImportance;
import com.shutafin.model.entities.matching.MaxUserMatchingScore;
import com.shutafin.model.entities.matching.UserQuestionExtendedAnswer;
import com.shutafin.model.web.UserQuestionExtendedAnswersWeb;
import com.shutafin.repository.initialization.locale.AnswerExtendedRepository;
import com.shutafin.repository.initialization.locale.QuestionExtendedRepository;
import com.shutafin.repository.initialization.locale.QuestionImportanceRepository;
import com.shutafin.repository.matching.MaxUserMatchingScoreRepository;
import com.shutafin.repository.matching.UserQuestionExtendedAnswerRepository;
import com.shutafin.service.AnswerSimilarityService;
import com.shutafin.service.UserMatchingScoreService;
import com.shutafin.service.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserQuestionExtendedAnswerServiceImpl implements UserQuestionExtendedAnswerService {

    private UserQuestionExtendedAnswerRepository userQuestionExtendedAnswerRepository;
    private QuestionExtendedRepository questionExtendedRepository;
    private AnswerExtendedRepository answerExtendedRepository;
    private QuestionImportanceRepository questionImportanceRepository;
    private MaxUserMatchingScoreRepository maxUserMatchingScoreRepository;
    private AnswerSimilarityService answerSimilarityService;
    private UserMatchingScoreService userMatchingScoreService;

    @Autowired
    public UserQuestionExtendedAnswerServiceImpl(UserQuestionExtendedAnswerRepository userQuestionExtendedAnswerRepository,
                                                 QuestionExtendedRepository questionExtendedRepository,
                                                 AnswerExtendedRepository answerExtendedRepository,
                                                 QuestionImportanceRepository questionImportanceRepository,
                                                 MaxUserMatchingScoreRepository maxUserMatchingScoreRepository,
                                                 AnswerSimilarityService answerSimilarityService,
                                                 UserMatchingScoreService userMatchingScoreService) {
        this.userQuestionExtendedAnswerRepository = userQuestionExtendedAnswerRepository;
        this.questionExtendedRepository = questionExtendedRepository;
        this.answerExtendedRepository = answerExtendedRepository;
        this.questionImportanceRepository = questionImportanceRepository;
        this.maxUserMatchingScoreRepository = maxUserMatchingScoreRepository;
        this.answerSimilarityService = answerSimilarityService;
        this.userMatchingScoreService = userMatchingScoreService;
    }

    @Override
    public Map<QuestionExtended, List<UserQuestionExtendedAnswer>> getAllUserQuestionAnswers(User user) {
        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userQuestionExtendedAnswerMap = new HashMap<>();
        for (UserQuestionExtendedAnswer userAnswer :
                userQuestionExtendedAnswerRepository.getAllUserQuestionExtendedAnswers(user)) {

            if (userQuestionExtendedAnswerMap.get(userAnswer.getQuestion()) == null) {
                userQuestionExtendedAnswerMap.put(userAnswer.getQuestion(), new ArrayList<>());
            }
            userQuestionExtendedAnswerMap.get(userAnswer.getQuestion()).add(userAnswer);
        }
        return userQuestionExtendedAnswerMap;
    }


    @Override
    public void addUserQuestionAnswersWeb(List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList,
                                          User user) {
        deleteUserQuestionAnswers(user);
        userMatchingScoreService.deleteUserMatchingScores(user);
        Integer maxScore = 0;
        for (UserQuestionExtendedAnswersWeb questionExtendedAnswersWeb : userQuestionExtendedAnswersWebList) {
            QuestionExtended question = questionExtendedRepository
                    .findById(questionExtendedAnswersWeb.getQuestionId());
            QuestionImportance importance = questionImportanceRepository
                    .findById(questionExtendedAnswersWeb.getQuestionImportanceId());
            UserQuestionExtendedAnswer userQuestionExtendedAnswer;

            AnswerExtended answer = null;
            for (Integer answerId : questionExtendedAnswersWeb.getAnswersId()) {
                answer = answerExtendedRepository.findById(answerId);
                userQuestionExtendedAnswer = new UserQuestionExtendedAnswer(user, question, answer, importance);
                userQuestionExtendedAnswerRepository.save(userQuestionExtendedAnswer);
            }
            maxScore += importance.getWeight() * answerSimilarityService.getAnswerSimilarity(answer, answer)
                    .getSimilarityScore();

        }

        MaxUserMatchingScore maxUserMatchingScore = maxUserMatchingScoreRepository.getUserMaxMatchingScore(user);
        if (maxUserMatchingScore == null) {
            maxUserMatchingScore = new MaxUserMatchingScore(user, maxScore);
            maxUserMatchingScoreRepository.save(maxUserMatchingScore);
        } else {
            maxUserMatchingScore.setScore(maxScore);
            maxUserMatchingScoreRepository.update(maxUserMatchingScore);
        }
    }

    private void deleteUserQuestionAnswers(User user) {
        for (UserQuestionExtendedAnswer userQuestionExtendedAnswer :
                userQuestionExtendedAnswerRepository.getAllUserQuestionExtendedAnswers(user)) {
            userQuestionExtendedAnswerRepository.delete(userQuestionExtendedAnswer);
        }
    }
}
