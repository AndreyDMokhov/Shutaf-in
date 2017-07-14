package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.entities.RegistrationConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.types.AccountStatus;
import com.shutafin.model.entities.types.AccountType;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.repository.*;
import com.shutafin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService{

    private static final int ACCOUNT_STATUS_ID = 1;
    private static final int ACCOUNT_TYPE_ID = 1;
    private static final int LANGUAGE_ID = 1;
    private static final String PASSWORD_SALT = "Salt";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

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
    private SessionManagementService sessionManagementService;

    @Override
    @Transactional
    public void save(RegistrationRequestWeb registrationRequestWeb) {
        User user = saveUser(registrationRequestWeb);
        saveUserAccount(user, registrationRequestWeb);
        saveUserCredentials(user, registrationRequestWeb.getPassword());

        RegistrationConfirmation registrationConfirmation = new RegistrationConfirmation();
        registrationConfirmation.setUser(user);
        registrationConfirmation.setConfirmed(false);
        registrationConfirmation.setUrlLink(UUID.randomUUID().toString());
        registrationConfirmationRepository.save(registrationConfirmation);

        Language language = languageRepository.findById(registrationRequestWeb.getUserLanguageId());

        String link = environmentConfigurationService.getServerAddress() + "/#/users/registration/confirmation/" + registrationConfirmation.getUrlLink();
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(registrationConfirmation.getUser(), EmailReason.REGISTRATION_CONFIRMATION, language, link);
        mailSenderService.sendEmail(emailMessage, EmailReason.REGISTRATION_CONFIRMATION);
    }

    @Override
    public String confirmRegistration(String link) {
        RegistrationConfirmation registrationConfirmation = registrationConfirmationRepository.getRegistrationConfirmationByUrlLink(link);
        if (registrationConfirmation == null){
            throw new ResourceNotFoundException();
        }
        registrationConfirmation.setConfirmed(true);
        registrationConfirmationRepository.update(registrationConfirmation);
        return sessionManagementService.generateNewSession(registrationConfirmation.getUser());
    }

    private void saveUserCredentials(User user, String password){
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUser(user);
        userCredentials.setPasswordHash(password);
        userCredentials.setPasswordSalt(PASSWORD_SALT);
        userCredentialsRepository.save(userCredentials);
    }

    private void saveUserAccount(User user, RegistrationRequestWeb registrationRequestWeb){
        UserAccount userAccount = new UserAccount();
        userAccount.setUser(user);
        userAccount.setAccountStatus(AccountStatus.NEW);
        userAccount.setAccountType(AccountType.REGULAR);

        Language language = languageRepository.findById(registrationRequestWeb.getUserLanguageId());
        if (language == null){
            language = languageRepository.findById(LANGUAGE_ID);
        }
        userAccount.setLanguage(language);
        userAccountRepository.save(userAccount);
    }

    private User saveUser(RegistrationRequestWeb registrationRequestWeb) {
        User user = new User();
        user.setFirstName(registrationRequestWeb.getFirstName());
        user.setLastName(registrationRequestWeb.getLastName());
        String email = registrationRequestWeb.getEmail();
        if (userRepository.findUserByEmail(email) != null){
            throw new EmailNotUniqueValidationException("Email " + email + " already exists");
        }
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }
}
