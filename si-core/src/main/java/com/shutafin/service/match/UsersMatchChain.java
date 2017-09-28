package com.shutafin.service.match;

import com.shutafin.model.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by evgeny on 9/23/2017.
 */
@Getter
@Setter
public abstract class UsersMatchChain {
    private User user;
    private UsersMatchChain next;

    public void setNext(UsersMatchChain next){
        this.next = next;
    }

    abstract public List<User> doMatch(List<User> users);

    public List<User> innerJoinUserLists(List<User> matchedUsersList, List<User> additionalUsersFilterList) {
        List<User> result = new ArrayList<>();
        Set<User> additionalUsersFilterSet = new HashSet<>(additionalUsersFilterList);
        for (User user : matchedUsersList) {
            if(additionalUsersFilterSet.contains(user)){
                result.add(user);
            }
        }
        result.remove(user);
        return result;
    }
}
