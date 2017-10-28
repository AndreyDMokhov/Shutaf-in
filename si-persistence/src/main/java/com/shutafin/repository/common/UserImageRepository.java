package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;

public interface UserImageRepository extends BaseJpaRepository<UserImage, Long> {

    List<UserImage> findAllByUser(User user);
}
