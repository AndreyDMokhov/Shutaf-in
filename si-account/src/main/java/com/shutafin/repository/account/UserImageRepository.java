package com.shutafin.repository.account;

import com.shutafin.model.entities.UserImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends CrudRepository<UserImage, Long> {

}
