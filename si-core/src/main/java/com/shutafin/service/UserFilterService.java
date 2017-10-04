package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.AgeRangeRequest;

import java.util.List;

/**
 * Created by evgeny on 10/3/2017.
 */
public interface UserFilterService {
    List<User> findFilteredUsers(User user);
    void saveUserFilterCity(User user, List<Integer> cities);
    void saveUserFilterGender(User user, Integer genderId);
    void saveUserFilterAgeRange(User user, AgeRangeRequest ageRangeRequest);
}
