package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Gender;

import java.util.List;

@Deprecated
public interface FilterGenderRepositoryCustom {
    Gender getUserFilterGender(User user);
    List<User> getAllMatchedUsers(User user, List<User> matchedUsers);
    void deleteUserFilterGender(User user);
}
