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
import com.shutafin.model.web.user.EmailChangedResponse;
import com.shutafin.repository.account.EmailChangeConfirmationRepository;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class EmailChangeConfirmationServiceImpl implements EmailChangeConfirmationService {

    private PasswordService passwordService;
    private EnvironmentConfigurationService environmentConfigurationService;
    private EmailTemplateService emailTemplateService;
    private UserAccountRepository userAccountRepository;
    private EmailNotificationSenderService mailSenderService;
    private EmailChangeConfirmationRepository emailChangeConfirmationRepository;
    private UserRepository userRepository;
    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    public EmailChangeConfirmationServiceImpl(
            PasswordService passwordService,
            EnvironmentConfigurationService environmentConfigurationService,
            EmailTemplateService emailTemplateService,
            UserAccountRepository userAccountRepository,
            EmailNotificationSenderService mailSenderService,
            EmailChangeConfirmationRepository emailChangeConfirmationRepository,
            UserRepository userRepository,
            DiscoveryRoutingService discoveryRoutingService) {
        this.passwordService = passwordService;
        this.environmentConfigurationService = environmentConfigurationService;
        this.emailTemplateService = emailTemplateService;
        this.userAccountRepository = userAccountRepository;
        this.mailSenderService = mailSenderService;
        this.emailChangeConfirmationRepository = emailChangeConfirmationRepository;
        this.userRepository = userRepository;
        this.discoveryRoutingService = discoveryRoutingService;
    }

    @Override
    @Transactional
    public void emailChangeRequest(User user, EmailChangeConfirmationWeb emailChangeConfirmationWeb) {

        //todo MS-email
        if (!passwordService.isPasswordCorrect(user, emailChangeConfirmationWeb.getUserPassword())) {
            log.warn("Authentication exception:");
            log.warn("User password with ID {} is incorrect", user.getId());
            throw new AuthenticationException();
        }

        if (userRepository.findByEmail(emailChangeConfirmationWeb.getNewEmail()) != null) {
            log.warn("Email not unique validation exception:");
            log.warn("Such email already exists");
            throw new EmailNotUniqueValidationException("Such email already exists");
        }


        emailChangeConfirmationRepository.deleteAllByUser(user);

        UserAccount userAccount = userAccountRepository.findByUser(user);
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
        emailChangeConfirmationRepository.save(oldEmailObject);

        sendChangeEmailNotification(oldEmailObject, userAccount);
        sendChangeEmailNotification(newEmailObject, userAccount);
    }


    @Override
    @Transactional
    public EmailChangedResponse emailChangeConfirmation(String link) {

        //todo MS-email
        EmailChangeConfirmation emailChangeConfirmation = emailChangeConfirmationRepository.findByUrlLinkAndExpiresAtBefore(link, new Date());

        if (emailChangeConfirmation == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }

        emailChangeConfirmation.setIsConfirmed(true);

        updateUserEmail(
                emailChangeConfirmation.getUser(),
                getNewEmail(emailChangeConfirmation)
        );


        return emailChangeConfirmation.getConnectedId().getIsConfirmed() ?
                new EmailChangedResponse(true)
                :
                new EmailChangedResponse(false);
    }

    private void updateUserEmail(User user, String newEmail) {
        // TODO: MS-account EmailChangeController.emailChange()
        try {
            user.setEmail(newEmail);
//            TODO
            userRepository.save(user);
        } catch (ConstraintViolationException e) {
            log.warn("Email not unique validation exception:");
            log.warn("Such email {} already exists", newEmail);
            throw new EmailNotUniqueValidationException("Such email already exist");
        }
    }

    private String getNewEmail(EmailChangeConfirmation emailChangeConfirmation) {
        if (emailChangeConfirmation.getIsNewEmail()) {
            return emailChangeConfirmation.getUpdateEmailAddress();
        }
        return emailChangeConfirmation.getConnectedId().getUpdateEmailAddress();
    }

    private void sendChangeEmailNotification(EmailChangeConfirmation emailChangeConfirmation, UserAccount userAccount) {
        String link = environmentConfigurationService.getServerAddress() + "/#/settings/change-email/confirmation/" + emailChangeConfirmation.getUrlLink();
        EmailMessage emailMessage;
        if (emailChangeConfirmation.getIsNewEmail()) {
            emailMessage = emailTemplateService.getEmailMessage(
                    emailChangeConfirmation.getUpdateEmailAddress(),
                    EmailReason.CHANGE_EMAIL,
                    userAccount.getLanguage(),
                    link);

        } else {
            emailMessage = emailTemplateService.getEmailMessage(
                    emailChangeConfirmation.getUser(),
                    EmailReason.CHANGE_EMAIL,
                    userAccount.getLanguage(),
                    link);
        }

        //todo MS-email
        mailSenderService.sendEmail(emailMessage, EmailReason.CHANGE_EMAIL);

    }

    private EmailChangeConfirmation saveToBD(
            User user,
            boolean isNewEmail,
            String updateEmailAddress,
            EmailChangeConfirmation connectedId,
            Date expiresAt) {

        EmailChangeConfirmation emailChangeConfirmation = new EmailChangeConfirmation();
        emailChangeConfirmation.setUser(user);
        emailChangeConfirmation.setIsNewEmail(isNewEmail);
        emailChangeConfirmation.setUpdateEmailAddress(updateEmailAddress);
        emailChangeConfirmation.setUrlLink(UUID.randomUUID().toString());
        emailChangeConfirmation.setIsConfirmed(Boolean.FALSE);
        emailChangeConfirmation.setConnectedId(connectedId);
        emailChangeConfirmation.setExpiresAt(expiresAt);
        emailChangeConfirmationRepository.save(emailChangeConfirmation);
        return emailChangeConfirmation;
    }
}
