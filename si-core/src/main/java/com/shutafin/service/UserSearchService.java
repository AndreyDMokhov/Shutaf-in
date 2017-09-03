package com.shutafin.service;

import com.shutafin.model.web.user.UserSearchResponse;

import java.util.List;

public interface UserSearchService {
    List<UserSearchResponse> userSearch(String fullName);
}
