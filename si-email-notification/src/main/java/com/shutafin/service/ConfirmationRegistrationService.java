package com.shutafin.service;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationRegistration;

public interface ConfirmationRegistrationService extends BaseConfirmationService<ConfirmationRegistration> {

    ConfirmationRegistration get(EmailNotificationWeb emailNotificationWeb);

}
