package com.shutafin.service.filter;

import com.shutafin.model.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
@Getter
@Setter
public abstract class UsersFiltersChain {
    private User user;
    private UsersFiltersChain next;

    public void setNext(UsersFiltersChain next){
        this.next = next;
    }

    abstract public List<User> doMatch(List<User> users);

}
