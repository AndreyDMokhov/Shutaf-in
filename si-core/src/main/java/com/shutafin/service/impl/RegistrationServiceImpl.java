package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.model.entities.infrastructure.AccountStatus;
import com.shutafin.model.entities.infrastructure.AccountType;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserCredentialsRepository;
import com.shutafin.repository.UserRepository;
import com.shutafin.repository.infrastructure.AccountStatusRepository;
import com.shutafin.repository.infrastructure.AccountTypeRepository;
import com.shutafin.repository.infrastructure.LanguageRepository;
import com.shutafin.service.RegistrationService;
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private AccountStatusRepository accountStatusRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private SessionManagementService sessionManagementService;

    @Override
    @Transactional
    public String save(RegistrationRequestWeb registrationRequestWeb) {
        User user = saveUser(registrationRequestWeb);
        saveUserAccount(user);
        saveUserCredentials(user, registrationRequestWeb.getPassword());
        return sessionManagementService.generateNewSession(user);
    }

    private void saveUserCredentials(User user, String password){
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUser(user);
        userCredentials.setPasswordHash(password);
        userCredentials.setPasswordSalt(PASSWORD_SALT);
        userCredentialsRepository.save(userCredentials);
    }

    private void saveUserAccount(User user){
        UserAccount userAccount = new UserAccount();
        userAccount.setUser(user);
        AccountStatus accountStatus = accountStatusRepository.findById(ACCOUNT_STATUS_ID);
        userAccount.setAccountStatus(accountStatus);
        AccountType accountType = accountTypeRepository.findById(ACCOUNT_TYPE_ID);
        userAccount.setAccountType(accountType);
        Language language = languageRepository.findById(LANGUAGE_ID);
        userAccount.setLanguage(language);
        userAccountRepository.save(userAccount);
    }

    private User saveUser(RegistrationRequestWeb registrationRequestWeb) {
        User user = new User();
        user.setFirstName(registrationRequestWeb.getFirstName());
        user.setLastName(registrationRequestWeb.getLastName());
        String email = registrationRequestWeb.getEmail();
        if (userRepository.findUserByEmail(email) != null){
            throw new EmailNotUniqueValidationException("Email " + email + " exist!");
        }
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }
}
