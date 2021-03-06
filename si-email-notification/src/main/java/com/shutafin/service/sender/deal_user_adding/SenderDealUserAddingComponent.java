package com.shutafin.service.sender.deal_user_adding;

import com.shutafin.model.entity.confirmation.deal_user_adding.ConfirmationDealUserAdding;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationDealWeb;
import com.shutafin.model.web.email.EmailUserLanguage;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.confirmation.ConfirmationDealUserAddingService;
import com.shutafin.service.sender.BaseEmailDealInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("dealUserAdding")
public class SenderDealUserAddingComponent implements BaseEmailDealInterface {

    private static final String DEAL_USER_ADDING_CONFIRMATION_URL = "/#/deal/confirmation/user/add/";
    private static final String URL_PROFILE = "/#/profile/";

    @Autowired
    private ConfirmationDealUserAddingService confirmationDealUserAddingService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    public void send(EmailNotificationDealWeb emailNotificationDealWeb) {

        String groupUUID = UUID.randomUUID().toString();
        for (EmailUserLanguage emailUserLanguage : emailNotificationDealWeb.getEmailUserLanguage()) {
            if (emailUserLanguage.getUserId().equals(emailNotificationDealWeb.getUserOrigin().getUserId())){
                continue;
            }
            senderEmailMessageService.sendEmailMessage(emailNotificationDealWeb.getEmailReason(),
                    getEmailMessage(emailNotificationDealWeb, groupUUID, emailUserLanguage));
        }
    }

    private EmailMessage getEmailMessage(EmailNotificationDealWeb emailNotificationDealWeb, String groupUUID,
                                         EmailUserLanguage emailUserLanguage) {
        ConfirmationDealUserAdding confirmation =
                confirmationDealUserAddingService.get(
                        emailNotificationDealWeb.getDealId(),
                        emailNotificationDealWeb.getUserOrigin().getUserId(),
                        emailNotificationDealWeb.getUserToChange().getUserId(),
                        groupUUID);
        confirmationDealUserAddingService.save(confirmation);

        return emailTemplateService.getEmailMessageDeal(
                emailNotificationDealWeb,
                emailUserLanguage,
                confirmation.getConfirmationUUID(),
                DEAL_USER_ADDING_CONFIRMATION_URL,
                URL_PROFILE);
    }


}
