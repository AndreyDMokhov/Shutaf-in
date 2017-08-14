package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by evgeny on 8/10/2017.
 */
@Entity
@Table(name = "USER_MANDATORY_MATCH_RESULT")
public class UserMandatoryMatchResult extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @ManyToOne
    private Set<User> users;

    @Column(name = "USER_MATCH_EXPRESSION", nullable = false, length = 1024)
    private String userMatchExpression;

    public UserMandatoryMatchResult() {
    }

    public String getUserMatchExpression() {
        return userMatchExpression;
    }

    public void setUserMatchExpression(String userMatchExpression) {
        this.userMatchExpression = userMatchExpression;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
