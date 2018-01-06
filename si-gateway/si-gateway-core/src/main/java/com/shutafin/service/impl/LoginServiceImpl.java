package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.EmailResendWeb;
import com.shutafin.sender.account.LoginControllerSender;
import com.shutafin.sender.email.EmailNotificationSenderControllerSender;
import com.shutafin.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginControllerSender loginControllerSender;

    @Autowired
    private EmailNotificationSenderControllerSender emailNotificationSenderControllerSender;


    @Override
    public AccountUserWeb getUserByLoginWebModel(AccountLoginRequest loginWeb) {
        return loginControllerSender.login(loginWeb);
    }

    @Override
    public void resendEmailRegistration(AccountLoginRequest loginWeb) {
        loginControllerSender.checkUserPassword(loginWeb);
        EmailResendWeb emailResendWeb = new EmailResendWeb(loginWeb.getEmail(), EmailReason.REGISTRATION);
        emailNotificationSenderControllerSender.resendEmail(emailResendWeb);
    }

}
