package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.EmailChangeConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.user.EmailChangeConfirmationWeb;
import com.shutafin.repository.EmailChangeConfirmationRepository;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.service.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * Created by usera on 7/16/2017.
 */

@Service
@Transactional
public class EmailChangeConfirmationServiceImpl implements EmailChangeConfirmationService {

    @Autowired
    private PasswordServiceImpl passwordService;

    @Autowired
    private EnvironmentConfigurationService environmentConfigurationService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private EmailNotificationSenderService mailSenderService;

    @Autowired
    private EmailChangeConfirmationRepository emailChangeConfirmationRepository;

    @Override
    @Transactional
    public void emailChangeRequest (User user, EmailChangeConfirmationWeb emailChangeConfirmationWeb) {
        if (!passwordService.isPasswordCorrect(user, emailChangeConfirmationWeb.getUserPassword()))
            throw new AuthenticationException();
        UserAccount userAccount = userAccountRepository.getAccountUserByUserId(user);
        Date datePlus24 = DateUtils.addDays(new Date(),1);
        EmailChangeConfirmation oldEmailObject = saveToBD(user, false, null,
                                                                        false, null, datePlus24);

        EmailChangeConfirmation newEmailObject = saveToBD(user, true, emailChangeConfirmationWeb.getNewEmail(),
                                                                        false, oldEmailObject, datePlus24);
        oldEmailObject.setConnectedId(newEmailObject.getConnectedId());
        emailChangeConfirmationRepository.update(oldEmailObject);

        sendChangeConfirmationEmail(oldEmailObject, userAccount);
        sendChangeConfirmationEmail(newEmailObject, userAccount);

    }

    private void sendChangeConfirmationEmail(EmailChangeConfirmation emailChangeConfirmation, UserAccount userAccount) {
        String link = environmentConfigurationService.getServerAddress() + "/#/account/email-change/" + emailChangeConfirmation.getUrlLink();
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailChangeConfirmation.getUser(), EmailReason.CHANGE_EMAIL, userAccount.getLanguage(), link);
        mailSenderService.sendEmail(emailMessage, EmailReason.CHANGE_EMAIL);
    }

    private EmailChangeConfirmation saveToBD(User user, boolean isNewEmail, String updateEmailAddress,
                                             boolean isConfirmed, EmailChangeConfirmation connectedId, Date expiresAt ) {
        EmailChangeConfirmation emailChangeConfirmation = new EmailChangeConfirmation();
        emailChangeConfirmation.setUser(user);
        emailChangeConfirmation.setNewEmail(isNewEmail);
        emailChangeConfirmation.setUpdateEmailAddress(updateEmailAddress);
        emailChangeConfirmation.setUrlLink(UUID.randomUUID().toString());
        emailChangeConfirmation.setConfirmed(isConfirmed);
        emailChangeConfirmation.setConnectedId(connectedId);
        emailChangeConfirmation.setExpiresAt(expiresAt);
        emailChangeConfirmationRepository.save(emailChangeConfirmation);
        return emailChangeConfirmation;
    }

    public void emailChangeConfirmation (User user, EmailChangeConfirmationWeb EmailChangeConfirmationWeb) {


    }
}
