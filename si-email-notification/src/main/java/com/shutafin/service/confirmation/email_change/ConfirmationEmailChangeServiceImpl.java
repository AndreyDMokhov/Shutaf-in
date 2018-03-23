package com.shutafin.service.confirmation.email_change;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.entity.confirmation.email_change.ConfirmationEmailChange;
import com.shutafin.repository.email_change.ConfirmationEmailChangeRepository;
import com.shutafin.service.confirmation.ConfirmationEmailChangeService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationEmailChangeServiceImpl implements ConfirmationEmailChangeService {

    private ConfirmationEmailChangeRepository confirmationEmailChangeRepository;

    @Autowired
    public ConfirmationEmailChangeServiceImpl(ConfirmationEmailChangeRepository confirmationEmailChangeRepository) {
        this.confirmationEmailChangeRepository = confirmationEmailChangeRepository;
    }

    @Override
    public ConfirmationEmailChange get(EmailNotificationWeb emailNotificationWeb, String emailChange, ConfirmationEmailChange connectedId) {
        return ConfirmationEmailChange.builder()
                .userId(emailNotificationWeb.getUserId())
                .confirmationUUID(UUID.randomUUID().toString())
                .isConfirmed(false)
                .expiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION))
                .emailChange(emailChange)
                .connectedConfirmationEmailChange(connectedId)
                .build();
    }

    public ConfirmationEmailChange save(ConfirmationEmailChange confirmationEmailChange) {
        return confirmationEmailChangeRepository.save(confirmationEmailChange);
    }

    @Override
    public ConfirmationEmailChange getConfirmed(String link) {
        return confirmationEmailChangeRepository.findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(link, new Date());
    }

    @Override
    public void revertConfirmation(String link) {
        ConfirmationEmailChange confirmation = confirmationEmailChangeRepository.findByConfirmationUUID(link);
        if (confirmation != null) {
            confirmation.setIsConfirmed(false);
            confirmationEmailChangeRepository.save(confirmation);
        }

    }
}
