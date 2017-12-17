package com.shutafin.service;

import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.web.deal.DealDocumentWeb;
import com.shutafin.model.web.deal.DealPanelWeb;

import java.util.List;

public interface DealPanelService {

    DealPanel addDealPanel(DealPanelWeb dealPanelWeb);
    DealPanel getDealPanel(Long dealPanelId, Long userId);
    DealPanel renameDealPanel(Long dealPanelId, Long userId, String newTitle);
    void deleteDealPanel(Long dealPanelId, Long userId);
    List<DealDocumentWeb> getDealPanelDocuments(Long dealPanelId, Long userId);
}
