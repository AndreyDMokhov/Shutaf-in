package com.shutafin.service;

import com.shutafin.model.entities.UserDocument;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.UserDocumentWeb;

public interface UserDocumentService {

    UserDocument addUserDocument(UserDocumentWeb userDocumentWeb, PermissionType permissionType);
    UserDocument getUserDocument(Long userId, Long userDocumentId);
    void deleteUserDocument(Long userId, Long userDocumentId);
    UserDocument renameUserDocument(Long userId, Long userDocumentId, String newTitle);

}
