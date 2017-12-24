package com.shutafin.service.deal;

import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.DealUserWeb;
import com.shutafin.model.web.deal.DealWeb;
import com.shutafin.model.web.deal.NewTitleWeb;

import java.util.List;

public interface DealService {

    DealWeb initiateDeal(DealWeb dealWeb, Long userId);
    void confirmDealUser(Long dealId, Long userId);
    void leaveDeal(Long dealId, Long userId);
    void removeDealUser(Long dealId, Long userOriginId, Long userToRemoveId);
    List<DealUserWeb> getAllUserDeals(Long userId);
    DealResponse getDeal(Long dealId, Long userId);
    DealWeb renameDeal(Long dealId, Long userId, NewTitleWeb newTitleWeb);
    void deleteDeal(Long dealId, Long userId);
}
