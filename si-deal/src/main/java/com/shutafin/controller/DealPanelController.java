package com.shutafin.controller;

import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
import com.shutafin.service.DealPanelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

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

        return dealPanelService.addDealPanel(dealPanelWeb);
    }

    @GetMapping(value = "/{userId}/{panelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealPanelResponse getDealPanel(@PathVariable(value = "userId") Long userId,
                                          @PathVariable(value = "panelId") Long dealPanelId) {
        log.debug("GET /deal/panel/{userId}/{panelId}");
        return dealPanelService.getDealPanel(dealPanelId, userId);
    }

    @PostMapping(value = "/{userId}/{panelId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealPanelResponse renameDealPanel(@PathVariable(value = "userId") Long userId,
                                             @PathVariable(value = "panelId") Long dealPanelId,
                                             @RequestBody @Valid NewTitleWeb panelTitle) {
        log.debug("POST /deal/panel/{userId}/{panelId}");
        return dealPanelService.renameDealPanel(dealPanelId, userId, panelTitle.getTitle());
    }

    @DeleteMapping(value = "/{userId}/{panelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteDealPanel(@PathVariable(value = "userId") Long userId,
                                @PathVariable(value = "panelId") Long dealPanelId) {
        log.debug("DELETE /deal/panel/{userId}/{panelId}");
        dealPanelService.deleteDealPanel(dealPanelId, userId);
    }

}
