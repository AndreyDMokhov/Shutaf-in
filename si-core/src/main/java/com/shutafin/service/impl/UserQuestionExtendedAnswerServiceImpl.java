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
import com.shutafin.repository.matching.UserMatchingScoreRepository;
import com.shutafin.repository.matching.UserQuestionExtendedAnswerRepository;
import com.shutafin.service.AnswerSimilarityService;
import com.shutafin.service.UserQuestionExtendedAnswerService;
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
    public Map<QuestionExtended, List<UserQuestionExtendedAnswer>> getAllUserQuestionExtendedAnswers(User user) {
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
    public void addUserQuestionExtendedAnswers(List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList,
                                               User user) {
        deleteUserQuestionAnswers(user);
        userMatchingScoreRepository.deleteUserMatchingScores(user);
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

        saveMaxUserMatchingScore(user, maxScore);
    }

    private void saveMaxUserMatchingScore(User user, Integer maxScore) {
        MaxUserMatchingScore maxUserMatchingScore = maxUserMatchingScoreRepository.getUserMaxMatchingScore(user);
        if (maxUserMatchingScore == null) {
            maxUserMatchingScore = new MaxUserMatchingScore(user, maxScore);
            maxUserMatchingScoreRepository.save(maxUserMatchingScore);
        } else {
            maxUserMatchingScore.setScore(maxScore);
            maxUserMatchingScoreRepository.update(maxUserMatchingScore);
        }
    }

    @Override
    public List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(User user) {
        List<UserQuestionExtendedAnswersWeb> selectedAnswers = new ArrayList<>();
        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> allUserAnswers = getAllUserQuestionExtendedAnswers(user);
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
        List<QuestionExtended> questionsExtended = questionExtendedRepository.getAllQuestionsExtended();
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

    private void deleteUserQuestionAnswers(User user) {
        for (UserQuestionExtendedAnswer userQuestionExtendedAnswer :
                userQuestionExtendedAnswerRepository.getAllUserQuestionExtendedAnswers(user)) {
            userQuestionExtendedAnswerRepository.delete(userQuestionExtendedAnswer);
        }
    }
}
