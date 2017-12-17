package com.shutafin.controller.deal;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.DealUserWeb;
import com.shutafin.model.web.deal.DealWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
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
    public DealWeb initiateDeal(@AuthenticatedUser User user, @RequestBody @Valid DealWeb dealWeb, BindingResult result) {
        log.debug("/deal/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new RuntimeException();
        }

        return dealService.initiateDeal(dealWeb, user);
    }

    @PutMapping(value = "/{dealId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void confirmDealUser(@AuthenticatedUser User user, @PathVariable(value = "dealId") Long dealId) {
        log.debug("/{dealId}");
        dealService.confirmDealUser(dealId, user);
    }

    @PutMapping(value = "/leave/{dealId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void leaveDeal(@AuthenticatedUser User user, @PathVariable(value = "dealId") Long dealId) {
        log.debug("/leave/{dealId}");
        dealService.leaveDeal(dealId, user);
    }

    @PutMapping(value = "/remove/{dealId}/{userToRemoveId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void removeDealUser(@AuthenticatedUser User user, @PathVariable(value = "dealId") Long dealId,
                               @PathVariable(value = "userToRemoveId") Long userToRemoveId) {
        log.debug("/remove/{dealId}/{userToRemoveId}");
        dealService.removeDealUser(dealId, user, userToRemoveId);
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<DealUserWeb> getAllUserDeals(@AuthenticatedUser User user) {
        log.debug("/all");
        return dealService.getAllUserDeals(user);
    }

    @GetMapping(value = "{dealId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealResponse getDeal(@AuthenticatedUser User user, @PathVariable(value = "dealId") Long dealId) {
        log.debug("/{dealId}");
        return dealService.getDeal(dealId, user);
    }

    @PostMapping(value = "/rename/{dealId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealWeb renameDeal(@AuthenticatedUser User user, @PathVariable(value = "dealId") Long dealId,
                              @RequestBody @Valid NewTitleWeb newTitle, BindingResult result) {
        log.debug("POST /rename/{dealId}");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new RuntimeException();
        }
        return dealService.renameDeal(dealId, user, newTitle);
    }

    @DeleteMapping(value = "/{dealId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteDeal(@AuthenticatedUser User user, @PathVariable(value = "dealId") Long dealId) {
        log.debug("DELETE /deal/{dealId}/{userId}");
        dealService.deleteDeal(dealId, user);
    }
}
