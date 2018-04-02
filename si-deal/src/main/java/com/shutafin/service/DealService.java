package com.shutafin.service;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.web.deal.*;

import java.util.List;

public interface DealService {

    InternalDealWeb initiateDeal(InternalDealWeb dealWeb);
    void confirmDealUser(Long dealId, Long userId);
    void leaveDeal(Long dealId, Long userId);
    void removeDealUser(InternalDealUserWeb internalDealUserWeb);
    void addDealUser(InternalDealUserWeb internalDealUserWeb);
    void confirmAddDealUser(InternalDealUserWeb internalDealUserWeb);
    List<DealUserWeb> getAllUserDeals(Long userId);
    DealResponse getDeal(Long dealId, Long userId);
    InternalDealWeb renameDeal(Long dealId, Long userId, DealTitleChangeWeb dealTitleChangeWeb);
    Deal checkDealPermissions(Long dealId, Long userId, Boolean needFullAccess);
    void deleteDeal(Long dealId, Long userId);

    List<Long> getAvailableUsers(List<Long> users);
}
