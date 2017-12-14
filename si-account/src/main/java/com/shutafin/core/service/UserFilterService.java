package com.shutafin.core.service;

import com.shutafin.model.User;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.model.web.user.FiltersWeb;

import java.util.List;

/**
 * Created by evgeny on 10/3/2017.
 */
public interface UserFilterService {
    List<User> filterMatchedUsers(Long userId, List<Long> matchedUsersList);
    void saveUserFilters(Long userId, FiltersWeb filtersWeb);
    void saveUserFilterCity(Long userId, List<Integer> cities);
    void saveUserFilterGender(Long userId, Integer genderId);
    void saveUserFilterAgeRange(Long userId, AgeRangeWebDTO ageRangeWebDTO);
    List<Integer> getCitiesForFilter(Long userId);
    Integer getGenderForFilter(Long userId);
    AgeRangeWebDTO getAgeRangeForFilter(Long userId);
}
