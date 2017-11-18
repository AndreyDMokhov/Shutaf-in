package com.shutafin.controller;

import com.shutafin.core.service.UserFilterService;
import com.shutafin.core.service.UserSearchService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.FiltersWeb;
import com.shutafin.model.web.user.UserSearchResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserSearchController {

    @Autowired
    private UserFilterService userFilterService;

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getMatchingUsers(@RequestParam(value = "name", required = false) String fullName, Long userId) {
        User user = userService.findUserById(userId);
        return userSearchService.userSearchByList(userFilterService.findFilteredUsers(user), fullName);
    }

    @RequestMapping(value = "/search/{user_id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserSearchResponse getUserById(@PathVariable("user_id") Long userToSearchId) {
        return userSearchService.findUserDataById(userToSearchId);
    }

    @RequestMapping(value = "/search/save/filters", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> saveUserFilters(Long userId, @RequestBody @Valid FiltersWeb filtersWeb) {
        User user = userService.findUserById(userId);
        userFilterService.saveUserFilters(user, filtersWeb);
        return userSearchService.userSearchByList(userFilterService.findFilteredUsers(user));
    }
}

