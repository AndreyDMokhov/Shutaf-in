package com.shutafin.controller.deal;

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

    @PostMapping(value = "/")
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


    @PutMapping(value = "/leave/{dealId}")
    public DealResponse leaveDeal(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId) {
        log.debug("/deal/leave/{dealId}");
        return dealService.leaveDeal(dealId, userId);
    }

    @GetMapping(value = "/remove/{dealId}/{userToRemoveId}")
    public void removeDealUser(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId,
                               @PathVariable(value = "userToRemoveId") Long userToRemoveId) {
        log.debug("/deal/remove/{dealId}/{userToRemoveId}");
        dealService.removeDealUser(dealId, userId, userToRemoveId);
    }


    @GetMapping(value = "/add/{dealId}/{userToAddId}")
    public DealResponse addDealUser(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId,
                                    @PathVariable(value = "userToAddId") Long userToAddId) {
        log.debug("/deal/add/{dealId}/{userToChangeId}");
        return dealService.addDealUser(dealId, userId, userToAddId);
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

    @PostMapping(value = "/rename/{dealId}")
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

    @GetMapping("/available-users")
    public List<Long> getAvailableUsers(@RequestParam("users") List<Long> users) {

        return dealService.getAvailableUsers(users);
    }

    //todo DEAL DELETION FOR SNAPSHOT USER
//    @DeleteMapping(value = "/{dealId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteDeal(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId) {
        log.debug("DELETE /deal/{dealId}/{userId}");
        dealService.deleteDeal(dealId, userId);
    }
}
