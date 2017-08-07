package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSearch;
import com.shutafin.model.web.user.UserSearchWeb;

import java.util.List;

public interface UserSearchService {
    List<UserSearchWeb> userSearch(String fullName);
}
