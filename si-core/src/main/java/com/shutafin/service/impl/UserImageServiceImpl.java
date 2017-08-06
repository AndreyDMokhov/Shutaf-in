package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.ImageStorage;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.repository.common.ImageStorageRepository;
import com.shutafin.repository.common.UserImageRepository;
import com.shutafin.service.EnvironmentConfigurationService;
import com.shutafin.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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
    public UserImage addUserImage(UserImageWeb image, User user) {
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
        return userImage;
    }

    @Override
    @Transactional
    public UserImage getUserImage(User user, Long userImageId) {
        UserImage userImage = getUserImageFromFileSystem(user, userImageId);
        if (userImage != null) {
            return userImage;
        }
        userImage = userImageRepository.findById(userImageId);
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

    @Override
    public List<UserImage> getAllUserImages(User user) {
        return userImageRepository.findAllUserImages(user);
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

    private UserImage getUserImageFromFileSystem(User user, Long userImageId) {
        UserImage userImage = new UserImage();
        userImage.setId(userImageId);
        userImage.setUser(user);
        userImage.setImageStorage(new ImageStorage());
        String imageLocalPath = getUserDirectoryPath(user) + String.valueOf(userImageId) + IMAGE_EXTENSION;
        userImage.setLocalPath(imageLocalPath);
        File imageFile = new File(imageLocalPath);
        try {
            if (imageFile.exists()) {
                FileInputStream inputStream = new FileInputStream(imageFile);
                byte[] imageDecoded = new byte[inputStream.available()];
                inputStream.read(imageDecoded);
                inputStream.close();
                userImage.setCreatedDate(Date.from(Instant.ofEpochMilli(imageFile.lastModified())));
                userImage.getImageStorage().setImageEncoded(Base64.getEncoder().encodeToString(imageDecoded));
                return userImage;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found\n" + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while reading image from file system\n" + e);
        }
        return null;
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
