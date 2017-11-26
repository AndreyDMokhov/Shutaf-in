package com.shutafin.service.impl;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationNewEmail;
import com.shutafin.repository.ConfirmationNewEmailRepository;
import com.shutafin.service.ConfirmationNewEmailService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationNewEmailServiceImpl implements ConfirmationNewEmailService {

    private static final Integer LINK_HOURS_EXPIRATION = 24;

    private ConfirmationNewEmailRepository confirmationNewEmailRepository;

    @Autowired
    public ConfirmationNewEmailServiceImpl(ConfirmationNewEmailRepository confirmationNewEmailRepository) {
        this.confirmationNewEmailRepository = confirmationNewEmailRepository;
    }

    @Override
    public ConfirmationNewEmail get(EmailNotificationWeb emailNotificationWeb, String newEmail, ConfirmationNewEmail connectedId) {
        return confirmationNewEmailRepository.save(
                ConfirmationNewEmail.builder()
                        .userId(emailNotificationWeb.getUserId())
                        .confirmationUUID(UUID.randomUUID().toString())
                        .isConfirmed(false)
                        .expiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION))
                        .newEmail(newEmail)
                        .connectedConfirmationNewEmail(connectedId)
                        .build());
    }

    public ConfirmationNewEmail save(ConfirmationNewEmail confirmationNewEmail) {
        return confirmationNewEmailRepository.save(confirmationNewEmail);
    }

    @Override
    public ConfirmationNewEmail getConfirmed(String link) {
        return confirmationNewEmailRepository.findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(link, new Date());
    }
}
