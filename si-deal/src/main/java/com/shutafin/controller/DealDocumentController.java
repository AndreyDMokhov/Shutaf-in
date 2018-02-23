package com.shutafin.controller;

import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.deal.PermissionType;
import com.shutafin.model.web.deal.InternalDealUserDocumentWeb;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
import com.shutafin.service.DealDocumentService;
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

    private static final Long SHIFT_VALUE = 131L;

    @Autowired
    private DealDocumentService dealDocumentService;

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public InternalDealUserDocumentWeb addDealDocument(@RequestBody @Valid InternalDealUserDocumentWeb dealUserDocumentWeb,
                                                       BindingResult result) {
        log.debug("/documents/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        DealDocument dealDocument = dealDocumentService.addDealDocument(dealUserDocumentWeb, PermissionType.DEAL);

        return getDealUserDocumentWeb(dealDocument, false);
    }

    @GetMapping(value = "/{userId}/{docId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public InternalDealUserDocumentWeb getDealDocument(@PathVariable(value = "userId") Long userId,
                                                       @PathVariable(value = "docId") Long dealDocumentId) {
        log.debug("GET /documents/{userId}/{docId}");
        DealDocument dealDocument = dealDocumentService.getDealDocument(userId, dealDocumentId);
        return getDealUserDocumentWeb(dealDocument, true);
    }

    @PostMapping(value = "/{userId}/{docId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public InternalDealUserDocumentWeb renameDealDocument(@PathVariable(value = "userId") Long userId,
                                                          @PathVariable(value = "docId") Long userDocumentId,
                                                          @RequestBody @Valid DealTitleChangeWeb documentTitle) {
        log.debug("POST /documents/{userId}/{docId}");
        DealDocument dealDocument = dealDocumentService.renameDealDocument(userId, userDocumentId, documentTitle.getTitle());
        return getDealUserDocumentWeb(dealDocument, false);
    }

    @DeleteMapping(value = "/{userId}/{docId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserDocument(@PathVariable(value = "userId") Long userId,
                                   @PathVariable(value = "docId") Long userDocumentId) {
        log.debug("DELETE /documents/{userId}/{docId}");
        dealDocumentService.deleteDealDocument(userId, userDocumentId);
    }

    private InternalDealUserDocumentWeb getDealUserDocumentWeb(DealDocument dealDocument, Boolean includeEncoded) {
        InternalDealUserDocumentWeb dealUserDocumentWeb = InternalDealUserDocumentWeb
                .builder()
                .id(dealDocument.getId())
                .userId(dealDocument.getModifiedByUser())
                .dealPanelId(dealDocument.getDealPanel().getId())
                .fileData(null)
                .createdDate(dealDocument.getCreatedDate().getTime())
                .documentTypeId(dealDocument.getDocumentType())
                .documentTitle(dealDocument.getTitle())
                .build();

        if (includeEncoded) {
            dealUserDocumentWeb.setFileData(dealDocument.getDocumentStorage().getDocumentEncoded());
        }
        return dealUserDocumentWeb;
    }
}
