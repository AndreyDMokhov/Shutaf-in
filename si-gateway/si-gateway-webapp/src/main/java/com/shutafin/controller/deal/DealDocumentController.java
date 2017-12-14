package com.shutafin.controller.deal;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.DealUserDocumentWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.deal.DealDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/deal/documents")
@Slf4j
public class DealDocumentController {

    @Autowired
    private DealDocumentService dealDocumentService;

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealUserDocumentWeb addDealDocument(@AuthenticatedUser User user,
                                               @RequestBody @Valid DealUserDocumentWeb dealUserDocumentWeb,
                                               BindingResult result) {
        log.debug("/documents/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new RuntimeException();
        }

        return dealDocumentService.addDealDocument(dealUserDocumentWeb, user);
    }

    @GetMapping(value = "/{docId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealUserDocumentWeb getDealDocument(@AuthenticatedUser User user,
                                               @PathVariable(value = "docId") Long dealDocumentId) {
        log.debug("GET /documents/{docId}");
        return dealDocumentService.getDealDocument(user, dealDocumentId);
    }

    @PostMapping(value = "/{docId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealUserDocumentWeb renameDealDocument(@AuthenticatedUser User user,
                                                  @PathVariable(value = "docId") Long userDocumentId,
                                                  @RequestBody @Valid NewTitleWeb documentTitle) {
        log.debug("POST /documents/{docId}");
        return dealDocumentService.renameDealDocument(user, userDocumentId, documentTitle);
    }

    @DeleteMapping(value = "/{docId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserDocument(@AuthenticatedUser User user,
                                   @PathVariable(value = "docId") Long userDocumentId) {
        log.debug("DELETE /documents/{userId}/{docId}");
        dealDocumentService.deleteDealDocument(user, userDocumentId);
    }

}

