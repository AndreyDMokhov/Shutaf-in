package com.shutafin.service.confirmation;

import com.shutafin.model.entity.confirmation.reset_password.ConfirmationResetPassword;
import com.shutafin.model.web.email.EmailNotificationWeb;

public interface ConfirmationResetPasswordService extends BaseConfirmationService<ConfirmationResetPassword> {

    ConfirmationResetPassword get(EmailNotificationWeb emailNotificationWeb);

}
