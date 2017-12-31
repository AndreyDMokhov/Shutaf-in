package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountInitializationResponse;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.initialization.InitializationResponse;
import com.shutafin.model.web.matching.MatchingInitializationResponse;
import com.shutafin.sender.account.AccountInitializationControllerSender;
import com.shutafin.sender.matching.MatchingInitializationControllerSender;
import com.shutafin.service.ChatInfoService;
import com.shutafin.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InitializationServiceImpl implements InitializationService {


    @Autowired
    private AccountInitializationControllerSender accountInitializationControllerSender;

    @Autowired
    private ChatInfoService chatInfoService;

    @Autowired
    private MatchingInitializationControllerSender matchingInitializationControllerSender;


    @Override
    @Transactional(readOnly = true)
    public List<LanguageWeb> findAllLanguages() {
        return accountInitializationControllerSender.getLanguages();
    }


    @Override
    public InitializationResponse getInitializationResponse(Long userId) {

        AccountInitializationResponse accountInitialization = accountInitializationControllerSender.getInitializationResponse(userId);

        MatchingInitializationResponse matchingInitialization = matchingInitializationControllerSender.getInitializationResponse(
                userId,
                accountInitialization
                        .getUserProfile()
                        .getLanguageId());

        return InitializationResponse
                .builder()
                .accountInitialization(accountInitialization)
                .matchingInitializationResponse(matchingInitialization)
                .listOfChats(chatInfoService.getListChats(userId))
                .build();
    }
}
