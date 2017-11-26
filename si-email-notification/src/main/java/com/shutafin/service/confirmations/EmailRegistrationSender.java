package com.shutafin.service.confirmations;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationRegistration;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.service.ConfirmationRegistrationService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class EmailRegistrationSender implements EmailInterface {

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    @Autowired
    private ConfirmationRegistrationService confirmationRegistrationService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    private String confirmationUrl;

    public EmailRegistrationSender(String confirmationUrl) {
        if (StringUtils.isEmpty(confirmationUrl)) {
            log.warn("Link is blank or empty");
            throw new NullPointerException();
        }
        this.confirmationUrl = confirmationUrl;
    }

    public void send(EmailNotificationWeb emailNotificationWeb) {

        ConfirmationRegistration confirmationRegistration = confirmationRegistrationService.get(emailNotificationWeb, null, null);
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, confirmationRegistration.getConfirmationUUID(), confirmationUrl);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);
    }
}
