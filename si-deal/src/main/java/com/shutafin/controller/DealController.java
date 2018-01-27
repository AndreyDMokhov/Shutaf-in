package com.shutafin.controller;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.deal.*;
import com.shutafin.service.DealService;
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
    public InternalDealWeb initiateDeal(@RequestBody @Valid InternalDealWeb dealWeb, BindingResult result) {
        log.debug("/deal/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        return dealService.initiateDeal(dealWeb);
    }

    @PutMapping(value = "/{dealId}/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void confirmDealUser(@PathVariable(value = "dealId") Long dealId,
                         @PathVariable(value = "userId") Long userId) {
        log.debug("/{dealId}/{userId}");
        dealService.confirmDealUser(dealId, userId);
    }

    @PutMapping(value = "/leave/{dealId}/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void leaveDeal(@PathVariable(value = "dealId") Long dealId, @PathVariable(value = "userId") Long userId) {
        log.debug("/leave/{dealId}/{userId}");
        dealService.leaveDeal(dealId, userId);
    }

    @PutMapping(value = "/remove", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void removeDealUser(@RequestBody InternalDealUserWeb internalDealUserWeb) {
        log.debug("/remove");
        dealService.removeDealUser(internalDealUserWeb);
    }

    @PutMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addDealUser(@RequestBody InternalDealUserWeb internalDealUserWeb) {
        log.debug("/add");
        dealService.addDealUser(internalDealUserWeb);
    }

    @GetMapping(value = "/all/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<DealUserWeb> getAllUserDeals(@PathVariable(value = "userId") Long userId) {
        log.debug("/all/{userId}");
        return dealService.getAllUserDeals(userId);
    }

    @GetMapping(value = "/{dealId}/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealResponse getDeal(@PathVariable(value = "dealId") Long dealId, @PathVariable(value = "userId") Long userId) {
        log.debug("GET /{dealId}/{userId}");
        return dealService.getDeal(dealId, userId);
    }

    @PostMapping(value = "/rename/{dealId}/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public InternalDealWeb renameDeal(@PathVariable(value = "dealId") Long dealId, @PathVariable(value = "userId") Long userId,
                                      @RequestBody @Valid DealTitleChangeWeb newTitle, BindingResult result) {
        log.debug("POST /rename/{dealId}/{userId}");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return dealService.renameDeal(dealId, userId, newTitle);
    }

    @DeleteMapping(value = "/{dealId}/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteDeal(@PathVariable(value = "dealId") Long dealId, @PathVariable(value = "userId") Long userId) {
        log.debug("DELETE /deal/{dealId}/{userId}");
        dealService.deleteDeal(dealId, userId);
    }
}