package com.shutafin.service.deal;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.DealDocumentWeb;
import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.NewTitleWeb;

import java.util.List;

public interface DealPanelService {

    DealPanelResponse addDealPanel(DealPanelWeb dealPanelWeb, User user);
    DealPanelResponse getDealPanel(Long dealPanelId, User user);
    DealPanelResponse renameDealPanel(Long dealPanelId, User user, NewTitleWeb newTitle);
    void deleteDealPanel(Long dealPanelId, User user);
}
