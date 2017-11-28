package com.shutafin.service.filter.filters;

import com.shutafin.model.entities.User;
import com.shutafin.repository.common.FilterCityRepository;
import com.shutafin.service.filter.UsersFiltersChain;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
@Deprecated
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
