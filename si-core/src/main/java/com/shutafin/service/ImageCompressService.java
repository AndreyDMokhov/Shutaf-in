package com.shutafin.service;

import com.shutafin.model.entities.UserImage;

public interface ImageCompressService {

    UserImage getCompressedUserImage(UserImage userImage);
}
