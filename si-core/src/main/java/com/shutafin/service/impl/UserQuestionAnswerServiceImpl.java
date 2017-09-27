package com.shutafin.service.impl;

import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.repository.common.UserQuestionAnswerRepository;
import com.shutafin.service.UserQuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void updateUserQuestionAnswer(UserQuestionAnswer userQuestionAnswer) {
        userQuestionAnswerRepository.update(userQuestionAnswer);
    }
}
