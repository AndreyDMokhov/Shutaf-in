package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;

/**
 * Created by evgeny on 6/20/2017.
 */
@Entity
@Table(name = "USER_LOGIN_LOG")
public class UserLoginLog extends AbstractEntity {
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(name = "IS_LOGIN_SUCCESS", nullable = false)
    private Boolean isLoginSuccess;

    public UserLoginLog() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getLoginSuccess() {
        return isLoginSuccess;
    }

    public void setLoginSuccess(Boolean loginSuccess) {
        isLoginSuccess = loginSuccess;
    }
}
