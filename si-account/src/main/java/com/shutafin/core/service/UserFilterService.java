package com.shutafin.core.service;

import com.shutafin.model.web.common.AgeRangeWebDTO;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;

import java.util.List;

/**
 * Created by evgeny on 10/3/2017.
 */
public interface UserFilterService {
    List<UserSearchResponse> filterMatchedUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest);
    List<UserSearchResponse> saveUserFiltersAndGetUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest);
    void saveUserFilterCity(Long userId, List<Integer> cities);
    void saveUserFilterGender(Long userId, Integer genderId);
    void saveUserFilterAgeRange(Long userId, AgeRangeWebDTO ageRangeWebDTO);
    FiltersWeb getUserFilters (Long userId);
    List<Integer> getCitiesForFilter(Long userId);
    Integer getGenderForFilter(Long userId);
    AgeRangeWebDTO getAgeRangeForFilter(Long userId);
}
