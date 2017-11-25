package com.shutafin.service.impl;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.EmailConfirmation;
import com.shutafin.repository.EmailConfirmationRepository;
import com.shutafin.service.EmailConfirmationService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    private static final Integer LINK_HOURS_EXPIRATION = 24;

    private EmailConfirmationRepository emailConfirmationRepository;

    @Autowired
    public EmailConfirmationServiceImpl(EmailConfirmationRepository emailConfirmationRepository) {
        this.emailConfirmationRepository = emailConfirmationRepository;
    }

    @Override
    public EmailConfirmation get(EmailNotificationWeb emailNotificationWeb, String newEmail, EmailConfirmation connectedId) {
        return emailConfirmationRepository.save(EmailConfirmation.builder()
                .userId(emailNotificationWeb.getUserId())
                .confirmationUUID(UUID.randomUUID().toString())
                .isConfirmed(false)
                .expiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION))
                .newEmail(newEmail)
                .connectedEmailConfirmation(connectedId)
                .build());
    }

    public EmailConfirmation save(EmailConfirmation emailConfirmation){
        return emailConfirmationRepository.save(emailConfirmation);
    }

    @Override
    public EmailConfirmation getConfirmed(String link) {
        return emailConfirmationRepository.findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(link, new Date());
    }
}
