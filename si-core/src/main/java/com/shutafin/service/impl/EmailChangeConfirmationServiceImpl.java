package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.entities.EmailChangeConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.user.EmailChangeConfirmationWeb;
import com.shutafin.model.web.user.EmailChangedWeb;
import com.shutafin.repository.EmailChangeConfirmationRepository;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserRepository;
import com.shutafin.service.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
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

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public void emailChangeRequest(User user, EmailChangeConfirmationWeb emailChangeConfirmationWeb) {
        if (!passwordService.isPasswordCorrect(user, emailChangeConfirmationWeb.getUserPassword())) {
            throw new AuthenticationException();
        }

        if (userRepository.isEmailExists(emailChangeConfirmationWeb.getNewEmail())) {
            throw new EmailNotUniqueValidationException("Such email already exists");
        }

        deleteAllCurrentEmailChangeRequests(user);

        UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);
        Date expirationTime = DateUtils.addDays(new Date(), 1);
        EmailChangeConfirmation oldEmailObject = saveToBD(
                                                        user,
                                                        false,
                                                        null,
                                                        null,
                                                        expirationTime);

        EmailChangeConfirmation newEmailObject = saveToBD(
                                                        user,
                                                        true,
                                                        emailChangeConfirmationWeb.getNewEmail(),
                                                        oldEmailObject,
                                                        expirationTime);

        oldEmailObject.setConnectedId(newEmailObject);
        emailChangeConfirmationRepository.update(oldEmailObject);

        sendChangeEmailNotification(oldEmailObject, userAccount);
        sendChangeEmailNotification(newEmailObject, userAccount);
    }

    private void deleteAllCurrentEmailChangeRequests(User user) {
        emailChangeConfirmationRepository.deleteAllCurrentEmailChangeRequests(user);
    }

    @Override
    @Transactional
    public EmailChangedWeb emailChangeConfirmation(String link) {
        EmailChangeConfirmation emailChangeConfirmation = emailChangeConfirmationRepository.getEmailChangeConfirmationByUrlLink(link, new Date());

        if (emailChangeConfirmation == null) {
            throw new ResourceNotFoundException();
        }

        emailChangeConfirmation.setConfirmed(true);

        updateUserEmail(
                emailChangeConfirmation.getUser(),
                getNewEmail(emailChangeConfirmation)
        );



        return (emailChangeConfirmation.getConnectedId().isConfirmed()) ?
                new EmailChangedWeb(true)
                :
                new EmailChangedWeb(false);
    }

    private void updateUserEmail(User user, String newEmail) {
        try {
            user.setEmail(newEmail);
            userRepository.update(user);
        } catch (ConstraintViolationException e) {
            throw new EmailNotUniqueValidationException("Such email already exist");
        }
    }

    private String getNewEmail(EmailChangeConfirmation emailChangeConfirmation) {
        if (emailChangeConfirmation.isNewEmail()) {
            return emailChangeConfirmation.getUpdateEmailAddress();
        }
        return emailChangeConfirmation.getConnectedId().getUpdateEmailAddress();
    }

    private void sendChangeEmailNotification(EmailChangeConfirmation emailChangeConfirmation, UserAccount userAccount) {
        String link = environmentConfigurationService.getServerAddress() + "/#/account/email-change/" + emailChangeConfirmation.getUrlLink();
        if (emailChangeConfirmation.isNewEmail()) {
            EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailChangeConfirmation.getUpdateEmailAddress(), EmailReason.CHANGE_EMAIL, userAccount.getLanguage(), link);
            mailSenderService.sendEmail(emailMessage, EmailReason.CHANGE_EMAIL);
        } else {
            EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailChangeConfirmation.getUser(), EmailReason.CHANGE_EMAIL, userAccount.getLanguage(), link);
            mailSenderService.sendEmail(emailMessage, EmailReason.CHANGE_EMAIL);
        }

    }

    private EmailChangeConfirmation saveToBD(
                                        User user,
                                        boolean isNewEmail,
                                        String updateEmailAddress,
                                        EmailChangeConfirmation connectedId,
                                        Date expiresAt) {

        EmailChangeConfirmation emailChangeConfirmation = new EmailChangeConfirmation();
        emailChangeConfirmation.setUser(user);
        emailChangeConfirmation.setNewEmail(isNewEmail);
        emailChangeConfirmation.setUpdateEmailAddress(updateEmailAddress);
        emailChangeConfirmation.setUrlLink(UUID.randomUUID().toString());
        emailChangeConfirmation.setConfirmed(Boolean.FALSE);
        emailChangeConfirmation.setConnectedId(connectedId);
        emailChangeConfirmation.setExpiresAt(expiresAt);
        emailChangeConfirmationRepository.save(emailChangeConfirmation);
        return emailChangeConfirmation;
    }
}
