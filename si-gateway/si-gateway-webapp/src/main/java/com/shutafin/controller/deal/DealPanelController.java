package com.shutafin.controller.deal;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
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
    public DealPanelResponse addDealPanel(@AuthenticatedUser User user, @RequestBody @Valid DealPanelWeb dealPanelWeb,
                                          BindingResult result) {
        log.debug("/deal/panel/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new RuntimeException();
        }

        return dealPanelService.addDealPanel(dealPanelWeb, user);
    }

    @GetMapping(value = "/{panelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealPanelResponse getDealPanel(@AuthenticatedUser User user,
                                          @PathVariable(value = "panelId") Long dealPanelId) {
        log.debug("GET /deal/panel/{userId}/{panelId}");
        return dealPanelService.getDealPanel(dealPanelId, user);
    }

    @PostMapping(value = "/{panelId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealPanelResponse renameDealPanel(@AuthenticatedUser User user,
                                             @PathVariable(value = "panelId") Long dealPanelId,
                                             @RequestBody @Valid NewTitleWeb panelTitle) {
        log.debug("POST /deal/panel/{panelId}");
        return dealPanelService.renameDealPanel(dealPanelId, user, panelTitle);
    }

    @DeleteMapping(value = "/{panelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteDealPanel(@AuthenticatedUser User user,
                                @PathVariable(value = "panelId") Long dealPanelId) {
        log.debug("DELETE /deal/panel/{userId}/{panelId}");
        dealPanelService.deleteDealPanel(dealPanelId, user);
    }
}
