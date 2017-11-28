package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.model.web.user.UserBaseResponse;
import com.shutafin.model.web.user.UserSearchResponse;

import java.util.List;

//TODO move not @Deprecated methods to account service

public interface UserSearchService {
    List<UserSearchResponse> userSearchByList(List<User> users, String fullName);
    List<UserSearchResponse> userSearchByList(List<User> users);
    @Deprecated
    List<Integer> getCitiesForFilter(User user);
    @Deprecated
    Integer getGenderForFilter(User user);
    @Deprecated
    AgeRangeWebDTO getAgeRangeForFilter(User user);
    List<UserBaseResponse> userBaseResponseByList(List<User> users);
    UserSearchResponse findUserDataById(Long userId);
}
