package com.shutafin.service.filter.filters;

import com.shutafin.repository.FilterGenderRepository;
import com.shutafin.service.filter.UsersFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public class UsersFilterByGender implements UsersFilter {

    @Autowired
    private FilterGenderRepository filterGenderRepository;

    @Override
    public List<Long> doFilter(Long userId, List<Long> filteredUsers) {
        Integer filterGender = filterGenderRepository.findGenderIdByUserId(userId);
        if (filterGender == null) {
            return filteredUsers;
        }
        return filterGenderRepository.filterUsersFromListByGender(filteredUsers,filterGender);
    }
}
