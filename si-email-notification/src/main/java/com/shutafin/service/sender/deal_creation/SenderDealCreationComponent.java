package com.shutafin.service.sender.deal_creation;

import com.shutafin.model.entity.confirmation.deal_creation.ConfirmationDealCreation;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationDealWeb;
import com.shutafin.model.web.email.EmailUserLanguage;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.confirmation.ConfirmationDealCreationService;
import com.shutafin.service.sender.BaseEmailDealInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dealCreation")
public class SenderDealCreationComponent implements BaseEmailDealInterface {

    private static final String DEAL_CREATION_CONFIRMATION_URL = "/#/deal/creation/confirmation/";
    private static final String URL_PROFILE = "/#/profile/";

    @Autowired
    private ConfirmationDealCreationService confirmationDealCreationService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    public void send(EmailNotificationDealWeb emailNotificationDealWeb) {

        for (EmailUserLanguage emailUserLanguage : emailNotificationDealWeb.getEmailUserLanguage()) {
            ConfirmationDealCreation confirmation =
                    confirmationDealCreationService.get(emailNotificationDealWeb.getDealId());
            confirmationDealCreationService.save(confirmation);
            EmailMessage emailMessage = emailTemplateService.getEmailMessageDeal(
                    emailNotificationDealWeb,
                    emailUserLanguage,
                    confirmation.getConfirmationUUID(),
                    DEAL_CREATION_CONFIRMATION_URL,
                    URL_PROFILE);
            senderEmailMessageService.sendEmailMessage(emailNotificationDealWeb.getEmailReason(), emailMessage);
        }
    }

}
