package com.shutafin.core.service.impl;

import com.shutafin.core.service.PasswordService;
import com.shutafin.core.service.ResetPasswordService;
import com.shutafin.core.service.UserAccountService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.account.AccountResetPassword;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.repository.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private PasswordService passwordService;

    @Override
    public EmailNotificationWeb getResetPasswordEmailNotification(AccountEmailRequest accountEmailRequest) {
        User user = userRepository.findByEmail(accountEmailRequest.getEmail());
        if (user == null) {
            return null;
        }

        UserAccount userAccount = userAccountService.findUserAccountByUser(user);

        return EmailNotificationWeb
                .builder()
                .emailReason(EmailReason.RESET_PASSWORD)
                .userId(user.getId())
                .emailTo(user.getEmail())
                .languageCode(userAccount.getLanguage().getDescription())
                .build();
    }

    @Override
    public void resetPassword(AccountResetPassword accountResetPassword) {
        User user = userRepository.findOne(accountResetPassword.getUserId());
        if (user == null) {
            return;
        }

        passwordService.updateUserPasswordInDb(user, accountResetPassword.getNewPassword());
    }
}
