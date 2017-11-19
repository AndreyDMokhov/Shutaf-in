package com.shutafin.service.filter.filters;



import com.shutafin.model.DTO.AgeRangeWebDTO;
import com.shutafin.repository.FilterAgeRangeRepository;
import com.shutafin.service.filter.UsersFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by evgeny on 10/4/2017.
 */
//TODO establish connection with account service to get users with right age
//TODO maybe shell cancel the reverse dependency from users filter age settings?
public class UsersFilterByAgeRange implements UsersFilter {

    @Autowired
    private FilterAgeRangeRepository filterAgeRangeRepository;

    @Override
    public List<Long> doFilter(Long userId, List<Long> filteredUsers) {

        AgeRangeWebDTO ageRangeWebDTO = filterAgeRangeRepository.findAgeRangeByUserId(userId);
        if (ageRangeWebDTO == null) {
            return filteredUsers;
        }
        return filterAgeRangeRepository.filterUsersFromListByAge(filteredUsers,
                ageRangeWebDTO.getFromAge(),
                ageRangeWebDTO.getToAge());
    }
}
