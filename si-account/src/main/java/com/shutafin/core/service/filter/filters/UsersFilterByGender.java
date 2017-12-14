package com.shutafin.core.service.filter.filters;

import com.shutafin.core.service.filter.UsersFilter;
import com.shutafin.repository.FilterGenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
@Service
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
