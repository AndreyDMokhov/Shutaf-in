package com.shutafin.service.impl;

import com.shutafin.model.entities.*;
import com.shutafin.model.types.DealUserPermissionType;
import com.shutafin.model.types.DealUserStatus;
import com.shutafin.model.web.deal.DealDocumentWeb;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.repository.*;
import com.shutafin.service.DealDocumentService;
import com.shutafin.service.DealPanelService;
import com.shutafin.service.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class DealPanelServiceImpl implements DealPanelService {

    private static final Boolean NEED_FULL_ACCESS = true;

    @Autowired
    private DealPanelRepository dealPanelRepository;

    @Autowired
    private DealDocumentService dealDocumentService;

    @Autowired
    private DealDocumentRepository dealDocumentRepository;

    @Autowired
    private DealService dealService;

    @Autowired
    private DealUserRepository dealUserRepository;

    @Autowired
    private DealPanelUserRepository dealPanelUserRepository;

    @Autowired
    private DealDocumentUserRepository dealDocumentUserRepository;

    @Override
    public DealPanel addDealPanel(DealPanelWeb dealPanelWeb) {

        Deal deal = dealService.checkDealPermissions(dealPanelWeb.getDealId(),
                dealPanelWeb.getUserId(), NEED_FULL_ACCESS);

        DealPanel dealPanel = new DealPanel();
        dealPanel.setDeal(deal);
        dealPanel.setTitle(dealPanelWeb.getTitle());
        dealPanel.setModifiedByUser(dealPanelWeb.getUserId());
        dealPanel.setIsDeleted(false);

        dealPanel = dealPanelRepository.save(dealPanel);

        List<DealUser> activeDealUsers = dealUserRepository.findAllByDealIdAndDealUserStatus(deal.getId(),
                DealUserStatus.ACTIVE);
        for (DealUser dealUser : activeDealUsers) {
            DealPanelUser dealPanelUser = new DealPanelUser(dealUser.getUserId(), dealPanel,
                    DealUserPermissionType.CREATE);
            dealPanelUserRepository.save(dealPanelUser);
        }
        return dealPanel;
    }

    @Override
    public DealPanel getDealPanel(Long dealPanelId, Long userId) {
        DealPanel dealPanel = getDealPanelWithPermissions(userId, dealPanelId, !NEED_FULL_ACCESS);
        return dealPanel;
    }

    @Override
    public DealPanel renameDealPanel(Long dealPanelId, Long userId, String newTitle) {
        DealPanel dealPanel = getDealPanelWithPermissions(userId, dealPanelId, NEED_FULL_ACCESS);
        dealPanel.setTitle(newTitle);
        dealPanel.setModifiedByUser(userId);
        return dealPanel;
    }

    @Override
    public void deleteDealPanel(Long dealPanelId, Long userId) {
        DealPanel dealPanel = getDealPanelWithPermissions(userId, dealPanelId, NEED_FULL_ACCESS);
        dealPanel.setIsDeleted(true);
        dealPanel.setModifiedByUser(userId);
        List<DealDocument> dealDocuments = dealDocumentRepository.findAllByDealPanelIdAndIsDeletedFalse(dealPanelId);
        dealDocuments.forEach(doc -> dealDocumentService.deleteDealDocument(userId, doc.getId()));

        List<DealPanelUser> dealPanelUsers = dealPanelUserRepository.
                findAllByDealPanelIdAndDealUserPermissionType(dealPanelId, DealUserPermissionType.CREATE);
        dealPanelUsers.forEach(d -> d.setDealUserPermissionType(DealUserPermissionType.NO_READ));
    }

    @Override
    public List<DealDocumentWeb> getDealPanelDocuments(Long dealPanelId, Long userId) {
        List<DealDocument> dealPanelDocuments = dealDocumentRepository.findAllByDealPanelId(dealPanelId);
        if (!dealPanelDocuments.isEmpty()) {
            return dealPanelDocuments
                    .stream()
                    .filter(dealDocument ->
                            dealDocumentUserRepository.findByDealDocumentIdAndUserId(dealDocument.getId(), userId)
                                    .getDealUserPermissionType() != DealUserPermissionType.NO_READ)
                    .map(dealDocument -> new DealDocumentWeb(dealDocument.getId(),
                            dealDocument.getTitle(),
                            dealDocument.getDocumentType().getCode(),
                            dealDocument.getCreatedDate().getTime()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private DealPanel getDealPanelWithPermissions(Long userId, Long dealPanelId, Boolean fullAccess) {
        DealPanel dealPanel = dealPanelRepository.findOne(dealPanelId);
        if (dealPanel == null) {
            log.warn("Deal panel with ID {} was not found", dealPanelId);
            throw new RuntimeException(String.format("Deal panel with ID %d was not found", dealPanelId));
        }
        DealPanelUser dealPanelUser = dealPanelUserRepository.findByDealPanelIdAndUserId(dealPanelId, userId);
        if (dealPanelUser == null || dealPanelUser.getDealUserPermissionType() == DealUserPermissionType.NO_READ) {
            log.warn("User with ID {} does not have permissions", userId);
            throw new RuntimeException(String.format("User panel with ID %d was not found", dealPanelId));
        }
        if (dealPanel.getIsDeleted() && dealPanelUser.getDealUserPermissionType() == DealUserPermissionType.NO_READ) {
            log.warn("Deal panel with ID {} was deleted", dealPanelId);
            throw new RuntimeException(String.format("Deal panel with ID %d was not found", dealPanelId));
        }
        dealService.checkDealPermissions(dealPanel.getDeal().getId(), userId, fullAccess);
        return dealPanel;
    }

}
