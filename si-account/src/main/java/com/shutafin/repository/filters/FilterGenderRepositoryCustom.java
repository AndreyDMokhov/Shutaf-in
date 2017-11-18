package com.shutafin.repository.filters;

import com.shutafin.model.entities.User;
import com.shutafin.model.infrastructure.Gender;

import java.util.List;

public interface FilterGenderRepositoryCustom {
    Gender getUserFilterGender(User user);
    List<User> getAllMatchedUsers(User user, List<User> matchedUsers);
    void deleteUserFilterGender(User user);
}
