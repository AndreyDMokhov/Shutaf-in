package com.shutafin.service.response;

import com.shutafin.model.confirmations.EmailChangeResponse;
import com.shutafin.model.entity.ConfirmationEmailChange;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.service.ConfirmationEmailChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ConfirmationResponseEmailChange implements BaseConfirmationResponseInterface<EmailChangeResponse> {

    @Autowired
    private ConfirmationEmailChangeService confirmationEmailChangeService;

    @Override
    public EmailChangeResponse getResponse(String link) {

        ConfirmationEmailChange confirmationEmailChange = confirmationEmailChangeService.getConfirmed(link);
        if (confirmationEmailChange == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }

        confirmationEmailChange.setIsConfirmed(true);
        confirmationEmailChangeService.save(confirmationEmailChange);

        EmailChangeResponse emailChangeResponse = new EmailChangeResponse();
        emailChangeResponse.setUserId(confirmationEmailChange.getUserId());

        if (confirmationEmailChange.getConnectedConfirmationEmailChange() == null) {
            return emailChangeResponse;
        }

        ConfirmationEmailChange confirmationEmailChangeConnected = confirmationEmailChange.getConnectedConfirmationEmailChange();
        if (confirmationEmailChangeConnected.getIsConfirmed()) {
            emailChangeResponse.setEmailChange(
                    confirmationEmailChange.getEmailChange() == null ?
                            confirmationEmailChangeConnected.getEmailChange()
                            :
                            confirmationEmailChange.getEmailChange());
        }
        return emailChangeResponse;
    }
}
