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
    List<DealUserWeb> getAllUserDeals(User user);
    DealResponse getDeal(Long dealId);
    DealWeb renameDeal(Long dealId, User user, NewTitleWeb newTitleWeb);
    void deleteDeal(Long dealId, User user);
}
