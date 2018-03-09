package com.shutafin.controller;



import com.shutafin.core.service.UserFilterService;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.common.AgeRangeWebDTO;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/filters")
@Slf4j
public class UserFilterController {

    private final UserFilterService userFilterService;

    @Autowired
    public UserFilterController(UserFilterService userFilterService) {
        this.userFilterService = userFilterService;
    }

    @Deprecated
    @PostMapping(value = "/users/id/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Long> saveUserFiltersAndGetUserIds(@PathVariable("userId") Long userId,
                                                   @RequestBody @Valid AccountUserFilterRequest accountUserFilterRequest,
                                                   BindingResult result) {
        log.debug("/filters/users/id/{userId}");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return userFilterService.filterMatchedUserIds(userId, accountUserFilterRequest);
    }

    @Deprecated
    @PostMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getUsers(@RequestBody List<Long> usersId) {
        return userFilterService.getUsers(usersId);
    }

    @PostMapping(value = "filter/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getFilteredUsers(@PathVariable("userId") Long userId,
                                                     @RequestBody AccountUserFilterRequest accountUserFilterRequest) {
        return userFilterService.filterMatchedUsers(userId, accountUserFilterRequest);
    }

    @PostMapping(value = "/save/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> saveUserFiltersAndGetUsers(@PathVariable("userId") Long userId, @RequestBody @Valid AccountUserFilterRequest accountUserFilterRequest) {
       return userFilterService.saveUserFiltersAndGetUsers(userId, accountUserFilterRequest);
    }

    @Deprecated
    @PutMapping(value = "/city/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilterCity(@PathVariable("userId") Long userId, @RequestBody List<Integer> cities) {
        userFilterService.saveUserFilterCity(userId, cities);
    }

    @Deprecated
    @PutMapping(value = "/gender/{userId}/{genderId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilterGender(@PathVariable("userId") Long userId, @PathVariable("genderId") Integer genderId) {
        userFilterService.saveUserFilterGender(userId, genderId);
    }

    @Deprecated
    @PutMapping(value = "/age-range/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilterAgeRange(@PathVariable("userId") Long userId, @RequestBody @Valid AgeRangeWebDTO ageRangeWebDTO) {
        userFilterService.saveUserFilterAgeRange(userId, ageRangeWebDTO);
    }

    @Deprecated
    @GetMapping(value = "/get/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public FiltersWeb getUserFilters(@PathVariable("userId") Long userId) {
        return userFilterService.getUserFilters(userId);
    }

    @Deprecated
    @GetMapping(value = "/city/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Integer> getCitiesForFilter(@PathVariable("userId") Long userId) {
        return userFilterService.getCitiesForFilter(userId);
    }

    @Deprecated
    @GetMapping(value = "/gender/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Integer getGenderForFilter(@PathVariable("userId") Long userId) {
        return userFilterService.getGenderForFilter(userId);
    }


    @Deprecated
    @GetMapping(value = "/age-range/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AgeRangeWebDTO getAgeRangeForFilter(@PathVariable("userId") Long userId) {
        return userFilterService.getAgeRangeForFilter(userId);
    }
}
