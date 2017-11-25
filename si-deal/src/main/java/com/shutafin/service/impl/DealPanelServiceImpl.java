package com.shutafin.service.impl;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.web.DealDocumentWeb;
import com.shutafin.model.web.DealPanelWeb;
import com.shutafin.repository.DealDocumentRepository;
import com.shutafin.repository.DealPanelRepository;
import com.shutafin.service.DealDocumentService;
import com.shutafin.service.DealPanelService;
import com.shutafin.service.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public DealPanel addDealPanel(DealPanelWeb dealPanelWeb) {

        Deal deal = dealService.checkDealPermissions(dealPanelWeb.getDealId(),
                dealPanelWeb.getUserId(), NEED_FULL_ACCESS);

        DealPanel dealPanel = new DealPanel();
        dealPanel.setDeal(deal);
        dealPanel.setTitle(dealPanelWeb.getTitle());
        dealPanel.setModifiedByUser(dealPanelWeb.getUserId());
        dealPanel.setIsDeleted(false);

        dealPanelRepository.save(dealPanel);

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
        List<DealDocument> dealDocuments = dealDocumentRepository.findAllByDealPanelDealId(dealPanelId);
        dealDocuments.forEach(doc -> dealDocumentService.deleteDealDocument(userId, doc.getId()));
    }

    @Override
    public List<DealDocumentWeb> getDealPanelDocuments(Long dealPanelId) {
        List<DealDocument> dealPanelDocuments = dealDocumentRepository.findAllByDealPanelId(dealPanelId);
        if (!dealPanelDocuments.isEmpty()) {
            return dealPanelDocuments
                    .stream().map(dealDocument -> new DealDocumentWeb(dealDocument.getId(),
                            dealDocument.getTitle(),
                            dealDocument.getDocumentType().getCode(),
                            dealDocument.getCreatedDate().getTime()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    private DealPanel getDealPanelWithPermissions(Long userId, Long dealFolderId, Boolean fullAccess) {
        DealPanel dealPanel = dealPanelRepository.findOne(dealFolderId);
        if (dealPanel == null) {
            log.warn("Resource not found exception:");
            log.warn("Deal panel with ID {} was not found", dealFolderId);
            throw new RuntimeException(String.format("Deal panel with ID %d was not found", dealFolderId));
        }
        dealService.checkDealPermissions(dealPanel.getDeal().getId(), userId, fullAccess);
        return dealPanel;
    }

}
