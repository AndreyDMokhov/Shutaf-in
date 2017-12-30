package com.shutafin.service.impl;

import com.shutafin.model.entities.*;
import com.shutafin.model.exception.exceptions.NoPermissionException;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.exception.exceptions.SystemException;
import com.shutafin.model.types.DealUserPermissionType;
import com.shutafin.model.types.DealUserStatus;
import com.shutafin.model.types.DocumentType;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.deal.InternalDealUserDocumentWeb;
import com.shutafin.repository.*;
import com.shutafin.service.DealDocumentService;
import com.shutafin.service.DealService;
import com.shutafin.service.DealSnapshotService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
@Slf4j
public class DealDocumentServiceImpl implements DealDocumentService {

    private static final Boolean NEED_FULL_ACCESS = true;

    @Value("${windows.base.path}")
    private String windowsBasePath;

    @Value("${unix.base.path}")
    private String unixBasePath;

    @Autowired
    private DealDocumentRepository dealDocumentRepository;

    @Autowired
    private DocumentStorageRepository documentStorageRepository;

    @Autowired
    private DealPanelRepository dealPanelRepository;

    @Autowired
    private DealService dealService;

    @Autowired
    private DealUserRepository dealUserRepository;

    @Autowired
    private DealDocumentUserRepository dealDocumentUserRepository;

    @Autowired
    private DealSnapshotService dealSnapshotService;

    @Override
    public DealDocument addDealDocument(InternalDealUserDocumentWeb dealUserDocumentWeb, PermissionType permissionType) {
        DocumentType documentType = DocumentType.getById(dealUserDocumentWeb.getDocumentTypeId());
        if (!fileSignatureCorrect(dealUserDocumentWeb, documentType)) {
            log.warn("File content differs from document type or file is corrupted");
            throw new SystemException("File content differs from document type or file is corrupted");
        }

        DealPanel dealPanel = dealPanelRepository.findOne(dealUserDocumentWeb.getDealPanelId());
        if (dealPanel == null) {
            log.warn("Deal panel with given id does not exist");
            throw new ResourceNotFoundException(String.format("Deal panel with id %d does not exist",
                    dealUserDocumentWeb.getDealPanelId()));
        }

        dealService.checkDealPermissions(dealPanel.getDeal().getId(),
                dealUserDocumentWeb.getUserId(), NEED_FULL_ACCESS);

        DealDocument dealDocument = new DealDocument();
        dealDocument.setDealPanel(dealPanel);
        dealDocument.setModifiedByUser(dealUserDocumentWeb.getUserId());
        dealDocument.setTitle(dealUserDocumentWeb.getDocumentTitle());
        dealDocument.setPermissionType(permissionType);
        dealDocument.setDocumentType(documentType);
        dealDocument.setIsDeleted(false);

        String documentEncoded = dealUserDocumentWeb.getFileData();
        DocumentStorage documentStorage = createDocumentBackup(dealDocument, documentEncoded);

        dealDocument.setDocumentStorage(documentStorage);

        dealDocument = dealDocumentRepository.save(dealDocument);
        String localPath = generateDealDocumentLocalPath(dealDocument);
        dealDocument.setLocalPath(localPath);
        createDealDocumentsDirectory(dealPanel);
        saveDealDocumentToFileSystem(dealDocument);

        Deal deal = dealPanel.getDeal();
        List<DealUser> activeDealUsers = dealUserRepository.findAllByDealIdAndDealUserStatus(deal.getId(),
                DealUserStatus.ACTIVE);
        for (DealUser dealUser : activeDealUsers) {
            DealDocumentUser dealDocumentUser = new DealDocumentUser(dealUser.getUserId(), dealDocument,
                    DealUserPermissionType.CREATE);
            dealDocumentUserRepository.save(dealDocumentUser);
        }

        return dealDocument;
    }

    @Override
    public DealDocument getDealDocument(Long userId, Long dealDocumentId) {
//        dealDocumentId >>= SHIFT_VALUE;
        DealDocument dealDocument = getDealDocumentWithPermissions(userId, dealDocumentId, !NEED_FULL_ACCESS);
        DealDocumentUser dealDocumentUser = dealDocumentUserRepository.findByDealDocumentIdAndUserId(dealDocumentId, userId);
        if (dealDocumentUser.getDealUserPermissionType() == DealUserPermissionType.READ_ONLY) {
            return dealSnapshotService.getDealDocumentFromSnapshot(dealDocument, userId);
        }
        return dealDocument;
    }

