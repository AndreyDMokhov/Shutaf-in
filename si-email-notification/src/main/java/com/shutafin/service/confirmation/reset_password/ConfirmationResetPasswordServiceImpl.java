package com.shutafin.service.confirmation.reset_password;

import com.shutafin.model.entity.confirmation.reset_password.ConfirmationResetPassword;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.repository.reset_password.ConfirmationResetPasswordRepository;
import com.shutafin.service.confirmation.ConfirmationResetPasswordService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationResetPasswordServiceImpl implements ConfirmationResetPasswordService {

    private ConfirmationResetPasswordRepository confirmationResetPasswordRepository;

    @Autowired
    public ConfirmationResetPasswordServiceImpl(ConfirmationResetPasswordRepository confirmationResetPasswordRepository) {
        this.confirmationResetPasswordRepository = confirmationResetPasswordRepository;
    }

    @Override
    public ConfirmationResetPassword get(EmailNotificationWeb emailNotificationWeb) {
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

    @Override
    public void revertConfirmation(String link) {
        ConfirmationResetPassword confirmation = confirmationResetPasswordRepository.findByConfirmationUUID(link);
        if (confirmation != null) {
            confirmation.setIsConfirmed(false);
            confirmationResetPasswordRepository.save(confirmation);
        }
    }
}
