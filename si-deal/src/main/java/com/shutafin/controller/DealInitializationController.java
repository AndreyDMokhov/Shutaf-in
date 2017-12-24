package com.shutafin.controller;

import com.shutafin.model.web.deal.DealInitializationResponse;
import com.shutafin.service.DealInitializationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deal")
@Slf4j
public class DealInitializationController {

    @Autowired
    private DealInitializationService dealInitializationService;

    @GetMapping(value = "/initialization", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealInitializationResponse getDealInitializationResponse() {
        return DealInitializationResponse.builder()
                .dealStatuses(dealInitializationService.getDealStatuses())
                .dealUserStatuses(dealInitializationService.getDealUserStatuses())
                .dealUserPermissions(dealInitializationService.getDealUserPermissions())
                .permissionTypes(dealInitializationService.getPermissionTypes())
                .documentTypes(dealInitializationService.getDocumentTypes())
                .build();
    }
}
