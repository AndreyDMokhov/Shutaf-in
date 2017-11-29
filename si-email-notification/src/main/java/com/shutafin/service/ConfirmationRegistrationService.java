package com.shutafin.service;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.entity.registration.ConfirmationRegistration;

public interface ConfirmationRegistrationService extends BaseConfirmationService<ConfirmationRegistration> {

    ConfirmationRegistration get(EmailNotificationWeb emailNotificationWeb);

}
