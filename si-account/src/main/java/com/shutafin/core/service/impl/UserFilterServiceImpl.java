package com.shutafin.core.service.impl;


import com.shutafin.core.service.UserFilterService;
import com.shutafin.core.service.filter.UsersFilter;
import com.shutafin.model.filter.FilterAgeRange;
import com.shutafin.model.filter.FilterCity;
import com.shutafin.model.filter.FilterGender;
import com.shutafin.model.web.common.AgeRangeWebDTO;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.account.AccountUserFilterRequest;
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

    private FilterCityRepository filterCityRepository;
    private FilterGenderRepository filterGenderRepository;
    private FilterAgeRangeRepository filterAgeRangeRepository;
    private UserRepository userRepository;
    private CityRepository cityRepository;
    private GenderRepository genderRepository;
    private UserInfoRepository userInfoRepository;
    private List<UsersFilter> usersFilters;

    @Autowired
    public UserFilterServiceImpl(UserInfoRepository userInfoRepository, GenderRepository genderRepository, CityRepository cityRepository, FilterCityRepository filterCityRepository, FilterGenderRepository filterGenderRepository, FilterAgeRangeRepository filterAgeRangeRepository, UserRepository userRepository, List<UsersFilter> usersFilters) {
        this.filterCityRepository = filterCityRepository;
        this.filterGenderRepository = filterGenderRepository;
        this.filterAgeRangeRepository = filterAgeRangeRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.genderRepository = genderRepository;
        this.userInfoRepository = userInfoRepository;
        this.usersFilters = usersFilters;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSearchResponse> filterMatchedUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        List<Long> filteredUsersId = accountUserFilterRequest.getUserIds();

        if (!StringUtils.isEmpty(accountUserFilterRequest.getFullName())) {
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
    @Transactional
    public List<UserSearchResponse> saveUserFiltersAndGetUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        if (accountUserFilterRequest.getFiltersWeb() != null) {
            saveUserFilterCity(userId, accountUserFilterRequest.getFiltersWeb().getFilterCitiesIds());
            saveUserFilterGender(userId, accountUserFilterRequest.getFiltersWeb().getFilterGenderId());
            saveUserFilterAgeRange(userId, accountUserFilterRequest.getFiltersWeb().getFilterAgeRange());
        }

        return filterMatchedUsers(userId,accountUserFilterRequest);
    }

    @Override
    @Transactional
    public void saveUserFilterCity(Long userId, List<Integer> cities) {
        filterCityRepository.deleteByUserId(userId);
        if (cities != null) {
            for (Integer cityId : cities) {
                filterCityRepository.save(new FilterCity(userRepository.findOne(userId), cityRepository.findOne(cityId)));
            }
        }
    }

    @Override
    @Transactional
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
        filtersWeb.setFilterAgeRange(getAgeRangeForFilter(userId));
        filtersWeb.setFilterCitiesIds(getCitiesForFilter(userId));
        filtersWeb.setFilterGenderId(getGenderForFilter(userId));
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
