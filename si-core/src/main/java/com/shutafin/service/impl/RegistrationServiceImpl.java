package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.entities.RegistrationConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.types.AccountStatus;
import com.shutafin.model.entities.types.AccountType;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.model.web.user.UserInfoRequest;
import com.shutafin.repository.account.RegistrationConfirmationRepository;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.repository.initialization.LanguageRepository;
import com.shutafin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private static final int LANGUAGE_ID = 1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private EmailNotificationSenderService mailSenderService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private RegistrationConfirmationRepository registrationConfirmationRepository;

    @Autowired
    private EnvironmentConfigurationService environmentConfigurationService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserImageService userImageService;

    @Autowired
    private UserInfoService userInfoService;



    @Override
    @Transactional
    public void save(RegistrationRequestWeb registrationRequestWeb) {
        User user = saveUser(registrationRequestWeb);
        UserAccount userAccount = saveUserAccount(user, registrationRequestWeb);
        saveUserCredentials(user, registrationRequestWeb.getPassword());
        userImageService.createUserImageDirectory(user);
        userInfoService.createUserInfo(new UserInfoRequest(), user);
        sendConfirmRegistrationEmail(user, userAccount);
    }

    @Override
    @Transactional
    public User confirmRegistration(String link) {
        RegistrationConfirmation registrationConfirmation = registrationConfirmationRepository.getRegistrationConfirmationByUrlLink(link);

        if (registrationConfirmation == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }

        registrationConfirmation.setIsConfirmed(true);
        registrationConfirmationRepository.update(registrationConfirmation);

        UserAccount userAccount = userAccountRepository.findUserAccountByUser(registrationConfirmation.getUser());
        userAccount.setAccountStatus(AccountStatus.CONFIRMED);
        userAccountRepository.update(userAccount);

        return registrationConfirmation.getUser();
    }

    private void sendConfirmRegistrationEmail(User user, UserAccount userAccount) {
        RegistrationConfirmation registrationConfirmation = new RegistrationConfirmation();
        registrationConfirmation.setUser(user);
        registrationConfirmation.setIsConfirmed(false);
        registrationConfirmation.setUrlLink(UUID.randomUUID().toString());
        registrationConfirmationRepository.save(registrationConfirmation);

        String link = environmentConfigurationService.getServerAddress() + "/#/users/registration/confirmation/" + registrationConfirmation.getUrlLink();
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(registrationConfirmation.getUser(), EmailReason.REGISTRATION_CONFIRMATION, userAccount.getLanguage(), link);
        mailSenderService.sendEmail(emailMessage, EmailReason.REGISTRATION_CONFIRMATION);
    }

    private void saveUserCredentials(User user, String password) {
        passwordService.createAndSaveUserPassword(user, password);
    }

    private UserAccount saveUserAccount(User user, RegistrationRequestWeb registrationRequestWeb) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUser(user);
        userAccount.setAccountStatus(AccountStatus.NEW);
        userAccount.setAccountType(AccountType.REGULAR);

        Language language = languageRepository.findById(registrationRequestWeb.getUserLanguageId());
        if (language == null) {
            language = languageRepository.findById(LANGUAGE_ID);
        }
        userAccount.setLanguage(language);
        userAccountRepository.save(userAccount);
        return userAccount;
    }

    private User saveUser(RegistrationRequestWeb registrationRequestWeb) {
        User user = new User();
        user.setFirstName(registrationRequestWeb.getFirstName());
        user.setLastName(registrationRequestWeb.getLastName());
        String email = registrationRequestWeb.getEmail();
        if (userRepository.findUserByEmail(email) != null) {
            log.warn("Email not unique validation exception:");
            log.warn("Email already exists");
            throw new EmailNotUniqueValidationException("Email " + email + " already exists");
        }
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }
}