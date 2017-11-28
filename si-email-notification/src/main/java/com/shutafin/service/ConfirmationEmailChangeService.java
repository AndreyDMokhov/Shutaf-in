package com.shutafin.service;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationEmailChange;

public interface ConfirmationEmailChangeService extends BaseConfirmationService<ConfirmationEmailChange> {

    ConfirmationEmailChange get(EmailNotificationWeb emailNotificationWeb, String emailChange, ConfirmationEmailChange connectedConfirmation);

}
