package com.shutafin.service;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationResetPassword;

public interface ConfirmationResetPasswordService extends BaseConfirmationService<ConfirmationResetPassword>{

    ConfirmationResetPassword get(EmailNotificationWeb emailNotificationWeb);

}
