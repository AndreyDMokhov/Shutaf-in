package com.shutafin.service.filter.filters;

import com.shutafin.repository.FilterCityRepository;
import com.shutafin.service.filter.UsersFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public class UsersFilterByCity implements UsersFilter {

    @Autowired
    private FilterCityRepository filterCityRepository;

    @Override
    public List<Long> doFilter(Long userId, List<Long> filteredUsers) {
        List<Integer> filterCity = filterCityRepository.findAllCityIdsByUserId(userId);
        if (filterCity.isEmpty()) {
            return filteredUsers;
        }
        return filterCityRepository.filterUsersFromListByCity(filteredUsers,filterCity);
    }
}
