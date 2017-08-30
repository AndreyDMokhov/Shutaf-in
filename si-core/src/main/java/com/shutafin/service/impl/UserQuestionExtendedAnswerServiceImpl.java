package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.infrastructure.QuestionImportance;
import com.shutafin.model.entities.matching.UserQuestionExtendedAnswer;
import com.shutafin.model.web.UserQuestionExtendedAnswersWeb;
import com.shutafin.repository.initialization.locale.AnswerExtendedRepository;
import com.shutafin.repository.initialization.locale.QuestionExtendedRepository;
import com.shutafin.repository.initialization.locale.QuestionImportanceRepository;
import com.shutafin.repository.matching.UserQuestionExtendedAnswerRepository;
import com.shutafin.service.QuestionExtendedService;
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

    @Autowired
    private UserQuestionExtendedAnswerRepository userQuestionExtendedAnswerRepository;

    @Autowired
    private QuestionExtendedRepository questionExtendedRepository;

    @Autowired
    private AnswerExtendedRepository answerExtendedRepository;

    @Autowired
    private QuestionImportanceRepository questionImportanceRepository;

    @Override
    public void addUserQuestionAnswer(UserQuestionExtendedAnswer userQuestionExtendedAnswer) {
        List<UserQuestionExtendedAnswer> oldAnswers =
                userQuestionExtendedAnswerRepository.getUserQuestionExtendedAnswer(
                        userQuestionExtendedAnswer.getUser(), userQuestionExtendedAnswer.getQuestion());
        if (oldAnswers == null) {
            userQuestionExtendedAnswerRepository.save(userQuestionExtendedAnswer);
        } else {
            updateUserQuestionAnswer(userQuestionExtendedAnswer);
        }
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
    public void updateUserQuestionAnswer(UserQuestionExtendedAnswer userQuestionExtendedAnswer) {
        List<UserQuestionExtendedAnswer> oldAnswers =
                userQuestionExtendedAnswerRepository.getUserQuestionExtendedAnswer(
                        userQuestionExtendedAnswer.getUser(), userQuestionExtendedAnswer.getQuestion());
        for (UserQuestionExtendedAnswer answer : oldAnswers) {
            userQuestionExtendedAnswerRepository.delete(answer);
        }
        userQuestionExtendedAnswerRepository.update(userQuestionExtendedAnswer);
    }

    @Override
    public void addUserQuestionAnswersWeb(List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList, User user) {
        for (UserQuestionExtendedAnswersWeb questionExtendedAnswersWeb : userQuestionExtendedAnswersWebList) {
            QuestionExtended question = questionExtendedRepository
                    .findById(questionExtendedAnswersWeb.getQuestionId());
            AnswerExtended answer = answerExtendedRepository
                    .findById(questionExtendedAnswersWeb.getAnswersId().get(0));
            QuestionImportance importance = questionImportanceRepository
                    .findById(questionExtendedAnswersWeb.getQuestionImportanceId());
            UserQuestionExtendedAnswer userQuestionExtendedAnswer =
                    new UserQuestionExtendedAnswer(user, question, answer, importance);
            userQuestionExtendedAnswerRepository.save(userQuestionExtendedAnswer);
        }
    }
}
