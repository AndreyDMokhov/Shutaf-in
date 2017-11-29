package com.shutafin.service;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.entity.email_change.ConfirmationEmailChange;

public interface ConfirmationEmailChangeService extends BaseConfirmationService<ConfirmationEmailChange> {

    ConfirmationEmailChange get(EmailNotificationWeb emailNotificationWeb, String emailChange, ConfirmationEmailChange connectedConfirmation);

}
