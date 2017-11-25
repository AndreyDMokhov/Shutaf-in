package com.shutafin.service.confirmations;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.EmailConfirmation;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.service.EmailConfirmationService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BaseEmailSender implements EmailInterface {

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    @Autowired
    private EmailConfirmationService emailConfirmationService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    private String confirmationUrl;

    public BaseEmailSender(String confirmationUrl) {
        if (StringUtils.isEmpty(confirmationUrl)){
            log.warn("Link is blank or empty");
            throw new NullPointerException();
        }
        this.confirmationUrl = confirmationUrl;
    }

    public void send(EmailNotificationWeb emailNotificationWeb) {

        EmailConfirmation emailConfirmation = emailConfirmationService.get(emailNotificationWeb, null, null);
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, emailConfirmation, confirmationUrl);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);
    }
}
