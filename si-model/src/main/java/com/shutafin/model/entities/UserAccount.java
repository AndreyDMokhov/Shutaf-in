package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by evgeny on 6/20/2017.
 */
@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccount extends AbstractBaseEntity {
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @JoinColumn(name = "ACCOUNT_STATUS_ID", nullable = false)
    @OneToOne
    private IAccountStatus iAccountStatus;

    @JoinColumn(name = "ACCOUNT_TYPE_ID", nullable = false)
    @OneToOne
    private IAccountType iAccountType;

    @JoinColumn(name = "LANGUAGE_ID", nullable = false)
    @OneToOne
    private ILanguage iLanguage;

    public UserAccount() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IAccountStatus getiAccountStatus() {
        return iAccountStatus;
    }

    public void setiAccountStatus(IAccountStatus iAccountStatus) {
        this.iAccountStatus = iAccountStatus;
    }

    public IAccountType getiAccountType() {
        return iAccountType;
    }

    public void setiAccountType(IAccountType iAccountType) {
        this.iAccountType = iAccountType;
    }

    public ILanguage getiLanguage() {
        return iLanguage;
    }

    public void setiLanguage(ILanguage iLanguage) {
        this.iLanguage = iLanguage;
    }
}
