package com.shutafin.service.confirmation;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.entity.confirmation.registration.ConfirmationRegistration;

public interface ConfirmationRegistrationService extends BaseConfirmationService<ConfirmationRegistration> {

    ConfirmationRegistration get(EmailNotificationWeb emailNotificationWeb);

}
