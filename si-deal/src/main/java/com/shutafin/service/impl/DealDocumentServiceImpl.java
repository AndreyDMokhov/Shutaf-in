package com.shutafin.service.impl;

import com.shutafin.model.entities.*;
import com.shutafin.model.types.DealUserStatus;
import com.shutafin.model.types.DocumentType;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.DealUserDocumentWeb;
import com.shutafin.repository.DealDocumentRepository;
import com.shutafin.repository.DealFolderRepository;
import com.shutafin.repository.DealUserRepository;
import com.shutafin.repository.DocumentStorageRepository;
import com.shutafin.service.DealDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Base64;

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
    private DealFolderRepository dealFolderRepository;

    @Autowired
    private DealUserRepository dealUserRepository;


    @Override
    public DealDocument addDealDocument(DealUserDocumentWeb dealUserDocumentWeb, PermissionType permissionType) {
        DocumentType documentType = DocumentType.getById(dealUserDocumentWeb.getDocumentTypeId());
        if (!fileSignatureCorrect(dealUserDocumentWeb, documentType)) {
            log.warn("File content differs from document type or file is corrupted");
            throw new RuntimeException();
        }

        DealFolder dealFolder = dealFolderRepository.findOne(dealUserDocumentWeb.getDealFolderId());
        if (dealFolder == null) {
            log.warn("Deal folder with give id does not exist");
            throw new RuntimeException();
        }

        checkUserPermissionForDealFolder(dealUserDocumentWeb.getUserId(), dealFolder, NEED_FULL_ACCESS);

        DealDocument dealDocument = new DealDocument();
        dealDocument.setDealFolder(dealFolder);
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
        createDealDocumentsDirectory(dealFolder);
        saveDealDocumentToFileSystem(dealDocument);

        return dealDocument;
    }

    @Override
    public DealDocument getDealDocument(Long userId, Long dealDocumentId) {
        DealDocument dealDocument = getDealDocumentWithPermissions(userId, dealDocumentId, !NEED_FULL_ACCESS);
        return dealDocument;
    }

    @Override
    public void deleteDealDocument(Long userId, Long dealDocumentId) {
        DealDocument dealDocument = getDealDocumentWithPermissions(userId, dealDocumentId, NEED_FULL_ACCESS);
        dealDocument.setIsDeleted(true);
        dealDocument.setModifiedByUser(userId);
    }

    @Override
    public DealDocument renameDealDocument(Long userId, Long dealDocumentId, String newTitle) {
        DealDocument dealDocument = getDealDocumentWithPermissions(userId, dealDocumentId, NEED_FULL_ACCESS);
        dealDocument.setTitle(newTitle);
        dealDocument.setModifiedByUser(userId);
        return dealDocument;
    }

    private DealDocument getDealDocumentWithPermissions(Long userId, Long dealDocumentId, Boolean fullAccess) {
        DealDocument dealDocument = dealDocumentRepository.findOne(dealDocumentId);
        if (dealDocument == null) {
            log.warn("Resource not found exception:");
            log.warn("Deal Document with ID {} was not found", dealDocumentId);
            throw new RuntimeException(String.format("User Document with ID %d was not found", dealDocumentId));
        }
        checkUserPermissionForDealFolder(userId, dealDocument.getDealFolder(), fullAccess);
        return dealDocument;
    }

    private DocumentStorage createDocumentBackup(DealDocument dealDocument, String documentEncoded) {
        DocumentStorage documentStorage = new DocumentStorage();
        documentStorage.setDealDocument(dealDocument);
        documentStorage.setDocumentEncoded(documentEncoded);
        return documentStorageRepository.save(documentStorage);
    }

    private boolean fileSignatureCorrect(DealUserDocumentWeb dealUserDocumentWeb, DocumentType documentType) {
        for (String fileSignature : documentType.getFileSignature()) {
            if (dealUserDocumentWeb.getFileData().startsWith(fileSignature)) {
                return true;
            }
        }
        return false;
    }

    private String getDealFolderDirectoryPath(DealFolder dealFolder) {
        StringBuilder basePath = new StringBuilder();
        if (SystemUtils.IS_OS_WINDOWS) {
            basePath.append(windowsBasePath);
        } else {
            basePath.append(unixBasePath);
        }
        basePath.append(dealFolder.getDeal().getId())
                .append(File.separator)
                .append(dealFolder.getId())
                .append(File.separator);
        return basePath.toString();
    }

    private void createDealDocumentsDirectory(DealFolder dealFolder) {
        String dealFolderDirPath = getDealFolderDirectoryPath(dealFolder);
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
        return getDealFolderDirectoryPath(dealDocument.getDealFolder())
                + dealDocument.getId()
                + dealDocument.getDocumentType().getFileExtension();
    }

    private void checkUserPermissionForDealFolder(Long userId, DealFolder dealFolder, Boolean needFullAccess) {
        Deal deal = dealFolder.getDeal();
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
