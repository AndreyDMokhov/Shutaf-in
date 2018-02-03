package com.shutafin.service.deal;

import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.DealTitleChangeWeb;

public interface DealPanelService {

    DealPanelResponse addDealPanel(DealPanelWeb dealPanelWeb, Long userId);
    DealPanelResponse getDealPanel(Long dealPanelId, Long userId);
    DealPanelResponse renameDealPanel(Long dealPanelId, Long userId, DealTitleChangeWeb newTitle);
    void deleteDealPanel(Long dealPanelId, Long userId);
}
