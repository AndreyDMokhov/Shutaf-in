package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.ImageStorage;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserImageWeb;
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
import java.util.Base64;

@Service
@Transactional
public class UserImageServiceImpl implements UserImageService {

    private static final String IMAGE_EXTENSION = ".jpg";

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private ImageStorageRepository imageStorageRepository;

    @Autowired
    private EnvironmentConfigurationService environmentConfigurationService;

    @Autowired
    private UserImageService userImageService;

    @Override
    @Transactional
    public void addUserImage(UserImageWeb image, User user) {
        UserImage userImage = new UserImage();
        String imageEncoded = image.getImage();
        userImage.setUser(user);
        Long userImageId = (Long) userImageRepository.save(userImage);
        userImage.setId(userImageId);
        String imageLocalPath = getUserDirectoryPath(user) + String.valueOf(userImageId) + IMAGE_EXTENSION;
        userImage.setLocalPath(imageLocalPath);
        saveUserImageToFileSystem(imageEncoded, userImage);
        ImageStorage imageStorage = createImageBackup(userImage, imageEncoded);
        userImage.setImageStorage(imageStorage);
        userImageRepository.update(userImage);

    }

    @Override
    @Transactional
    public UserImage getUserImage(User user, Long userImageId) {
        UserImage userImage = userImageRepository.findById(userImageId);
        if (userImage == null || !user.getId().equals(userImage.getUser().getId())) {
            throw new ResourceNotFoundException(String.format("User Image with ID %d was not found", userImageId));
        }
        saveUserImageToFileSystem(userImage.getImageStorage().getImageEncoded(), userImage);
        return userImage;
    }

    @Override
    public void deleteUserImage(User user, Long userImageId) {

        UserImage userImage = userImageService.getUserImage(user, userImageId);
        deleteLocalImage(userImage);
        imageStorageRepository.delete(userImage.getImageStorage());
        userImageRepository.delete(userImage);
    }

    @Override
    public void createUserImageDirectory(User user) {
        String userDirPath = getUserDirectoryPath(user);
        File directory = new File(userDirPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private void deleteLocalImage(UserImage userImage) {
        File image = new File(userImage.getLocalPath());
        image.delete();
    }

    private String getUserDirectoryPath(User user) {
        String userDirPath = environmentConfigurationService.getLocalImagePath() + String.valueOf(user.getId())
                + File.separator;
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
            e.printStackTrace();
            System.out.println("File not found\n" + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while saving image to file\n" + e);
        }
    }

    private ImageStorage createImageBackup(UserImage userImage, String image) {
        ImageStorage imageStorage = new ImageStorage();
        imageStorage.setImageEncoded(image);
        imageStorage.setUserImage(userImage);
        Long storedImageId = (Long) imageStorageRepository.save(imageStorage);
        imageStorage.setId(storedImageId);
        return imageStorage;
    }
}
