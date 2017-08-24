package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESET_PASSWORD_CONFIRMATION")
public class ResetPasswordConfirmation extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @Column(name = "URL_LINK", nullable = false, unique = true)
    private String urlLink;

    @Column(name = "IS_CONFIRMED", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "EXPIRES_AT", nullable = false)
    private Date expiresAt;

    public ResetPasswordConfirmation() {
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

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
