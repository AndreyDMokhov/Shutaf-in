package com.shutafin.controller.deal;

import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.service.deal.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deal/confirmation")
@Slf4j
public class DealConfirmationController {

    @Autowired
    private DealService dealService;

    @NoAuthentication
    @GetMapping(value = "/initialize")
    public DealResponse confirmDealUser(@RequestParam("link") String link) {
        log.debug("/deal/confirmation");
        return dealService.confirmDealUser(link);
    }

    @NoAuthentication
    @GetMapping(value = "/remove")
    public DealResponse confirmRemoveDealUser(@RequestParam("link") String link) {
        log.debug("/deal/confirmation/remove");
        return dealService.confirmRemoveDealUser(link);
    }

    @NoAuthentication
    @GetMapping(value = "/add")
    public DealResponse confirmAddDealUser(@RequestParam("link") String link) {
        log.debug("/deal/confirmation/add");
        return dealService.confirmAddDealUser(link);
    }


}
