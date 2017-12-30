package com.shutafin.service;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.InternalDealWeb;

public interface DealSnapshotService {
    void saveDealSnapshot(Deal deal, Long userId);
    DealResponse getDealSnapshot(Long dealId, Long userId);
    InternalDealWeb renameDealSnapshot(Long dealId, Long userId, String newTitle);

    DealPanelResponse getDealPanelFromSnapshot(Long dealPanelId, Long userId);
    DealPanelResponse renameDealPanelInSnapshot(Long dealPanelId, Long userId, String newTitle);
    void deleteDealPanelFromSnapshot(Long dealPanelId, Long userId);

    DealDocument getDealDocumentFromSnapshot(DealDocument dealDocument, Long userId);
    DealDocument renameDealDocumentInSnapshot(DealDocument dealDocument, Long userId, String newTitle);
    void deleteDealDocumentFromSnapshot(Long dealDocumentId, Long userId);
}
