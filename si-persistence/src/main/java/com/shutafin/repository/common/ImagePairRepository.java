package com.shutafin.repository.common;

import com.shutafin.model.entities.ImagePair;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.base.PersistentDao;

public interface ImagePairRepository  extends PersistentDao<ImagePair> {

    UserImage findCompressedUserImage(UserImage userImage);
    UserImage findOriginalUserImage(UserImage userImage);
}
