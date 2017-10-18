package com.shutafin.service.impl;

import com.shutafin.model.entities.FilterAgeRange;
import com.shutafin.model.entities.FilterCity;
import com.shutafin.model.entities.FilterGender;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.model.web.user.FiltersWeb;
import com.shutafin.repository.common.FilterAgeRangeRepository;
import com.shutafin.repository.common.FilterCityRepository;
import com.shutafin.repository.common.FilterGenderRepository;
import com.shutafin.repository.initialization.locale.CityRepository;
import com.shutafin.repository.initialization.locale.GenderRepository;
import com.shutafin.service.UserFilterService;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.filter.UsersFiltersChain;
import com.shutafin.service.filter.filters.UsersByAgeRangeFiltersChainElement;
import com.shutafin.service.filter.filters.UsersByCityFiltersChainElement;
import com.shutafin.service.filter.filters.UsersByGenderFiltersChainElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
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
    private CityRepository cityRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private UserMatchService userMatchService;

    @Override
    @Transactional(readOnly = true)
    public List<User> findFilteredUsers(User user) {

        List<User> matchingUsersList = userMatchService.findMatchingUsers(user);

        //Call chain of responsibility
        UsersFiltersChain usersFiltersChain = createFilterChain(user);

        return usersFiltersChain.doMatch(matchingUsersList);
    }

    private UsersFiltersChain createFilterChain(User user) {
        UsersFiltersChain genderFilter = new UsersByGenderFiltersChainElement(user, filterGenderRepository);

        UsersFiltersChain cityFilter = new UsersByCityFiltersChainElement(user, filterCityRepository);
        genderFilter.setNext(cityFilter);

        UsersFiltersChain ageRangeFilter = new UsersByAgeRangeFiltersChainElement(user, filterAgeRangeRepository);
        cityFilter.setNext(ageRangeFilter);

        return genderFilter;
    }

    @Override
    @Transactional
    public void saveUserFilters(User user, FiltersWeb filtersWeb) {
        saveUserFilterCity(user, filtersWeb.getFilterCitiesIds());
        saveUserFilterGender(user, filtersWeb.getFilterGenderId());
        saveUserFilterAgeRange(user, filtersWeb.getFilterAgeRange());
    }

    @Override
    @Transactional
    public void saveUserFilterCity(User user, List<Integer> cities) {
        filterCityRepository.deleteUserFilterCity(user);
        if (cities != null){
            for (Integer cityId : cities) {
                filterCityRepository.save(new FilterCity(user, cityRepository.findById(cityId)));
            }
        }
    }

    @Override
    @Transactional
    public void saveUserFilterGender(User user, Integer genderId) {
        filterGenderRepository.deleteUserFilterGender(user);
        if (genderId != null){
            filterGenderRepository.save(new FilterGender(user, genderRepository.findById(genderId)));
        }
    }

    @Override
    @Transactional
    public void saveUserFilterAgeRange(User user, @Valid AgeRangeWebDTO ageRangeWebDTO) {
        filterAgeRangeRepository.deleteUserFilterAgeRange(user);
        if (ageRangeWebDTO != null){
            filterAgeRangeRepository.save(new FilterAgeRange(user, ageRangeWebDTO.getFromAge(), ageRangeWebDTO.getToAge()));
        }
    }
}
