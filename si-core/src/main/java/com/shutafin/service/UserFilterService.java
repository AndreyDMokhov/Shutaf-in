package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.AgeRangeRequest;
import com.shutafin.model.web.user.FiltersWeb;

import java.util.List;

/**
 * Created by evgeny on 10/3/2017.
 */
public interface UserFilterService {
    List<User> findFilteredUsers(User user);
    void saveUserFilters(User user, FiltersWeb filtersWeb);
    void saveUserFilterCity(User user, List<Integer> cities);
    void saveUserFilterGender(User user, Integer genderId);
    void saveUserFilterAgeRange(User user, AgeRangeRequest ageRangeRequest);
}
