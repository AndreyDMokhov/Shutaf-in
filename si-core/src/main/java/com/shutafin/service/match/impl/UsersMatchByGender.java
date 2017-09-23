package com.shutafin.service.match.impl;

import com.shutafin.model.entities.User;
import com.shutafin.repository.common.UserQuestionAnswerGenderRepository;
import com.shutafin.service.match.UsersMatchChain;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public class UsersMatchByGender extends UsersMatchChain {

    private UserQuestionAnswerGenderRepository userQuestionAnswerGenderRepository;

    public UsersMatchByGender(User user, UserQuestionAnswerGenderRepository userQuestionAnswerGenderRepository){
        this.setUser(user);
        this.userQuestionAnswerGenderRepository = userQuestionAnswerGenderRepository;
    }

    @Override
    public List<User> doMatch(List<User> users) {
        List<User> matchResult = innerJoinUserLists(users, userQuestionAnswerGenderRepository.getAllMatchedUsers(getUser()));
        if (getNext() != null){
            return getNext().doMatch(matchResult);
        }
        return matchResult;
    }
}
