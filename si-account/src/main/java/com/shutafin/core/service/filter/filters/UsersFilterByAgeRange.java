package com.shutafin.core.service.filter.filters;


import com.shutafin.core.service.filter.UsersFilter;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.repository.FilterAgeRangeRepository;
import com.shutafin.repository.account.UserInfoRepository;
import org.joda.time.DateTime;
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
        List<Long> filteredUsersByAgeRange = filterUsersInUserInfoByUserIds(filteredUsers, ageRangeWebDTO);

        return filterAgeRangeRepository.filterUsersFromListByAge(
                filteredUsersByAgeRange,
                ageRangeWebDTO.getFromAge(),
                ageRangeWebDTO.getToAge());
    }

    private List<Long> filterUsersInUserInfoByUserIds(List<Long> filteredUsers, AgeRangeWebDTO ageRangeWebDTO) {
        Date fromBirthDate = DateTime.now().minusYears(ageRangeWebDTO.getToAge()).toDate();
        Date toBirthDate = DateTime.now().minusYears(ageRangeWebDTO.getFromAge()).toDate();
        return userInfoRepository.filterUsersFromListByAge(filteredUsers, fromBirthDate, toBirthDate);
    }
}
