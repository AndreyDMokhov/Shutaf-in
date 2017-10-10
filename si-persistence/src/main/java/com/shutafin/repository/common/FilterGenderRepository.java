package com.shutafin.repository.common;

import com.shutafin.model.entities.FilterGender;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public interface FilterGenderRepository extends PersistentDao<FilterGender> {
    List<Gender> getUserFilterGender(User user);
    List<User> getAllMatchedUsers(User user, List<User> matchedUsers);
    void deleteUserFilterGender(User user);
}
