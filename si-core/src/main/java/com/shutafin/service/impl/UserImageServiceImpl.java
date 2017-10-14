package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.ImageStorage;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.types.CompressionType;
import com.shutafin.model.entities.types.PermissionType;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.repository.common.ImageStorageRepository;
import com.shutafin.repository.common.UserImageRepository;
import com.shutafin.service.EnvironmentConfigurationService;
import com.shutafin.service.ImageCompressService;
import com.shutafin.service.UserImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserImageServiceImpl implements UserImageService {

    private static final String IMAGE_EXTENSION = ".jpg";

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private ImageStorageRepository imageStorageRepository;

    @Autowired
    private EnvironmentConfigurationService environmentConfigurationService;

    @Autowired
    private ImageCompressService imageCompressService;

    @Override
    @Transactional
    public UserImage addUserImage(UserImageWeb image, User user, PermissionType permissionType, CompressionType compressionType) {
        image = convertToJpg(image);
        UserImage userImage = addUserImage(image, user, permissionType);
        if (compressionType != null && compressionType != CompressionType.NO_COMPRESSION) {
            return imageCompressService.addCompressedUserImage(userImage, compressionType);
        }
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
        if (userImage == null) {
            log.warn("Resource not found exception:");
            log.warn("User Image with ID {} was not found", userImageId);
            throw new ResourceNotFoundException(String.format("User Image with ID %d was not found", userImageId));
        } else if (!user.getId().equals(userImage.getUser().getId()) &&
                !userImage.getPermissionType().equals(PermissionType.PUBLIC)) {
            log.warn("User does not have authority to view this image, throw exception");
            throw new ResourceNotFoundException(String.format("User Image with ID %d was not found", userImageId));
        }
        saveUserImageToFileSystem(userImage.getImageStorage().getImageEncoded(), userImage);
        return userImage;
    }

    @Override
    @Transactional
    public void deleteUserImage(User user, Long userImageId) {
        UserImage userImage = this.getUserImage(user, userImageId);
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

    private UserImage addUserImage(UserImageWeb image, User user, PermissionType permissionType) {
        UserImage userImage = new UserImage();
        userImage.setPermissionType(permissionType);
        userImage.setCompressionType(CompressionType.NO_COMPRESSION);
        String imageEncoded = image.getImage();
        userImage.setUser(user);

        userImageRepository.save(userImage);

        String imageLocalPath = getUserDirectoryPath(user) + userImage.getId() + IMAGE_EXTENSION;
        userImage.setLocalPath(imageLocalPath);
        saveUserImageToFileSystem(imageEncoded, userImage);
        ImageStorage imageStorage = createImageBackup(userImage, imageEncoded);
        userImage.setImageStorage(imageStorage);
        userImageRepository.update(userImage);
        return userImage;
    }

    private String getUserDirectoryPath(User user) {
        return environmentConfigurationService.getLocalImagePath() + user.getId()
                + File.separator;
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
            log.error("File not found exception:");
            log.error("File not found", e);
        } catch (IOException e) {
            log.error("Input output exception:");
            log.error("Error while saving image to file", e);
        }
    }

    private UserImage getUserImageFromFileSystem(User user, Long userImageId) {
        UserImage userImage = new UserImage();
        userImage.setId(userImageId);
        userImage.setUser(user);
        userImage.setImageStorage(new ImageStorage());
        String imageLocalPath = getUserDirectoryPath(user) + userImageId + IMAGE_EXTENSION;
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
            log.error("File not found exception:");
            log.error("File not found", e);
        } catch (IOException e) {
            log.error("Input output exception:");
            log.error("Error while reading image from file system", e);
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

    private UserImageWeb convertToJpg(UserImageWeb userImageWeb) {
        String imageEncoded = userImageWeb.getImage();
        try {
            byte[] imageData = Base64.getDecoder().decode(imageEncoded);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(newBufferedImage, "jpg", baos);
            byte[] byteArray = baos.toByteArray();
            userImageWeb.setImage(Base64.getEncoder().encodeToString(byteArray));
            return userImageWeb;
        } catch (IOException exp) {
            log.error("Could not read or write image: ", exp);
        }
        return null;
    }
}
