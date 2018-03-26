package com.shutafin.service.deal;

import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
import com.shutafin.model.web.deal.DealUserWeb;
import com.shutafin.model.web.deal.DealWeb;

import java.util.List;

public interface DealService {

    DealWeb initiateDeal(DealWeb dealWeb, Long userId);
    DealResponse confirmDealUser(String link);
    DealResponse leaveDeal(Long dealId, Long userId);
    void removeDealUser(Long dealId, Long userOriginId, Long userToRemoveId);
    DealResponse confirmRemoveDealUser(String link);
    DealResponse addDealUser(Long dealId, Long userOriginId, Long userToAddId);
    DealResponse confirmAddDealUser(String link);
    List<DealUserWeb> getAllUserDeals(Long userId);
    DealResponse getDeal(Long dealId, Long userId);
    DealWeb renameDeal(Long dealId, Long userId, DealTitleChangeWeb dealTitleChangeWeb);
    void deleteDeal(Long dealId, Long userId);
}
