package com.shutafin.service.impl;


import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.sender.account.LoginControllerSender;
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


    public AccountUserWeb getUserByLoginWebModel(AccountLoginRequest loginWeb) {
        return loginControllerSender.login(loginWeb);
    }




}
