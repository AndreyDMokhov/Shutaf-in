package com.shutafin.repository.account;

import com.shutafin.model.entities.ImagePair;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImagePairRepository extends BaseJpaRepository<ImagePair, Long> {

    @Query("select ip.compressedImage from ImagePair ip where ip.originalImage = :userImage")
    UserImage findCompressedUserImage(@Param("userImage") UserImage userImage);

    @Query("select ip.originalImage from ImagePair ip where ip.compressedImage = :userImage")
    UserImage findOriginalUserImage(@Param("userImage") UserImage userImage);

    @Query("select ip.originalImage.id from ImagePair ip where ip.compressedImage.id = :userImageId")
    Long findOriginalUserImageById(@Param("userImageId") Long userImageId);
}
