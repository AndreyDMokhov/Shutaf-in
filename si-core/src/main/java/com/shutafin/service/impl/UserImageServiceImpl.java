package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceDoesNotExistException;
import com.shutafin.model.entities.ImageStorage;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.ImageStorageRepository;
import com.shutafin.repository.UserImageRepository;
import com.shutafin.service.EnvironmentConfigurationService;
import com.shutafin.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;

@Service
@Transactional
public class UserImageServiceImpl implements UserImageService {

    private static final String IMAGE_EXTENSION = ".jpg";

    @Autowired
    UserImageRepository userImageRepository;

    @Autowired
    ImageStorageRepository imageStorageRepository;

    @Autowired
    EnvironmentConfigurationService environmentConfigurationService;

    @Override
    @Transactional
    public void addUserImage(String image, User user) {
        UserImage userImage = createUserImage(user);
        Long userImageId = (Long) userImageRepository.save(userImage);
        userImage.setId(userImageId);
        userImage.setLocalPath(getUserDirectoryPath(user) + String.valueOf(userImageId) + IMAGE_EXTENSION);
        saveUserImageToFileSystem(image, userImage);
        createImageBackup(userImage, image);
        userImageRepository.update(userImage);

    }

    @Override
    @Transactional
    public UserImage getUserImage(User user, Long userImageId) {
        UserImage userImage = userImageRepository.findById(userImageId);
        if (userImage == null || user.getId() != userImage.getUser().getId()) {
            throw new ResourceDoesNotExistException("User Image does not exist");
        }
        saveUserImageToFileSystem(userImage.getImageStorage().getImageEncoded(), userImage);
        return userImage;
    }

    @Override
    public void deleteUserImage(UserImage userImage) {
        deleteLocalImage(userImage);
        imageStorageRepository.delete(userImage.getImageStorage());
        userImageRepository.delete(userImage);
    }

    @Override
    public void createUserImageDirectory(User user) {
        String userDirPath = getUserDirectoryPath(user);
        File directory = new File(userDirPath);
        if (!directory.exists()) {
            try {
                directory.mkdir();
            } catch (Exception exp) {
                System.out.println("Cannot create directory\n" + exp);
            }
        }
    }

    private void deleteLocalImage(UserImage userImage) {
        File image = new File(userImage.getLocalPath());
        image.delete();
    }

    private UserImage createUserImage(User user) {
        UserImage userImage = new UserImage();
        Timestamp timestampCreated = new Timestamp(System.currentTimeMillis());

        userImage.setUser(user);
        userImage.setCreatedDate(timestampCreated);

        return userImage;
    }

    private String getUserDirectoryPath(User user) {
        String userDirPath = environmentConfigurationService.getLocalImagePath() + String.valueOf(user.getId())
                + environmentConfigurationService.getDelimetr();
        return userDirPath;
    }



    private void saveUserImageToFileSystem(String image, UserImage userImage) {
        File imageFile = new File(userImage.getLocalPath());
        try {
            if (!imageFile.exists()) {
                imageFile.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                byte[] imageDecoded = Base64.getDecoder().decode(image);
                outputStream.write(imageDecoded);
                outputStream.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found\n" + e);
        } catch (IOException e) {
            System.out.println("Error while saving image to file\n" + e);
        }
    }

    private void createImageBackup(UserImage userImage, String image) {
        ImageStorage imageStorage = new ImageStorage();
        imageStorage.setImageEncoded(image);
        imageStorage.setUserImage(userImage);
        Long storedImageId = (Long) imageStorageRepository.save(imageStorage);
        imageStorage.setId(storedImageId);
        userImage.setImageStorage(imageStorage);
    }
}
