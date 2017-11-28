package com.shutafin.service;



import com.shutafin.model.dto.AgeRangeWebDTO;
import com.shutafin.model.dto.FiltersWeb;

import java.util.List;

/**
 * Created by evgeny on 10/3/2017.
 */
public interface UserFilterService {
    List<Long> findFilteredUsers(Long userId);
    void saveUserFilters(Long userId, FiltersWeb filtersWeb);
    void saveUserFilterCity(Long userId, List<Integer> cities);
    void saveUserFilterGender(Long userId, Integer genderId);
    void saveUserFilterAgeRange(Long userId, AgeRangeWebDTO ageRangeWebDTO);
    List<Integer> getCitiesForFilter(Long userId);
    Integer getGenderForFilter(Long userId);
    AgeRangeWebDTO getAgeRangeForFilter(Long userId);
}
