package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.ImagePair;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.ImagePairRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ImagePairRepositoryImpl extends AbstractEntityDao<ImagePair> implements ImagePairRepository {

    @Override
    public UserImage findCompressedUserImage(UserImage userImage) {
        return (UserImage) getSession()
                .createQuery("select ip.compressedImage from ImagePair ip where ip.originalImage.id = :userImageId")
                .setParameter("userImageId", userImage.getId()).uniqueResult();
    }

    @Override
    public UserImage findOriginalUserImage(UserImage userImage) {
        return (UserImage) getSession()
                .createQuery("select ip.originalImage from ImagePair ip where ip.compressedImage.id = :userImageId")
                .setParameter("userImageId", userImage.getId()).uniqueResult();
    }
}
