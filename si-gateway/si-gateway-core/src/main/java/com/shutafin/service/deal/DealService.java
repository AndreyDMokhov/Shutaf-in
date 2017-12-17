package com.shutafin.service.deal;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.DealUserWeb;
import com.shutafin.model.web.deal.DealWeb;
import com.shutafin.model.web.deal.NewTitleWeb;

import java.util.List;

public interface DealService {

    DealWeb initiateDeal(DealWeb dealWeb, User user);
    void confirmDealUser(Long dealId, User user);
    void leaveDeal(Long dealId, User user);
    void removeDealUser(Long dealId, User userOrigin, Long userToRemoveId);
    List<DealUserWeb> getAllUserDeals(User user);
    DealResponse getDeal(Long dealId, User user);
    DealWeb renameDeal(Long dealId, User user, NewTitleWeb newTitleWeb);
    void deleteDeal(Long dealId, User user);
}
