package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;

/**
 * Created by evgeny on 6/19/2017.
 */
@Entity
@Table(name = "USER_CREDENTIALS")
public class UserCredentials extends AbstractEntity {
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(name = "PASSWORD_HASH", nullable = false)
    @Lob
    private String passwordHash;

    @Column(name = "PASSWORD_SALT", nullable = false)
    @Lob
    private String passwordSalt;

    public UserCredentials() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
}
