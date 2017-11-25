package com.shutafin.controller;

import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.DealUserDocumentWeb;
import com.shutafin.model.web.NewTitleWeb;
import com.shutafin.service.DealDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/documents")
@Slf4j
public class DealDocumentController {

    @Autowired
    private DealDocumentService dealDocumentService;

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DealUserDocumentWeb addDealDocument(@RequestBody @Valid DealUserDocumentWeb dealUserDocumentWeb, BindingResult result) {
        log.debug("/documents/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new RuntimeException();
        }

        DealDocument dealDocument = dealDocumentService.addDealDocument(dealUserDocumentWeb, PermissionType.DEAL);

        return getDealUserDocumentWeb(dealDocument, false);
    }

    @GetMapping(value = "/{userId}/{docId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealUserDocumentWeb getDealDocument(@PathVariable(value = "userId") Long userId,
                                               @PathVariable(value = "docId") Long dealDocumentId) {
        log.debug("GET /documents/{userId}/{docId}");
        DealDocument dealDocument = dealDocumentService.getDealDocument(userId, dealDocumentId);
        return getDealUserDocumentWeb(dealDocument, true);
    }

    @PostMapping(value = "/{userId}/{docId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public DealUserDocumentWeb renameDealDocument(@PathVariable(value = "userId") Long userId,
                                                  @PathVariable(value = "docId") Long userDocumentId,
                                                  @RequestBody @Valid NewTitleWeb documentTitle) {
        log.debug("POST /documents/{userId}/{docId}");
        DealDocument dealDocument = dealDocumentService.renameDealDocument(userId, userDocumentId, documentTitle.getTitle());
        return getDealUserDocumentWeb(dealDocument, false);
    }

    @DeleteMapping(value = "/{userId}/{docId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserDocument(@RequestParam(value = "userId") Long userId,
                                   @RequestParam(value = "docId") Long userDocumentId) {
        log.debug("DELETE /documents/{userId}/{docId}");
        dealDocumentService.deleteDealDocument(userId, userDocumentId);
    }

    private DealUserDocumentWeb getDealUserDocumentWeb(DealDocument dealDocument, Boolean includeEncoded) {
        DealUserDocumentWeb dealUserDocumentWeb = new DealUserDocumentWeb(dealDocument.getId(),
                dealDocument.getModifiedByUser(),
                dealDocument.getDealPanel().getId(),
                null,
                dealDocument.getCreatedDate().getTime(),
                dealDocument.getDocumentType().getCode(),
                dealDocument.getTitle());
        if (includeEncoded) {
            dealUserDocumentWeb.setFileData(dealDocument.getDocumentStorage().getDocumentEncoded());
        }
        return dealUserDocumentWeb;
    }
}
