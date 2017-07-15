package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.types.AccountStatus;
import com.shutafin.model.entities.types.AccountType;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserRepository;
import com.shutafin.repository.LanguageRepository;
import com.shutafin.service.PasswordService;
import com.shutafin.service.RegistrationService;
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService{

    private static final int LANGUAGE_ID = 1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SessionManagementService sessionManagementService;

    @Autowired
    private PasswordService passwordService;

    @Override
    @Transactional
    public String save(RegistrationRequestWeb registrationRequestWeb) {
        User user = saveUser(registrationRequestWeb);
        saveUserAccount(user, registrationRequestWeb);
        saveUserCredentials(user, registrationRequestWeb.getPassword());
        return sessionManagementService.generateNewSession(user);
    }

    private void saveUserCredentials(User user, String password){
        passwordService.createAndSaveUserPassword(user, password);
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