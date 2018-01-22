package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountEmailChangeRequest;
import com.shutafin.model.web.account.AccountEmailChangeValidationRequest;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.response.EmailChangeResponse;
import com.shutafin.model.web.user.GatewayEmailChangedResponse;
import com.shutafin.sender.account.EmailChangeControllerSender;
import com.shutafin.sender.email.EmailNotificationSenderControllerSender;
import com.shutafin.service.EmailChangeConfirmationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailChangeConfirmationServiceImpl implements EmailChangeConfirmationService {

    @Autowired
    private EmailNotificationSenderControllerSender emailSender;

    @Autowired
    private EmailChangeControllerSender emailChangeControllerSender;


    @Override
    public void emailChangeRequest(Long userId, AccountEmailChangeValidationRequest emailChangeConfirmationWeb) {
        emailChangeControllerSender.validateChangeEmailRequest(emailChangeConfirmationWeb, userId);
    }


    @Override
    public GatewayEmailChangedResponse emailChangeConfirmation(String link) {
        EmailChangeResponse emailChangeResponse = (EmailChangeResponse) emailSender.confirmLink(link, EmailReason.EMAIL_CHANGE);
        if (emailChangeResponse.getEmailChange() != null) {

            emailChangeControllerSender.changeEmail(emailChangeResponse.getUserId(),
                    new AccountEmailChangeRequest(emailChangeResponse.getEmailChange()));
        }

        return new GatewayEmailChangedResponse(emailChangeResponse.getEmailChange() != null);
    }

}
