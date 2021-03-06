package com.shutafin.service.impl;

import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.account.AccountResetPassword;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.response.EmailResetPasswordResponse;
import com.shutafin.model.web.user.PasswordWeb;
import com.shutafin.sender.account.ResetPasswordControllerSender;
import com.shutafin.sender.email.EmailNotificationSenderControllerSender;
import com.shutafin.service.ResetPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    private ResetPasswordControllerSender resetPasswordControllerSender;

    @Autowired
    private EmailNotificationSenderControllerSender emailSender;

    @Override
    public void resetPasswordRequest(AccountEmailRequest emailWeb) {
        resetPasswordControllerSender.resetPasswordRequest(emailWeb);
    }

    @Override
    public void resetPasswordValidation(String link) {
        Boolean linkValid = emailSender.isLinkValid(link, EmailReason.RESET_PASSWORD);
        if (!linkValid) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public void passwordChange(PasswordWeb passwordWeb, String link) {
        EmailResetPasswordResponse o = (EmailResetPasswordResponse) emailSender.confirmLink(link, EmailReason.RESET_PASSWORD);
        AccountResetPassword resetPassword = AccountResetPassword.builder()
                .newPassword(passwordWeb.getNewPassword())
                .userId(o.getUserId())
                .build();
        resetPasswordControllerSender.resetPassword(resetPassword);
    }

}
