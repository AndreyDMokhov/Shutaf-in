package com.shutafin.service.impl;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationRegistration;
import com.shutafin.repository.ConfirmationRegistrationRepository;
import com.shutafin.service.ConfirmationRegistrationService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationRegistrationServiceImpl implements ConfirmationRegistrationService {

    private ConfirmationRegistrationRepository confirmationRegistrationRepository;

    @Autowired
    public ConfirmationRegistrationServiceImpl(ConfirmationRegistrationRepository confirmationRegistrationRepository) {
        this.confirmationRegistrationRepository = confirmationRegistrationRepository;
    }

    @Override
    public ConfirmationRegistration get(EmailNotificationWeb emailNotificationWeb) {
        return ConfirmationRegistration.builder()
                        .userId(emailNotificationWeb.getUserId())
                        .confirmationUUID(UUID.randomUUID().toString())
                        .isConfirmed(false)
                        .expiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION))
                        .build();
    }

    @Override
    public ConfirmationRegistration save(ConfirmationRegistration confirmation) {
        return confirmationRegistrationRepository.save(confirmation);
    }

    @Override
    public ConfirmationRegistration getConfirmed(String link) {
        return confirmationRegistrationRepository.findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(link, new Date());
    }
}
