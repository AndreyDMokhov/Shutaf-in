package com.shutafin.service.impl;

import com.shutafin.model.entities.DocumentStorage;
import com.shutafin.model.entities.UserDocument;
import com.shutafin.model.exceptions.InvalidResourceException;
import com.shutafin.model.exceptions.ResourceNotFoundException;
import com.shutafin.model.types.DocumentType;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.UserDocumentWeb;
import com.shutafin.repository.DocumentStorageRepository;
import com.shutafin.repository.UserDocumentRepository;
import com.shutafin.service.UserDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserDocumentServiceImpl implements UserDocumentService {

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Autowired
    private DocumentStorageRepository documentStorageRepository;


    @Override
    public UserDocument addUserDocument(UserDocumentWeb userDocumentWeb, PermissionType permissionType) {
        DocumentType documentType = DocumentType.getById(userDocumentWeb.getDocumentTypeId());
        if (!fileSignatureCorrect(userDocumentWeb, documentType)) {
            log.warn("File content differs from document type or file is corrupted");
            throw new InvalidResourceException();
        }

        UserDocument userDocument = new UserDocument();
        userDocument.setUserId(userDocumentWeb.getUserId());
        userDocument.setPermissionType(permissionType);
        userDocument.setDocumentType(documentType);

        String documentEncoded = userDocumentWeb.getFileData();
        DocumentStorage documentStorage = createDocumentBackup(userDocument, documentEncoded);

        userDocument.setDocumentStorage(documentStorage);

        return userDocumentRepository.save(userDocument);
    }


    @Override
    public UserDocument getUserDocument(Long userId, Long userDocumentId) {
        UserDocument userDocument = userDocumentRepository.findOne(userDocumentId);

        if (userDocument == null) {
            log.warn("Resource not found exception:");
            log.warn("User Document with ID {} was not found", userDocumentId);
            throw new ResourceNotFoundException(String.format("User Document with ID %d was not found", userDocumentId));
        } else if (!userId.equals(userDocument.getUserId()) &&
                !userDocument.getPermissionType().equals(PermissionType.PUBLIC)) {
            log.warn("User does not have authority to view this image, throw exception");
            throw new ResourceNotFoundException(String.format("User Document with ID %d was not found", userDocumentId));
        }

        return userDocument;
    }

    @Override
    public void deleteUserDocument(Long userId, Long userDocumentId) {
        UserDocument userDocument = getUserDocument(userId, userDocumentId);
        documentStorageRepository.delete(userDocument.getDocumentStorage());
        userDocumentRepository.delete(userDocument);
    }

    private DocumentStorage createDocumentBackup(UserDocument userDocument, String documentEncoded) {
        DocumentStorage documentStorage = new DocumentStorage();
        documentStorage.setUserDocument(userDocument);
        documentStorage.setDocumentEncoded(documentEncoded);
        return documentStorageRepository.save(documentStorage);
    }

    private boolean fileSignatureCorrect(UserDocumentWeb userDocumentWeb, DocumentType documentType) {
        for (String fileSignature : documentType.getFileSignature()) {
            if (userDocumentWeb.getFileData().startsWith(fileSignature)){
                return true;
            }
        }
        return false;
    }
}
