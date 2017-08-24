package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.ResetPasswordConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.user.EmailWeb;
import com.shutafin.model.web.user.PasswordWeb;
import com.shutafin.repository.ResetPasswordConfirmationRepository;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private static final int LINK_HOURS_EXPIRATION = 24;
    private static final String RESET_PASSWORD_CONFIRMATION_URL = "/#/reset-password/confirmation/";


    private UserRepository userRepository;
    private ResetPasswordConfirmationRepository resetPasswordConfirmationRepository;
    private UserAccountRepository userAccountRepository;
    private EmailTemplateService emailTemplateService;
    private EmailNotificationSenderService mailSenderService;
    private EnvironmentConfigurationService environmentConfigurationService;
    private PasswordService passwordService;

    @Autowired
    public ResetPasswordServiceImpl(
            UserRepository userRepository,
            ResetPasswordConfirmationRepository resetPasswordConfirmationRepository,
            UserAccountRepository userAccountRepository,
            EmailTemplateService emailTemplateService,
            EmailNotificationSenderService mailSenderService,
            EnvironmentConfigurationService environmentConfigurationService,
            PasswordService passwordService) {
        this.userRepository = userRepository;
        this.resetPasswordConfirmationRepository = resetPasswordConfirmationRepository;
        this.userAccountRepository = userAccountRepository;
        this.emailTemplateService = emailTemplateService;
        this.mailSenderService = mailSenderService;
        this.environmentConfigurationService = environmentConfigurationService;
        this.passwordService = passwordService;
    }

    @Transactional
    @Override
    public void resetPasswordRequest(EmailWeb emailWeb) {
        User user = userRepository.findUserByEmail(emailWeb.getEmail());
        if (user != null) {
            ResetPasswordConfirmation resetPasswordConfirmation = saveResetPasswordConfirmation(user);
            Language userLanguage = userAccountRepository.findUserLanguage(user);
            sendMessage(user, userLanguage, resetPasswordConfirmation.getUrlLink());
        }
    }

    private ResetPasswordConfirmation saveResetPasswordConfirmation(User user) {
        ResetPasswordConfirmation resetPasswordConfirmation = new ResetPasswordConfirmation();
        resetPasswordConfirmation.setUser(user);
        resetPasswordConfirmation.setUrlLink(UUID.randomUUID().toString());
        resetPasswordConfirmation.setConfirmed(false);
        resetPasswordConfirmation.setExpiresAt(DateUtils.addHours(new Date(), (LINK_HOURS_EXPIRATION)));
        resetPasswordConfirmationRepository.save(resetPasswordConfirmation);
        return resetPasswordConfirmation;
    }

    private void sendMessage(User user, Language language, String uuid) {
        String link = createLink(uuid);
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(user, EmailReason.RESET_PASSWORD, language, link);
        mailSenderService.sendEmail(emailMessage, EmailReason.RESET_PASSWORD);
    }

    private String createLink(String uuid) {
        return environmentConfigurationService.getServerAddress() + RESET_PASSWORD_CONFIRMATION_URL + uuid;
    }

    @Transactional(readOnly = true)
    @Override
    public void resetPasswordValidation(String link) {
        if (resetPasswordConfirmationRepository.findValidUrlLink(link) == null) {
            throw new ResourceNotFoundException();
        }
    }

    @Transactional
    @Override
    public void passwordChange(PasswordWeb passwordWeb, String link) {
        ResetPasswordConfirmation resetPasswordConfirmation = resetPasswordConfirmationRepository.findValidUrlLink(link);
        if (resetPasswordConfirmation == null) {
            throw new ResourceNotFoundException();
        }
        resetPasswordConfirmation.setConfirmed(Boolean.TRUE);
        resetPasswordConfirmationRepository.update(resetPasswordConfirmation);

        passwordService.updateUserPasswordInDb(resetPasswordConfirmation.getUser(), passwordWeb.getNewPassword());
    }

}
