package com.shutafin.model.smtp;

import com.shutafin.model.entities.User;

import static org.apache.commons.lang3.Validate.*;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public class EmailMessage {

    private User user;
    private BaseTemplate mailTemplate;

    public EmailMessage(User user, BaseTemplate mailTemplate) {
        notNull(user);
        notNull(mailTemplate);
        this.user = user;
        this.mailTemplate = mailTemplate;
    }

    public User getUser() {
        return user;
    }

    public BaseTemplate getMailTemplate() {
        return mailTemplate;
    }
}
