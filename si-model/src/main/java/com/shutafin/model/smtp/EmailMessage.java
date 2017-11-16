package com.shutafin.model.smtp;

import com.shutafin.model.entities.User;

import java.util.Map;

import static org.apache.commons.lang3.Validate.*;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public class EmailMessage {

    private BaseTemplate mailTemplate;
    private String emailTo;
    private User user;
    private Map<String, byte[]> imageSources;


    public EmailMessage(String emailTo, BaseTemplate mailTemplate) {
        notNull(emailTo);
        notNull(mailTemplate);
        this.emailTo = emailTo;
        this.mailTemplate = mailTemplate;
    }

    public EmailMessage(String emailTo, BaseTemplate mailTemplate, Map<String, byte[]> imageSources) {
        notNull(emailTo);
        notNull(mailTemplate);
        notNull(imageSources);
        this.emailTo = emailTo;
        this.mailTemplate = mailTemplate;
        this.imageSources = imageSources;
    }

    public EmailMessage(User user, BaseTemplate mailTemplate) {
        notNull(user);
        notNull(mailTemplate);
        this.user = user;
        this.mailTemplate = mailTemplate;
    }


    public String getEmailTo() {
        if (user != null) {
            return user.getEmail();
        }
        return emailTo;
    }

    public BaseTemplate getMailTemplate() {
        return mailTemplate;
    }

    public User getUser() {
        return user;
    }

    public Map<String, byte[]> getImageSources() {
        return imageSources;
    }
}