package com.shutafin.controller.deal;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.deal.DealPanelService;
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
    public DealPanelResponse addDealPanel(@AuthenticatedUser Long userId,
                                          @RequestBody @Valid DealPanelWeb dealPanelWeb,
                                          BindingResult result) {
        log.debug("/deal/panel/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        return dealPanelService.addDealPanel(dealPanelWeb, userId);
    }

    @GetMapping(value = "/{panelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealPanelResponse getDealPanel(@AuthenticatedUser Long userId,
                                          @PathVariable(value = "panelId") Long dealPanelId) {
        log.debug("GET /deal/panel/{userId}/{panelId}");
        return dealPanelService.getDealPanel(dealPanelId, userId);
    }

    @PostMapping(value = "/{panelId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealPanelResponse renameDealPanel(@AuthenticatedUser Long userId,
                                             @PathVariable(value = "panelId") Long dealPanelId,
                                             @RequestBody @Valid DealTitleChangeWeb panelTitle) {
        log.debug("POST /deal/panel/{panelId}");
        return dealPanelService.renameDealPanel(dealPanelId, userId, panelTitle);
    }

    @DeleteMapping(value = "/{panelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteDealPanel(@AuthenticatedUser Long userId,
                                @PathVariable(value = "panelId") Long dealPanelId) {
        log.debug("DELETE /deal/panel/{userId}/{panelId}");
        dealPanelService.deleteDealPanel(dealPanelId, userId);
    }
}