    @Override
    public void deleteDealDocument(Long userId, Long dealDocumentId) {
//        dealDocumentId >>= SHIFT_VALUE;
        DealDocument dealDocument = getDealDocumentWithPermissions(userId, dealDocumentId, !NEED_FULL_ACCESS);
        DealDocumentUser dealDocumentUser = dealDocumentUserRepository.findByDealDocumentIdAndUserId(dealDocumentId, userId);
        if (dealDocumentUser.getDealUserPermissionType() == DealUserPermissionType.READ_ONLY) {
            dealSnapshotService.deleteDealDocumentFromSnapshot(dealDocumentId, userId);
        } else {
            dealDocument.setIsDeleted(true);
            dealDocument.setModifiedByUser(userId);

            List<DealDocumentUser> dealDocumentUsers = dealDocumentUserRepository.
                    findAllByDealDocumentIdAndDealUserPermissionType(dealDocumentId, DealUserPermissionType.CREATE);
            dealDocumentUsers.forEach(d -> d.setDealUserPermissionType(DealUserPermissionType.NO_READ));
        }
    }

    @Override
    public DealDocument renameDealDocument(Long userId, Long dealDocumentId, String newTitle) {
//        dealDocumentId >>= SHIFT_VALUE;
        DealDocument dealDocument = getDealDocumentWithPermissions(userId, dealDocumentId, NEED_FULL_ACCESS);
        dealDocument.setTitle(newTitle);
        dealDocument.setModifiedByUser(userId);
        return dealDocument;
    }

    private DealDocument getDealDocumentWithPermissions(Long userId, Long dealDocumentId, Boolean fullAccess) {
        DealDocument dealDocument = dealDocumentRepository.findOne(dealDocumentId);
        if (dealDocument == null) {
            log.warn("Deal Document with ID {} was not found", dealDocumentId);
            throw new ResourceNotFoundException(String.format("User Document with ID %d was not found", dealDocumentId));
        }
        DealDocumentUser dealDocumentUser = dealDocumentUserRepository.
                findByDealDocumentIdAndUserId(dealDocumentId, userId);
        if (dealDocumentUser == null || dealDocumentUser.getDealUserPermissionType() == DealUserPermissionType.NO_READ) {
            log.warn("User with ID {} does not have permissions", userId);
            throw new NoPermissionException(String.format("User Document with ID %d was not found", dealDocumentId));
        }
        if (dealDocument.getIsDeleted() && dealDocumentUser.getDealUserPermissionType() == DealUserPermissionType.NO_READ) {
            log.warn("Deal Document with ID {} was deleted", dealDocumentId);
            throw new ResourceNotFoundException(String.format("User Document with ID %d was not found", dealDocumentId));
        }
        dealService.checkDealPermissions(dealDocument.getDealPanel().getDeal().getId(), userId, fullAccess);
        return dealDocument;
    }

    private DocumentStorage createDocumentBackup(DealDocument dealDocument, String documentEncoded) {
        DocumentStorage documentStorage = new DocumentStorage();
        documentStorage.setDealDocument(dealDocument);
        documentStorage.setDocumentEncoded(documentEncoded);
        return documentStorageRepository.save(documentStorage);
    }

    private boolean fileSignatureCorrect(InternalDealUserDocumentWeb dealUserDocumentWeb, DocumentType documentType) {
        for (String fileSignature : documentType.getFileSignature()) {
            if (dealUserDocumentWeb.getFileData().startsWith(fileSignature)) {
                return true;
            }
        }
        return false;
    }

    private String getDealFolderDirectoryPath(DealPanel dealPanel) {
        StringBuilder basePath = new StringBuilder();
        if (SystemUtils.IS_OS_WINDOWS) {
            basePath.append(windowsBasePath);
        } else {
            basePath.append(unixBasePath);
        }
        basePath.append(dealPanel.getDeal().getId())
                .append(File.separator)
                .append(dealPanel.getId())
                .append(File.separator);
        return basePath.toString();
    }

    private void createDealDocumentsDirectory(DealPanel dealPanel) {
        String dealFolderDirPath = getDealFolderDirectoryPath(dealPanel);
        File directory = new File(dealFolderDirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void saveDealDocumentToFileSystem(DealDocument dealDocument) {
        File documentFile = new File(dealDocument.getLocalPath());
        try {
            if (!documentFile.exists()) {
                documentFile.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(documentFile);
                String documentEncoded = dealDocument.getDocumentStorage().getDocumentEncoded();
                byte[] documentDecoded = Base64.getDecoder().decode(documentEncoded);
                outputStream.write(documentDecoded);
                outputStream.close();
            }
        } catch (FileNotFoundException e) {
            log.error("File not found exception:");
            log.error("File not found", e);
        } catch (IOException e) {
            log.error("Input output exception:");
            log.error("Error while saving image to file", e);
        }
    }

    private String generateDealDocumentLocalPath(DealDocument dealDocument) {
        return getDealFolderDirectoryPath(dealDocument.getDealPanel())
                + dealDocument.getId()
                + dealDocument.getDocumentType().getFileExtension();
    }

}
