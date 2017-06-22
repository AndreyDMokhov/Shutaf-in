package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.entities.infrastructure.AccountStatus;
import com.shutafin.model.entities.infrastructure.AccountType;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserCredentialsRepository;
import com.shutafin.repository.UserRepository;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.repository.infrastructure.AccountStatusRepository;
import com.shutafin.repository.infrastructure.AccountTypeRepository;
import com.shutafin.service.RegistrationService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService{

    private static final int ACCOUNT_STATUS_ID = 1;
    private static final int ACCOUNT_TYPE_ID = 1;
    private static final String PASSWORD_SALT = "Salt";
    private static final Boolean IS_EXPIRABLE = false;
    private static final Boolean IS_VALID = true;
    private static final int NUMBER_DAYS_EXPIRATION = 30;

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
    private UserSessionRepository userSessionRepository;

    @Override
    @Transactional
    public String save(RegistrationRequestWeb registrationRequestWeb) {
        User user = saveUser(registrationRequestWeb);
        saveUserAccount(user);
        saveUserCredentials(user, registrationRequestWeb.getPassword());
        UserSession userSession = saveUserSession(user);
        return userSession.getSessionId();
    }

    private String newSessionId(){
        return UUID.randomUUID().toString();
    }

    private Date getExpirationDate(int numberOfDays){
        return DateUtils.addDays(new Date(), numberOfDays);
    }

    private UserSession saveUserSession(User user){
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setValid(IS_VALID);
        userSession.setSessionId(newSessionId());
        userSession.setExpirationDate(getExpirationDate(NUMBER_DAYS_EXPIRATION));

        userSession.setExpirable(IS_EXPIRABLE);

        Long userSessionId = (Long) userSessionRepository.save(userSession);
        userSession.setId(userSessionId);
        return userSession;
    }

    private void saveUserCredentials(User user, String password){
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUser(user);
        userCredentials.setPasswordHash(password);
        userCredentials.setPasswordSalt(PASSWORD_SALT);

        Long userCredentialsId = (Long) userCredentialsRepository.save(userCredentials);
        userCredentials.setId(userCredentialsId);
    }

    private void saveUserAccount(User user){
        UserAccount userAccount = new UserAccount();
        userAccount.setUser(user);
        AccountStatus accountStatus = accountStatusRepository.findById(ACCOUNT_STATUS_ID);
        userAccount.setAccountStatus(accountStatus);
        AccountType accountType = accountTypeRepository.findById(ACCOUNT_TYPE_ID);
        userAccount.setAccountType(accountType);

        Long userAccountId = (Long) userAccountRepository.save(userAccount);
        userAccount.setId(userAccountId);
    }

    private User saveUser(RegistrationRequestWeb registrationRequestWeb) {
        User user = new User();
        user.setId(registrationRequestWeb.getUserId());
        user.setFirstName(registrationRequestWeb.getFirstName());
        user.setLastName(registrationRequestWeb.getLastName());
        user.setEmail(registrationRequestWeb.getEmail());

        Long userId = (Long) userRepository.save(user);
        user.setId(userId);
        return user;
    }
}
