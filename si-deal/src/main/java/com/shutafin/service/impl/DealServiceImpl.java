package com.shutafin.service.impl;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.entities.DealUser;
import com.shutafin.model.types.DealStatus;
import com.shutafin.model.types.DealUserStatus;
import com.shutafin.model.web.DealWeb;
import com.shutafin.model.web.NewTitleWeb;
import com.shutafin.repository.DealPanelRepository;
import com.shutafin.repository.DealRepository;
import com.shutafin.repository.DealUserRepository;
import com.shutafin.service.DealPanelService;
import com.shutafin.service.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class DealServiceImpl implements DealService {

    private static final boolean NEED_FULL_ACCESS = true;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private DealUserRepository dealUserRepository;

    @Autowired
    private DealPanelRepository dealPanelRepository;

    @Autowired
    private DealPanelService dealPanelService;

    @Override
    public Deal initiateDeal(DealWeb dealWeb) {
        Deal deal = new Deal();
        deal.setDealStatus(DealStatus.INITIATED);
        deal.setTitle(dealWeb.getTitle());
        deal.setModifiedByUser(dealWeb.getOriginUserId());

        for (Long userId : dealWeb.getUserIds()) {
            DealUser dealUser = new DealUser(userId, deal, DealUserStatus.PENDING);
            dealUserRepository.save(dealUser);
        }

        confirmDealUser(deal.getId(), deal.getModifiedByUser());
        return deal;
    }

    @Override
    public Deal getDeal(Long dealId, Long userId) {
        Deal deal = checkDealPermissions(userId, dealId, !NEED_FULL_ACCESS);
        return deal;
    }

    @Override
    public Deal updateDealStatus(Long dealId, Long userId, DealStatus newDealStatus) {
        Deal deal = checkDealPermissions(userId, dealId, NEED_FULL_ACCESS);
        deal.setDealStatus(newDealStatus);
        deal.setModifiedByUser(userId);

        if (newDealStatus.equals(DealStatus.ARCHIVE)) {
            getDealPanels(dealId, userId).forEach(dealPanel ->
                    dealPanelService.deleteDealPanel(dealPanel.getId(), userId));
        }

        return deal;
    }

    @Override
    public Deal renameDeal(Long dealId, Long userId, NewTitleWeb newTitleWeb) {
        Deal deal = checkDealPermissions(userId, dealId, NEED_FULL_ACCESS);
        deal.setTitle(newTitleWeb.getTitle());
        deal.setModifiedByUser(userId);
        return deal;
    }

    @Override
    public List<DealPanel> getDealPanels(Long dealId, Long userId) {
        Deal deal = checkDealPermissions(userId, dealId, !NEED_FULL_ACCESS);
        return dealPanelRepository.findAllByDealId(deal.getId());
    }

    @Override
    public Deal checkDealPermissions(Long dealId, Long userId, Boolean needFullAccess) {
        Deal deal = dealRepository.findOne(dealId);
        if (deal == null) {
            log.warn("Resource not found exception:");
            log.warn("Deal with ID {} was not found", dealId);
            throw new RuntimeException(String.format("Deal with ID %d was not found", dealId));
        }
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        if (dealUser == null) {
            log.warn("User has not ever been in this deal");
            throw new RuntimeException();
        } else if (dealUser.getDealUserStatus() == DealUserStatus.REMOVED && needFullAccess) {
            log.warn("User was removed from this deal");
            throw new RuntimeException();
        }

        return deal;
    }

    @Override
    public Boolean confirmDealUser(Long dealId, Long userId) {
        checkDealPermissions(dealId, userId, !NEED_FULL_ACCESS);
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        dealUser.setDealUserStatus(DealUserStatus.ACTIVE);
        return true;
    }

    @Override
    public Boolean removeDealUser(Long dealId, Long userId) {
        checkDealPermissions(dealId, userId, NEED_FULL_ACCESS);
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        dealUser.setDealUserStatus(DealUserStatus.REMOVED);
        return true;
    }

}
