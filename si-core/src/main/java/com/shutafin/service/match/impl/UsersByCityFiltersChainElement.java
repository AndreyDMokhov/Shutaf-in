package com.shutafin.service.match.impl;

import com.shutafin.model.entities.User;
import com.shutafin.repository.common.FilterCityRepository;
import com.shutafin.service.match.UsersFiltersChain;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public class UsersByCityFiltersChainElement extends UsersFiltersChain {

    private FilterCityRepository filterCityRepository;

    public UsersByCityFiltersChainElement(User user, FilterCityRepository filterCityRepository){
        this.setUser(user);
        this.filterCityRepository = filterCityRepository;
    }

    @Override
    public List<User> doMatch(List<User> users) {
        List<User> matchResult = filterCityRepository.getAllMatchedUsers(getUser(), users);
        matchResult.remove(getUser());
        if (getNext() != null){
            return getNext().doMatch(matchResult);
        }
        return matchResult;
    }
}
