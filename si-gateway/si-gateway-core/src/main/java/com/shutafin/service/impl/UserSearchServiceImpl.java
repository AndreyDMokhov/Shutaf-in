package com.shutafin.service.impl;

import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.user.UserBaseResponse;
import com.shutafin.sender.account.UserFilterControllerSender;
import com.shutafin.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    public UserFilterControllerSender userFilterControllerSender;


    @Override
    public List<UserSearchResponse> userSearchByList(Long authenticatedUserId, List<Long> users, String fullName) {
        return userFilterControllerSender.getFilteredUsers(authenticatedUserId, new AccountUserFilterRequest(users,fullName));
    }


    @Override
    public List<UserSearchResponse> userSearchByList(Long authenticatedUserId, List<Long> users, FiltersWeb filtersWeb) {
        userFilterControllerSender.saveUserFilters(authenticatedUserId, filtersWeb);
        return userFilterControllerSender.getFilteredUsers(authenticatedUserId, new AccountUserFilterRequest(users, null));
    }

    @Override
    public List<UserBaseResponse> userBaseResponseByList(Long authenticatedUser, List<Long> users) {
        return userSearchByList(authenticatedUser, users, "")
                .stream()
                .map(x -> new UserBaseResponse(x.getUserId(), x.getFirstName(), x.getLastName(), x.getUserImage()))
                .collect(Collectors.toList());
    }

    @Override
    public UserSearchResponse findUserDataById(Long userId) {
        return null;
    }
}
