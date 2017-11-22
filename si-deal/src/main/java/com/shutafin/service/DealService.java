package com.shutafin.service;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.types.DealStatus;
import com.shutafin.model.web.DealWeb;
import com.shutafin.model.web.NewTitleWeb;

import java.util.List;

public interface DealService {

    Deal initiateDeal(DealWeb dealWeb);
    Deal getDeal(Long dealId, Long userId);
    Deal updateDealStatus(Long dealId, Long userId, DealStatus newDealStatus);
    Deal renameDeal(Long dealId, Long userId, NewTitleWeb newTitleWeb);
    List<DealPanel> getDealPanels(Long dealId, Long userId);
    Deal checkDealPermissions(Long dealId, Long userId, Boolean needFullAccess);
    Boolean confirmDealUser(Long dealId, Long userId);
    Boolean removeDealUser(Long dealId, Long userId);
}
