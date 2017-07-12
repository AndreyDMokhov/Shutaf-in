package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.ResetPasswordConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.user.EmailWeb;
import com.shutafin.model.web.user.PasswordWeb;
import com.shutafin.repository.ResetPasswordConfirmationRepository;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserCredentialsRepository;
import com.shutafin.repository.UserRepository;
import com.shutafin.service.EmailNotificationSenderService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.EnvironmentConfigurationService;
import com.shutafin.service.ResetPasswordService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private static final Boolean IS_FALSE = false;
    private static final int HOURS_EXPIRATION = 24;
    private static final String LINK = "/api/reset-password/confirmation/";
    private static final String HTTP = "http://";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordConfirmationRepository resetPasswordConfirmationRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private EmailNotificationSenderService mailSenderService;

    @Autowired
    private EnvironmentConfigurationService environmentConfigurationService;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;


    @Transactional
    @Override
    public void resetPasswordRequest(EmailWeb emailWeb) {
        User user = userRepository.findUserByEmail(emailWeb.getEmail());
        if (user != null){
            String uuid = createResetPasswordConfirmation(user);
            UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);
            sendMessage(user, userAccount.getLanguage(), uuid);
        }
    }

    private void sendMessage(User user, Language language, String uuid) {
        String link = createLink(uuid);
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(user, EmailReason.RESET_PASSWORD, language, link);
        mailSenderService.sendEmail(emailMessage, EmailReason.RESET_PASSWORD);
    }

    private String createLink(String uuid) {
        String link;
        try {
            link = HTTP + environmentConfigurationService.getServerAddress() + LINK + uuid;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unexpected error occurred");
        }
        return link;
    }

    private String createResetPasswordConfirmation(User user){
        ResetPasswordConfirmation resetPswdConfirm = new ResetPasswordConfirmation();
        resetPswdConfirm.setUser(user);
        resetPswdConfirm.setUrlLink(UUID.randomUUID().toString());
        resetPswdConfirm.setConfirmed(IS_FALSE);
        resetPswdConfirm.setExpiresAt(DateUtils.addHours(new Date(), (HOURS_EXPIRATION)));
        resetPasswordConfirmationRepository.save(resetPswdConfirm);
        return resetPswdConfirm.getUrlLink();
    }

    @Override
    public void resetPasswordValidation(String link) {
        if (resetPasswordConfirmationRepository.findValidUrlLink(link) == null){
            throw new ResourceNotFoundException();
        }
    }

    @Transactional
    @Override
    public void passwordChange(PasswordWeb passwordWeb, String link) {
        ResetPasswordConfirmation resetPasswordConfirmation = resetPasswordConfirmationRepository.findValidUrlLink(link);
        if (resetPasswordConfirmation == null){
            throw new ResourceNotFoundException();
        }
        UserCredentials userCredentials = userCredentialsRepository.findUserByUserId(resetPasswordConfirmation.getUser());
        userCredentials.setPasswordHash(passwordWeb.getNewPassword());
    }

}
