package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.types.*;

import javax.persistence.*;

/**
 * Created by evgeny on 6/20/2017.
 */
@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccount extends AbstractBaseEntity {
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(name = "ACCOUNT_STATUS_ID", nullable = false)
    @Convert(converter = AccountStatusConverter.class)
    private AccountStatus accountStatus;

    @Column(name = "ACCOUNT_TYPE_ID", nullable = false)
    @Convert(converter = AccountTypeConverter.class)
    private AccountType accountType;

    @Convert(converter = LanguageEnumConverter.class)
    @Column(name = "LANGUAGE_ID", nullable = false)
    private LanguageEnum language;

    @JoinColumn(name = "USER_IMAGE_ID")
    @OneToOne
    private UserImage userImage;

    public UserAccount() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }

    public UserImage getUserImage() {
        return userImage;
    }

    public void setUserImage(UserImage userImage) {
        this.userImage = userImage;
    }
}
