package com.shutafin.core.service.impl;


import com.shutafin.core.service.UserFilterService;
import com.shutafin.core.service.filter.UsersFilter;
import com.shutafin.model.User;
import com.shutafin.model.filter.FilterAgeRange;
import com.shutafin.model.filter.FilterCity;
import com.shutafin.model.filter.FilterGender;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.model.web.user.FiltersWeb;
import com.shutafin.repository.FilterAgeRangeRepository;
import com.shutafin.repository.FilterCityRepository;
import com.shutafin.repository.FilterGenderRepository;
import com.shutafin.repository.account.UserRepository;
import com.shutafin.repository.locale.CityRepository;
import com.shutafin.repository.locale.GenderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private List<UsersFilter> usersFilters;

    @Autowired
    public UserFilterServiceImpl(GenderRepository genderRepository, CityRepository cityRepository, FilterCityRepository filterCityRepository, FilterGenderRepository filterGenderRepository, FilterAgeRangeRepository filterAgeRangeRepository, UserRepository userRepository, List<UsersFilter> usersFilters) {
        this.filterCityRepository = filterCityRepository;
        this.filterGenderRepository = filterGenderRepository;
        this.filterAgeRangeRepository = filterAgeRangeRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.genderRepository = genderRepository;
        this.usersFilters = usersFilters;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> filterMatchedUsers(Long userId, List<Long> matchedUsersList) {
        for (UsersFilter filter : usersFilters) {
            if (matchedUsersList.isEmpty()) {
                continue;
            }
            matchedUsersList = filter.doFilter(userId, matchedUsersList);
        }
        return getUsersList(matchedUsersList);
    }

    private List<User> getUsersList(List<Long> matchedUsersList){
        return matchedUsersList.stream().map(id->{
            com.shutafin.model.entities.User userEntity = userRepository.findOne(id);
            return new User(userEntity.getId(),
                    userEntity.getFirstName(),
                    userEntity.getLastName(),
                    userEntity.getEmail(),
                    userEntity.getCreatedDate().getTime(),
                    userEntity.getUpdatedDate().getTime());
        }).collect(Collectors.toList());
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
