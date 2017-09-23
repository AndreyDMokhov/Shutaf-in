package com.shutafin.service.match.impl;

import com.shutafin.model.entities.User;
import com.shutafin.repository.common.UserQuestionAnswerCityRepository;
import com.shutafin.service.match.UsersMatchChain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public class UsersMatchByCity extends UsersMatchChain {

    private UserQuestionAnswerCityRepository userQuestionAnswerCityRepository;

    public UsersMatchByCity(User user, UserQuestionAnswerCityRepository userQuestionAnswerCityRepository){
        this.setUser(user);
        this.userQuestionAnswerCityRepository = userQuestionAnswerCityRepository;
    }

    @Override
    public List<User> doMatch(List<User> users) {
        List<User> matchResult = innerJoinUserLists(users, userQuestionAnswerCityRepository.getAllMatchedUsers(getUser()));
        if (getNext() != null){
            return getNext().doMatch(matchResult);
        }
        return matchResult;
    }
}
