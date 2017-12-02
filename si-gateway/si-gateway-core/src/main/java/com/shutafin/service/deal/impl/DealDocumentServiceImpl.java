package com.shutafin.service.deal.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.DealUserDocumentWeb;
import com.shutafin.model.web.deal.InternalDealUserDocumentWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.InternalRestTemplateService;
import com.shutafin.service.deal.DealDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class DealDocumentServiceImpl implements DealDocumentService {

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    private InternalRestTemplateService internalRestTemplateService;

    @Override
    public DealUserDocumentWeb addDealDocument(DealUserDocumentWeb dealUserDocumentWeb, User user) {
        InternalDealUserDocumentWeb internalDealUserDocumentWeb = new InternalDealUserDocumentWeb();
        internalDealUserDocumentWeb.setUserId(user.getId());
        internalDealUserDocumentWeb.setDealPanelId(dealUserDocumentWeb.getDealPanelId());
        internalDealUserDocumentWeb.setDocumentTitle(dealUserDocumentWeb.getDocumentTitle());
        internalDealUserDocumentWeb.setDocumentTypeId(dealUserDocumentWeb.getDocumentTypeId());
        internalDealUserDocumentWeb.setFileData(dealUserDocumentWeb.getFileData());
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/documents/";
        internalDealUserDocumentWeb = internalRestTemplateService.getResponse(HttpMethod.POST, dealUrl, null, internalDealUserDocumentWeb,
                InternalDealUserDocumentWeb.class);
        dealUserDocumentWeb.setId(internalDealUserDocumentWeb.getId());
        return dealUserDocumentWeb;
    }

    @Override
    public DealUserDocumentWeb getDealDocument(User user, Long dealDocumentId) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/documents/";
        String requestUrl = dealUrl + "{userId}/{docId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", user.getId());
        uriVariables.put("docId", dealDocumentId);
        return internalRestTemplateService.getResponse(HttpMethod.GET, requestUrl, uriVariables, null,
                DealUserDocumentWeb.class);
    }

    @Override
    public void deleteDealDocument(User user, Long dealDocumentId) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/documents/";
        String requestUrl = dealUrl + "{userId}/{docId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", user.getId());
        uriVariables.put("docId", dealDocumentId);
        internalRestTemplateService.getResponse(HttpMethod.DELETE, requestUrl, uriVariables, null,
                DealUserDocumentWeb.class);
    }

    @Override
    public DealUserDocumentWeb renameDealDocument(User user, Long dealDocumentId, NewTitleWeb newTitle) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/documents/";
        String requestUrl = dealUrl + "{userId}/{docId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", user.getId());
        uriVariables.put("docId", dealDocumentId);
        return internalRestTemplateService.getResponse(HttpMethod.POST, requestUrl, uriVariables, newTitle,
                DealUserDocumentWeb.class);
    }

}
