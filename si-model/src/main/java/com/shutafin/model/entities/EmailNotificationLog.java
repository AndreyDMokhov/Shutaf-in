package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.entities.types.EmailReasonConverter;

import javax.persistence.*;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@Entity
@Table(name = "EMAIL_NOTIFICATION_LOG")
public class EmailNotificationLog extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = true)
    @ManyToOne
    private User user;

    @Column(name = "EMAIL_TO", nullable = false, length = 100)
    private String emailTo;

    @Column(name = "IS_SEND_FAILED")
    private Boolean isSendFailed;

    @Column(name = "EMAIL_CONTENT", nullable = false)
    @Lob
    private String emailContent;

    @Column(name = "EMAIL_REASON_ID", nullable = false)
    @Convert(converter = EmailReasonConverter.class)
    private EmailReason emailReason;

    public EmailNotificationLog() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public Boolean getSendFailed() {
        return isSendFailed;
    }

    public void setSendFailed(Boolean sendFailed) {
        isSendFailed = sendFailed;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public EmailReason getEmailReason() {
        return emailReason;
    }

    public void setEmailReason(EmailReason emailReason) {
        this.emailReason = emailReason;
    }
}
