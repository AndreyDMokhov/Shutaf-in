package com.shutafin.controller;

import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserMatchService;
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
    private UserMatchService userMatchService;

    @Autowired
    private UserSearchService userSearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getMatchingUsers(@RequestParam(value = "name", required = false) String fullName,
                                                     @AuthenticatedUser Long authenticatedUserId) {
        return userMatchService.getMatchedUserSearchResponses(authenticatedUserId, fullName, null);
    }

    @RequestMapping(value = "/search/{user_id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserSearchResponse getUserById(@PathVariable("user_id") Long userId) {
        return userSearchService.findUserDataById(userId);
    }

    @RequestMapping(value = "/search/save/filters", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> saveUserFilters(@RequestParam(value = "name", required = false) String fullName,
                                                    @AuthenticatedUser Long authenticatedUserId,
                                                    @RequestBody @Valid FiltersWeb filtersWeb) {
        return userMatchService.getMatchedUserSearchResponses(authenticatedUserId, fullName, filtersWeb);
    }
}
