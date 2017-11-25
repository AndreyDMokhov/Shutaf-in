package com.shutafin.service.filter;


import java.util.List;

/**
 * Created by Rogov on 18.11.2017.
 */

//TODO move this package to service
public interface UsersFilter {

    List<Long> doFilter(Long userId, List<Long> filteredUsers);
}
