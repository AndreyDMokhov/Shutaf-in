package com.shutafin.service;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.entity.reset_password.ConfirmationResetPassword;

public interface ConfirmationResetPasswordService extends BaseConfirmationService<ConfirmationResetPassword>{

    ConfirmationResetPassword get(EmailNotificationWeb emailNotificationWeb);

}
