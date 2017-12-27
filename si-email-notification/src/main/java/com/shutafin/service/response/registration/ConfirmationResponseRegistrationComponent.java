package com.shutafin.service.response.registration;

import com.shutafin.model.entity.registration.ConfirmationRegistration;
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
        if (confirmationRegistration == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }

        confirmationRegistration.setIsConfirmed(true);
        confirmationRegistrationService.save(confirmationRegistration);

        EmailRegistrationResponse emailRegistrationResponse = new EmailRegistrationResponse();
        emailRegistrationResponse.setUserId(confirmationRegistration.getUserId());
        return emailRegistrationResponse;
    }

}
