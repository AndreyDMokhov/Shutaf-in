package com.shutafin.controller;

import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.web.DealPanelResponse;
import com.shutafin.model.web.DealPanelWeb;
import com.shutafin.model.web.NewTitleWeb;
import com.shutafin.service.DealPanelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/deal/panel")
@Slf4j
public class DealPanelController {

    @Autowired
    private DealPanelService dealPanelService;

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealPanelResponse addDealPanel(@RequestBody @Valid DealPanelWeb dealPanelWeb, BindingResult result) {
        log.debug("/deal/panel/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new RuntimeException();
        }

        DealPanel dealPanel = dealPanelService.addDealPanel(dealPanelWeb);

        return getDealPanelResponse(dealPanel, false);
    }

    @GetMapping(value = "/{userId}/{panelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealPanelResponse getDealPanel(@PathVariable(value = "userId") Long userId,
                                          @PathVariable(value = "panelId") Long dealPanelId) {
        log.debug("GET /deal/panel/{userId}/{panelId}");
        DealPanel dealPanel = dealPanelService.getDealPanel(dealPanelId, userId);
        return getDealPanelResponse(dealPanel, true);
    }

    @PostMapping(value = "/{userId}/{panelId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealPanelResponse renameDealPanel(@PathVariable(value = "userId") Long userId,
                                             @PathVariable(value = "panelId") Long dealPanelId,
                                             @RequestBody @Valid NewTitleWeb panelTitle) {
        log.debug("POST /deal/panel/{userId}/{panelId}");
        DealPanel dealPanel = dealPanelService.renameDealPanel(dealPanelId, userId, panelTitle.getTitle());
        return getDealPanelResponse(dealPanel, false);
    }

    @DeleteMapping(value = "/{userId}/{panelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteDealPanel(@RequestParam(value = "userId") Long userId,
                                @RequestParam(value = "panelId") Long dealPanelId) {
        log.debug("DELETE /deal/panel/{userId}/{panelId}");
        dealPanelService.deleteDealPanel(dealPanelId, userId);
    }

    private DealPanelResponse getDealPanelResponse(DealPanel dealPanel, Boolean includeDocuments) {
        DealPanelResponse dealPanelResponse = new DealPanelResponse();
        dealPanelResponse.setPanelId(dealPanel.getId());
        dealPanelResponse.setTitle(dealPanel.getTitle());
        if (includeDocuments) {
            dealPanelResponse.setDocuments(dealPanelService.getDealPanelDocuments(dealPanel.getId()));
        }
        return dealPanelResponse;
    }
}
