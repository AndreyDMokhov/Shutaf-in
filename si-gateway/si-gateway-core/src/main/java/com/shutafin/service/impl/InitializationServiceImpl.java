package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountInitializationResponse;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.deal.DealInitializationResponse;
import com.shutafin.model.web.initialization.InitializationResponse;
import com.shutafin.model.web.matching.MatchingInitializationResponse;
import com.shutafin.sender.account.AccountInitializationControllerSender;
import com.shutafin.sender.deal.DealInitializationControllerSender;
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

    @Autowired
    private DealInitializationControllerSender dealInitializationControllerSender;


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

        DealInitializationResponse dealInitializationResponse = dealInitializationControllerSender.getDealInitializationResponse();

        return InitializationResponse
                .builder()
                .accountInitialization(accountInitialization)
                .matchingInitializationResponse(matchingInitialization)
                .listOfChats(chatInfoService.getListChats(userId))
                .dealInitializationResponse(dealInitializationResponse)
                .build();
    }
}
