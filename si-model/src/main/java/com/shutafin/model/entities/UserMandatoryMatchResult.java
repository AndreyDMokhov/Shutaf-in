package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;

/**
 * Created by evgeny on 8/10/2017.
 */
@Entity
@Table(name = "USER_MANDATORY_MATCH_RESULT")
public class UserMandatoryMatchResult extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(name = "USER_MATCH_EXPRESSION", nullable = false, length = 1024)
    private String userMatchExpression;

    @Column(name = "MATCH_REGEX", nullable = false, length = 1024)
    private String matchRegExp;

    public UserMandatoryMatchResult() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserMatchExpression() {
        return userMatchExpression;
    }

    public void setUserMatchExpression(String userMatchExpression) {
        this.userMatchExpression = userMatchExpression;
    }

    public String getMatchRegExp() {
        return matchRegExp;
    }

    public void setMatchRegExp(String matchRegExp) {
        this.matchRegExp = matchRegExp;
    }
}
