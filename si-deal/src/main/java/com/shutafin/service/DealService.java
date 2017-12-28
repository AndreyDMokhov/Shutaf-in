package com.shutafin.service;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.web.deal.*;

import java.util.List;

public interface DealService {

    InternalDealWeb initiateDeal(InternalDealWeb dealWeb);
    void confirmDealUser(Long dealId, Long userId);
    void leaveDeal(Long dealId, Long userId);
    void removeDealUser(InternalDealRemoveUserWeb internalDealRemoveUserWeb);
    List<DealUserWeb> getAllUserDeals(Long userId);
    DealResponse getDeal(Long dealId, Long userId);
    InternalDealWeb renameDeal(Long dealId, Long userId, NewTitleWeb newTitleWeb);
    Deal checkDealPermissions(Long dealId, Long userId, Boolean needFullAccess);
    void deleteDeal(Long dealId, Long userId);
}
