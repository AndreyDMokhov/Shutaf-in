package com.shutafin.core.service.impl;


import com.shutafin.core.service.UserFilterService;
import com.shutafin.core.service.filter.UsersFilter;
import com.shutafin.model.filter.FilterAgeRange;
import com.shutafin.model.filter.FilterCity;
import com.shutafin.model.filter.FilterGender;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.AgeRangeWebDTO;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.repository.FilterAgeRangeRepository;
import com.shutafin.repository.FilterCityRepository;
import com.shutafin.repository.FilterGenderRepository;
import com.shutafin.repository.account.UserInfoRepository;
import com.shutafin.repository.account.UserRepository;
import com.shutafin.repository.locale.CityRepository;
import com.shutafin.repository.locale.GenderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgeny on 10/3/2017.
 */
@Service
@Transactional
@Slf4j
public class UserFilterServiceImpl implements UserFilterService {

    @Autowired
    private FilterCityRepository filterCityRepository;

    @Autowired
    private FilterGenderRepository filterGenderRepository;

    @Autowired
    private FilterAgeRangeRepository filterAgeRangeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private List<UsersFilter> usersFilters;

    @Autowired
    private UserFilterService userFilterService;


    @Override
    public List<Long> filterMatchedUserIds(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        saveUserFilters(userId, accountUserFilterRequest);

        List<Long> filteredUsersId = getFilteredUsers(userId, accountUserFilterRequest);

        if (filteredUsersId.isEmpty()) {
            return new ArrayList<>();
        }

        return userInfoRepository.getUserIdListByUserId(filteredUsersId);
    }

    private List<Long> getFilteredUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        List<Long> filteredUsersId = accountUserFilterRequest.getUserIds();

        if (!StringUtils.isEmpty(accountUserFilterRequest.getFullName())) {
            filteredUsersId = userRepository.findAllByFullName(filteredUsersId, accountUserFilterRequest.getFullName());
        }

        return applyFilters(userId, filteredUsersId);
    }

    @Override
    public List<UserSearchResponse> getUsers(List<Long> usersId) {
        return userInfoRepository.getUserSearchListByUserId(usersId);
    }

    @Override
    public List<UserSearchResponse> filterMatchedUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        List<Long> filteredUsersId = getFilteredUsers(userId, accountUserFilterRequest);

        if (!StringUtils.isEmpty(accountUserFilterRequest.getFullName()) && !filteredUsersId.isEmpty()) {
            filteredUsersId = userRepository.findAllByFullName(filteredUsersId, accountUserFilterRequest.getFullName());
        }

        filteredUsersId = applyFilters(userId, filteredUsersId);

        if (filteredUsersId.isEmpty()) {
            return new ArrayList<>();
        }

        return userInfoRepository.getUserSearchListByUserId(filteredUsersId);
    }

    private List<Long> applyFilters(Long userId, List<Long> matchedUsersList) {
        for (UsersFilter filter : usersFilters) {
            if (matchedUsersList.isEmpty()) {
                continue;
            }
            matchedUsersList = filter.doFilter(userId, matchedUsersList);
        }
        return matchedUsersList;
    }

    @Override
    public List<UserSearchResponse> saveUserFiltersAndGetUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        saveUserFilters(userId, accountUserFilterRequest);
        return userFilterService.filterMatchedUsers(userId, accountUserFilterRequest);
    }

    private void saveUserFilters(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        if (accountUserFilterRequest.getFiltersWeb() != null) {
            userFilterService.saveUserFilterCity(userId, accountUserFilterRequest.getFiltersWeb().getFilterCitiesIds());
            userFilterService.saveUserFilterGender(userId, accountUserFilterRequest.getFiltersWeb().getFilterGenderId());
            userFilterService.saveUserFilterAgeRange(userId, accountUserFilterRequest.getFiltersWeb().getFilterAgeRange());
        }
    }

    @Override
    public void saveUserFilterCity(Long userId, List<Integer> cities) {
        filterCityRepository.deleteByUserId(userId);
        if (cities != null) {
            for (Integer cityId : cities) {
                filterCityRepository.save(new FilterCity(userRepository.findOne(userId), cityRepository.findOne(cityId)));
            }
        }
    }

    @Override
    public void saveUserFilterGender(Long userId, Integer genderId) {
        filterGenderRepository.deleteByUserId(userId);
        if (genderId != null) {
            filterGenderRepository.save(new FilterGender(userRepository.findOne(userId), genderRepository.findOne(genderId)));
        }
    }

    @Override
    @Transactional
    public void saveUserFilterAgeRange(Long userId, AgeRangeWebDTO ageRangeWebDTO) {
        filterAgeRangeRepository.deleteByUserId(userId);
        if (ageRangeWebDTO != null) {
            filterAgeRangeRepository.save(new FilterAgeRange(userRepository.findOne(userId), ageRangeWebDTO.getFromAge(), ageRangeWebDTO.getToAge()));
        }
    }

    @Override
    public FiltersWeb getUserFilters(Long userId) {
        FiltersWeb filtersWeb = new FiltersWeb();
        filtersWeb.setFilterAgeRange(userFilterService.getAgeRangeForFilter(userId));
        filtersWeb.setFilterCitiesIds(userFilterService.getCitiesForFilter(userId));
        filtersWeb.setFilterGenderId(userFilterService.getGenderForFilter(userId));
        return filtersWeb;
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
