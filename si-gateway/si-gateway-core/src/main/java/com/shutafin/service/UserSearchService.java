package com.shutafin.service;

import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.matching.UserBaseResponse;
import com.shutafin.model.web.common.UserSearchResponse;

import java.util.List;
import java.util.Map;


public interface UserSearchService {
    List<UserSearchResponse> userSearchByList(Long authenticatedUserId, List<Long> users, String fullName);
    List<UserSearchResponse> userSearchByMap(Long authenticatedUserId, Map<Long, Integer> users, String fullName, FiltersWeb filtersWeb);

    @Deprecated
    List<UserBaseResponse> userBaseResponseByList(Long authenticatedUser, List<Long> users);
    UserSearchResponse findUserDataById(Long userId);
}
