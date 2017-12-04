package com.shutafin.core.service.impl;


import com.shutafin.core.service.*;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.types.AccountStatus;
import com.shutafin.model.types.AccountType;
import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.user.UserInfoRequest;
import com.shutafin.repository.LanguageRepository;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private static final int LANGUAGE_ID = 1;

    private UserRepository userRepository;
    private UserAccountRepository userAccountRepository;
    private LanguageRepository languageRepository;
    private PasswordService passwordService;
    private UserImageService userImageService;
    private UserInfoService userInfoService;
    private UserAccountService userAccountService;

    @Autowired
    public RegistrationServiceImpl(
            UserRepository userRepository,
            UserAccountRepository userAccountRepository,
            LanguageRepository languageRepository,
            PasswordService passwordService,
            UserImageService userImageService,
            UserInfoService userInfoService,
            UserAccountService userAccountService) {
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
        this.languageRepository = languageRepository;
        this.passwordService = passwordService;
        this.userImageService = userImageService;
        this.userInfoService = userInfoService;
        this.userAccountService = userAccountService;
    }

    @Override
    @Transactional
    public void save(AccountRegistrationRequest registrationRequestWeb) {
        User user = saveUser(registrationRequestWeb);
        saveUserAccount(user, registrationRequestWeb);
        saveUserCredentials(user, registrationRequestWeb.getPassword());
        userImageService.createUserImageDirectory(user);
        userInfoService.createUserInfo(new UserInfoRequest(), user);
    }

    @Override
    public User confirmRegistration(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            log.warn("User was not found for id: " + userId);
            throw new AuthenticationException();
        }
        UserAccount userAccount = userAccountService.checkUserAccountStatus(user);
        userAccount.setAccountStatus(AccountStatus.CONFIRMED);
        return user;
    }

    private void saveUserCredentials(User user, String password) {
        passwordService.createAndSaveUserPassword(user, password);
    }

    private void saveUserAccount(User user, AccountRegistrationRequest registrationRequestWeb) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUser(user);
        userAccount.setAccountStatus(AccountStatus.NEW);
        userAccount.setAccountType(AccountType.REGULAR);

        Language language = languageRepository.findOne(registrationRequestWeb.getUserLanguageId());
        if (language == null) {
            language = languageRepository.findOne(LANGUAGE_ID);
        }
        userAccount.setLanguage(language);
        userAccountRepository.save(userAccount);
    }

    private User saveUser(AccountRegistrationRequest registrationRequestWeb) {
        User user = new User();
        user.setFirstName(registrationRequestWeb.getFirstName());
        user.setLastName(registrationRequestWeb.getLastName());
        String email = registrationRequestWeb.getEmail();
        if (userRepository.findByEmail(email) != null) {
            log.warn("Email not unique validation exception:");
            log.warn("Email already exists");
            throw new EmailNotUniqueValidationException("Email " + email + " already exists");
        }
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }
}