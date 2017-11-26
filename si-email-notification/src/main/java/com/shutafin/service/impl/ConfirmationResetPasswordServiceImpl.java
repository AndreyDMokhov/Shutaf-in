package com.shutafin.service.impl;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationResetPassword;
import com.shutafin.repository.ConfirmationResetPasswordRepository;
import com.shutafin.service.ConfirmationResetPasswordService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationResetPasswordServiceImpl implements ConfirmationResetPasswordService {

    private static final Integer LINK_HOURS_EXPIRATION = 24;

    private ConfirmationResetPasswordRepository confirmationResetPasswordRepository;

    @Autowired
    public ConfirmationResetPasswordServiceImpl(ConfirmationResetPasswordRepository confirmationResetPasswordRepository) {
        this.confirmationResetPasswordRepository = confirmationResetPasswordRepository;
    }

    @Override
    public ConfirmationResetPassword get(EmailNotificationWeb emailNotificationWeb, String newEmail, ConfirmationResetPassword connectedConfirmation) {
        return ConfirmationResetPassword.builder()
                        .userId(emailNotificationWeb.getUserId())
                        .confirmationUUID(UUID.randomUUID().toString())
                        .isConfirmed(false)
                        .expiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION))
                        .build();
    }

    @Override
    public ConfirmationResetPassword save(ConfirmationResetPassword confirmation) {
        return confirmationResetPasswordRepository.save(confirmation);
    }

    @Override
    public ConfirmationResetPassword getConfirmed(String link) {
        return confirmationResetPasswordRepository.findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(link, new Date());
    }

}
