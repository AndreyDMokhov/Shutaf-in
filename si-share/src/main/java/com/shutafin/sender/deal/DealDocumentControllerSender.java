package com.shutafin.sender.deal;

import com.shutafin.model.web.deal.DealUserDocumentWeb;
import com.shutafin.model.web.deal.InternalDealUserDocumentWeb;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class DealDocumentControllerSender {
    
    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    private RestTemplate restTemplate;


    public DealUserDocumentWeb addDealDocument(DealUserDocumentWeb dealUserDocumentWeb, Long userId) {
        String requestUrl = getDealUrl();
        InternalDealUserDocumentWeb internalDealUserDocumentWeb = InternalDealUserDocumentWeb
                .builder()
                .userId(userId)
                .dealPanelId(dealUserDocumentWeb.getDealPanelId())
                .documentTitle(dealUserDocumentWeb.getDocumentTitle())
                .documentTypeId(dealUserDocumentWeb.getDocumentTypeId())
                .fileData(dealUserDocumentWeb.getFileData())
                .build();


        internalDealUserDocumentWeb = restTemplate
                .postForEntity(requestUrl, internalDealUserDocumentWeb, InternalDealUserDocumentWeb.class)
                .getBody();

        dealUserDocumentWeb.setId(internalDealUserDocumentWeb.getId());
        return dealUserDocumentWeb;
    }

    public DealUserDocumentWeb getDealDocument(Long userId, Long dealDocumentId) {
        String requestUrl = getDealUrl() + "{userId}/{docId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("docId", dealDocumentId);
        return restTemplate
                .getForEntity(requestUrl, DealUserDocumentWeb.class, uriVariables)
                .getBody();
    }

    public void deleteDealDocument(Long userId, Long dealDocumentId) {
        String requestUrl = getDealUrl() + "{userId}/{docId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("docId", dealDocumentId);
        restTemplate.delete(requestUrl, uriVariables);
    }

    public DealUserDocumentWeb renameDealDocument(Long userId, Long dealDocumentId, DealTitleChangeWeb newTitle) {
        String requestUrl = getDealUrl() + "{userId}/{docId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("docId", dealDocumentId);
        return restTemplate
                .postForEntity(requestUrl, newTitle, DealUserDocumentWeb.class, uriVariables)
                .getBody();
    }

    private String getDealUrl() {
        return discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/documents/";
    }
}
