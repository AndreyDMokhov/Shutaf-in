package com.shutafin.repository;

import com.shutafin.model.entities.ImageStorage;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.base.PersistentDao;

public interface ImageStorageRepository extends PersistentDao<ImageStorage> {

    ImageStorage findStoredUserImage(UserImage userImage);
}
