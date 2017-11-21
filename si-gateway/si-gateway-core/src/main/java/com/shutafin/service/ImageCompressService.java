package com.shutafin.service;

import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.types.CompressionType;

public interface ImageCompressService {

    UserImage addCompressedUserImage(UserImage userImage, CompressionType compressionType);
}
