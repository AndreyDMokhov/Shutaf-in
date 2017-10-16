package com.shutafin.service.filter.filters;

import com.shutafin.model.entities.User;
import com.shutafin.repository.common.FilterGenderRepository;
import com.shutafin.service.filter.UsersFiltersChain;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public class UsersByGenderFiltersChainElement extends UsersFiltersChain {

    private FilterGenderRepository filterGenderRepository;

    public UsersByGenderFiltersChainElement(User user, FilterGenderRepository filterGenderRepository){
        this.setUser(user);
        this.filterGenderRepository = filterGenderRepository;
    }

    @Override
    public List<User> doMatch(List<User> users) {
        List<User> matchResult = filterGenderRepository.getAllMatchedUsers(getUser(), users);
        matchResult.remove(getUser());
        if (getNext() != null){
            return getNext().doMatch(matchResult);
        }
        return matchResult;
    }
}
