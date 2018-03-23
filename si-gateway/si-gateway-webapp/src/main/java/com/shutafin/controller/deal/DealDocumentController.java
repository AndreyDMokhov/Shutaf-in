package com.shutafin.controller.deal;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.deal.DealUserDocumentWeb;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
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

    @PostMapping(value = "/")
    public DealUserDocumentWeb addDealDocument(@AuthenticatedUser Long userId,
                                               @RequestBody @Valid DealUserDocumentWeb dealUserDocumentWeb,
                                               BindingResult result) {
        log.debug("/documents/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        return dealDocumentService.addDealDocument(dealUserDocumentWeb, userId);
    }

    @GetMapping(value = "/{docId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealUserDocumentWeb getDealDocument(@AuthenticatedUser Long userId,
                                               @PathVariable(value = "docId") Long dealDocumentId) {
        log.debug("GET /documents/{docId}");
        return dealDocumentService.getDealDocument(userId, dealDocumentId);
    }

    @PostMapping(value = "/{docId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealUserDocumentWeb renameDealDocument(@AuthenticatedUser Long userId,
                                                  @PathVariable(value = "docId") Long userDocumentId,
                                                  @RequestBody @Valid DealTitleChangeWeb documentTitle) {
        log.debug("POST /documents/{docId}");
        return dealDocumentService.renameDealDocument(userId, userDocumentId, documentTitle);
    }

    @DeleteMapping(value = "/{docId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserDocument(@AuthenticatedUser Long userId,
                                   @PathVariable(value = "docId") Long userDocumentId) {
        log.debug("DELETE /documents/{docId}");
        dealDocumentService.deleteDealDocument(userId, userDocumentId);
    }

}

