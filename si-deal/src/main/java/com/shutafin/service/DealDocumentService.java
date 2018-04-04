package com.shutafin.service;

import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.web.deal.DealUserPermissionType;
import com.shutafin.model.web.deal.InternalDealUserDocumentWeb;
import com.shutafin.model.web.deal.PermissionType;

public interface DealDocumentService {

    DealDocument addDealDocument(InternalDealUserDocumentWeb dealUserDocumentWeb, PermissionType permissionType);
    DealDocument getDealDocument(Long userId, Long dealDocumentId);
    void deleteDealDocument(Long userId, Long dealDocumentId);
    DealDocument renameDealDocument(Long userId, Long dealDocumentId, String newTitle);
    void grantDealUserDocumentAccessPermissions(Long userId, Long dealId, DealUserPermissionType permissionLevel);

}
