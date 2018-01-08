package com.shutafin.service.impl;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.entities.DealUser;
import com.shutafin.model.exception.exceptions.NoPermissionException;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.exception.exceptions.SystemException;
import com.shutafin.model.types.DealStatus;
import com.shutafin.model.types.DealUserPermissionType;
import com.shutafin.model.types.DealUserStatus;
import com.shutafin.model.web.deal.*;
import com.shutafin.repository.*;
import com.shutafin.service.DealPanelService;
import com.shutafin.service.DealService;
import com.shutafin.service.DealSnapshotService;
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

    @Autowired
    private DealPanelUserRepository dealPanelUserRepository;

    @Autowired
    private DealDocumentUserRepository dealDocumentUserRepository;

    @Autowired
    private DealSnapshotService dealSnapshotService;

    @Override
    public InternalDealWeb initiateDeal(InternalDealWeb dealWeb) {
        Deal deal = new Deal();
        deal.setDealStatus(DealStatus.INITIATED);
        deal.setTitle(dealWeb.getTitle() == null ? DEFAULT_DEAL_TITLE : dealWeb.getTitle());
        deal.setModifiedByUser(dealWeb.getOriginUserId());
        dealWeb.getUsers().remove(dealWeb.getOriginUserId());
        dealRepository.save(deal);

        for (Long userId : dealWeb.getUsers()) {
            if (!userHasActiveDeal(userId)) {
                DealUser dealUser = new DealUser(userId, deal, DealUserStatus.PENDING, DealUserPermissionType.READ_ONLY);
                dealUserRepository.save(dealUser);
            } else {
                log.warn("User {} has already active deal", userId);
                throw new SystemException(String.format("User %d has already active deal", userId));
            }
        }

        DealUser dealUserOrigin = new DealUser(dealWeb.getOriginUserId(), deal, DealUserStatus.ACTIVE,
                DealUserPermissionType.CREATE);
        dealUserRepository.save(dealUserOrigin);

        dealWeb.setDealId(deal.getId());
        dealWeb.setTitle(deal.getTitle());
        return dealWeb;
    }

    private boolean userHasActiveDeal(Long userId) {
        List<DealUser> userDeals = dealUserRepository.findAllByUserId(userId);
        if (userDeals != null && !userDeals.isEmpty()) {
            for (DealUser dealUser : userDeals) {
                if (dealUser.getDealUserStatus() == DealUserStatus.ACTIVE) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public DealResponse getDeal(Long dealId, Long userId) {
        Deal deal = checkDealPermissions(dealId, userId, !NEED_FULL_ACCESS);
        DealUser currentDealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        if (currentDealUser.getDealUserPermissionType() == DealUserPermissionType.READ_ONLY) {
            return dealSnapshotService.getDealSnapshot(dealId, userId);
        }
        DealResponse dealResponse = new DealResponse();
        dealResponse.setDealId(deal.getId());
        dealResponse.setTitle(deal.getTitle());
        dealResponse.setStatusId(deal.getDealStatus().getCode());

        dealResponse.setUsers(dealUserRepository.findAllByDealIdAndDealUserStatus(dealId, DealUserStatus.ACTIVE)
                .stream()
                .map(DealUser::getUserId)
                .collect(Collectors.toList()));

        List<DealPanel> dealPanels = dealPanelRepository.findAllByDealId(dealId);
        if (!dealPanels.isEmpty()) {
            dealPanels = dealPanels.stream()
                    .filter(dealPanel -> dealPanelUserRepository
                            .findByDealPanelIdAndUserId(dealPanel.getId(), userId)
                            .getDealUserPermissionType() != DealUserPermissionType.NO_READ)
                    .collect(Collectors.toList());

            dealResponse.setPanels(dealPanels.stream()
                    .collect(Collectors.toMap(
                            DealPanel::getId,
                            DealPanel::getTitle)
                    )
            );

            dealResponse.setFirstPanel(getFirstDealPanel(dealPanels, userId));
        } else {
            dealResponse.setPanels(new HashMap<>());
        }

        return dealResponse;
    }

    @Override
    public InternalDealWeb renameDeal(Long dealId, Long userId, NewTitleWeb newTitleWeb) {
        Deal deal = checkDealPermissions(dealId, userId, NEED_FULL_ACCESS);
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        if (dealUser.getDealUserPermissionType() == DealUserPermissionType.READ_ONLY) {
            return dealSnapshotService.renameDealSnapshot(dealId, userId, newTitleWeb.getTitle());
        } else {
            deal.setTitle(newTitleWeb.getTitle());
            deal.setModifiedByUser(userId);
            return new InternalDealWeb(dealId, null, deal.getTitle(), null);
        }
    }

    @Override
    public Deal checkDealPermissions(Long dealId, Long userId, Boolean needFullAccess) {
        Deal deal = dealRepository.findOne(dealId);
        if (deal == null) {
            log.warn("Deal with ID {} was not found", dealId);
            throw new ResourceNotFoundException(String.format("Deal with ID %d was not found", dealId));
        }
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        if (dealUser == null) {
            log.warn("User has not ever been in this deal");
            throw new SystemException(String.format("User with ID %d has not ever been in the deal with ID %d",
                    userId, dealId));
        } else if (dealUser.getDealUserPermissionType() != DealUserPermissionType.CREATE && needFullAccess) {
            log.warn("User does not have permissions");
            throw new NoPermissionException("User does not have permissions");
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
    public void leaveDeal(Long dealId, Long userId) {
        Deal deal = checkDealPermissions(dealId, userId, !NEED_FULL_ACCESS);
        dealSnapshotService.saveDealSnapshot(deal, userId);
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        dealUser.setDealUserStatus(DealUserStatus.LEAVED);
        dealUser.setDealUserPermissionType(DealUserPermissionType.READ_ONLY);

        setPermissionToReadOnly(deal, userId);
    }

    @Override
    public void removeDealUser(InternalDealUserWeb internalDealUserWeb) {
        Deal deal = checkDealPermissions(internalDealUserWeb.getDealId(),
                internalDealUserWeb.getUserOriginId(), NEED_FULL_ACCESS);
        Long userToRemoveId = internalDealUserWeb.getUserToChangeId();
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(deal.getId(), userToRemoveId);
        if (dealUser == null) {
            log.warn("User {} was not included to deal {}", userToRemoveId,
                    internalDealUserWeb.getDealId());
            throw new SystemException(String.format("User %d was not included to deal %d", userToRemoveId,
                    internalDealUserWeb.getDealId()));
        }
        dealSnapshotService.saveDealSnapshot(deal, userToRemoveId);
        dealUser.setDealUserStatus(DealUserStatus.REMOVED);
        dealUser.setDealUserPermissionType(DealUserPermissionType.READ_ONLY);

        setPermissionToReadOnly(deal, userToRemoveId);
    }

    @Override
    public void addDealUser(InternalDealUserWeb internalDealUserWeb) {
        Deal deal = checkDealPermissions(internalDealUserWeb.getDealId(),
                internalDealUserWeb.getUserOriginId(), NEED_FULL_ACCESS);
        Long userToAddId = internalDealUserWeb.getUserToChangeId();
        if (userHasActiveDeal(userToAddId)) {
            log.warn("User {} has already active deal", userToAddId);
            throw new SystemException(String.format("User %d has already active deal", userToAddId));
        }
        DealUser dealUser = new DealUser(userToAddId, deal, DealUserStatus.PENDING, DealUserPermissionType.READ_ONLY);
        dealUserRepository.save(dealUser);
    }

    private void setPermissionToReadOnly(Deal deal, Long userToRemoveId) {
        dealPanelUserRepository
                .findAllByDealPanelDealIdAndUserId(deal.getId(), userToRemoveId)
                .forEach(dealPanelUser -> {
                    if (dealPanelUser.getDealUserPermissionType() == DealUserPermissionType.CREATE)
                        dealPanelUser.setDealUserPermissionType(DealUserPermissionType.READ_ONLY);
                });

        dealDocumentUserRepository
                .findAllByDealDocumentDealPanelDealIdAndUserId(deal.getId(), userToRemoveId)
                .forEach(dealDocumentUser -> {
                    if (dealDocumentUser.getDealUserPermissionType() == DealUserPermissionType.CREATE)
                        dealDocumentUser.setDealUserPermissionType(DealUserPermissionType.READ_ONLY);
                });
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

    private DealPanelResponse getFirstDealPanel(List<DealPanel> dealPanels, Long userId) {
        DealPanel firstPanel = dealPanels.stream()
                .min(Comparator.comparing(dealPanel -> dealPanel.getCreatedDate()))
                .get();
        DealPanelResponse dealPanelResponse = getDealPanelResponse(userId, firstPanel);
        return dealPanelResponse;
    }

    private DealPanelResponse getDealPanelResponse(Long userId, DealPanel dealPanel) {
        DealPanelResponse dealPanelResponse = new DealPanelResponse();
        dealPanelResponse.setPanelId(dealPanel.getId());
        dealPanelResponse.setTitle(dealPanel.getTitle());
        dealPanelResponse.setDocuments(dealPanelService.getDealPanelDocuments(dealPanel.getId(), userId));
        return dealPanelResponse;
    }

}
