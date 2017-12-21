package com.shutafin.service;

import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.user.UserBaseResponse;
import com.shutafin.model.web.common.UserSearchResponse;

import java.util.List;


public interface UserSearchService {
    List<UserSearchResponse> userSearchByList(Long authenticatedUserId, List<Long> users, String fullName);
    List<UserSearchResponse> userSearchByList(Long authenticatedUserId, List<Long> users, FiltersWeb filtersWeb);
    List<UserBaseResponse> userBaseResponseByList(Long authenticatedUser, List<Long> users);
    UserSearchResponse findUserDataById(Long userId);
}
