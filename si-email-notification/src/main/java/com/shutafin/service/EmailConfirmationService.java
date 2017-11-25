package com.shutafin.service;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.EmailConfirmation;

public interface EmailConfirmationService {

    EmailConfirmation get(EmailNotificationWeb emailNotificationWeb, String newEmail, EmailConfirmation connectedId);

    EmailConfirmation save(EmailConfirmation emailConfirmation);

    EmailConfirmation getConfirmed(String link);

}
