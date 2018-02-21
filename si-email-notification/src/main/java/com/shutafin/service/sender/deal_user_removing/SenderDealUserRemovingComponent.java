package com.shutafin.service.sender.deal_user_removing;

import com.shutafin.model.entity.confirmation.deal_user_removing.ConfirmationDealUserRemoving;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationDealWeb;
import com.shutafin.model.web.email.EmailUserLanguage;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.confirmation.ConfirmationDealUserRemovingService;
import com.shutafin.service.sender.BaseEmailDealInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("dealUserRemoving")
public class SenderDealUserRemovingComponent implements BaseEmailDealInterface {

    private static final String DEAL_USER_REMOVING_CONFIRMATION_URL = "/#/deal/user/remove/confirmation/";
    private static final String URL_PROFILE = "/#/profile/";

    @Autowired
    private ConfirmationDealUserRemovingService confirmationDealUserRemovingService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    public void send(EmailNotificationDealWeb emailNotificationDealWeb) {

        String groupUUID = UUID.randomUUID().toString();
        for (EmailUserLanguage emailUserLanguage : emailNotificationDealWeb.getEmailUserLanguage()) {
            ConfirmationDealUserRemoving confirmation =
                    confirmationDealUserRemovingService.get(
                            emailNotificationDealWeb.getDealId(),
                            emailNotificationDealWeb.getUserOrigin().getUserId(),
                            emailNotificationDealWeb.getUserToChange().getUserId(),
                            groupUUID);
            confirmationDealUserRemovingService.save(confirmation);
            EmailMessage emailMessage = emailTemplateService.getEmailMessageDeal(
                    emailNotificationDealWeb,
                    emailUserLanguage,
                    confirmation.getConfirmationUUID(),
                    DEAL_USER_REMOVING_CONFIRMATION_URL,
                    URL_PROFILE);
            senderEmailMessageService.sendEmailMessage(emailNotificationDealWeb.getEmailReason(), emailMessage);
        }
    }
}
