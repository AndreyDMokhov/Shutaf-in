package com.shutafin.core.service.filter;


import java.util.List;

/**
 * Created by Rogov on 18.11.2017.
 */

public interface UsersFilter {

    List<Long> doFilter(Long userId, List<Long> filteredUsers);
}
