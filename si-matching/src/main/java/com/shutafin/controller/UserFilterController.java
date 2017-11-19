package com.shutafin.controller;


import com.shutafin.model.DTO.AgeRangeWebDTO;
import com.shutafin.model.DTO.FiltersWeb;
import com.shutafin.service.UserFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/")
public class UserFilterController {

    @Autowired
    private UserFilterService userFilterService;

    @RequestMapping(value = "/filter/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Long> getFilteredUsers(@PathVariable("userId") Long userId) {
        return userFilterService.findFilteredUsers(userId);
    }

    @RequestMapping(value = "/save/filters/{userId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilters(@PathVariable("userId") Long userId, @RequestBody @Valid FiltersWeb filtersWeb) {
        userFilterService.saveUserFilters(userId, filtersWeb);
    }

    @RequestMapping(value = "/save/filter/city/{userId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilterCity(@PathVariable("userId") Long userId, @RequestBody List<Integer> cities) {
        userFilterService.saveUserFilterCity(userId, cities);
    }

    @RequestMapping(value = "/save/filter/gender/{userId}/{genderId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilterGender(@PathVariable("userId") Long userId, @PathVariable("genderId") Integer genderId) {
        userFilterService.saveUserFilterGender(userId, genderId);
    }

    @RequestMapping(value = "/save/filter/agerange/{userId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserFilterAgeRange(@PathVariable("userId") Long userId, @RequestBody @Valid AgeRangeWebDTO ageRangeWebDTO) {
        userFilterService.saveUserFilterAgeRange(userId, ageRangeWebDTO);
    }

    @RequestMapping(value = "get/filter/city/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Integer> getCitiesForFilter(@PathVariable("userId") Long userId) {
        return userFilterService.getCitiesForFilter(userId);
    }
    @RequestMapping(value = "get/filter/gender/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Integer getGenderForFilter(@PathVariable("userId") Long userId) {
        return userFilterService.getGenderForFilter(userId);
    }

    @RequestMapping(value = "get/filter/agerange/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public AgeRangeWebDTO getAgeRangeForFilter(@PathVariable("userId") Long userId) {
        return userFilterService.getAgeRangeForFilter(userId);
    }
}
