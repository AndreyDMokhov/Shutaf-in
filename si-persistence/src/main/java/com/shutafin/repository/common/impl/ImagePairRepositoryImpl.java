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
                .createQuery("select ip.compressedUserImage from ImagePair ip where ip.originalUserImage.id = :userImageId")
                .setParameter("userImageId", userImage.getId()).uniqueResult();
    }

    @Override
    public UserImage findOriginalUserImage(UserImage userImage) {
        return (UserImage) getSession()
                .createQuery("select ip.originalUserImage from ImagePair ip where ip.compressedUserImage.id = :userImageId")
                .setParameter("userImageId", userImage.getId()).uniqueResult();
    }
}
