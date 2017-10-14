package com.shutafin.service.impl;

import com.shutafin.model.entities.ImagePair;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.types.CompressionType;
import com.shutafin.model.entities.types.PermissionType;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.repository.common.ImagePairRepository;
import com.shutafin.repository.common.UserImageRepository;
import com.shutafin.service.ImageCompressService;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@Transactional
@Slf4j
public class ImageCompressServiceImpl implements ImageCompressService {

    private static final String IMAGE_EXTENSION = "jpg";

    private ImagePairRepository imagePairRepository;
    private UserImageRepository userImageRepository;
    private UserImageService userImageService;

    @Autowired
    public ImageCompressServiceImpl(ImagePairRepository imagePairRepository,
                                    UserImageRepository userImageRepository,
                                    UserImageService userImageService) {
        this.imagePairRepository = imagePairRepository;
        this.userImageRepository = userImageRepository;
        this.userImageService = userImageService;
    }

    @Override
    public UserImage addCompressedUserImage(UserImage userImage, CompressionType compressionType) {
        UserImage compressedUserImage = imagePairRepository.findCompressedUserImage(userImage);
        if (compressedUserImage == null) {
            String imageEncoded = compressUserImage(userImage.getImageStorage().getImageEncoded(), compressionType);
            compressedUserImage = saveCompressedUserImage(imageEncoded, userImage.getUser(), userImage.getPermissionType(),
                    compressionType);
            saveImagePair(userImage, compressedUserImage);
        }
        return compressedUserImage;
    }

    private UserImage saveCompressedUserImage(String imageEncoded, User user, PermissionType permissionType,
                                              CompressionType compressionType) {
        UserImageWeb compressedImage = new UserImageWeb();
        compressedImage.setImage(imageEncoded);
        UserImage compressedUserImage = userImageService.addUserImage(compressedImage, user, permissionType, null);
        compressedUserImage.setCompressionType(compressionType);
        userImageRepository.update(compressedUserImage);
        return compressedUserImage;
    }

    private String convertToJpg(String imageEncoded) {
        try {
            byte[] imageData = Base64.getDecoder().decode(imageEncoded);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(newBufferedImage, IMAGE_EXTENSION, baos);
            byte[] byteArray = baos.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        } catch (IOException exp) {
            log.error("Could not read or write image: ", exp);
        }
        return null;
    }

    private String compressUserImage(String imageEncoded, CompressionType compressionType) {
        imageEncoded = convertToJpg(imageEncoded);
        imageEncoded = resizeImage(imageEncoded, compressionType);
        imageEncoded = compressImageQuality(imageEncoded, compressionType);
        return imageEncoded;
    }

    private String compressImageQuality(String imageEncoded, CompressionType compressionType) {
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
        imageWriteParam.setCompressionQuality(compressionType.getCompressionQuality());

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

    private String resizeImage(String imageEncoded, CompressionType compressionType) {
        BufferedImage bufferedImage = getBufferedImage(imageEncoded);

        Integer type = (bufferedImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;

        Integer compressedSize = compressionType.getCompressSize();
        BufferedImage img = new BufferedImage(compressedSize, compressedSize, type);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(bufferedImage, 0, 0, compressedSize, compressedSize, null);
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

    private void saveImagePair(UserImage originalImage, UserImage compressedImage) {
        ImagePair imagePair = new ImagePair(originalImage, compressedImage);
        imagePairRepository.save(imagePair);
    }
}
