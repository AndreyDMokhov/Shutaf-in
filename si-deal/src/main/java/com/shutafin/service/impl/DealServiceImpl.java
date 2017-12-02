package com.shutafin.service.impl;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.entities.DealUser;
import com.shutafin.model.types.DealStatus;
import com.shutafin.model.types.DealUserPermissionType;
import com.shutafin.model.types.DealUserStatus;
import com.shutafin.model.web.deal.*;
import com.shutafin.repository.DealPanelRepository;
import com.shutafin.repository.DealRepository;
import com.shutafin.repository.DealUserRepository;
import com.shutafin.service.DealPanelService;
import com.shutafin.service.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class DealServiceImpl implements DealService {

    private static final boolean NEED_FULL_ACCESS = true;
    private static final String DEFAULT_DEAL_TITLE = "getroomie deal";

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private DealUserRepository dealUserRepository;

    @Autowired
    private DealPanelRepository dealPanelRepository;

    @Autowired
    private DealPanelService dealPanelService;

    @Override
    public DealWeb initiateDeal(DealWeb dealWeb) {
        Deal deal = new Deal();
        deal.setDealStatus(DealStatus.INITIATED);
        deal.setTitle(dealWeb.getTitle() == null ? DEFAULT_DEAL_TITLE : dealWeb.getTitle());
        deal.setModifiedByUser(dealWeb.getOriginUserId());
        dealRepository.save(deal);

        for (Long userId : dealWeb.getUsers()) {
            DealUser dealUser = new DealUser(userId, deal, DealUserStatus.PENDING, DealUserPermissionType.READ_ONLY);
            dealUserRepository.save(dealUser);
        }

        DealUser dealUserOrigin = new DealUser(dealWeb.getOriginUserId(), deal, DealUserStatus.ACTIVE,
                DealUserPermissionType.CREATE);
        dealUserRepository.save(dealUserOrigin);

        dealWeb.setDealId(deal.getId());
        dealWeb.setTitle(deal.getTitle());
        return dealWeb;
    }

    @Override
    public DealResponse getDeal(Long dealId) {
        Deal deal = dealRepository.findOne(dealId);
        if (deal == null) {
            log.warn("Resource not found exception:");
            log.warn("Deal with ID {} was not found", dealId);
            throw new RuntimeException(String.format("Deal with ID %d was not found", dealId));
        }
        DealResponse dealResponse = new DealResponse();
        dealResponse.setDealId(deal.getId());
        dealResponse.setTitle(deal.getTitle());
        dealResponse.setStatusId(deal.getDealStatus().getCode());

        dealResponse.setUsers(dealUserRepository.findAllByDealId(dealId)
                .stream()
                .map(dealUser -> dealUser.getUserId())
                .collect(Collectors.toList()));

        List<DealPanel> dealPanels = dealPanelRepository.findAllByDealIdAndIsDeletedFalse(dealId);
        if (!dealPanels.isEmpty()) {
            dealResponse.setPanels(dealPanels.stream()
                    .collect(Collectors.toMap(
                            DealPanel::getId,
                            DealPanel::getTitle)
                    )
            );

            dealResponse.setFirstPanel(getFirstDealPanel(dealPanels));
        } else {
            dealResponse.setPanels(new HashMap<>());
        }

        return dealResponse;
    }

    @Override
    public Deal renameDeal(Long dealId, Long userId, NewTitleWeb newTitleWeb) {
        Deal deal = checkDealPermissions(dealId, userId, NEED_FULL_ACCESS);
        deal.setTitle(newTitleWeb.getTitle());
        deal.setModifiedByUser(userId);
        return deal;
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
        } else if (dealUser.getDealUserPermissionType() != DealUserPermissionType.CREATE && needFullAccess) {
            log.warn("User does not have permissions");
            throw new RuntimeException();
        }

        return deal;
    }

    @Override
    public void deleteDeal(Long dealId, Long userId) {
        Deal deal = checkDealPermissions(dealId, userId, NEED_FULL_ACCESS);
        deal.setDealStatus(DealStatus.ARCHIVE);
        deal.setModifiedByUser(userId);
        dealPanelRepository.findAllByDealIdAndIsDeletedFalse(dealId)
                .forEach(dealPanel -> dealPanelService.deleteDealPanel(dealPanel.getId(), userId));
        dealUserRepository.findAllByDealId(dealId)
                .forEach(dealUser ->
                {
                    dealUser.setDealUserStatus(DealUserStatus.REMOVED);
                    dealUser.setDealUserPermissionType(DealUserPermissionType.READ_ONLY);
                });
    }

    @Override
    public void confirmDealUser(Long dealId, Long userId) {
        Deal deal = checkDealPermissions(dealId, userId, !NEED_FULL_ACCESS);
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        dealUser.setDealUserStatus(DealUserStatus.ACTIVE);
        dealUser.setDealUserPermissionType(DealUserPermissionType.CREATE);
        if (deal.getDealStatus() == DealStatus.INITIATED) {
            deal.setDealStatus(DealStatus.ACTIVE);
        }
    }

    @Override
    public List<DealUserWeb> getAllUserDeals(Long userId) {
        List<DealUser> userDeals = dealUserRepository.findAllByUserId(userId);
        if (userDeals == null) {
            log.warn("User {} does not have any deal", userId);
            return null;
        }
        List<DealUserWeb> userDealsWeb = userDeals.stream()
                .map(dealUser -> new DealUserWeb(dealUser.getDeal().getId(),
                        dealUser.getDeal().getTitle(),
                        dealUser.getDeal().getDealStatus().getCode()))
                .collect(Collectors.toList());
        return userDealsWeb;
    }

    private DealPanelResponse getFirstDealPanel(List<DealPanel> dealPanels) {
        DealPanel firstPanel = dealPanels.stream()
                .min(Comparator.comparing(dealPanel -> dealPanel.getCreatedDate()))
                .get();
        DealPanelResponse dealPanelResponse = new DealPanelResponse();
        dealPanelResponse.setPanelId(firstPanel.getId());
        dealPanelResponse.setTitle(firstPanel.getTitle());
        dealPanelResponse.setDocuments(dealPanelService.getDealPanelDocuments(firstPanel.getId()));
        return dealPanelResponse;
    }

}
