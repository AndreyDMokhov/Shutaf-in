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
import lombok.SneakyThrows;
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
            compressedUserImage = saveCompressedUserImage(
                    imageEncoded,
                    userImage.getUser(),
                    userImage.getPermissionType(),
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
        userImageRepository.save(compressedUserImage);
        return compressedUserImage;
    }

    private String compressUserImage(String imageEncoded, CompressionType compressionType) {
        imageEncoded = resizeImage(imageEncoded, compressionType);
        imageEncoded = compressImageQuality(imageEncoded, compressionType);
        return imageEncoded;
    }

    @SneakyThrows
    private String compressImageQuality(String imageEncoded, CompressionType compressionType) {
        BufferedImage bufferedImage = getBufferedImage(imageEncoded);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);

        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(IMAGE_EXTENSION).next();

        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(compressionType.getCompressionQuality());

        imageWriter.setOutput(imageOutputStream);

        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);

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

    @SneakyThrows
    private String encodeBufferedImage(BufferedImage img) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img, IMAGE_EXTENSION, byteArrayOutputStream);
        byte[] imageDecoded = byteArrayOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(imageDecoded);
    }

    @SneakyThrows
    private BufferedImage getBufferedImage(String imageEncoded) {
        byte[] imageDecoded = Base64.getDecoder().decode(imageEncoded);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageDecoded);
        return ImageIO.read(byteArrayInputStream);
    }

    private void saveImagePair(UserImage originalImage, UserImage compressedImage) {
        imagePairRepository.save(new ImagePair(originalImage, compressedImage));
    }
}
