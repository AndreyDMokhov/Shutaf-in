package com.shutafin.core.service.filter.filters;

import com.shutafin.core.service.filter.UsersFilter;
import com.shutafin.model.filter.FilterGender;
import com.shutafin.repository.FilterGenderRepository;
import com.shutafin.repository.account.UserInfoRepository;
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

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public List<Long> doFilter(Long userId, List<Long> filteredUsers) {
        Integer filterGender = filterGenderRepository.findGenderIdByUserId(userId);
        if (filterGender == null) {
            return filteredUsers;
        }

        filteredUsers = userInfoRepository.filterUsersFromListByGender(filteredUsers, filterGender);

        return filterUserIdsUserInfoByUserGender(userId, filteredUsers);
    }

    private List<Long> filterUserIdsUserInfoByUserGender(Long userId, List<Long> filteredUsers) {
        Integer gender = userInfoRepository.getGenderByUserId(userId);
        if (gender == null || filteredUsers.isEmpty()) {
            return filteredUsers;
        }
        List<FilterGender> genders = filterGenderRepository.findAllByUserId(filteredUsers);
        for (FilterGender filterGender : genders) {
            if (!filterGender.getGender().getId().equals(gender)) {
                filteredUsers.remove(filterGender.getUser().getId());
            }
        }
        return filteredUsers;
    }
}
