package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.ImageStorage;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.types.PermissionType;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.repository.common.ImageStorageRepository;
import com.shutafin.repository.common.UserImageRepository;
import com.shutafin.service.EnvironmentConfigurationService;
import com.shutafin.service.UserImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
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

    private static final String IMAGE_EXTENSION = "jpg";
    private static final String DOT_DELIMITER = ".";
    private static final Integer COMPRESSED_IMAGE_SIZE = 256;
    private static final Float COMPRESSION_QUALITY = 0.7f;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private ImageStorageRepository imageStorageRepository;

    @Autowired
    private EnvironmentConfigurationService environmentConfigurationService;

    @Override
    @Transactional
    public UserImage addUserImage(UserImageWeb image, User user, PermissionType permissionType) {
        UserImage userImage = new UserImage();
        userImage.setPermissionType(permissionType);
        String imageEncoded = image.getImage();
        userImage.setUser(user);

        userImageRepository.save(userImage);

        String imageLocalPath = getUserDirectoryPath(user) + userImage.getId() + DOT_DELIMITER + IMAGE_EXTENSION;
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

    @Override
    public UserImage compressUserImage(UserImage userImage) {
        String imageEncoded = userImage.getImageStorage().getImageEncoded();
        imageEncoded = resizeImage(imageEncoded);
        imageEncoded = compressImageQuality(imageEncoded);
        UserImageWeb compressedImage = new UserImageWeb();
        compressedImage.setImage(imageEncoded);
        return addUserImage(compressedImage, userImage.getUser(), userImage.getPermissionType());
    }

    private String compressImageQuality(String imageEncoded) {
        BufferedImage bufferedImage = getBufferedImage(imageEncoded);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream = null;
        try {
            imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);
        } catch (IOException e) {
            log.error("Could not create Image Output Stream: ", e);
        }
        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(IMAGE_EXTENSION).next();

        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(COMPRESSION_QUALITY);

        imageWriter.setOutput(imageOutputStream);

        try {
            imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
        } catch (IOException e) {
            log.error("Could not write Image to stream: ", e);
        }

        imageWriter.dispose();

        byte[] decodedImage = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(decodedImage);

    }

    private String resizeImage(String imageEncoded) {
        BufferedImage bufferedImage = getBufferedImage(imageEncoded);

        Integer type = (bufferedImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;

        BufferedImage img = new BufferedImage(COMPRESSED_IMAGE_SIZE, COMPRESSED_IMAGE_SIZE, type);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(bufferedImage, 0, 0, COMPRESSED_IMAGE_SIZE, COMPRESSED_IMAGE_SIZE, null);
        g2.dispose();

        return encodeBufferedImage(img);
    }

    private String encodeBufferedImage(BufferedImage img) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, IMAGE_EXTENSION, byteArrayOutputStream);
        } catch (IOException e) {
            log.error("Could not write image to Byte Array: ", e);
        }
        byte[] imageDecoded = byteArrayOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(imageDecoded);
    }

    private BufferedImage getBufferedImage(String imageEncoded) {
        byte[] imageDecoded = Base64.getDecoder().decode(imageEncoded);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageDecoded);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            log.error("Could not read image from Byte Array: ", e);
        }
        return bufferedImage;
    }

    private void deleteLocalImage(UserImage userImage) {
        File image = new File(userImage.getLocalPath());
        image.delete();
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
        String imageLocalPath = getUserDirectoryPath(user) + userImageId + DOT_DELIMITER + IMAGE_EXTENSION;
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
}
