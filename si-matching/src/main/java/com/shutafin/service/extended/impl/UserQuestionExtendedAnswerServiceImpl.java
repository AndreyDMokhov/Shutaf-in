package com.shutafin.service.extended.impl;


import com.shutafin.model.dto.UserQuestionExtendedAnswersWeb;
import com.shutafin.model.entities.extended.*;
import com.shutafin.repository.extended.*;
import com.shutafin.service.extended.AnswerSimilarityService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserQuestionExtendedAnswerServiceImpl implements UserQuestionExtendedAnswerService {

    private UserQuestionExtendedAnswerRepository userQuestionExtendedAnswerRepository;
    private QuestionExtendedRepository questionExtendedRepository;
    private AnswerExtendedRepository answerExtendedRepository;
    private QuestionImportanceRepository questionImportanceRepository;
    private MaxUserMatchingScoreRepository maxUserMatchingScoreRepository;
    private AnswerSimilarityService answerSimilarityService;
    private UserMatchingScoreRepository userMatchingScoreRepository;

    @Autowired
    public UserQuestionExtendedAnswerServiceImpl(UserQuestionExtendedAnswerRepository userQuestionExtendedAnswerRepository,
                                                 QuestionExtendedRepository questionExtendedRepository,
                                                 AnswerExtendedRepository answerExtendedRepository,
                                                 QuestionImportanceRepository questionImportanceRepository,
                                                 MaxUserMatchingScoreRepository maxUserMatchingScoreRepository,
                                                 AnswerSimilarityService answerSimilarityService,
                                                 UserMatchingScoreRepository userMatchingScoreRepository) {
        this.userQuestionExtendedAnswerRepository = userQuestionExtendedAnswerRepository;
        this.questionExtendedRepository = questionExtendedRepository;
        this.answerExtendedRepository = answerExtendedRepository;
        this.questionImportanceRepository = questionImportanceRepository;
        this.maxUserMatchingScoreRepository = maxUserMatchingScoreRepository;
        this.answerSimilarityService = answerSimilarityService;
        this.userMatchingScoreRepository = userMatchingScoreRepository;
    }

    @Override
    public Map<QuestionExtended, List<UserQuestionExtendedAnswer>> getAllUserQuestionExtendedAnswers(Long userId) {
        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userQuestionExtendedAnswerMap = new HashMap<>();
        for (UserQuestionExtendedAnswer userAnswer :
                userQuestionExtendedAnswerRepository.findAllByUserId(userId)) {

            if (userQuestionExtendedAnswerMap.get(userAnswer.getQuestion()) == null) {
                userQuestionExtendedAnswerMap.put(userAnswer.getQuestion(), new ArrayList<>());
            }
            userQuestionExtendedAnswerMap.get(userAnswer.getQuestion()).add(userAnswer);
        }
        return userQuestionExtendedAnswerMap;
    }


    @Override
    public void addUserQuestionExtendedAnswers(List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList,
                                               Long userId) {
        deleteUserQuestionAnswers(userId);
        userMatchingScoreRepository.deleteAllByUserOriginIdOrUserToMatchId(userId, userId);
        Integer maxScore = 0;
        for (UserQuestionExtendedAnswersWeb questionExtendedAnswersWeb : userQuestionExtendedAnswersWebList) {
            QuestionExtended question = questionExtendedRepository
                    .findOne(questionExtendedAnswersWeb.getQuestionId());
            QuestionImportance importance = questionImportanceRepository
                    .findOne(questionExtendedAnswersWeb.getQuestionImportanceId());
            UserQuestionExtendedAnswer userQuestionExtendedAnswer;

            AnswerExtended answer = null;
            for (Integer answerId : questionExtendedAnswersWeb.getAnswersId()) {
                answer = answerExtendedRepository.findOne(answerId);
                userQuestionExtendedAnswer = new UserQuestionExtendedAnswer(userId, question, answer, importance);
                userQuestionExtendedAnswerRepository.save(userQuestionExtendedAnswer);
            }
            maxScore += importance.getWeight() * answerSimilarityService.getAnswerSimilarity(answer, answer)
                    .getSimilarityScore();

        }

        saveMaxUserMatchingScore(userId, maxScore);
    }

    private void saveMaxUserMatchingScore(Long userId, Integer maxScore) {
        MaxUserMatchingScore maxUserMatchingScore = maxUserMatchingScoreRepository.findByUserId(userId);
        if (maxUserMatchingScore == null) {
            maxUserMatchingScore = new MaxUserMatchingScore(userId, maxScore);
            maxUserMatchingScoreRepository.save(maxUserMatchingScore);
        } else {
            maxUserMatchingScore.setScore(maxScore);
        }
    }

    @Override
    public List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(Long userId) {
        List<UserQuestionExtendedAnswersWeb> selectedAnswers = new ArrayList<>();
        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> allUserAnswers = getAllUserQuestionExtendedAnswers(userId);
        for (Map.Entry<QuestionExtended, List<UserQuestionExtendedAnswer>> question : allUserAnswers.entrySet()) {
            List<Integer> questionAnswers = new ArrayList<>();
            for (UserQuestionExtendedAnswer answer : question.getValue()) {
                questionAnswers.add(answer.getAnswer().getId());
            }
            selectedAnswers.add(new UserQuestionExtendedAnswersWeb(question.getKey().getId(),
                    question.getValue().get(0).getImportance().getId(), questionAnswers));
        }
        selectedAnswers.addAll(getNotAnsweredQuestions(allUserAnswers));
        return selectedAnswers;
    }

    private List<UserQuestionExtendedAnswersWeb> getNotAnsweredQuestions(Map<QuestionExtended,
            List<UserQuestionExtendedAnswer>> allUserAnswers) {
        List<UserQuestionExtendedAnswersWeb> notAnsweredQuestions = new ArrayList<>();
        List<QuestionExtended> questionsExtended = questionExtendedRepository.findAll();
        Set<Integer> answeredQuestions = allUserAnswers.keySet()
                .stream()
                .map(question -> question.getId())
                .collect(Collectors.toSet());
        for (QuestionExtended question : questionsExtended) {
            if (!answeredQuestions.contains(question.getId())) {
                notAnsweredQuestions.add(new UserQuestionExtendedAnswersWeb(question.getId(), null, null));
            }
        }
        return notAnsweredQuestions;
    }

    private void deleteUserQuestionAnswers(Long userId) {
        for (UserQuestionExtendedAnswer userQuestionExtendedAnswer :
                userQuestionExtendedAnswerRepository.findAllByUserId(userId)) {
            userQuestionExtendedAnswerRepository.delete(userQuestionExtendedAnswer);
        }
    }
}
