package com.shutafin.service.impl;

import com.shutafin.model.dto.AgeRangeWebDTO;
import com.shutafin.model.dto.FiltersWeb;
import com.shutafin.model.entities.FilterAgeRange;
import com.shutafin.model.entities.FilterCity;
import com.shutafin.model.entities.FilterGender;
import com.shutafin.repository.FilterAgeRangeRepository;
import com.shutafin.repository.FilterCityRepository;
import com.shutafin.repository.FilterGenderRepository;
import com.shutafin.service.UserFilterService;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.filter.UsersFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by evgeny on 10/3/2017.
 */
@Service
@Transactional
@Slf4j
public class UserFilterServiceImpl implements UserFilterService {

    private FilterCityRepository filterCityRepository;
    private FilterGenderRepository filterGenderRepository;
    private FilterAgeRangeRepository filterAgeRangeRepository;
    private UserMatchService userMatchService;
    private List<UsersFilter> usersFilters;

    @Autowired
    public UserFilterServiceImpl(FilterCityRepository filterCityRepository, FilterGenderRepository filterGenderRepository, FilterAgeRangeRepository filterAgeRangeRepository, UserMatchService userMatchService, List<UsersFilter> usersFilters) {
        this.filterCityRepository = filterCityRepository;
        this.filterGenderRepository = filterGenderRepository;
        this.filterAgeRangeRepository = filterAgeRangeRepository;
        this.userMatchService = userMatchService;
        this.usersFilters = usersFilters;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findFilteredUsers(Long userId) {

        List<Long> matchingUsersList = userMatchService.findMatchingUsers(userId);

        return matchingUsersList.isEmpty() ? matchingUsersList : filterMatchedUsers(userId, matchingUsersList);
    }

    private List<Long> filterMatchedUsers(Long userId, List<Long> matchingUsersList) {
        for (UsersFilter filter : usersFilters) {
            if (matchingUsersList.isEmpty()) {
                continue;
            }
            matchingUsersList = filter.doFilter(userId, matchingUsersList);
        }
        return matchingUsersList;
    }

    @Override
    @Transactional
    public void saveUserFilters(Long userId, FiltersWeb filtersWeb) {
        if (filtersWeb == null) {
            return;
        }
        saveUserFilterCity(userId, filtersWeb.getFilterCitiesIds());
        saveUserFilterGender(userId, filtersWeb.getFilterGenderId());
        saveUserFilterAgeRange(userId, filtersWeb.getFilterAgeRange());
    }

    @Override
    @Transactional
    public void saveUserFilterCity(Long userId, List<Integer> cities) {
        filterCityRepository.deleteByUserId(userId);
        if (cities != null) {
            for (Integer cityId : cities) {
                filterCityRepository.save(new FilterCity(userId, cityId));
            }
        }
    }

    @Override
    @Transactional
    public void saveUserFilterGender(Long userId, Integer genderId) {
        filterGenderRepository.deleteByUserId(userId);
        if (genderId != null) {
            filterGenderRepository.save(new FilterGender(userId, genderId));
        }
    }

    @Override
    @Transactional
    public void saveUserFilterAgeRange(Long userId, AgeRangeWebDTO ageRangeWebDTO) {
        filterAgeRangeRepository.deleteByUserId(userId);
        if (ageRangeWebDTO != null) {
            filterAgeRangeRepository.save(new FilterAgeRange(userId, ageRangeWebDTO.getFromAge(), ageRangeWebDTO.getToAge()));
        }
    }

    @Override
    public List<Integer> getCitiesForFilter(Long userId) {
        List<Integer> cities = filterCityRepository.findAllCityIdsByUserId(userId);
        if (cities.isEmpty()) {
            return null;
        }
        return cities;
    }

    @Override
    public Integer getGenderForFilter(Long userId) {
        return filterGenderRepository.findGenderIdByUserId(userId);
    }

    @Override
    public AgeRangeWebDTO getAgeRangeForFilter(Long userId) {
        return filterAgeRangeRepository.findAgeRangeByUserId(userId);
    }
}
