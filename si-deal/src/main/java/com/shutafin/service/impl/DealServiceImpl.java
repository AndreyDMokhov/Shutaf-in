package com.shutafin.service.impl;

import com.shutafin.model.base.AbstractEntity;
import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.entities.DealUser;
import com.shutafin.model.exception.exceptions.*;
import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.model.web.deal.*;
import com.shutafin.repository.*;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.sender.matching.UserMatchControllerSender;
import com.shutafin.service.DealDocumentService;
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

    @Autowired
    private UserAccountControllerSender userAccountControllerSender;

    @Autowired
    private UserMatchControllerSender userMatchControllerSender;

    @Autowired
    private DealDocumentService dealDocumentService;

    @Override
    public InternalDealWeb initiateDeal(InternalDealWeb dealWeb) {
        Deal deal = new Deal();
        deal.setDealStatus(DealStatus.INITIATED);
        deal.setTitle(dealWeb.getTitle() == null ? DEFAULT_DEAL_TITLE : dealWeb.getTitle());
        deal.setModifiedByUser(dealWeb.getOriginUserId());
        dealRepository.save(deal);

        dealWeb.getUsers().remove(dealWeb.getOriginUserId());

        for (Long userId : dealWeb.getUsers()) {
            if (!userHasActiveDeal(userId)) {
                DealUser dealUser = new DealUser(userId, deal, DealUserStatus.PENDING, DealUserPermissionType.READ_ONLY);
                dealUserRepository.save(dealUser);
            } else {
                log.warn("User {} has already active deal", userId);
                throw new MultipleDealsException(String.format("User %d has already active deal", userId));
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
        if (!userDeals.isEmpty()) {
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
        if (currentDealUser.getDealUserPermissionType() == DealUserPermissionType.READ_ONLY && currentDealUser.getDealUserStatus() == DealUserStatus.PENDING) {
            throw new DealNotConfirmedException();
        }
        if (currentDealUser.getDealUserPermissionType() == DealUserPermissionType.READ_ONLY) {
            return dealSnapshotService.getDealSnapshot(dealId, userId);
        }
        DealResponse dealResponse = new DealResponse();
        dealResponse.setDealId(deal.getId());
        dealResponse.setTitle(deal.getTitle());
        dealResponse.setStatusId(deal.getDealStatus());

        List<Long> userIds = dealUserRepository.findAllByDealIdAndDealUserStatus(dealId, DealUserStatus.ACTIVE)
                .stream()
                .map(DealUser::getUserId)
                .collect(Collectors.toList());
        List<AccountUserImageWeb> userProfiles = userAccountControllerSender.getUserAccountProfileImages(userIds);

        dealResponse.setUsers(userProfiles);

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
    public InternalDealWeb renameDeal(Long dealId, Long userId, DealTitleChangeWeb dealTitleChangeWeb) {
        Deal deal = checkDealPermissions(dealId, userId, NEED_FULL_ACCESS);
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        if (dealUser.getDealUserPermissionType() == DealUserPermissionType.READ_ONLY) {
            return dealSnapshotService.renameDealSnapshot(dealId, userId, dealTitleChangeWeb.getTitle());
        } else {
            deal.setTitle(dealTitleChangeWeb.getTitle());
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
        checkDealPermissions(dealId, userId, false);
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        if (dealUser.getDealUserStatus() == DealUserStatus.LEAVED) {
            dealUser.setDealUserStatus(DealUserStatus.REMOVED);
            dealUser.setDealUserPermissionType(DealUserPermissionType.NO_READ);
        } else {
            log.warn("Deal with ID {} not deleted, because user with ID {} haven't status LEAVED", dealId, userId);
            throw new NoPermissionException(String.format(
                    "Deal with ID {} not deleted, because user with ID {} haven't status LEAVED", dealId, userId));
        }
    }

    @Override
    public void confirmDealUser(Long dealId, Long userId) {
        Deal deal = checkDealPermissions(dealId, userId, !NEED_FULL_ACCESS);
        Long userOriginId = changeUserOriginStatus(deal);
        try {
            DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
            dealUser.setDealUserStatus(DealUserStatus.ACTIVE);
            dealUser.setDealUserPermissionType(DealUserPermissionType.CREATE);
            if (deal.getDealStatus() == DealStatus.INITIATED) {
                deal.setDealStatus(DealStatus.ACTIVE);
            }
        } catch (Exception e) {
            rollbackUserOriginStatus(userOriginId);
        }
    }

    private Long changeUserOriginStatus(Deal deal) {
        if (deal.getDealStatus() == DealStatus.INITIATED) {
            setUserAccountMatchingStatus(deal.getModifiedByUser(), AccountStatus.DEAL, false, false);
            return deal.getModifiedByUser();
        }
        return 0L;
    }

    private void rollbackUserOriginStatus(Long userOriginId) {
        if (userOriginId != 0L) {
            setUserAccountMatchingStatus(userOriginId, AccountStatus.COMPLETED_REQUIRED_MATCHING, true, true);
        }
    }

    private void setUserAccountMatchingStatus(Long userId, AccountStatus accountStatus, Boolean enforce, Boolean isMatchingEnabled) {
        userAccountControllerSender.updateUserAccountStatus(userId, accountStatus, enforce);
        userMatchControllerSender.configure(userId, isMatchingEnabled);
    }

    @Override
    public void leaveDeal(Long dealId, Long userId) {
        Deal deal = checkDealPermissions(dealId, userId, !NEED_FULL_ACCESS);
        dealSnapshotService.saveDealSnapshot(deal, userId);
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(dealId, userId);
        dealUser.setDealUserStatus(DealUserStatus.LEAVED);
        dealUser.setDealUserPermissionType(DealUserPermissionType.READ_ONLY);

        setPermissionToReadOnly(deal, userId);

        if (dealUserRepository.findAllByDealIdAndDealUserStatus(deal.getId(), DealUserStatus.ACTIVE).size() == 1) {
            deal.setDealStatus(DealStatus.ARCHIVE);
            dealRepository.save(deal);

            List<DealUser> dealUsers = dealUserRepository.findAllByDealIdAndDealUserStatus(deal.getId(), DealUserStatus.ACTIVE);
            dealUsers.forEach(x-> x.setDealUserStatus(DealUserStatus.LEAVED));

            dealUserRepository.save(dealUsers);
        }
    }

    @Override
    public void removeDealUser(InternalDealUserWeb internalDealUserWeb) {
        leaveDeal(internalDealUserWeb.getDealId(), internalDealUserWeb.getUserToChangeId());
    }


    @Override
    public void addDealUser(InternalDealUserWeb internalDealUserWeb) {
        addDealUserWithStatusAndType(internalDealUserWeb, DealUserStatus.PENDING, DealUserPermissionType.READ_ONLY);
    }

    @Override
    public void confirmAddDealUser(InternalDealUserWeb internalDealUserWeb) {
        addDealUserWithStatusAndType(internalDealUserWeb, DealUserStatus.ACTIVE, DealUserPermissionType.CREATE);
    }

    private void addDealUserWithStatusAndType(InternalDealUserWeb internalDealUserWeb,
                                              DealUserStatus dealUserStatus,
                                              DealUserPermissionType dealUserPermissionType) {
        Deal deal = checkDealPermissions(internalDealUserWeb.getDealId(), internalDealUserWeb.getUserOriginId(), NEED_FULL_ACCESS);
        Long userToAddId = internalDealUserWeb.getUserToChangeId();
        if (userHasActiveDeal(userToAddId)) {
            log.warn("User {} has already active deal", userToAddId);
            throw new MultipleDealsException(String.format("User %d has already active deal", userToAddId));
        }
        DealUser dealUser = dealUserRepository.findByDealIdAndUserId(deal.getId(), userToAddId);
        if (dealUser == null) {
            dealUser = new DealUser(userToAddId, deal, dealUserStatus, dealUserPermissionType);
        } else {
            dealUser.setDealUserStatus(dealUserStatus);
            dealUser.setDealUserPermissionType(dealUserPermissionType);
        }
        dealUserRepository.save(dealUser);

        dealPanelService.grantDealUserPanelAccessPermissions(userToAddId, deal.getId(), dealUserPermissionType);
        dealDocumentService.grantDealUserDocumentAccessPermissions(userToAddId, deal.getId(), dealUserPermissionType);

    }

    private void setPermissionToReadOnly(Deal deal, Long userToRemoveId) {
        dealPanelUserRepository
                .findAllByDealPanelDealIdAndUserIdAndDealUserPermissionTypeNot(deal.getId(), userToRemoveId, DealUserPermissionType.NO_READ)
                .forEach(dealPanelUser -> {
                    if (dealPanelUser.getDealUserPermissionType() == DealUserPermissionType.CREATE)
                        dealPanelUser.setDealUserPermissionType(DealUserPermissionType.READ_ONLY);
                });

        dealDocumentUserRepository
                .findAllByDealDocumentDealPanelDealIdAndUserIdAndDealUserPermissionTypeNot(deal.getId(), userToRemoveId, DealUserPermissionType.NO_READ)
                .forEach(dealDocumentUser -> {
                    if (dealDocumentUser.getDealUserPermissionType() == DealUserPermissionType.CREATE)
                        dealDocumentUser.setDealUserPermissionType(DealUserPermissionType.READ_ONLY);
                });
    }

    @Override
    public List<DealUserWeb> getAllUserDeals(Long userId) {
        List<DealUser> userDeals = dealUserRepository.findAllByUserIdAndDealUserStatusNot(userId, DealUserStatus.REMOVED);
        if (userDeals == null) {
            log.warn("User {} does not have any deal", userId);
            return null;
        }
        return userDeals.stream()
                .map(dealUser ->
                        DealUserWeb
                                .builder()
                                .dealId(dealUser.getDeal().getId())
                                .title(dealUser.getDeal().getTitle())
                                .userStatusId(dealUser.getDealUserStatus())
                                .statusId(dealUser.getDeal().getDealStatus())
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public DealAvailableUsersResponse getAvailableUsers(Long currentUserId, List<Long> users) {
        List<DealUser> allByUserIdIn = dealUserRepository.findAllByUserIdInAndDealUserStatus(users, DealUserStatus.ACTIVE);
        allByUserIdIn.stream()
                .map(DealUser::getUserId)
                .forEach(users::remove);
        DealUserWeb dealUserWeb = getAllUserDeals(currentUserId).stream()
                .filter(x -> x.getStatusId() == DealStatus.ACTIVE && x.getUserStatusId() == DealUserStatus.ACTIVE)
                .findFirst()
                .orElse(null);

        return DealAvailableUsersResponse.builder()
                .activeDeal(dealUserWeb)
                .availableUsers(users)
                .build();
    }

    private DealPanelResponse getFirstDealPanel(List<DealPanel> dealPanels, Long userId) {
        DealPanel firstPanel = dealPanels.stream()
                .min(Comparator.comparing(AbstractEntity::getCreatedDate))
                .get();
        return getDealPanelResponse(userId, firstPanel);
    }

    private DealPanelResponse getDealPanelResponse(Long userId, DealPanel dealPanel) {
        DealPanelResponse dealPanelResponse = new DealPanelResponse();
        dealPanelResponse.setPanelId(dealPanel.getId());
        dealPanelResponse.setTitle(dealPanel.getTitle());
        dealPanelResponse.setDocuments(dealPanelService.getDealPanelDocuments(dealPanel.getId(), userId));
        return dealPanelResponse;
    }

}
