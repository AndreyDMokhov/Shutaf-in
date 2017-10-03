package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserBaseResponse;
import com.shutafin.model.web.user.UserSearchResponse;

import java.util.List;

public interface UserSearchService {
    List<UserSearchResponse> userSearchByList(List<User> users, String fullName);
    List<UserSearchResponse> userSearchByList(List<User> users);
    List<UserBaseResponse> userBaseResponseByList(List<User> users);
}
