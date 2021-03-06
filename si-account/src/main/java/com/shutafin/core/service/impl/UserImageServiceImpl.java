package com.shutafin.core.service.impl;

import com.shutafin.core.service.EnvironmentConfigurationService;
import com.shutafin.core.service.ImageCompressService;
import com.shutafin.core.service.UserImageService;
import com.shutafin.model.entities.ImageStorage;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.types.CompressionType;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.repository.ImageStorageRepository;
import com.shutafin.repository.account.ImagePairRepository;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserImageRepository;
import lombok.SneakyThrows;
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

import static com.shutafin.image.Image.getImage;

@Service
@Transactional
@Slf4j
public class UserImageServiceImpl implements UserImageService {

    private static final String DOT_SEPARATOR = ".";
    private static final String IMAGE_EXTENSION = "jpg";
    private static final String DEFAULT_AVATAR = "default_avatar.jpg";

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private ImageStorageRepository imageStorageRepository;

    @Autowired
    private EnvironmentConfigurationService environmentConfigurationService;

    @Autowired
    private ImageCompressService imageCompressService;

    @Autowired
    private ImagePairRepository imagePairRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    @Transactional
    public UserImage addUserImage(AccountUserImageWeb image, User user, CompressionType compressionType) {
        image = convertToJpg(image);
        UserImage userImage = addUserImage(image, user);
        if (compressionType != null && compressionType != CompressionType.NO_COMPRESSION) {
            return imageCompressService.addCompressedUserImage(userImage, compressionType);
        }
        return userImage;
    }

    @Override
    @Transactional
    public UserImage getUserImage(User user, Long userImageId) {
        if (userImageId == null) {
            return null;
        }
        UserImage userImage = getUserImageFromFileSystem(user, userImageId);
        if (userImage != null) {
            return userImage;
        }
        userImage = userImageRepository.findOne(userImageId);
        if (userImage == null) {
            log.warn("Resource not found exception:");
            log.warn("User Image with ID {} was not found", userImageId);
            throw new ResourceNotFoundException(String.format("User Image with ID %d was not found", userImageId));
        }
        saveUserImageToFileSystem(userImage.getImageStorage().getImageEncoded(), userImage);
        return userImage;
    }

    @Override
    @Transactional
    public UserImage getCompressedUserImage(User user) {
        return getUserImage(user, userAccountRepository.findCompressedUserImageIdByUserId(user.getId()));
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
    @Transactional
    public void createUserImageDirectory(User user) {
        String userDirPath = getUserDirectoryPath(user);
        File directory = new File(userDirPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    @Override
    @Transactional
    public List<UserImage> getAllUserImages(User user) {
        return userImageRepository.findAllByUser(user);
    }

    @Override
    @Transactional
    public UserImage getOriginalUserImage(UserImage compressedUserImage) {
        return imagePairRepository.findOriginalUserImage(compressedUserImage);
    }

    @Override
    @Transactional
    public UserImage getOriginalUserImage(User user) {
        Long compressedImageId = userAccountRepository.findCompressedUserImageIdByUserId(user.getId());
        return getUserImage(user, imagePairRepository.findOriginalUserImageById(compressedImageId));
    }

    @Override
    @Transactional
    public UserImage getCompressedUserImage(UserImage originalUserImage) {
        return imagePairRepository.findCompressedUserImage(originalUserImage);
    }

    @Override
    @Transactional
    public String getDefaultImageBase64() {
        return getImage(this.getClass(), DEFAULT_AVATAR);
    }

    private void deleteLocalImage(UserImage userImage) {
        File image = new File(userImage.getLocalPath());
        image.delete();
    }

    private UserImage addUserImage(AccountUserImageWeb image, User user) {
        UserImage userImage = new UserImage();
        userImage.setCompressionType(CompressionType.NO_COMPRESSION);
        String imageEncoded = image.getImage();
        userImage.setUser(user);

        userImageRepository.save(userImage);

        String imageLocalPath = getUserDirectoryPath(user) + userImage.getId() + DOT_SEPARATOR + IMAGE_EXTENSION;
        userImage.setLocalPath(imageLocalPath);
        saveUserImageToFileSystem(imageEncoded, userImage);
        ImageStorage imageStorage = createImageBackup(userImage, imageEncoded);
        userImage.setImageStorage(imageStorage);
        return userImageRepository.save(userImage);
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
        String imageLocalPath = getUserDirectoryPath(user) + userImageId + DOT_SEPARATOR + IMAGE_EXTENSION;
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
        return imageStorageRepository.save(imageStorage);
    }

    @SneakyThrows
    private AccountUserImageWeb convertToJpg(AccountUserImageWeb userImageWeb) {
        String imageEncoded = userImageWeb.getImage();

        byte[] imageData = Base64.getDecoder().decode(imageEncoded);
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
        BufferedImage newBufferedImage = new BufferedImage(
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBufferedImage, IMAGE_EXTENSION, baos);
        byte[] byteArray = baos.toByteArray();
        userImageWeb.setImage(Base64.getEncoder().encodeToString(byteArray));
        return userImageWeb;
    }
}
