package com.shutafin.core.service.impl;


import com.shutafin.core.service.*;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.types.AccountStatus;
import com.shutafin.model.types.AccountType;
import com.shutafin.model.web.user.RegistrationRequestWeb;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserImageService userImageService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    @Transactional
    public void save(RegistrationRequestWeb registrationRequestWeb) {
        User user = saveUser(registrationRequestWeb);
        UserAccount userAccount = saveUserAccount(user, registrationRequestWeb);
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

    private UserAccount saveUserAccount(User user, RegistrationRequestWeb registrationRequestWeb) {
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
        return userAccount;
    }

    private User saveUser(RegistrationRequestWeb registrationRequestWeb) {
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