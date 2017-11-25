package com.shutafin.service;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.web.DealResponse;
import com.shutafin.model.web.DealUserWeb;
import com.shutafin.model.web.DealWeb;
import com.shutafin.model.web.NewTitleWeb;

import java.util.List;

public interface DealService {

    DealWeb initiateDeal(DealWeb dealWeb);
    void confirmDealUser(Long dealId, Long userId);
    List<DealUserWeb> getAllUserDeals(Long userId);
    DealResponse getDeal(Long dealId);
    Deal renameDeal(Long dealId, Long userId, NewTitleWeb newTitleWeb);
    Deal checkDealPermissions(Long dealId, Long userId, Boolean needFullAccess);
}
