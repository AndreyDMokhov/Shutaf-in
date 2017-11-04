package com.shutafin.controller;

import com.shutafin.model.entities.UserDocument;
import com.shutafin.model.exceptions.InputValidationException;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.UserDocumentWeb;
import com.shutafin.service.UserDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/documents")
@Slf4j
public class UserDocumentController {

    @Autowired
    private UserDocumentService userDocumentService;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public UserDocumentWeb addUserDocument(@RequestBody @Valid UserDocumentWeb userDocumentWeb, BindingResult result) {
        log.debug("/documents/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        UserDocument userDocument = userDocumentService.addUserDocument(userDocumentWeb, PermissionType.PRIVATE);

        return new UserDocumentWeb(userDocument.getId(),
                userDocument.getUserId(),
                null,
                userDocument.getCreatedDate().getTime(),
                userDocument.getDocumentType().getCode());
    }

    @RequestMapping(value = "/{userId}/{docId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserDocumentWeb getUserDocument(@PathVariable(value = "userId") Long userId,
                                           @PathVariable(value = "docId") Long userDocumentId) {
        log.debug("GET /documents/{userId}/{docId}");
        UserDocument userDocument = userDocumentService.getUserDocument(userId, userDocumentId);
        return new UserDocumentWeb(userDocument.getId(),
                userDocument.getUserId(),
                userDocument.getDocumentStorage().getDocumentEncoded(),
                userDocument.getCreatedDate().getTime(),
                userDocument.getDocumentType().getCode());
    }

    @DeleteMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserDocument(@RequestParam(value = "userId") Long userId,
                                   @RequestParam(value = "docId") Long userDocumentId) {
        log.debug("DELETE /documents/{userId}/{docId}");
        userDocumentService.deleteUserDocument(userId, userDocumentId);
    }
}
