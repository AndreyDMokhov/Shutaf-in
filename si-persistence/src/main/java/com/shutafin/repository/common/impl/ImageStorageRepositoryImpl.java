package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.ImageStorage;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.common.ImageStorageRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

@Repository
public class ImageStorageRepositoryImpl extends AbstractEntityDao<ImageStorage> implements ImageStorageRepository {

    @Override
    public ImageStorage findImageStorage(UserImage userImage) {
        return (ImageStorage) getSession()
                .createQuery("from ImageStorage img where img.userImage = :userImage")
                .setParameter("userImage", userImage).uniqueResult();
    }
}
