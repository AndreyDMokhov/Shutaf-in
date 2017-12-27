package com.shutafin.core.service.filter.filters;


import com.shutafin.core.service.filter.UsersFilter;
import com.shutafin.model.filter.FilterAgeRange;
import com.shutafin.model.web.common.AgeRangeWebDTO;
import com.shutafin.repository.FilterAgeRangeRepository;
import com.shutafin.repository.account.UserInfoRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by evgeny on 10/4/2017.
 */

@Service
public class UsersFilterByAgeRange implements UsersFilter {

    @Autowired
    private FilterAgeRangeRepository filterAgeRangeRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public List<Long> doFilter(Long userId, List<Long> filteredUsers) {

        AgeRangeWebDTO ageRangeWebDTO = filterAgeRangeRepository.findAgeRangeByUserId(userId);
        if (ageRangeWebDTO == null) {
            return filteredUsers;
        }
        filteredUsers = filterUserIdsInUserInfoByFilter(filteredUsers, ageRangeWebDTO);

        return filterUserIdsInAgeRangeByUserAge(userId, filteredUsers);
    }

    private List<Long> filterUserIdsInUserInfoByFilter(List<Long> filteredUsers, AgeRangeWebDTO ageRangeWebDTO) {
        Date fromBirthDate = DateTime.now().minusYears(ageRangeWebDTO.getToAge()).toDate();
        Date toBirthDate = DateTime.now().minusYears(ageRangeWebDTO.getFromAge()).toDate();
        return userInfoRepository.filterUsersFromListByAge(filteredUsers, fromBirthDate, toBirthDate);
    }

    private List<Long> filterUserIdsInAgeRangeByUserAge(Long userId, List<Long> filteredUsers){
        Date userBirthDate = userInfoRepository.getBirthDateByUserId(userId);
        if(userBirthDate == null || filteredUsers.isEmpty()){
            return filteredUsers;
        }
        Integer userAge = org.joda.time.Years.yearsBetween(new LocalDate(userBirthDate), LocalDate.now()).getYears();
        List<FilterAgeRange> filteredUsersAgeRanges = filterAgeRangeRepository.findAllByUserIdIn(filteredUsers);
        for (FilterAgeRange filteredUsersAgeRange : filteredUsersAgeRanges) {
            if(filteredUsersAgeRange.getFromAge()>userAge||filteredUsersAgeRange.getToAge()<userAge){
                filteredUsers.remove(filteredUsersAgeRange.getUser().getId());
            }
        }
        return filteredUsers;
    }

}
