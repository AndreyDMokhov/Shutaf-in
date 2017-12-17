package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountChangePasswordWeb;
import com.shutafin.sender.account.ChangePasswordControllerSender;
import com.shutafin.service.ChangePasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChangePasswordServiceImpl implements ChangePasswordService {

    @Autowired
    private ChangePasswordControllerSender changePasswordControllerSender;

    @Override
    public void changePassword(AccountChangePasswordWeb changePasswordWeb, Long userId) {
        changePasswordControllerSender.changePassword(changePasswordWeb, userId);
    }
}
