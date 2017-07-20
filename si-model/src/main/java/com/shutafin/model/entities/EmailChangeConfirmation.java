package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EMAIL_CHANGE_CONFIRMATION")
public class EmailChangeConfirmation extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @Column(name = "IS_NEW_EMAIL", nullable = false)
    private Boolean isNewEmail;

    @Column(name = "UPDATE_EMAIL_ADDRESS", nullable = true)
    private String updateEmailAddress;

    @Column(name = "URL_LINK", nullable = false, unique = true)
    private String urlLink;

    @Column(name = "IS_CONFIRMED", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "EXPIRES_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    @JoinColumn(name = "CONNECTED_ID", nullable = true)
    @OneToOne
    private EmailChangeConfirmation connectedId;

    public EmailChangeConfirmation() {
    }

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Boolean getNewEmail() {return isNewEmail;}

    public void setNewEmail(Boolean newEmail) {isNewEmail = newEmail;}

    public String getUpdateEmailAddress() {return updateEmailAddress;}

    public void setUpdateEmailAddress(String updateEmailAddress) {this.updateEmailAddress = updateEmailAddress;}

    public String getUrlLink() {return urlLink;}

    public void setUrlLink(String urlLink) {this.urlLink = urlLink;}

    public Boolean getConfirmed() {return isConfirmed;}

    public void setConfirmed(Boolean confirmed) {isConfirmed = confirmed;}

    public EmailChangeConfirmation getConnectedId() {return connectedId;}

    public void setConnectedId(EmailChangeConfirmation connectedId) {this.connectedId = connectedId;}

    public Date getExpiresAt() {return expiresAt;}

    public void setExpiresAt(Date expiresAt) {this.expiresAt = expiresAt;}

}
