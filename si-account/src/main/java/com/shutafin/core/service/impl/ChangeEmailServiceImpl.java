package com.shutafin.core.service.impl;

import com.shutafin.core.service.ChangeEmailService;
import com.shutafin.core.service.PasswordService;
import com.shutafin.model.entities.User;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.web.account.AccountEmailChangeWeb;
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

    @Override
    public void changeEmail(User user, AccountEmailChangeWeb emailChangeWeb) {

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

        user = userRepository.findOne(user.getId());
        user.setEmail(emailChangeWeb.getNewEmail());
    }
}
