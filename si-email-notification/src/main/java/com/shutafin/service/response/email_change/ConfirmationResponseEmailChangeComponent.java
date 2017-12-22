package com.shutafin.service.response.email_change;

import com.shutafin.model.entity.email_change.ConfirmationEmailChange;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.email.response.EmailChangeResponse;
import com.shutafin.service.confirmation.ConfirmationEmailChangeService;
import com.shutafin.service.response.BaseConfirmationResponseInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("emailChangeConfirmationResponse")
@Slf4j
public class ConfirmationResponseEmailChangeComponent implements BaseConfirmationResponseInterface<EmailChangeResponse> {

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
