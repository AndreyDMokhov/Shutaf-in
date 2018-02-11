package com.shutafin.service.confirmation;

import com.shutafin.model.entity.confirmation.email_change.ConfirmationEmailChange;
import com.shutafin.model.web.email.EmailNotificationWeb;

public interface ConfirmationEmailChangeService extends BaseConfirmationService<ConfirmationEmailChange> {

    ConfirmationEmailChange get(EmailNotificationWeb emailNotificationWeb, String emailChange, ConfirmationEmailChange connectedConfirmation);

}
