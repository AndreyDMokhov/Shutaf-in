package com.shutafin.service.response.registration;

import com.shutafin.model.entity.confirmation.registration.ConfirmationRegistration;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.email.response.EmailRegistrationResponse;
import com.shutafin.service.confirmation.ConfirmationRegistrationService;
import com.shutafin.service.response.BaseConfirmationResponseInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("registrationConfirmationResponse")
@Slf4j
public class ConfirmationResponseRegistrationComponent implements BaseConfirmationResponseInterface<EmailRegistrationResponse> {

    @Autowired
    private ConfirmationRegistrationService confirmationRegistrationService;

    @Override
    public EmailRegistrationResponse getResponse(String link) {

        ConfirmationRegistration confirmationRegistration = confirmationRegistrationService.getConfirmed(link);

        confirmationRegistration.setIsConfirmed(true);
        confirmationRegistrationService.save(confirmationRegistration);

        EmailRegistrationResponse emailRegistrationResponse = new EmailRegistrationResponse();
        emailRegistrationResponse.setUserId(confirmationRegistration.getUserId());
        return emailRegistrationResponse;
    }

    @Override
    public void revertConfirmation(String link) {
        confirmationRegistrationService.revertConfirmation(link);
    }
}
