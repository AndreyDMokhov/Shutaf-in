package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountUserLanguageWeb;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.service.UserLanguageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by evgeny on 6/26/2017.
 */
@Service
@Transactional
public class UserLanguageServiceImpl implements UserLanguageService {


    @Autowired
    private UserAccountControllerSender userAccountControllerSender;


    @Override
    @SneakyThrows
    public void updateUserLanguage(AccountUserLanguageWeb userLanguageWeb, Long userId) {
        userAccountControllerSender.updateUserLanguage(userLanguageWeb, userId);
    }

}
