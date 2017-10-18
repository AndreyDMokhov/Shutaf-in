package com.shutafin.service.filter.filters;

import com.shutafin.model.entities.User;
import com.shutafin.repository.common.FilterAgeRangeRepository;
import com.shutafin.service.filter.UsersFiltersChain;

import java.util.List;

/**
 * Created by evgeny on 10/4/2017.
 */
public class UsersByAgeRangeFiltersChainElement extends UsersFiltersChain {

    private FilterAgeRangeRepository filterAgeRangeRepository;

    public UsersByAgeRangeFiltersChainElement(User user, FilterAgeRangeRepository filterAgeRangeRepository) {
        this.setUser(user);
        this.filterAgeRangeRepository = filterAgeRangeRepository;
    }

    @Override
    public List<User> doMatch(List<User> users) {
        List<User> matchResult = filterAgeRangeRepository.getAllMatchedUsers(getUser(), users);
        matchResult.remove(getUser());
        if (getNext() != null){
            return getNext().doMatch(matchResult);
        }
        return matchResult;
    }
}