package com.shutafin.core.service.filter.filters;

import com.shutafin.core.service.filter.UsersFilter;
import com.shutafin.repository.FilterCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
@Service
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
