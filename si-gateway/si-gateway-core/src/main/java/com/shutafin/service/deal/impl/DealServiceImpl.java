package com.shutafin.service.deal.impl;

import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.DealUserWeb;
import com.shutafin.model.web.deal.DealWeb;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
import com.shutafin.sender.deal.DealControllerSender;
import com.shutafin.service.deal.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class DealServiceImpl implements DealService {

    @Autowired
    private DealControllerSender dealControllerSender;

    @Override
    public DealWeb initiateDeal(DealWeb dealWeb, Long userId) {
        return dealControllerSender.initiateDeal(dealWeb, userId);
    }

    @Override
    public void confirmDealUser(Long dealId, Long userId) {
        dealControllerSender.confirmDealUser(dealId, userId);
    }

    @Override
    public void leaveDeal(Long dealId, Long userId) {
        dealControllerSender.leaveDeal(dealId, userId);
    }

    @Override
    public void removeDealUser(Long dealId, Long userOriginId, Long userToRemoveId) {
        dealControllerSender.removeDealUser(dealId, userOriginId, userToRemoveId);
    }

    @Override
    public void addDealUser(Long dealId, Long userOriginId, Long userToAddId) {
        dealControllerSender.addDealUser(dealId, userOriginId, userToAddId);
    }

    @Override
    public List<DealUserWeb> getAllUserDeals(Long userId) {
        return dealControllerSender.getAllUserDeals(userId);
    }

    @Override
    public DealResponse getDeal(Long dealId, Long userId) {
        return dealControllerSender.getDeal(dealId, userId);
    }

    @Override
    public DealWeb renameDeal(Long dealId, Long userId, DealTitleChangeWeb dealTitleChangeWeb) {
        return dealControllerSender.renameDeal(dealId, userId, dealTitleChangeWeb);
    }

    @Override
    public void deleteDeal(Long dealId, Long userId) {
        dealControllerSender.deleteDeal(dealId, userId);
    }
}
