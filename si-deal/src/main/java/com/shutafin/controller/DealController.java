package com.shutafin.controller;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.DealUserWeb;
import com.shutafin.model.web.deal.DealWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
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
    DealWeb initiateDeal(@RequestBody @Valid DealWeb dealWeb, BindingResult result) {
        log.debug("/deal/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new RuntimeException();
        }

        return dealService.initiateDeal(dealWeb);
    }

    @PutMapping(value = "/{dealId}/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    void confirmDealUser(@PathVariable(value = "dealId") Long dealId,
                         @PathVariable(value = "userId") Long userId) {
        log.debug("/{dealId}/{userId}");
        dealService.confirmDealUser(dealId, userId);
    }

    @GetMapping(value = "/all/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<DealUserWeb> getAllUserDeals(@PathVariable(value = "userId") Long userId) {
        log.debug("/all/{userId}");
        return dealService.getAllUserDeals(userId);
    }

    @GetMapping(value = "{dealId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    DealResponse getDeal(@PathVariable(value = "dealId") Long dealId) {
        log.debug("/{dealId}");
        return dealService.getDeal(dealId);
    }

    @PostMapping(value = "/rename/{dealId}/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    DealWeb renameDeal(@PathVariable(value = "dealId") Long dealId, @PathVariable(value = "userId") Long userId,
                       @RequestBody @Valid NewTitleWeb newTitle, BindingResult result) {
        log.debug("POST /rename/{dealId}/{userId}");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new RuntimeException();
        }
        Deal deal = dealService.renameDeal(dealId, userId, newTitle);
        return new DealWeb(deal.getId(), null, deal.getTitle(), null);
    }

    @DeleteMapping(value = "/{dealId}/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    void deleteDeal(@PathVariable(value = "dealId") Long dealId, @PathVariable(value = "userId") Long userId) {
        log.debug("DELETE /deal/{dealId}/{userId}");
        dealService.deleteDeal(dealId, userId);
    }
}