package com.shutafin.controller;



import com.shutafin.core.service.UserFilterService;
import com.shutafin.model.User;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.model.web.user.FiltersWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/filters")
public class UserFilterController {

    private final UserFilterService userFilterService;

    @Autowired
    public UserFilterController(UserFilterService userFilterService) {
        this.userFilterService = userFilterService;
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getFilteredUsers(@PathVariable("userId") Long userId, @RequestBody List<Long> matchedUsersList) {
        return userFilterService.filterMatchedUsers(userId, matchedUsersList);
    }

    @PostMapping(value = "/filters/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilters(@PathVariable("userId") Long userId, @RequestBody @Valid FiltersWeb filtersWeb) {
        userFilterService.saveUserFilters(userId, filtersWeb);
    }

    @PostMapping(value = "/city/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilterCity(@PathVariable("userId") Long userId, @RequestBody List<Integer> cities) {
        userFilterService.saveUserFilterCity(userId, cities);
    }

    @PostMapping(value = "/gender/{userId}/{genderId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilterGender(@PathVariable("userId") Long userId, @PathVariable("genderId") Integer genderId) {
        userFilterService.saveUserFilterGender(userId, genderId);
    }

    @PostMapping(value = "/age-range/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilterAgeRange(@PathVariable("userId") Long userId, @RequestBody @Valid AgeRangeWebDTO ageRangeWebDTO) {
        userFilterService.saveUserFilterAgeRange(userId, ageRangeWebDTO);
    }

    @GetMapping(value = "/city/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Integer> getCitiesForFilter(@PathVariable("userId") Long userId) {
        return userFilterService.getCitiesForFilter(userId);
    }
    @GetMapping(value = "/gender/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Integer getGenderForFilter(@PathVariable("userId") Long userId) {
        return userFilterService.getGenderForFilter(userId);
    }

    @GetMapping(value = "/age-range/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AgeRangeWebDTO getAgeRangeForFilter(@PathVariable("userId") Long userId) {
        return userFilterService.getAgeRangeForFilter(userId);
    }
}
