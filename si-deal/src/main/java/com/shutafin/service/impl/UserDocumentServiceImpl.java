package com.shutafin.service.impl;

import com.shutafin.model.entities.DocumentStorage;
import com.shutafin.model.entities.UserDocument;
import com.shutafin.model.types.DocumentType;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.UserDocumentWeb;
import com.shutafin.repository.DocumentStorageRepository;
import com.shutafin.repository.UserDocumentRepository;
import com.shutafin.service.UserDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class UserDocumentServiceImpl implements UserDocumentService {

    @Value("${windows.base.path}")
    private String windowsBasePath;

    @Value("${unix.base.path}")
    private String unixBasePath;

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Autowired
    private DocumentStorageRepository documentStorageRepository;


    @Override
    public UserDocument addUserDocument(UserDocumentWeb userDocumentWeb, PermissionType permissionType) {
        DocumentType documentType = DocumentType.getById(userDocumentWeb.getDocumentTypeId());
        if (!fileSignatureCorrect(userDocumentWeb, documentType)) {
            log.warn("File content differs from document type or file is corrupted");
            throw new RuntimeException();
        }

        UserDocument userDocument = new UserDocument();
        userDocument.setUserId(userDocumentWeb.getUserId());
        userDocument.setPermissionType(permissionType);
        userDocument.setDocumentType(documentType);

        String documentEncoded = userDocumentWeb.getFileData();
        DocumentStorage documentStorage = createDocumentBackup(userDocument, documentEncoded);

        userDocument.setDocumentStorage(documentStorage);

        userDocument = userDocumentRepository.save(userDocument);
        String localPath = getUserDirectoryPath(userDocument.getUserId())
                + userDocument.getId()
                + userDocument.getDocumentType().getFileExtension();
        userDocument.setLocalPath(localPath);
        createUserDocumentsDirectory(userDocument.getUserId());
        saveUserDocumentToFileSystem(userDocument);

        return userDocument;
    }


    @Override
    public UserDocument getUserDocument(Long userId, Long userDocumentId) {
        UserDocument userDocument = getUserDocumentFromFileSystem(userId, userDocumentId);
        if (userDocument != null) {
            return userDocument;
        }

        userDocument = userDocumentRepository.findOne(userDocumentId);
        if (userDocument == null) {
            log.warn("Resource not found exception:");
            log.warn("User Document with ID {} was not found", userDocumentId);
            throw new RuntimeException(String.format("User Document with ID %d was not found", userDocumentId));
        } else if (!userId.equals(userDocument.getUserId()) &&
                !userDocument.getPermissionType().equals(PermissionType.PUBLIC)) {
            log.warn("User does not have authority to view this image, throw exception");
            throw new RuntimeException(String.format("User Document with ID %d was not found", userDocumentId));
        }

        saveUserDocumentToFileSystem(userDocument);
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

    private String getUserDirectoryPath(Long userId) {
        StringBuilder basePath = new StringBuilder();
        if (SystemUtils.IS_OS_WINDOWS) {
            basePath.append(windowsBasePath);
        } else {
            basePath.append(unixBasePath);
        }
        basePath.append(userId)
                .append(File.separator);
        return basePath.toString();
    }

    private void createUserDocumentsDirectory(Long userId) {
        String userDirPath = getUserDirectoryPath(userId);
        File directory = new File(userDirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void saveUserDocumentToFileSystem(UserDocument userDocument) {
        File documentFile = new File(userDocument.getLocalPath());
        try {
            if (!documentFile.exists()) {
                documentFile.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(documentFile);
                String documentEncoded = userDocument.getDocumentStorage().getDocumentEncoded();
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

    private UserDocument getUserDocumentFromFileSystem(Long userId, Long userDocumentId) {
        UserDocument userDocument = new UserDocument();
        userDocument.setId(userDocumentId);
        userDocument.setUserId(userId);
        userDocument.setDocumentStorage(new DocumentStorage());
        String documentLocalPath = getDocumentLocalPath(userId, userDocumentId);
        userDocument.setLocalPath(documentLocalPath);
        File documentFile = new File(documentLocalPath);
        try {
            if (documentFile.exists()) {
                FileInputStream inputStream = new FileInputStream(documentFile);
                byte[] documentDecoded = new byte[inputStream.available()];
                inputStream.read(documentDecoded);
                inputStream.close();
                userDocument.setCreatedDate(Date.from(Instant.ofEpochMilli(documentFile.lastModified())));
                userDocument.getDocumentStorage().setDocumentEncoded(Base64.getEncoder().encodeToString(documentDecoded));
                userDocument.setDocumentType(getDocumentType(documentFile));
                return userDocument;
            }
        } catch (FileNotFoundException e) {
            log.error("File not found exception:");
            log.error("File not found", e);
        } catch (IOException e) {
            log.error("Input output exception:");
            log.error("Error while reading image from file system", e);
        }
        return null;
    }

    private DocumentType getDocumentType(File documentFile) {
        String name = documentFile.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1);
        return DocumentType.valueOf(extension.toUpperCase());
    }

    private String getDocumentLocalPath(Long userId, Long userDocumentId) {
        String userDocumentsDirectory = getUserDirectoryPath(userId);
        File documentsDirectory = new File(userDocumentsDirectory);
        File[] files = documentsDirectory.listFiles((dir, name) -> name.startsWith(userDocumentId.toString()));
        if (files.length < 1){
            return null;
        }
        return files[0].getAbsolutePath();
    }
}
