package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.AbstractEntity;

import javax.persistence.*;

/**
 * Created by evgeny on 7/10/2017.
 */
@Entity
@Table(name = "REGISTRATION_CONFIRMATION")
public class RegistrationConfirmation extends AbstractEntity {
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(name = "URL_LINK", nullable = false, length = 50, unique = true)
    private String urlLink;

    @Column(name = "IS_CONFIRMED", nullable = false)
    private Boolean isConfirmed;

    public RegistrationConfirmation() {
    }

    public RegistrationConfirmation(User user, String urlLink, Boolean isConfirmed) {
        this.user = user;
        this.urlLink = urlLink;
        this.isConfirmed = isConfirmed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }
}
