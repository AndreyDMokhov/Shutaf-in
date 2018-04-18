package com.shutafin.service;

import com.shutafin.model.web.deal.DealDocumentWeb;
import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.DealUserPermissionType;

import java.util.List;

public interface DealPanelService {

    DealPanelResponse addDealPanel(DealPanelWeb dealPanelWeb);
    DealPanelResponse getDealPanel(Long dealPanelId, Long userId);
    DealPanelResponse renameDealPanel(Long dealPanelId, Long userId, String newTitle);
    void deleteDealPanel(Long dealPanelId, Long userId);
    List<DealDocumentWeb> getDealPanelDocuments(Long dealPanelId, Long userId);
    void grantDealUserPanelAccessPermissions(Long userId, Long dealId, DealUserPermissionType permissionLevel);
}
