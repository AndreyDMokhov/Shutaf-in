package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.FiltersWeb;
import com.shutafin.model.web.user.UserSearchResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserFilterService;
import com.shutafin.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by evgeny on 10/3/2017.
 */
@RestController
@RequestMapping("/users")
public class UserSearchController {

    @Autowired
    private UserFilterService userFilterService;

    @Autowired
    private UserSearchService userSearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getMatchingUsers(@RequestParam(value = "name", required = false) String fullName, @AuthenticatedUser User user) {
        return userSearchService.userSearchByList(userFilterService.findFilteredUsers(user), fullName);
    }

    @RequestMapping(value = "/search/save/filters", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilters(@AuthenticatedUser User user, @RequestBody @Valid FiltersWeb filtersWeb) {
        userFilterService.saveUserFilters(user, filtersWeb);
    }
}
