package com.shutafin.sender.deal;

import com.shutafin.model.web.deal.DealUserDocumentWeb;
import com.shutafin.model.web.deal.InternalDealUserDocumentWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class DealDocumentControllerSender {
    private static String dealUrl;

    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    public DealDocumentControllerSender(DiscoveryRoutingService discoveryRoutingService) {
        this.discoveryRoutingService = discoveryRoutingService;
        dealUrl = this.discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/documents/";
    }

    public DealUserDocumentWeb addDealDocument(DealUserDocumentWeb dealUserDocumentWeb, Long userId) {
        InternalDealUserDocumentWeb internalDealUserDocumentWeb = new InternalDealUserDocumentWeb();
        internalDealUserDocumentWeb.setUserId(userId);
        internalDealUserDocumentWeb.setDealPanelId(dealUserDocumentWeb.getDealPanelId());
        internalDealUserDocumentWeb.setDocumentTitle(dealUserDocumentWeb.getDocumentTitle());
        internalDealUserDocumentWeb.setDocumentTypeId(dealUserDocumentWeb.getDocumentTypeId());
        internalDealUserDocumentWeb.setFileData(dealUserDocumentWeb.getFileData());
        internalDealUserDocumentWeb = new RestTemplate().postForEntity(dealUrl, internalDealUserDocumentWeb,
                InternalDealUserDocumentWeb.class).getBody();
        dealUserDocumentWeb.setId(internalDealUserDocumentWeb.getId());
        return dealUserDocumentWeb;
    }

    public DealUserDocumentWeb getDealDocument(Long userId, Long dealDocumentId) {
        String requestUrl = dealUrl + "{userId}/{docId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("docId", dealDocumentId);
        return new RestTemplate().getForEntity(requestUrl, DealUserDocumentWeb.class, uriVariables).getBody();
    }

    public void deleteDealDocument(Long userId, Long dealDocumentId) {
        String requestUrl = dealUrl + "{userId}/{docId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("docId", dealDocumentId);
        new RestTemplate().delete(requestUrl, uriVariables);
    }

    public DealUserDocumentWeb renameDealDocument(Long userId, Long dealDocumentId, NewTitleWeb newTitle) {
        String requestUrl = dealUrl + "{userId}/{docId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("docId", dealDocumentId);
        return new RestTemplate().postForEntity(requestUrl, newTitle, DealUserDocumentWeb.class, uriVariables)
                .getBody();
    }

}
