package com.shutafin.service;

import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.DealUserDocumentWeb;

public interface DealDocumentService {

    DealDocument addDealDocument(DealUserDocumentWeb dealUserDocumentWeb, PermissionType permissionType);
    DealDocument getDealDocument(Long userId, Long dealDocumentId);
    void deleteDealDocument(Long userId, Long dealDocumentId);
    DealDocument renameDealDocument(Long userId, Long dealDocumentId, String newTitle);

}
