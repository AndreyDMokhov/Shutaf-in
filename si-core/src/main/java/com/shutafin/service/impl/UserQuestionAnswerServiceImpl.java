package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.repository.common.UserQuestionAnswerRepository;
import com.shutafin.service.UserQuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserQuestionAnswerServiceImpl implements UserQuestionAnswerService {

    @Autowired
    private UserQuestionAnswerRepository userQuestionAnswerRepository;

    @Override
    public void addUserQuestionAnswer(UserQuestionAnswer userQuestionAnswer) {
        userQuestionAnswerRepository.save(userQuestionAnswer);

    }

    @Override
    public Map<Question, UserQuestionAnswer> getAllUserQuestionAnswers(User user) {
        Map<Question, UserQuestionAnswer> userQuestionAnswerMap = new HashMap<>();
        for (UserQuestionAnswer userAnswer : userQuestionAnswerRepository.getAllUserQuestionAnswers(user)) {
            userQuestionAnswerMap.put(userAnswer.getQuestion(), userAnswer);
        }
        return userQuestionAnswerMap;
    }

    @Override
    public void updateUserQuestionAnswer(UserQuestionAnswer userQuestionAnswer) {
        userQuestionAnswerRepository.update(userQuestionAnswer);
    }
}
