package com.shutafin.controller.deal;

import com.shutafin.model.exception.exceptions.DealSelfRemovalException;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.deal.*;
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
    public void removeDealUser(@AuthenticatedUser Long userId,
                               @PathVariable(value = "dealId") Long dealId,
                               @PathVariable(value = "userToRemoveId") Long userToRemoveId) {
        log.debug("/deal/remove/{dealId}/{userToRemoveId}");
        if (userToRemoveId.equals(userId)) {
            log.warn("Cannot vote for self removal. Should use Leave Deal instead!");
            throw new DealSelfRemovalException();
        }
        dealService.removeDealUser(dealId, userId, userToRemoveId);
    }


    @GetMapping(value = "/add/{dealId}/{userToAddId}")
    public DealResponse addDealUser(@AuthenticatedUser Long userId,
                                    @PathVariable(value = "dealId") Long dealId,
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
    public DealAvailableUsersResponse getAvailableUsers(@AuthenticatedUser Long userId,
                                                        @RequestParam("users") List<Long> users) {

        return dealService.getAvailableUsers(userId, users);
    }


    @DeleteMapping(value = "/{dealId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteDeal(@AuthenticatedUser Long userId, @PathVariable(value = "dealId") Long dealId) {
        log.debug("DELETE /deal/{dealId}");
        dealService.deleteDeal(dealId, userId);
    }
}
