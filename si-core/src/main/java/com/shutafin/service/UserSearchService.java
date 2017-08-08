package com.shutafin.service;

import com.shutafin.model.web.user.UserSearchWeb;

import java.util.List;

public interface UserSearchService {
    List<UserSearchWeb> userSearch(String fullName);
}
