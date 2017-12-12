package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountInitializationResponse;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.initialization.InitializationResponse;
import com.shutafin.model.web.matching.MatchingInitializationResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class InitializationServiceImpl implements InitializationService {


    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;


    @Override
    @Transactional(readOnly = true)
    public List<LanguageWeb> findAllLanguages() {
        String url = discoveryRoutingService.getRoute(RouteDirection.SI_ACCOUNT) + "/initialization/languages";
        ResponseEntity<List> languages = new RestTemplate().getForEntity(url, List.class);
        return languages.getBody();
    }



    @Override
    public InitializationResponse getInitializationResponse(Long userId) {
        String accountUrl = getAccountInitializationUrl(userId);

        ResponseEntity<AccountInitializationResponse> accountInitialization = new RestTemplate().getForEntity(accountUrl, AccountInitializationResponse.class);

        String matchingUrl = getMatchingInitializationUrl(accountInitialization.getBody().getUserProfile().getLanguageId(), userId);
        ResponseEntity<MatchingInitializationResponse> matchingInitialization = new RestTemplate().getForEntity(matchingUrl, MatchingInitializationResponse.class);

        return InitializationResponse
                .builder()
                .accountInitialization(accountInitialization.getBody())
                .matchingInitializationResponse(matchingInitialization.getBody())
                .build();
    }

    private String getAccountInitializationUrl(Long userId) {
        return discoveryRoutingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/initialization/%d/all", userId);
    }

    private String getMatchingInitializationUrl(Integer languageId, Long userId) {
        return discoveryRoutingService.getRoute(RouteDirection.SI_MATCHING) + String.format("/initialization/%d/all/%d", userId, languageId);
    }
}
