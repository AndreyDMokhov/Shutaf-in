package com.shutafin.controller;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
import com.shutafin.model.web.deal.DealUserWeb;
import com.shutafin.model.web.deal.DealWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.service.deal.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/deal")
@Slf4j
public class DealController {

    @Autowired
    private DealService dealService;

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealWeb initiateDeal(@AuthenticatedUser Long userId,
                                @RequestBody @Valid DealWeb dealWeb,
                                BindingResult result) {
        log.debug("/deal/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        return dealService.initiateDeal(dealWeb, userId);
    }

    @NoAuthentication
    @GetMapping(value = "/confirmation", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealResponse confirmDealUser(@RequestParam("link") String link) {
        log.debug("/deal/confirmation");
        return dealService.confirmDealUser(link);
    }

    @GetMapping(value = "/leave/{dealId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealResponse leaveDeal(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId) {
        log.debug("/deal/leave/{dealId}");
        return dealService.leaveDeal(dealId, userId);
    }

    @GetMapping(value = "/remove/{dealId}/{userToRemoveId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void removeDealUser(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId,
                               @PathVariable(value = "userToRemoveId") Long userToRemoveId) {
        log.debug("/deal/remove/{dealId}/{userToRemoveId}");
        dealService.removeDealUser(dealId, userId, userToRemoveId);
    }

    @NoAuthentication
    @GetMapping(value = "/confirmation/remove/user", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealResponse confirmRemoveDealUser(@RequestParam("link") String link) {
        log.debug("/deal/confirmation/remove/user");
        return dealService.confirmRemoveDealUser(link);
    }

    @GetMapping(value = "/add/{dealId}/{userToAddId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealResponse addDealUser(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId,
                                    @PathVariable(value = "userToAddId") Long userToAddId) {
        log.debug("/deal/add/{dealId}/{userToChangeId}");
        return dealService.addDealUser(dealId, userId, userToAddId);
    }

    @NoAuthentication
    @GetMapping(value = "/confirmation/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealResponse confirmAddDealUser(@RequestParam("link") String link) {
        log.debug("/deal/confirmation/add");
        return dealService.confirmAddDealUser(link);
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<DealUserWeb> getAllUserDeals(@AuthenticatedUser Long userId) {
        log.debug("/deal/all");
        return dealService.getAllUserDeals(userId);
    }

    @GetMapping(value = "{dealId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealResponse getDeal(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId) {
        log.debug("/deal/{dealId}");
        return dealService.getDeal(dealId, userId);
    }

    @PostMapping(value = "/rename/{dealId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealWeb renameDeal(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId,
                              @RequestBody @Valid DealTitleChangeWeb newTitle, BindingResult result) {
        log.debug("POST deal/rename/{dealId}");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return dealService.renameDeal(dealId, userId, newTitle);
    }

    //todo DEAL DELETION FOR SNAPSHOT USER
//    @DeleteMapping(value = "/{dealId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteDeal(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId) {
        log.debug("DELETE /deal/{dealId}/{userId}");
        dealService.deleteDeal(dealId, userId);
    }
}
