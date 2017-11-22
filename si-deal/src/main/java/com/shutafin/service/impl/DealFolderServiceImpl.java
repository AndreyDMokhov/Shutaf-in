package com.shutafin.service.impl;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.entities.DealFolder;
import com.shutafin.model.entities.DealUser;
import com.shutafin.model.types.DealUserStatus;
import com.shutafin.model.web.DealFolderWeb;
import com.shutafin.repository.DealDocumentRepository;
import com.shutafin.repository.DealFolderRepository;
import com.shutafin.repository.DealRepository;
import com.shutafin.repository.DealUserRepository;
import com.shutafin.service.DealDocumentService;
import com.shutafin.service.DealFolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class DealFolderServiceImpl implements DealFolderService {

    private static final Boolean NEED_FULL_ACCESS = true;

    @Autowired
    private DealFolderRepository dealFolderRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private DealUserRepository dealUserRepository;

    @Autowired
    private DealDocumentService dealDocumentService;

    @Autowired
    private DealDocumentRepository dealDocumentRepository;


    @Override
    public DealFolder addDealFolder(DealFolderWeb dealFolderWeb) {
        Deal deal = dealRepository.findOne(dealFolderWeb.getDealId());
        if (deal == null) {
            log.warn("Deal with given id does not exist");
            throw new RuntimeException();
        }

        checkDealPermissions(dealFolderWeb.getUserId(), deal.getId(), NEED_FULL_ACCESS);

        DealFolder dealFolder = new DealFolder();
        dealFolder.setDeal(deal);
        dealFolder.setTitle(dealFolder.getTitle());
        dealFolder.setModifiedByUser(dealFolderWeb.getUserId());
        dealFolder.setIsDeleted(false);

        dealFolderRepository.save(dealFolder);

        return dealFolder;
    }

    @Override
    public DealFolder getDealFolder(Long dealFolderId, Long userId) {
        DealFolder dealFolder = getDealFolderWithPermissions(userId, dealFolderId, !NEED_FULL_ACCESS);
        return dealFolder;
    }

    @Override
    public DealFolder renameDealFolder(Long dealFolderId, Long userId, String newTitle) {
        DealFolder dealFolder = getDealFolderWithPermissions(userId, dealFolderId, NEED_FULL_ACCESS);
        dealFolder.setTitle(newTitle);
        dealFolder.setModifiedByUser(userId);
        return dealFolder;
    }

    @Override
    public void deleteDealFolder(Long dealFolderId, Long userId) {
        DealFolder dealFolder = getDealFolderWithPermissions(userId, dealFolderId, NEED_FULL_ACCESS);
        dealFolder.setIsDeleted(true);
        dealFolder.setModifiedByUser(userId);
        List<DealDocument> dealDocuments = dealDocumentRepository.findAllByDealFolderDealId(dealFolderId);
        dealDocuments.forEach(doc -> dealDocumentService.deleteDealDocument(userId, doc.getId()));
    }

    private DealFolder getDealFolderWithPermissions(Long userId, Long dealFolderId, Boolean fullAccess) {
        DealFolder dealFolder = dealFolderRepository.findOne(dealFolderId);
        if (dealFolder == null) {
            log.warn("Resource not found exception:");
            log.warn("Deal Document with ID {} was not found", dealFolderId);
            throw new RuntimeException(String.format("User Document with ID %d was not found", dealFolderId));
        }
        checkDealPermissions(userId, dealFolder.getDeal().getId(), fullAccess);
        return dealFolder;
    }

    private void checkDealPermissions(Long userId, Long dealId, Boolean needFullAccess) {
        Deal deal = dealRepository.findOne(dealId);
        DealUser dealUser = dealUserRepository.findByDealAndUserId(deal, userId);
        if (dealUser == null) {
            log.warn("User has not ever been in this deal");
            throw new RuntimeException();
        } else if (dealUser.getDealUserStatus() == DealUserStatus.REMOVED && needFullAccess) {
            log.warn("User was removed from this deal");
            throw new RuntimeException();
        }
    }
}
