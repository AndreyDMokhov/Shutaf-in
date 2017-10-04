package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.AgeRangeRequest;
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
@RequestMapping("/users/search")
public class UserSearchController {

    @Autowired
    private UserFilterService userFilterService;

    @Autowired
    private UserSearchService userSearchService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getMatchingUsers(@RequestParam(value = "name", required = false) String fullName, @AuthenticatedUser User user) {
        return userSearchService.userSearchByList(userFilterService.findFilteredUsers(user), fullName);
    }

    @RequestMapping(value = "/save/city/filter", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserCityFilter(@AuthenticatedUser User user, @RequestParam List<Integer> cities) {
        userFilterService.saveUserFilterCity(user, cities);
    }

    @RequestMapping(value = "/save/gender/filter", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserGenderFilter(@AuthenticatedUser User user, @RequestParam Integer genderId) {
        userFilterService.saveUserFilterGender(user, genderId);
    }

    @RequestMapping(value = "/save/ageRange/filter", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserGenderFilter(@AuthenticatedUser User user, @RequestBody @Valid AgeRangeRequest ageRangeRequest) {
        userFilterService.saveUserFilterAgeRange(user, ageRangeRequest);
    }
}
