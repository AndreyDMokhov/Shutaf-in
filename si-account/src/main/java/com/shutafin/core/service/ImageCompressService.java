package com.shutafin.core.service;

import com.shutafin.model.entities.UserImage;
import com.shutafin.model.types.CompressionType;

public interface ImageCompressService {

    UserImage addCompressedUserImage(UserImage userImage, CompressionType compressionType);
}
