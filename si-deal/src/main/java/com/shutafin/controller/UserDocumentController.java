package com.shutafin.controller;

import com.shutafin.model.entities.UserDocument;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.UserDocumentTitleWeb;
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
            throw new RuntimeException();
        }

        UserDocument userDocument = userDocumentService.addUserDocument(userDocumentWeb, PermissionType.PRIVATE);

        return getUserDocumentWeb(userDocument, false);
    }

    @RequestMapping(value = "/{userId}/{docId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserDocumentWeb getUserDocument(@PathVariable(value = "userId") Long userId,
                                           @PathVariable(value = "docId") Long userDocumentId) {
        log.debug("GET /documents/{userId}/{docId}");
        UserDocument userDocument = userDocumentService.getUserDocument(userId, userDocumentId);
        return getUserDocumentWeb(userDocument, true);
    }

    @RequestMapping(value = "/{userId}/{docId}", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserDocumentWeb renameUserDocument(@PathVariable(value = "userId") Long userId,
                                              @PathVariable(value = "docId") Long userDocumentId,
                                              @RequestBody @Valid UserDocumentTitleWeb documentTitle) {
        log.debug("POST /documents/{userId}/{docId}");
        UserDocument userDocument = userDocumentService.renameUserDocument(userId, userDocumentId, documentTitle.getTitle());
        return getUserDocumentWeb(userDocument, false);
    }

    @DeleteMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserDocument(@RequestParam(value = "userId") Long userId,
                                   @RequestParam(value = "docId") Long userDocumentId) {
        log.debug("DELETE /documents/{userId}/{docId}");
        userDocumentService.deleteUserDocument(userId, userDocumentId);
    }

    private UserDocumentWeb getUserDocumentWeb(UserDocument userDocument, Boolean includeEncoded) {
        UserDocumentWeb userDocumentWeb = new UserDocumentWeb(userDocument.getId(),
                userDocument.getUserId(),
                null,
                userDocument.getCreatedDate().getTime(),
                userDocument.getDocumentType().getCode(),
                userDocument.getTitle());
        if (includeEncoded) {
            userDocumentWeb.setFileData(userDocument.getDocumentStorage().getDocumentEncoded());
        }
        return userDocumentWeb;
    }
}
