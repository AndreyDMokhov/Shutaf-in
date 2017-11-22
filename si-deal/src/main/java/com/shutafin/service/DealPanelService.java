package com.shutafin.service;

import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.web.DealPanelWeb;

public interface DealPanelService {

    DealPanel addDealPanel(DealPanelWeb dealPanelWeb);
    DealPanel getDealPanel(Long dealFolderId, Long userId);
    DealPanel renameDealPanel(Long dealFolderId, Long userId, String newTitle);
    void deleteDealPanel(Long dealFolderId, Long userId);

}
