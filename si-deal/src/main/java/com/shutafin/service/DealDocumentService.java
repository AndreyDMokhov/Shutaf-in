package com.shutafin.service;

import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.deal.DealUserDocumentWeb;
import com.shutafin.model.web.deal.InternalDealUserDocumentWeb;

public interface DealDocumentService {

    DealDocument addDealDocument(InternalDealUserDocumentWeb dealUserDocumentWeb, PermissionType permissionType);
    DealDocument getDealDocument(Long userId, Long dealDocumentId);
    void deleteDealDocument(Long userId, Long dealDocumentId);
    DealDocument renameDealDocument(Long userId, Long dealDocumentId, String newTitle);

}
