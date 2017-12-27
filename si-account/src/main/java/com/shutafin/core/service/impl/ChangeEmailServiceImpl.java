package com.shutafin.core.service.impl;

import com.shutafin.core.service.ChangeEmailService;
import com.shutafin.core.service.PasswordService;
import com.shutafin.core.service.UserAccountService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.web.account.AccountEmailChangeRequest;
import com.shutafin.model.web.account.AccountEmailChangeValidationRequest;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.repository.account.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ChangeEmailServiceImpl implements ChangeEmailService {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public EmailNotificationWeb changeEmailChangeValidationRequest(User user, AccountEmailChangeValidationRequest emailChangeWeb) {

        if (!passwordService.isPasswordCorrect(user, emailChangeWeb.getUserPassword())) {
            log.warn("Authentication exception:");
            log.warn("User password with ID {} is incorrect", user.getId());
            throw new AuthenticationException();
        }

        if (userRepository.findByEmail(emailChangeWeb.getNewEmail()) != null) {
            log.warn("Email not unique validation exception:");
            log.warn("Such email already exists");
            throw new EmailNotUniqueValidationException("Such email already exists");
        }

        UserAccount userAccount = userAccountService.findUserAccountByUser(user);

        return EmailNotificationWeb
                .builder()
                .emailTo(user.getEmail())
                .languageCode(userAccount.getLanguage().getDescription())
                .emailTo(user.getEmail())
                .emailChange(emailChangeWeb.getNewEmail())
                .userId(user.getId())
                .emailReason(EmailReason.EMAIL_CHANGE)
                .build();
    }

    @Override
    public void changeEmailChangeRequest(User user, AccountEmailChangeRequest emailChangeWeb) {
        if (userRepository.findByEmail(emailChangeWeb.getNewEmail()) != null) {
            log.warn("Email not unique validation exception:");
            log.warn("Such email already exists");
            throw new EmailNotUniqueValidationException("Such email already exists");
        }

        user = userRepository.findOne(user.getId());
        user.setEmail(emailChangeWeb.getNewEmail());
        userRepository.save(user);
    }
}
