package com.shutafin.repository.impl;

import com.shutafin.model.entities.ImageStorage;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.ImageStorageRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

@Repository
public class ImageStorageRepositoryImpl extends AbstractEntityDao<ImageStorage> implements ImageStorageRepository {

    @Override
    public ImageStorage findStoredUserImage(UserImage userImage) {
        return (ImageStorage) getSession()
                .createQuery("from StoredImage si where si.userImage.id = :userImageId")
                .setParameter("userImageId", userImage.getId()).uniqueResult();
    }
}
